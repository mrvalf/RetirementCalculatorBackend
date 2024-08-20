package com.mrvalf.retirementcalculator.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetirementParameters {
    private int currentYear;
    private int birthYear;
    private int retirementAge;
    private double marketReturn;
    private double marketStandardDeviation;
    private double expenseRateInRetirement;
    private int numberOfTrails;
    private int numberOfYearsInRetirement;
    private Accounts accounts;
}

