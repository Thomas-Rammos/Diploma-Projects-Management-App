package diploma_mgt_app_skeleton.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StudentTest {
    @Test
    public void testEmptyConstructor() {
        Student student = new Student();
        assertEquals(0, student.getId());
    }
    
    @Test
    public void testStudent() {
        User user = new User("username", "1234", Role.STUDENT);
        Student student = new Student("test_student", 4, 5.55, 13, "student@gmail.com", 123456, 22, user);

        assertEquals("test_student", student.getFullName());
        assertEquals(4, student.getYearOfStudies());
        assertEquals(5.55, student.getCurrentAverageGrade(), 0.001);
        assertEquals(13, student.getRemainingCourses());
        assertEquals("student@gmail.com", student.getEmail());
        assertEquals(123456, student.getAM());
        assertEquals(22, student.getAge());
        assertEquals(user, student.getUser());
        assertTrue(student.getApplications().isEmpty());
    }

    @Test
    public void testGetterSetter() {
        Student student = new Student();
        student.setId(1);
        student.setFullName("test_student");
        student.setYearOfStudies(3);
        student.setCurrentAverageGrade(3.5);
        student.setRemainingCourses(5);
        User user = new User("username", "password", Role.STUDENT);
        List<Application> applications = new ArrayList<>();
        applications.add(new Application());
        student.setApplications(applications);
        student.setUser(user);
        student.setEmail("student@gmail.com");
        student.setAM(123456);
        student.setAge(22);

        assertEquals(1, student.getId());
        assertEquals("test_student", student.getFullName());
        assertEquals(3, student.getYearOfStudies());
        assertEquals(3.5, student.getCurrentAverageGrade(), 0.001);
        assertEquals(5, student.getRemainingCourses());
        assertEquals("student@gmail.com", student.getEmail());
        assertEquals(123456, student.getAM());
        assertEquals(22, student.getAge());
        assertFalse(student.getApplications().isEmpty());
    }

    
    @Test
    public void testNoArgsConstructor() {
        Student student = new Student();
        assertNotNull(student);
    }
    
    @Test
    public void testToString() {
        Student student = new Student();
        student.setId(1);
        student.setFullName("test_student");
        student.setYearOfStudies(3);
        student.setCurrentAverageGrade(3.5);
        student.setRemainingCourses(5);
        student.setAge(22);
        student.setAM(4491);
        student.setEmail("student@gmail.com");
        String expected = "Student(id=1, fullName=test_student, yearOfStudies=3, currentAverageGrade=3.5, remainingCourses=5, email=student@gmail.com, AM=4491, age=22, user=null, applications=[])";
        assertEquals(expected, student.toString());
        
    }

    @Test
    public void testApplications() {
        User studentUser = new User("test_student", "1234", Role.STUDENT);
        User professorUser = new User("username", "password", Role.PROFESSOR);
        
        Professor professor = new Professor("test_prof", "Computer Science", "prof@gmail.com", 123456, 45, 20, professorUser);
        Subject subject = new Subject("Machine Learning", "Asxolia me KNN \n classiffiers", professor, 6, true);
        Student student = new Student("student", 4, 5.55, 13, "student@gmail.com", 4491, 22, studentUser);
        
        Application application = new Application(student, subject);

        List<Application> applications = new ArrayList<>();
        applications.add(application);
        student.setApplications(applications);

        assertFalse(student.getApplications().isEmpty());
        assertEquals(application, student.getApplications().get(0));
    }

}
