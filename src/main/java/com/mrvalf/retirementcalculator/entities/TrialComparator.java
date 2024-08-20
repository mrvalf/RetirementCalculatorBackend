package com.mrvalf.retirementcalculator.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;

@Configuration
public class TrialComparator implements Comparator<Trial> {
    @Bean
    public TrialComparator TrialComparator() {
        return new TrialComparator();
    }

    @Override
    public int compare(Trial trial1, Trial trial2) {
        int sizeComparison = trial1.getYearsOfData().size() - trial2.getYearsOfData().size();
        if (sizeComparison != 0) {
            return sizeComparison;
        }
        return Double.compare(trial1.getYearsOfData().getLast().getAccounts().getTotalNetWorth(), trial2.getYearsOfData().getLast().getAccounts().getTotalNetWorth());
    }
}
