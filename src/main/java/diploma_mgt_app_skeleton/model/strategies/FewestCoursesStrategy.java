package diploma_mgt_app_skeleton.model.strategies;

import diploma_mgt_app_skeleton.model.Application;

public class FewestCoursesStrategy extends TemplateStrategyAlgorithm {
    public FewestCoursesStrategy() {
    }

    @Override
    protected int compareApplications(Application a1, Application a2) {
        return Integer.compare(a2.getStudent().getRemainingCourses(), a1.getStudent().getRemainingCourses());
    }

}
