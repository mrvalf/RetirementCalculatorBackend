package com.mrvalf.retirementcalculator.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccumulatedData {
    private RetirementParameters initialRetirementParameters;
    private Accounts baselineRetirementAccounts;
    private List<Trial> Trials;
}

