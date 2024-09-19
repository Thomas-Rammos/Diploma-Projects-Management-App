package diploma_mgt_app_skeleton.model.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.User;

public class ThressholdStrategyTest {

    @Test
    public void testFindBestApplicantEmptyApplications() {
        ThressholdStrategy strategy = new ThressholdStrategy();
        List<Application> applications = new ArrayList<>();

        Student result = strategy.findBestApplicant(applications, 7.0, 4);

        assertNull(result);
    }

    @Test
    public void testFindBestApplicant() {
        ThressholdStrategy strategy = new ThressholdStrategy();
        User user = new User(); // Assuming User has a no-args constructor

        Student student1 = new Student("std1", 4, 7.5, 5, "std1@email.com", 12345, 20, user);
        Student student2 = new Student("std2", 3, 8.5, 3, "std2@email.com", 12346, 21, user);
        Student student3 = new Student("std3", 5, 9.0, 2, "std3@email.com", 12348, 23, user);

        Application application1 = new Application(student1, null);
        Application application2 = new Application(student2, null);
        Application application3 = new Application(student3, null);

        List<Application> applications = new ArrayList<>();
        applications.add(application1);
        applications.add(application2);
        applications.add(application3);

        Student bestApplicant = strategy.findBestApplicant(applications, 8.0, 3);

        assertEquals(student3, bestApplicant);
    }

    @Test
    public void testFindBestApplicantNoStudentPassesThreshold() {
        ThressholdStrategy strategy = new ThressholdStrategy();
        User user = new User(); // Assuming User has a no-args constructor

        Student student1 = new Student("std1", 4, 7.5, 5, "std1@email.com", 12345, 20, user);
        Student student2 = new Student("std2", 3, 8.5, 3, "std2@email.com", 12346, 21, user);

        Application application1 = new Application(student1, null);
        Application application2 = new Application(student2, null);

        List<Application> applications = new ArrayList<>();
        applications.add(application1);
        applications.add(application2);

        Student bestApplicant = strategy.findBestApplicant(applications, 9.0, 2);

        assertNull(bestApplicant);
    }
}
