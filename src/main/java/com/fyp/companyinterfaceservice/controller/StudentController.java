package com.fyp.companyinterfaceservice.controller;

import com.fyp.companyinterfaceservice.exceptions.CompanyExceptionHandler;
import com.fyp.companyinterfaceservice.model.Emails;
import com.fyp.companyinterfaceservice.model.Stats;
import com.fyp.companyinterfaceservice.model.Student;
import com.fyp.companyinterfaceservice.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController extends CompanyExceptionHandler {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/getStudent")
    public Student getStudent(@RequestParam String email) {
        return studentService.getStudent(email);
    }

    @PostMapping("/getStats")
    public Stats getStats(@RequestBody Emails emails) {
        return studentService.getStats(emails);
    }

    public Stats countStats(List<Student> students) {
        return studentService.countStats(students);
    }
}
