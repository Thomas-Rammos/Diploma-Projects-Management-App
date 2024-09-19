package diploma_mgt_app_skeleton.model.strategies;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BestApplicantStrategyFactoryTest {
    
    @Test
    public void testCreateStrategy() {
        BestApplicantStrategy strategy = BestApplicantStrategyFactory.createStrategy("BestAvgGrade");
        assertTrue(strategy instanceof BestAvgGradeStrategy);

        strategy = BestApplicantStrategyFactory.createStrategy("FewestCourses");
        assertTrue(strategy instanceof FewestCoursesStrategy);

        strategy = BestApplicantStrategyFactory.createStrategy("Random");
        assertTrue(strategy instanceof RandomStrategy);
        
        strategy = BestApplicantStrategyFactory.createStrategy("Threshold");
        assertTrue(strategy instanceof ThressholdStrategy);
    }

    @Test
    public void testInvalidStrategyType() {
        assertThrows(IllegalArgumentException.class, () -> {
            BestApplicantStrategyFactory.createStrategy("InvalidStrategyType");
        });
    }
}
