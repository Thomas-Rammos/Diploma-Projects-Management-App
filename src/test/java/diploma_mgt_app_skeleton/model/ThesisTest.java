package diploma_mgt_app_skeleton.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ThesisTest {
    @Test
    public void testEmptyConstructor() {
        Thesis thesis = new Thesis();
        assertEquals(0, thesis.getId());
        assertEquals(0.0, thesis.getImplementationGrade());
        assertEquals(0.0, thesis.getReportGrade());
        assertEquals(0.0, thesis.getPresentationGrade());
        assertNull(thesis.getSupervisor());
        assertNull(thesis.getStudent());
        assertNull(thesis.getSubject());
    }

    @Test
    public void testConstructor() {
        User userProfessor = new User("professor", "password", Role.PROFESSOR);
        User userStudent = new User("username", "password", Role.STUDENT);
        Professor professor = new Professor("test_prof", "Computer Science", "prof@gmail.com", 123456, 50, 25, userProfessor);
        Student student = new Student("test_student", 4, 2.5, 4, "student@gmail.com", 123456, 22, userStudent);
        Subject subject = new Subject("Machine Learning", "Asxolia me KNN \n classiffiers", professor, 12, true);
        Thesis thesis = new Thesis(professor, student, subject, 7.5, 8.0, 9.0);

        assertEquals(professor, thesis.getSupervisor());
        assertEquals(student, thesis.getStudent());
        assertEquals(subject, thesis.getSubject());
        assertEquals(7.5, thesis.getImplementationGrade());
        assertEquals(8.0, thesis.getReportGrade());
        assertEquals(9.0, thesis.getPresentationGrade());
    }

    @Test
    public void testSetters() {
        Thesis thesis = new Thesis();
        thesis.setId(1);
        thesis.setSupervisor(new Professor());
        thesis.setStudent(new Student());
        thesis.setSubject(new Subject());
        thesis.setImplementationGrade(7.5);
        thesis.setReportGrade(8.0);
        thesis.setPresentationGrade(9.0);

        assertEquals(1, thesis.getId());
        assertNotNull(thesis.getSupervisor());
        assertNotNull(thesis.getStudent());
        assertNotNull(thesis.getSubject());
        assertEquals(7.5, thesis.getImplementationGrade());
        assertEquals(8.0, thesis.getReportGrade());
        assertEquals(9.0, thesis.getPresentationGrade());
    }

    @Test
    public void testToString() {
        User userProfessor = new User("professor", "password", Role.PROFESSOR);
        User userStudent = new User("username", "password", Role.STUDENT);
        Professor professor = new Professor("test_prof", "Computer Science", "prof@gmail.com", 123456, 50, 25, userProfessor);
        Student student = new Student("test_student", 4, 2.5, 4, "student@gmail.com", 123456, 22, userStudent);
        Subject subject = new Subject("Machine Learning", "Asxolia me KNN \n classiffiers", professor, 12, true);
        Thesis thesis = new Thesis(professor, student, subject, 7.5, 8.0, 9.0);

        String expected = "Thesis(id=" + thesis.getId() + ", implementationGrade=7.5, reportGrade=8.0, presentationGrade=9.0, supervisor=" + professor.toString() + ", subject=" + subject.toString() + ", student=" + student.toString() + ")";
        assertEquals(expected,thesis.toString());
    }
}
