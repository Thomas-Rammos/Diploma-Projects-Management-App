package diploma_mgt_app_skeleton.model.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.User;

public class RandomStrategyTest {

    @Test
    public void testFindBestApplicantEmptyApplications() {
        RandomStrategy strategy = new RandomStrategy();
        List<Application> applications = new ArrayList<>();

        Student result = strategy.findBestApplicant(applications, 0.0, 0);

        assertNull(result);
    }

    @Test
    public void testFindBestApplicantSingleApplication() {
        RandomStrategy strategy = new RandomStrategy();
        User user = new User(); // assuming User has a no-args constructor
        Student expectedStudent = new Student("test_std", 4, 7.8, 2, "student@email.com", 12345, 20, user);
        Application application = new Application(expectedStudent, null);
        List<Application> applications = List.of(application);

        Student result = strategy.findBestApplicant(applications, 0.0, 0);

        assertEquals(expectedStudent, result);
    }

    @Test
    public void testFindBestApplicantMultipleApplications() {
        RandomStrategy strategy = new RandomStrategy();
        List<Application> applications = new ArrayList<>();
        User user1 = new User(); // assuming User has a no-args constructor
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        applications.add(new Application(new Student("test_std1", 4, 7.8, 2, "student1@email.com", 12345, 20, user1), null));
        applications.add(new Application(new Student("test_std2", 4, 7.8, 2, "student2@email.com", 12345, 20, user2), null));
        applications.add(new Application(new Student("test_std3", 4, 7.8, 2, "student3@email.com", 12345, 20, user3), null));
        applications.add(new Application(new Student("test_std4", 4, 7.8, 2, "student4@email.com", 12345, 20, user4), null));

        Student result = strategy.findBestApplicant(applications, 0.0, 0);

        assertTrue(applications.stream().map(Application::getStudent).anyMatch(s -> s.equals(result)));
    }
}
