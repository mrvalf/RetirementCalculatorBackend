package com.mrvalf.retirementcalculator.controllers;

import com.mrvalf.retirementcalculator.entities.*;
import com.mrvalf.retirementcalculator.repositories.RetirementParametersRepository;
import com.mrvalf.retirementcalculator.taxes.TaxBracketFederal;
import com.mrvalf.retirementcalculator.taxes.TaxBracketVirginia;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController()
@RequestMapping("/retirementTool")
public class RetirementToolsController {
    Random random;

    final TrialComparator trialComparator;
    final RetirementParametersRepository retirementParametersRepository;
    final TaxBracketVirginia taxBracketVirginia;
    final TaxBracketFederal taxBracketFederal;

    public RetirementToolsController(TrialComparator trialComparator,
        RetirementParametersRepository retirementParametersRepository,
        TaxBracketVirginia taxBracketVirginia,
        TaxBracketFederal taxBracketFederal) {
        this.taxBracketVirginia = taxBracketVirginia;
        this.taxBracketFederal = taxBracketFederal;
        random = new Random(System.currentTimeMillis());
        this.trialComparator = trialComparator;
        this.retirementParametersRepository = retirementParametersRepository;
    }

    @PostMapping("/calculate")
    public AccumulatedData calculate(@RequestBody() RetirementParameters initialRetirementParameters, Principal principal){
        List<RetirementParametersEntity> databaseSavedRetirementParameters =
            retirementParametersRepository.findAllByUsername(
                principal.getName());

        if (databaseSavedRetirementParameters.isEmpty()){
            RetirementParametersEntity newRetirementParametersEntity = convertRetirementParameters(null, principal.getName(), initialRetirementParameters);
            retirementParametersRepository.save(newRetirementParametersEntity);
        } else {
            RetirementParametersEntity newRetirementParametersEntity = convertRetirementParameters(databaseSavedRetirementParameters.getFirst().getRetirementId(), principal.getName(), initialRetirementParameters);
            retirementParametersRepository.save(newRetirementParametersEntity);
        }

        Accounts retirementAccounts = calculateFutureIncome(initialRetirementParameters);


        return new AccumulatedData(initialRetirementParameters,
            retirementAccounts, monteCarlo(initialRetirementParameters, retirementAccounts));
    }

    @GetMapping("/initialRetirementParameters")
    public RetirementParameters initialRetirementParameters(Principal principal){

        List<RetirementParametersEntity> databaseSavedRetirementParameters =
            retirementParametersRepository.findAllByUsername(
                principal.getName());

        if (databaseSavedRetirementParameters.isEmpty()){
            return null;
        } else {
            return convertRetirementParameters(databaseSavedRetirementParameters.getFirst());
        }
    }



    private Accounts calculateFutureIncome(RetirementParameters retirementParameters){
        Accounts retirementAccounts = retirementParameters.getAccounts().deepClone();

        int yearsToRetirement = retirementParameters.getRetirementAge() + retirementParameters.getBirthYear() - retirementParameters.getCurrentYear();

        for(int i = 0; i < yearsToRetirement; i++){
            retirementAccounts.getRothIra().setBalance((1 + retirementParameters.getMarketReturn()) * (retirementAccounts.getRothIra().getBalance() + retirementAccounts.getRothIra().getContribution()));
            retirementAccounts.getTraditionalIra().setBalance((1 + retirementParameters.getMarketReturn()) * (retirementAccounts.getTraditionalIra().getBalance() + retirementAccounts.getTraditionalIra().getContribution()));
            retirementAccounts.getRoth401k().setBalance((1 + retirementParameters.getMarketReturn()) * (retirementAccounts.getRoth401k().getBalance() + retirementAccounts.getRoth401k().getContribution()));
            retirementAccounts.getTraditional401k().setBalance((1 + retirementParameters.getMarketReturn()) * (retirementAccounts.getTraditional401k().getBalance() + retirementAccounts.getTraditional401k().getContribution()));
            retirementAccounts.getPersonalInvestment().setBalance((1 + retirementParameters.getMarketReturn()) * (retirementAccounts.getPersonalInvestment().getBalance() + retirementAccounts.getPersonalInvestment().getContribution()));
        }

        return retirementAccounts;
    }

    private List<Trial> monteCarlo(RetirementParameters retirementParameters, Accounts baselineRetirementAccounts) {
        List<Trial> trials = new ArrayList<>(retirementParameters.getNumberOfTrails() + 1);

        for(int i = 0; i < retirementParameters.getNumberOfTrails(); i++){
            trials.add(singleMonteCarloTrial(retirementParameters, baselineRetirementAccounts));
        }
        trials.sort(new TrialComparator());
        return trials;
    }

