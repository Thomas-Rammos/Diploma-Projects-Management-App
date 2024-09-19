package diploma_mgt_app_skeleton.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SubjectTest {
    @Test
    public void testEmptyConstructor() {
        Subject subject = new Subject();
        assertEquals(0, subject.getId());
    }

    @Test
    public void testSubject() {
        User user = new User("zarakos", "xar43", Role.PROFESSOR);
        Professor professor = new Professor("test_prof", "Developer", "prof@gmail.com", 123456, 45, 20, user);
        Subject subject = new Subject("Machine Learning", "Asxolia me KNN \n classiffiers", professor, 12, true);

        assertEquals("Machine Learning", subject.getTitle());
        assertEquals("Asxolia me KNN \n classiffiers", subject.getObjectives());
        assertEquals(12, subject.getTotalMonths());
        assertEquals(professor, subject.getSupervisor());
        assertTrue(subject.isAvailability());
        assertTrue(subject.getApplications().isEmpty());
    }

    @Test
    public void testSetters() {
        Subject subject = new Subject();
        subject.setId(1);
        subject.setTitle("Machine Learning");
        subject.setObjectives("Learn about KNN classifiers");
        subject.setTotalMonths(12);
        subject.setAvailability(true);
        List<Application> applications = new ArrayList<>();
        applications.add(new Application());
        subject.setApplications(applications);

        assertEquals(1, subject.getId());
        assertEquals("Machine Learning", subject.getTitle());
        assertEquals("Learn about KNN classifiers", subject.getObjectives());
        assertEquals(12, subject.getTotalMonths());
        assertTrue(subject.isAvailability());
        assertFalse(subject.getApplications().isEmpty());
    }

    @Test
    public void testToString() {
        Subject subject = new Subject();
        subject.setId(1);
        subject.setTitle("Machine Learning");
        subject.setObjectives("Learn about KNN classifiers");
        subject.setTotalMonths(12);
        subject.setAvailability(true);

        String expected = "Subject(id=1, title=Machine Learning, objectives=Learn about KNN classifiers, totalMonths=12, supervisor=null, applications=[], availability=true)";
        assertEquals(expected, subject.toString());
    }

    @Test
    public void testNoArgsConstructor() {
        Subject subject = new Subject();
        assertNotNull(subject);
    }

    @Test
    public void testGetterSetter() {
        Subject subject = new Subject();
        Professor professor = new Professor();
        subject.setSupervisor(professor);
        assertEquals(professor, subject.getSupervisor());
    }
}
