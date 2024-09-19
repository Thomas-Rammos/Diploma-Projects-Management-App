package diploma_mgt_app_skeleton.model.strategies;
import java.util.List;

import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Student;

public interface BestApplicantStrategy {
    Student findBestApplicant(List<Application> applications,double th1,int th2);
}