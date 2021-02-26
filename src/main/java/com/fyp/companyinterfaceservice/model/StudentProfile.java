package com.fyp.companyinterfaceservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentProfile {
    private Long profileId;
    private String bio;
    private Course course;
    private Set<Skill> externalSkills;
    private List<Project> projects;
    private List<Experience> experiences;
    private double averageGrade;
    private String startCourse;
    private String endCourse;
}
