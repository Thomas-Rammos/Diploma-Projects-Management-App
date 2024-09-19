package diploma_mgt_app_skeleton.model.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.Subject;

public class FewestCoursesStrategyTest {

    @Test
    public void testFindBestApplicant() {
    	
    	Student s1 = new Student("std1", 4, 7.5, 5, "std1@email.com", 12345, 20, null);
        Student s2 = new Student("std2", 3, 8.5, 3, "std2@email.com", 12346, 21, null);
        Student s3 = new Student("std3", 5, 9.0, 2, "std3@email.com", 12348, 23, null);

        Subject subject = new Subject("Mathematics", "Calculus",null,5, true);

        Application a1 = new Application(s1, subject);
        Application a2 = new Application(s2, subject);
        Application a3 = new Application(s3, subject);

        List<Application> applications = new ArrayList<>();
        applications.add(a1);
        applications.add(a2);
        applications.add(a3);

        FewestCoursesStrategy strategy = new FewestCoursesStrategy();

        Student bestStudent = strategy.findBestApplicant(applications,0,0);

        assertEquals(s3, bestStudent);
    }

}