    private Trial singleMonteCarloTrial(RetirementParameters retirementParameters, Accounts baselineRetirementAccounts) {
        int retirementYear = retirementParameters.getRetirementAge() + retirementParameters.getBirthYear();
        ArrayList<SingleYearData> yearsOfData = new ArrayList<>(retirementParameters.getNumberOfYearsInRetirement() + 1);
        yearsOfData.add(new SingleYearData(retirementYear, retirementParameters.getRetirementAge(),0, baselineRetirementAccounts.deepClone()));

        double taxableIncome = 0;
        for(int i = 1; i <= retirementParameters.getNumberOfYearsInRetirement(); i++){
            int age = retirementParameters.getRetirementAge() + i;

            Accounts currentYear = yearsOfData.getLast().getAccounts().deepClone();
            double marketReturn = generateRandomMarketReturn(
                retirementParameters.getMarketReturn(),
                retirementParameters.getMarketStandardDeviation());

            taxableIncome = currentYear.updateAccounts(marketReturn, retirementParameters.getExpenseRateInRetirement() + taxableIncome, age);
            taxableIncome = taxBracketVirginia.CalculateBeforeTax(taxableIncome).Tax + taxBracketFederal.CalculateBeforeTax(taxableIncome).Tax;

            yearsOfData.add(new SingleYearData(retirementYear + i, age, marketReturn, currentYear));

            if (currentYear.getTotalNetWorth() <= 0) {
                break;
            }
        }
        return new Trial(yearsOfData);
    }

    private double generateRandomMarketReturn(double marketReturn, double standardDeviation) {
        return marketReturn + (standardDeviation * random.nextGaussian());
    }

    private RetirementParametersEntity convertRetirementParameters(Integer retirementId, String username, RetirementParameters initialRetirementParameters) {
        return new RetirementParametersEntity(
            retirementId,
            username,
            initialRetirementParameters.getCurrentYear(),
            initialRetirementParameters.getBirthYear(),
            initialRetirementParameters.getRetirementAge(),
            initialRetirementParameters.getMarketReturn(),
            initialRetirementParameters.getMarketStandardDeviation(),
            initialRetirementParameters.getExpenseRateInRetirement(),
            initialRetirementParameters.getNumberOfTrails(),
            initialRetirementParameters.getNumberOfYearsInRetirement(),
            initialRetirementParameters.getAccounts().getRothIra().getBalance(),
            initialRetirementParameters.getAccounts().getRothIra().getContribution(),
            initialRetirementParameters.getAccounts().getTraditionalIra().getBalance(),
            initialRetirementParameters.getAccounts().getTraditionalIra().getContribution(),
            initialRetirementParameters.getAccounts().getRoth401k().getBalance(),
            initialRetirementParameters.getAccounts().getRoth401k().getContribution(),
            initialRetirementParameters.getAccounts().getTraditional401k().getBalance(),
            initialRetirementParameters.getAccounts().getTraditional401k().getContribution(),
            initialRetirementParameters.getAccounts().getPersonalInvestment().getBalance(),
            initialRetirementParameters.getAccounts().getPersonalInvestment().getContribution());
    }

    private RetirementParameters convertRetirementParameters(RetirementParametersEntity retirementParameters) {
        return new RetirementParameters(
            retirementParameters.getCurrentYear(),
            retirementParameters.getBirthYear(),
            retirementParameters.getRetirementAge(),
            retirementParameters.getMarketReturn(),
            retirementParameters.getMarketStandardDeviation(),
            retirementParameters.getExpenseRateInRetirement(),
            retirementParameters.getNumberOfTrails(),
            retirementParameters.getNumberOfYearsInRetirement(),
            new Accounts(
                0,
                new Account(
                    retirementParameters.getRothIraBalance(),
                    retirementParameters.getRothIraContribution()
                ),
                new Account(
                    retirementParameters.getTraditionalIraBalance(),
                    retirementParameters.getTraditionalIraContribution()
                ),
                new Account(
                    retirementParameters.getRoth401kBalance(),
                    retirementParameters.getRoth401kContribution()
                ),
                new Account(
                    retirementParameters.getTraditional401kBalance(),
                    retirementParameters.getTraditional401kContribution()
                ),
                new Account(
                    retirementParameters.getPersonalInvestmentBalance(),
                    retirementParameters.getPersonalInvestmentContribution()
                )
            )
        );
    }
}
