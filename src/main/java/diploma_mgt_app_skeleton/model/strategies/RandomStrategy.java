package diploma_mgt_app_skeleton.model.strategies;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Student;


@Component
public class RandomStrategy implements BestApplicantStrategy {
	private final Random random = new Random();
	
	public RandomStrategy() {}

    @Override
    public Student findBestApplicant(List<Application> applications, double th1, int th2) {
        if (applications.isEmpty()) {
            return null;
        }

        int randomIndex = random.nextInt(applications.size());
        return applications.get(randomIndex).getStudent();
    }
}
