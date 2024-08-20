package com.mrvalf.retirementcalculator.entities;

public class CalculationHelper {
    public static final double EARLY_WITHDRAW_PENALTY = 0.9;
    private static final double[] RMD_DIVIDERS = new double[]{
        27.4,
        26.5,
        25.5,
        24.6,
        23.7,
        22.9,
        22.0,
        21.1,
        20.2,
        19.4,
        18.5,
        17.7,
        16.8,
        16.0,
        15.2,
        14.4,
        13.7,
        12.9,
        12.2,
        11.5,
        10.8,
        10.1,
        9.5,
        8.9,
        8.4,
        7.8,
        7.3,
        6.8,
        6.4
        };

    public static double RequiredDistribution(int age, double balance) {
        if (age < 75) {
            return 0;
        }
        if (age > 103) {
            return balance / RMD_DIVIDERS[RMD_DIVIDERS.length - 1];
        }
        return balance / RMD_DIVIDERS[age - 75];
    }
}
