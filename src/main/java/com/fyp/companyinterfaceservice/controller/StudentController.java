package com.fyp.companyinterfaceservice.controller;

import com.fyp.companyinterfaceservice.client.ProgradClient;
import com.fyp.companyinterfaceservice.model.Emails;
import com.fyp.companyinterfaceservice.model.Stats;
import com.fyp.companyinterfaceservice.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.fyp.companyinterfaceservice.constant.Constants.*;

@RestController
public class StudentController {

    private final ProgradClient client;

    @Autowired
    public StudentController(ProgradClient client) {
        this.client = client;
    }

    @GetMapping("/getStudent")
    public Student getStudent(@RequestParam String email) {
        return client.getStudent(SECRET_TOKEN, email);
    }

    @PostMapping("/getStats")
    public Stats getStats(@RequestBody Emails emails) {
        List<Student> students = new ArrayList<>();
        for(String email: emails.getEmails()) {
            students.add(client.getStudent(SECRET_TOKEN, email));
        }
        return countStats(students);
    }

    private Stats countStats(List<Student> students) {
        Stats stats = new Stats();
        ArrayList<String> ages = new ArrayList<>();
        ArrayList<String> genders = new ArrayList<>();
        ArrayList<String> races = new ArrayList<>();
        Map<String, Integer> courses = new HashMap<>();
        String course;
        for(Student student : students) {
            if(student.getProfile().getAge() != null)
                ages.add(student.getProfile().getAge());
            if(student.getProfile().getGender() != null)
                genders.add(student.getProfile().getGender());
            if(student.getProfile().getRace() != null)
                races.add(student.getProfile().getRace());
            if(student.getProfile().getCourse() != null) {
                course = student.getProfile().getCourse().getName() + " - " +
                        student.getProfile().getCourse().getUniversity();
                if(!courses.containsKey(course))
                    courses.put(course, 1);
                else
                    courses.put(course, courses.get(course)+1);
            }
        }

        stats.setCourses(courses);

        stats.setMale(Collections.frequency(genders, M));
        stats.setFemale(Collections.frequency(genders, F));

        stats.setAgeTier1(Collections.frequency(ages, TIER1));
        stats.setAgeTier2(Collections.frequency(ages, TIER2));
        stats.setAgeTier3(Collections.frequency(ages, TIER3));
        stats.setAgeTier4(Collections.frequency(ages, TIER4));
        stats.setAgeTier5(Collections.frequency(ages, TIER5));
        stats.setAgeTier6(Collections.frequency(ages, TIER6));
        stats.setAgeTier7(Collections.frequency(ages, TIER7));

        stats.setWhite(Collections.frequency(races, WHITE));
        stats.setBAA(Collections.frequency(races, BLACK_OR_AFRICAN_AMERICAN));
        stats.setAIAN(Collections.frequency(races, AMERICAN_INDIAN_OR_ALASKAN_NATIVE));
        stats.setAsian(Collections.frequency(races, ASIAN));
        stats.setNHPI(Collections.frequency(races, NATIVE_HAWAIIAN_OR_OTHER_PACIFIC_ISLANDER));
        stats.setMR(Collections.frequency(races, FROM_MULTIPLE_RACES));

        return stats;
    }
}
