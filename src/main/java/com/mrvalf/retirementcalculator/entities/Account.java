package com.mrvalf.retirementcalculator.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private double balance;
    private double contribution;

    public Account deepClone() {
        return new Account(getBalance(), getContribution());
    }
}
