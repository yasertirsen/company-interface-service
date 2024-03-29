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
    private String title;
    private String description;
    private String location;
    private String date;
    private double salary;
    private String url;
    private int clicks;
    private boolean priority;
    private boolean archive;
    private User company;
    private Set<Skill> requirements;
}
