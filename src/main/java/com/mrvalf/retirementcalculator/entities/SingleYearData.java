package com.mrvalf.retirementcalculator.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleYearData {
    private int year;
    private int age;
    private double marketReturn;
    private Accounts accounts;
}
