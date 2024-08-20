package com.mrvalf.retirementcalculator.taxes;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public abstract class ATaxBracket
{
    public int StandardDeduction;
    public ArrayList<TaxBracket> TaxBrackets;

    protected void PrepareTaxBracket()
    {
        double nextLowerLimit = 0;
        double taxed = 0;
        var taxBrackets = getTaxBrackets().reversed();

        for (TaxBracket taxBracket : taxBrackets) {
            taxBracket.LowerLimit = nextLowerLimit;
            taxBracket.Taxed = taxed;

            nextLowerLimit = taxBracket.UpperLimit + 1.0;

            taxed += (taxBracket.UpperLimit - taxBracket.LowerLimit) * taxBracket.Rate;
        }
        setTaxBrackets((ArrayList<TaxBracket>)taxBrackets.reversed());
    }

    public TaxInfo CalculateTax(double income, double inflationCoefficient)
    {
        if (income < StandardDeduction)
        {
            return new TaxInfo(0, 0);
        }

        double taxableIncome = income - StandardDeduction;

        for (TaxBracket taxBracket : getTaxBrackets())
        {
            var lowerLimit = taxBracket.LowerLimit * inflationCoefficient;
            if (taxableIncome > lowerLimit)
            {
                return new TaxInfo (Math.round((taxableIncome - lowerLimit) * taxBracket.Rate + taxBracket.Taxed), taxBracket.Rate);
            }
        }

        return new TaxInfo(0, 0);
    }

    public TaxInfo CalculateBeforeTax(double incomeAfterTax)
    {
        double incomeBeforeTax = incomeAfterTax - StandardDeduction;

        for (TaxBracket taxBracket : getTaxBrackets())
        {
            double limit = taxBracket.LowerLimit;

            if (incomeBeforeTax > limit)
            {
                double overhead = incomeBeforeTax - limit + taxBracket.Taxed;

                double taxedOverhead = overhead / (1 - taxBracket.Rate);

                return new TaxInfo (Math.round(taxedOverhead + limit - incomeBeforeTax), taxBracket.Rate);
            }
        }

        return new TaxInfo(0, 0);
    }
}
