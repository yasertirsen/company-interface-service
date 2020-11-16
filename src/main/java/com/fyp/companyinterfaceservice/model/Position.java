package com.fyp.companyinterfaceservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    private long positionId;
    private String positionName;
    private String positionDescription;
    private double positionSalary;
    private String positionUrl;
    private int clicks;
    private User user;
    private Set<Skill> requirements;
}
