package com.mrvalf.retirementcalculator.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "retirementParameters", schema = "public")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RetirementParametersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "retirementid")
    private Integer retirementId;
    @Basic @Column(name = "username") private String username;
    @Basic @Column(name = "currentYear") private Integer currentYear;
    @Basic @Column(name = "birthYear") private Integer birthYear;
    @Basic @Column(name = "retirementAge") private Integer retirementAge;
    @Basic @Column(name = "marketReturn") private Double marketReturn;
    @Basic @Column(name = "marketStandardDeviation") private Double marketStandardDeviation;
    @Basic @Column(name = "expenseRateInRetirement") private Double expenseRateInRetirement;
    @Basic @Column(name = "numberOfTrails") private Integer numberOfTrails;
    @Basic @Column(name = "numberOfYearsInRetirement") private Integer numberOfYearsInRetirement;
    @Basic @Column(name = "rothIraBalance") private Double rothIraBalance;
    @Basic @Column(name = "rothIraContribution") private Double rothIraContribution;
    @Basic @Column(name = "traditionalIraBalance") private Double traditionalIraBalance;
    @Basic @Column(name = "traditionalIraContribution") private Double traditionalIraContribution;
    @Basic @Column(name = "roth401kBalance") private Double roth401kBalance;
    @Basic @Column(name = "roth401kContribution") private Double roth401kContribution;
    @Basic @Column(name = "traditional401kBalance") private Double traditional401kBalance;
    @Basic @Column(name = "traditional401kContribution") private Double traditional401kContribution;
    @Basic @Column(name = "personalInvestmentBalance") private Double personalInvestmentBalance;
    @Basic @Column(name = "personalInvestmentContribution") private Double personalInvestmentContribution;
}
