package com.fyp.companyinterfaceservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stats {
    private double male;
    private double female;
    private double ageTier1;
    private double ageTier2;
    private double ageTier3;
    private double ageTier4;
    private double ageTier5;
    private double ageTier6;
    private double ageTier7;
    private double white;
    private double bAA;
    private double aIAN;
    private double asian;
    private double nHPI;
    private double mR;
    private Map<String, Double> courses;
}
