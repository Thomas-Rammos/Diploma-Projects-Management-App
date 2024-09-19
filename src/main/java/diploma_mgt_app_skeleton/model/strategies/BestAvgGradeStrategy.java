package diploma_mgt_app_skeleton.model.strategies;

import diploma_mgt_app_skeleton.model.Application;

public class BestAvgGradeStrategy extends TemplateStrategyAlgorithm {
    public BestAvgGradeStrategy() {
    }

    @Override
    protected int compareApplications(Application app1, Application app2) {
        double app1Grade = app1.getStudent().getCurrentAverageGrade();
        double app2Grade = app2.getStudent().getCurrentAverageGrade();
        return Double.compare(app1Grade, app2Grade);
    }

}
