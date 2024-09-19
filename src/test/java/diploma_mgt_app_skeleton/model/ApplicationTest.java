package diploma_mgt_app_skeleton.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ApplicationTest {
    @Test
    public void testEmptyConstructor() {
        Application application = new Application();
        assertEquals(0, application.getId());
        assertNull(application.getStudent());
        assertNull(application.getSubject());
    }

    @Test
    public void testConstructor() {
        User userStudent = new User("username", "password", Role.STUDENT);
        User userProfessor = new User("professor", "password", Role.PROFESSOR);
        Student student = new Student("test_prof", 4, 2.5, 4, "prof@gmail.com", 123456, 22, userStudent);
        Professor professor = new Professor("test_student", "Computer Science", "student@gmail.com", 123456, 50, 25, userProfessor);
        Subject subject = new Subject("Machine Learning", "Asxolia me KNN \n classiffiers", professor, 12, true);
        Application application = new Application(student, subject);

        assertEquals(student, application.getStudent());
        assertEquals(subject, application.getSubject());
    }

    @Test
    public void testSetters() {
        Application application = new Application();
        application.setId(1);
        application.setStudent(new Student());
        application.setSubject(new Subject());

        assertEquals(1, application.getId());
        assertNotNull(application.getStudent());
        assertNotNull(application.getSubject());
    }

    @Test
    public void testToString() {
        User userStudent = new User("username", "password", Role.STUDENT);
        User userProfessor = new User("professor", "password", Role.PROFESSOR);
        Student student = new Student("test_student", 4, 2.5, 4, "student@gmail.com", 123456, 22, userStudent);
        Professor professor = new Professor("test_prof", "Computer Science", "prof@gmail.com", 123456, 50, 25, userProfessor);
        Subject subject = new Subject("Machine Learning", "Asxolia me KNN \n classiffiers", professor, 12, true);
        Application application = new Application(student, subject);

        String expected = "Application(id=" + application.getId() + ", student=" + student.toString() + ", subject=" + subject.toString() + ")";
        assertEquals(expected, application.toString());
    }
}
