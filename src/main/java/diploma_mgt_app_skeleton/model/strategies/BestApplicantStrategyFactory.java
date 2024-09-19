package diploma_mgt_app_skeleton.model.strategies;

import org.springframework.stereotype.Component;

@Component
public class BestApplicantStrategyFactory {

	 public BestApplicantStrategyFactory() {
		
	 }
	 
	 public static BestApplicantStrategy createStrategy(String strategyType) {
		 if (strategyType.equalsIgnoreCase("BestAvgGrade")) {
	            return new BestAvgGradeStrategy();
	        } else if (strategyType.equalsIgnoreCase("FewestCourses")) {
	            return new FewestCoursesStrategy();
	        }else if(strategyType.equalsIgnoreCase("Random")) {
	        	return new RandomStrategy();
	        }else if(strategyType.equalsIgnoreCase("Threshold")) {
		       	return new ThressholdStrategy();
	        } else {
	            throw new IllegalArgumentException("Invalid strategy type provided");
	        }
	    }
}
