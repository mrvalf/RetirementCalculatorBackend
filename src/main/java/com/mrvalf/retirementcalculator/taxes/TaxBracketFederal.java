package com.mrvalf.retirementcalculator.taxes;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class TaxBracketFederal extends ATaxBracket {
    final int StandardDeduction = 27700;

    final ArrayList<TaxBracket> TaxBrackets = new ArrayList<TaxBracket>();

    public TaxBracketFederal() {
        TaxBrackets.add(new TaxBracket(0.37, 100000000));
        TaxBrackets.add(new TaxBracket(0.35, 693750));
        TaxBrackets.add(new TaxBracket(0.32, 462500));
        TaxBrackets.add(new TaxBracket(0.24, 364200));
        TaxBrackets.add(new TaxBracket(0.22, 190750));
        TaxBrackets.add(new TaxBracket(0.12, 89450));
        TaxBrackets.add(new TaxBracket(0.10, 22000));
        PrepareTaxBracket();

    }
}
