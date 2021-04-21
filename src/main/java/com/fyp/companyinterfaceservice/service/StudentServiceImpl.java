package com.fyp.companyinterfaceservice.service;

import com.fyp.companyinterfaceservice.client.ProgradClient;
import com.fyp.companyinterfaceservice.model.Emails;
import com.fyp.companyinterfaceservice.model.Image;
import com.fyp.companyinterfaceservice.model.Stats;
import com.fyp.companyinterfaceservice.model.Student;
import com.fyp.companyinterfaceservice.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.fyp.companyinterfaceservice.constant.Constants.*;

@Service
public class StudentServiceImpl implements StudentService {

    private final ProgradClient client;
    @Value("${token.secret}")
    private String secretToken;

    public StudentServiceImpl(ProgradClient client) {
        this.client = client;
    }

    @Override
    public Student getStudent(String email) {
        return client.getStudent(secretToken, email);
    }

    @Override
    public Stats getStats(Emails emails) {
        List<Student> students = new ArrayList<>();
        for(String email: emails.getEmails()) {
            students.add(client.getStudent(secretToken, email));
        }
        return countStats(students);
    }

    @Override
    public Stats countStats(List<Student> students) {
        Stats stats = new Stats();
        ArrayList<String> ages = new ArrayList<>();
        ArrayList<String> genders = new ArrayList<>();
        ArrayList<String> races = new ArrayList<>();
        Map<String, Double> courses = new HashMap<>();
        double total = 0;
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
                    courses.put(course, 1.0);
                else
                    courses.put(course, courses.get(course)+1);
                total++;
            }
        }

        for (Map.Entry<String, Double> entry : courses.entrySet()) {
            courses.put(entry.getKey(), (entry.getValue()/total) * 100);
        }

        stats.setCourses(courses);

        stats.setMale(((double) Collections.frequency((genders), M) / (double) genders.size()) * 100);
        stats.setFemale(((double) Collections.frequency((genders), F) / (double) genders.size()) * 100);

        stats.setAgeTier1(((double) Collections.frequency((ages), TIER1) / (double) ages.size()) * 100);
        stats.setAgeTier2(((double) Collections.frequency((ages), TIER2) / (double) ages.size()) * 100);
        stats.setAgeTier3(((double) Collections.frequency((ages), TIER3) / (double) ages.size()) * 100);
        stats.setAgeTier4(((double) Collections.frequency((ages), TIER4) / (double) ages.size()) * 100);
        stats.setAgeTier5(((double) Collections.frequency((ages), TIER5) / (double) ages.size()) * 100);
        stats.setAgeTier6(((double) Collections.frequency((ages), TIER6) / (double) ages.size()) * 100);
        stats.setAgeTier7(((double) Collections.frequency((ages), TIER7) / (double) ages.size()) * 100);

        stats.setWhite(((double) Collections.frequency((races), WHITE) / (double) races.size()) * 100);
        stats.setBAA(((double) Collections.frequency((races), BLACK_OR_AFRICAN_AMERICAN) / (double) races.size()) * 100);
        stats.setAIAN(((double) Collections.frequency((races), AMERICAN_INDIAN_OR_ALASKAN_NATIVE) / (double) races.size()) * 100);
        stats.setAsian(((double) Collections.frequency((races), ASIAN) / (double) races.size()) * 100);
        stats.setNHPI(((double) Collections.frequency((races), NATIVE_HAWAIIAN_OR_OTHER_PACIFIC_ISLANDER) / (double) races.size()) * 100);
        stats.setMR(((double) Collections.frequency((races), FROM_MULTIPLE_RACES) / (double) races.size()) * 100);

        return stats;
    }

    @Override
    public Image getImage(Long userId) {
        return client.getStudentAvatar(secretToken, userId);
    }

}
