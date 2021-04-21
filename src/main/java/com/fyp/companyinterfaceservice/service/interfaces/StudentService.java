package com.fyp.companyinterfaceservice.service.interfaces;

import com.fyp.companyinterfaceservice.model.Emails;
import com.fyp.companyinterfaceservice.model.Image;
import com.fyp.companyinterfaceservice.model.Stats;
import com.fyp.companyinterfaceservice.model.Student;

import java.util.List;

public interface StudentService {

    Student getStudent(String email);

    Stats getStats(Emails emails);

    Stats countStats(List<Student> students);

    Image getImage(Long userId);
}
