package diploma_mgt_app_skeleton.model.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Student;

public class BestAvgGradeStrategyTest {

    @Test
    public void testFindBestApplicant() {
        BestApplicantStrategy strategy = new BestAvgGradeStrategy();

        Student student1 = new Student();
        student1.setCurrentAverageGrade(8.0);

        Student student2 = new Student();
        student2.setCurrentAverageGrade(7.5);

        Student student3 = new Student();
        student3.setCurrentAverageGrade(9.0);

        Application application1 = new Application(student1, null);
        Application application2 = new Application(student2, null);
        Application application3 = new Application(student3, null);

        List<Application> applications = new ArrayList<>();
        applications.add(application1);
        applications.add(application2);
        applications.add(application3);

        Student bestApplicant = strategy.findBestApplicant(applications,0,0);

        assertEquals(student3, bestApplicant);
    }
}
