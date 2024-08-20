package com.mrvalf.retirementcalculator.taxes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxBracket
{
    public double Rate;
    public double LowerLimit;
    public double UpperLimit;
    public double Taxed;

    public TaxBracket(double rate, double upperLimit) {
        Rate = rate;
        UpperLimit = upperLimit;
    }
}