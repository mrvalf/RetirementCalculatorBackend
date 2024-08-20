package com.mrvalf.retirementcalculator.taxes;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class TaxBracketVirginia extends ATaxBracket {
    final int StandardDeduction = 17880;

    final ArrayList<TaxBracket> TaxBrackets = new ArrayList<TaxBracket>();

    public TaxBracketVirginia () {
        TaxBrackets.add(new TaxBracket(0.0575, 100000000));
        TaxBrackets.add(new TaxBracket(0.05, 17000));
        TaxBrackets.add(new TaxBracket(0.03, 5000));
        TaxBrackets.add(new TaxBracket(0.02, 3000));
        PrepareTaxBracket();

    }
}
