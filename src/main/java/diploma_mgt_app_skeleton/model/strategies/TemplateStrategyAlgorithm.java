package diploma_mgt_app_skeleton.model.strategies;

import java.util.List;

import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Student;

public abstract class TemplateStrategyAlgorithm  implements BestApplicantStrategy {
	 public TemplateStrategyAlgorithm() {
	    }
	 @Override
	public Student findBestApplicant(List<Application> applications,double th2,int th1) {
	        Application bestApplication = applications.get(0);
	        for (int i = 1; i < applications.size(); i++) {
	            if (compareApplications(applications.get(i), bestApplication) > 0) {
	                bestApplication = applications.get(i);
	            }
	        }
	        return bestApplication.getStudent();
	    }
	 
	  protected abstract int compareApplications(Application app1, Application app2);
	  
}
