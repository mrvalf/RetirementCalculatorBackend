package com.mrvalf.retirementcalculator.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Accounts {
    private double totalNetWorth;
    private Account rothIra;
    private Account traditionalIra;
    private Account roth401k;
    private Account traditional401k;
    private Account personalInvestment;

    public double getTotalNetWorth() {
        return getRothIra().getBalance() + getTraditionalIra().getBalance() + getRoth401k().getBalance() + getTraditional401k().getBalance() + getPersonalInvestment().getBalance();
    }

    public double updateAccounts(double marketReturn, double retirementExpenseRate, int age){
        resetContributions();

        if (age < 60) {
            double currentExpense = Math.min(getPersonalInvestment().getBalance(), retirementExpenseRate);
            getPersonalInvestment().setBalance(getPersonalInvestment().getBalance() - currentExpense);
            getPersonalInvestment().setContribution(-currentExpense);
            retirementExpenseRate -= currentExpense;

            if (retirementExpenseRate > 0) {
                currentExpense = Math.min(getTraditionalIra().getBalance() * CalculationHelper.EARLY_WITHDRAW_PENALTY, retirementExpenseRate);
                getTraditionalIra().setBalance(getTraditionalIra().getBalance() - (currentExpense / CalculationHelper.EARLY_WITHDRAW_PENALTY));
                getTraditionalIra().setContribution(-(currentExpense / CalculationHelper.EARLY_WITHDRAW_PENALTY));
                retirementExpenseRate -= currentExpense;

                if (retirementExpenseRate > 0) {
                    currentExpense = Math.min(getTraditional401k().getBalance() * CalculationHelper.EARLY_WITHDRAW_PENALTY, retirementExpenseRate);
                    getTraditional401k().setBalance(getTraditional401k().getBalance() - (currentExpense / CalculationHelper.EARLY_WITHDRAW_PENALTY));
                    getTraditional401k().setContribution(-(currentExpense / CalculationHelper.EARLY_WITHDRAW_PENALTY));
                    retirementExpenseRate -= currentExpense;

                    if (retirementExpenseRate > 0) {
                        currentExpense = Math.min(getRothIra().getBalance() * CalculationHelper.EARLY_WITHDRAW_PENALTY, retirementExpenseRate);
                        getRothIra().setBalance(getRothIra().getBalance() - (currentExpense / CalculationHelper.EARLY_WITHDRAW_PENALTY));
                        getRothIra().setContribution(-(currentExpense / CalculationHelper.EARLY_WITHDRAW_PENALTY));
                        retirementExpenseRate -= currentExpense;

                        if (retirementExpenseRate > 0) {
                            currentExpense = Math.min(getRoth401k().getBalance() * CalculationHelper.EARLY_WITHDRAW_PENALTY, retirementExpenseRate);
                            getRoth401k().setBalance(getRoth401k().getBalance() - (currentExpense / CalculationHelper.EARLY_WITHDRAW_PENALTY));
                            getRoth401k().setContribution(-(currentExpense / CalculationHelper.EARLY_WITHDRAW_PENALTY));
                            retirementExpenseRate -= currentExpense;

                            if (retirementExpenseRate > 0) {
                                emptyAccounts();
                                return 0;
                            }
                        }
                    }
                }
            }
        }
        else if (age >= 75) {
            double currentExpense = Math.min(getTraditionalIra().getBalance(), retirementExpenseRate);
            double requiredExpense = Math.max(currentExpense, CalculationHelper.RequiredDistribution(age, getTraditionalIra().getBalance()));
            getTraditionalIra().setBalance(getTraditionalIra().getBalance() - requiredExpense);
            getTraditionalIra().setContribution(-requiredExpense);
            retirementExpenseRate -= requiredExpense;

            currentExpense = Math.min(getTraditional401k().getBalance(), retirementExpenseRate);
            requiredExpense = Math.max(currentExpense, CalculationHelper.RequiredDistribution(age, getTraditional401k().getBalance()));
            getTraditional401k().setBalance(getTraditional401k().getBalance() - requiredExpense);
            getTraditional401k().setContribution(-requiredExpense);
            retirementExpenseRate -= requiredExpense;

            if (retirementExpenseRate > 0) {
                currentExpense = Math.min(getPersonalInvestment().getBalance(), retirementExpenseRate);
                getPersonalInvestment().setBalance(getPersonalInvestment().getBalance() - currentExpense);
                getPersonalInvestment().setContribution(-currentExpense);
                retirementExpenseRate -= currentExpense;

                if (retirementExpenseRate > 0) {
                    currentExpense = Math.min(getRothIra().getBalance(), retirementExpenseRate);
                    getRothIra().setBalance(getRothIra().getBalance() - currentExpense);
                    getRothIra().setContribution(-currentExpense);
                    retirementExpenseRate -= currentExpense;

                    if (retirementExpenseRate > 0) {
                        currentExpense = Math.min(getRoth401k().getBalance(), retirementExpenseRate);
                        getRoth401k().setBalance(getRoth401k().getBalance() - currentExpense);
                        getRoth401k().setContribution(-currentExpense);
                        retirementExpenseRate -= currentExpense;

                        if (retirementExpenseRate > 0) {
                            emptyAccounts();
                            return 0;
                        }
                    }
                }
            }
            else if (retirementExpenseRate < 0) {
                getPersonalInvestment().setBalance(getPersonalInvestment().getBalance() - retirementExpenseRate);
                getPersonalInvestment().setContribution(-retirementExpenseRate);
            }
        }
        else {
            double currentExpense = Math.min(getTraditionalIra().getBalance(), retirementExpenseRate);
            getTraditionalIra().setBalance(getTraditionalIra().getBalance() - currentExpense);
            getTraditionalIra().setContribution(-currentExpense);
            retirementExpenseRate -= currentExpense;

            if (retirementExpenseRate > 0) {
                currentExpense = Math.min(getTraditional401k().getBalance(), retirementExpenseRate);
                getTraditional401k().setBalance(getTraditional401k().getBalance() - currentExpense);
                getTraditional401k().setContribution(-currentExpense);
                retirementExpenseRate -= currentExpense;

                if (retirementExpenseRate > 0) {
                    currentExpense = Math.min(getPersonalInvestment().getBalance(), retirementExpenseRate);
                    getPersonalInvestment().setBalance(getPersonalInvestment().getBalance() - currentExpense);
                    getPersonalInvestment().setContribution(-currentExpense);
                    retirementExpenseRate -= currentExpense;

                    if (retirementExpenseRate > 0) {
                        currentExpense = Math.min(getRothIra().getBalance(), retirementExpenseRate);
                        getRothIra().setBalance(getRothIra().getBalance() - currentExpense);
                        getRothIra().setContribution(-currentExpense);
                        retirementExpenseRate -= currentExpense;

                        if (retirementExpenseRate > 0) {
                            currentExpense = Math.min(getRoth401k().getBalance(), retirementExpenseRate);
                            getRoth401k().setBalance(getRoth401k().getBalance() - currentExpense);
                            getRoth401k().setContribution(-currentExpense);
                            retirementExpenseRate -= currentExpense;

                            if (retirementExpenseRate > 0) {
                                emptyAccounts();
                                return 0;
                            }
                        }
                    }
                }
            }
        }

        updateAccountsToMarketReturn(marketReturn);
        return -getTraditional401k().getContribution() - getTraditionalIra().getContribution() + Math.abs(getPersonalInvestment().getContribution());
    }

    private void updateAccountsToMarketReturn(double marketReturn){
        getRothIra().setBalance(getRothIra().getBalance() * (1 + marketReturn));
        getTraditionalIra().setBalance(getTraditionalIra().getBalance() * (1 + marketReturn));
        getRoth401k().setBalance(getRoth401k().getBalance() * (1 + marketReturn));
        getTraditional401k().setBalance(getTraditional401k().getBalance() * (1 + marketReturn));
        getPersonalInvestment().setBalance(getPersonalInvestment().getBalance() * (1 + marketReturn));
    }

    private void emptyAccounts(){
        getRothIra().setBalance(0);
        getTraditionalIra().setBalance(0);
        getRoth401k().setBalance(0);
        getTraditional401k().setBalance(0);
        getPersonalInvestment().setBalance(0);
    }

    private void resetContributions(){
        getRothIra().setContribution(0);
        getTraditionalIra().setContribution(0);
        getRoth401k().setContribution(0);
        getTraditional401k().setContribution(0);
        getPersonalInvestment().setContribution(0);
    }

    public Accounts deepClone() {
        return new Accounts(getTotalNetWorth(), getRothIra().deepClone(), getTraditionalIra().deepClone(), getRoth401k().deepClone(), getTraditional401k().deepClone(), getPersonalInvestment().deepClone());
    }
}


