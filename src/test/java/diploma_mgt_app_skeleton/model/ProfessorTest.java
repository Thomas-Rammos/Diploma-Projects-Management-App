package diploma_mgt_app_skeleton.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ProfessorTest {
    @Test
    public void testEmptyConstructor() {
        Professor professor = new Professor();
        assertEquals(0, professor.getId());
    }

    @Test
    public void testConstructor() {
        User user = new User("username", "password", Role.PROFESSOR);
        Professor professor = new Professor("test_prof", "Computer Science", "prof@gmail.com", 123456, 45, 20, user);

        assertEquals("test_prof", professor.getFullName());
        assertEquals("Computer Science", professor.getSpecialty());
        assertEquals(45, professor.getAge());
        assertEquals(123456, professor.getAM());
        assertEquals("prof@gmail.com", professor.getEmail());
        assertEquals(20, professor.getYearsOfTeaching());
        assertEquals(user, professor.getUser());
        assertTrue(professor.getSubjects().isEmpty());
        assertTrue(professor.getTheses().isEmpty());
    }

    @Test
    public void testGettersSetters() {
        Professor professor = new Professor();
        professor.setId(1);
        professor.setFullName("test_prof");
        professor.setSpecialty("Computer Science");
        professor.setAge(45);
        professor.setAM(123456);
        professor.setEmail("prof@gmail.com");
        professor.setYearsOfTeaching(20);
        User user = new User("username", "password", Role.PROFESSOR);
        professor.setUser(user);
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject());
        professor.setSubjects(subjects);
        List<Thesis> theses = new ArrayList<>();
        theses.add(new Thesis());
        professor.setTheses(theses);

        assertEquals(1, professor.getId());
        assertEquals("test_prof", professor.getFullName());
        assertEquals("Computer Science", professor.getSpecialty());
        assertEquals(45, professor.getAge());
        assertEquals(123456, professor.getAM());
        assertEquals("prof@gmail.com", professor.getEmail());
        assertEquals(20, professor.getYearsOfTeaching());
        assertEquals(user, professor.getUser());
        assertFalse(professor.getSubjects().isEmpty());
        assertFalse(professor.getTheses().isEmpty());
    }

    @Test
    public void testToString() {
        Professor professor = new Professor();
        professor.setId(1);
        professor.setFullName("test_prof");
        professor.setSpecialty("Computer Science");
        professor.setAge(45);
        professor.setAM(123456);
        professor.setEmail("prof@gmail.com");
        professor.setYearsOfTeaching(20);
        User user = new User("username", "password", Role.PROFESSOR);
        professor.setUser(user);

        String expected = "Professor(id=1, fullName=test_prof, specialty=Computer Science, yearsOfTeaching=20, email=prof@gmail.com, AM=123456, age=45, user=" + user.toString() + ", subjects=[], theses=[])";
        assertEquals(expected, professor.toString());
    }
}
