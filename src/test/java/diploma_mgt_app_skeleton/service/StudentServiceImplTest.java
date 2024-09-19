package diploma_mgt_app_skeleton.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import diploma_mgt_app_skeleton.dao.ApplicationDAORepository;
import diploma_mgt_app_skeleton.dao.StudentDAORepository;
import diploma_mgt_app_skeleton.dao.SubjectDAORepository;
import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Role;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.Subject;
import diploma_mgt_app_skeleton.model.User;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

	@Mock
    private StudentDAORepository studentDAORepository;

    @Mock
    private SubjectDAORepository subjectDAORepository;

    @Mock
    private ApplicationDAORepository applicationDAORepository;
    
    @InjectMocks
    private StudentServiceImpl studentService;
    
    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testSaveProfile() {
        Student student = new Student();
        User user = new User();
        user.setId(1);
        user.setPassword("1234");
        user.setUsername("giannis");
        user.setRole(Role.STUDENT);
        
        student.setId(1);
        student.setFullName("John");
        student.setUser(user);
        when(studentDAORepository.save(any(Student.class))).thenReturn(student);

        studentService.saveProfile(student);

        verify(studentDAORepository, times(1)).save(student);
    }

    @Test
    public void testRetrieveProfileFromUserID() {
        // Given
        int userId = 1;
        Student expectedStudent = new Student();
        expectedStudent.setId(1);
        expectedStudent.setFullName("John");

        // When
        when(studentDAORepository.findByUserId(userId)).thenReturn(expectedStudent);

        // Then
        Student actualStudent = studentService.retrieveProfileFromUserID(userId);
        assertEquals(expectedStudent, actualStudent);
    }
    @Test
    public void testRetrieveProfile() {
        Student expectedStudent = new Student();
        expectedStudent.setId(1);

        when(studentDAORepository.findById(1)).thenReturn(expectedStudent);

        Student actualStudent = studentService.retrieveProfile(1);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void testEditProfile() {
        Student student = new Student();
        student.setId(1);

        when(studentDAORepository.findById(1)).thenReturn(student);

        studentService.editprofile(1, "New Name", 2, 4.0, 3);

        verify(studentDAORepository, times(1)).save(student);
    }

    @Test
    public void testListStudentSubjects() {
        // Given
        int studentId = 1;
        Student student = new Student();
        student.setId(studentId);
        Subject subject = new Subject();
        subject.setId(1);
        subject.setAvailability(true);
        Application application = new Application();
        application.setSubject(subject);
        student.setApplications(Arrays.asList(application));

        when(studentDAORepository.findById(studentId)).thenReturn(student);
        when(subjectDAORepository.findAll()).thenReturn(Arrays.asList(subject));

        // When
        List<Subject> subjects = studentService.listStudentSubjects(studentId);

        // Then
        assertEquals(0, subjects.size());  // Because the subject has an application, it should be removed from the list
    }

    @Test
    public void testApplyToSubject() {
        // Given
        int studentId = 1;
        int subjectId = 1;
        Student student = new Student();
        student.setId(studentId);
        Subject subject = new Subject();
        subject.setId(subjectId);

        when(studentDAORepository.findById(studentId)).thenReturn(student);
        when(subjectDAORepository.findById(subjectId)).thenReturn(Optional.of(subject));

        // When
        studentService.applyToSubject(studentId, subjectId);

        // Then
        assertEquals(1, student.getApplications().size());
        assertEquals(subject, student.getApplications().get(0).getSubject());
        verify(studentDAORepository, times(1)).save(student);
    }
    @Test
    public void testListOfApplications() {
        // Given
        int studentId = 1;
        Student student = new Student();
        student.setId(studentId);
        Application application = new Application();
        application.setId(1);
        student.setApplications(Arrays.asList(application));

        //when(studentDAORepository.findById(studentId)).thenReturn(student);
        // Then
        assertEquals(1, student.getApplications().size());
        assertEquals(application, student.getApplications().get(0));
    }
    @Test
    public void testDeleteApplication() {
        // Given
        int applicationId = 1;
        Application application = new Application();
        application.setId(applicationId);
       
        Student student = new Student();
       
        student.setId(1);
        student.setApplications(new ArrayList<>(Arrays.asList(application)));
        application.setStudent(student);
        when(applicationDAORepository.findById(applicationId)).thenReturn(Optional.of(application));

        // When
        studentService.deleteApplication(applicationId);

        // Then
        assertEquals(0, student.getApplications().size());
        verify(studentDAORepository, times(1)).save(student);
    }


}
