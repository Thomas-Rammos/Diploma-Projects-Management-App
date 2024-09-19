package diploma_mgt_app_skeleton.model.strategies;

import java.util.List;

import org.springframework.stereotype.Component;

import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Student;

@Component
public class ThressholdStrategy implements BestApplicantStrategy{
	
	public ThressholdStrategy() {}
	
	@Override
	public Student findBestApplicant(List<Application> applications, double th1, int th2) {
		if (applications.isEmpty()) {
            return null;
        }
		Student bestApplicant = null;
        double maxAverageGrade = 0.0;

        for (Application application : applications) {
            Student student = application.getStudent();
            double averageGrade = student.getCurrentAverageGrade();
            int remainingCourses = student.getRemainingCourses();

            if (averageGrade > th1 && remainingCourses < th2) {
                if (averageGrade > maxAverageGrade) {
                    maxAverageGrade = averageGrade;
                    bestApplicant = student;
                }
            }
        }
        return bestApplicant;
	}
}
