package com.fyp.companyinterfaceservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stats {
    private int male;
    private int female;
    private int ageTier1;
    private int ageTier2;
    private int ageTier3;
    private int ageTier4;
    private int ageTier5;
    private int ageTier6;
    private int ageTier7;
    private int white;
    private int bAA;
    private int aIAN;
    private int asian;
    private int nHPI;
    private int mR;
    private Map<String, Integer> courses;
}
