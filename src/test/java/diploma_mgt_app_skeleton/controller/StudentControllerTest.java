package diploma_mgt_app_skeleton.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Professor;
import diploma_mgt_app_skeleton.model.Role;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.Subject;
import diploma_mgt_app_skeleton.model.Thesis;
import diploma_mgt_app_skeleton.model.User;
import diploma_mgt_app_skeleton.service.StudentService;
import diploma_mgt_app_skeleton.service.SubjectService;
import diploma_mgt_app_skeleton.service.ThesisService;
import diploma_mgt_app_skeleton.service.UserService;
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private SubjectService subjectService;

    @MockBean
    private ThesisService thesisService;
    
    @InjectMocks
    private StudentController studentController;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"STUDENT"})
    public void testGetStudentMainMenu() {
        Model model = mock(Model.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("username");

        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setPassword("1234");
        user.setRole(Role.STUDENT);

        when(userService.getUserByUsername(any(String.class))).thenReturn(Optional.of(user));

        // Test when student.getFullName() is null
        Student student = new Student();
        student.setUser(user);
        student.setId(1);

        when(studentService.retrieveProfileFromUserID(any(Integer.class))).thenReturn(student);

        String viewName = studentController.getStudentMainMenu(model);
        assertEquals("student/student-form", viewName);
        verify(model).addAttribute("student", student);

        // Test when student.getFullName() is not null
        student.setFullName("Full Name");

        viewName = studentController.getStudentMainMenu(model);
        assertEquals("student/student-homepage", viewName);
        verify(model, times(2)).addAttribute("student", student);
    }



    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"STUDENT"})
    public void testDeleteMyAccount() {
    	User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setPassword("1234");
        user.setRole(Role.STUDENT);
        when(userService.getUserByUsername(any(String.class))).thenReturn(Optional.of(user));

        String result = studentController.deleteMyAccount(mock(Model.class));
        assertEquals("redirect:/logout", result);
        verify(userService).deleteUser(user.getId());
    }
    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"STUDENT"})
    public void testRetrieveStudentProfile() {
        String result = studentController.retrieveStudentProfile(mock(Model.class));
        assertEquals("student/student-form", result);
    }
    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"STUDENT"})
    public void testSaveStudentProfile() {
    	 User user = new User();
         user.setId(1);
         user.setUsername("username");
         user.setPassword("1234");
         user.setRole(Role.STUDENT);

         Student student = new Student();
         student.setUser(user);
         student.setId(1);
        Model model = mock(Model.class);
        //when(studentService.saveProfile(student)).thenReturn(student);

        String result = studentController.saveStudentProfile(student, model);
        assertEquals("/student/student-homepage", result);
    }
    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"STUDENT"})
    public void testListStudentSubjects() {
    	 User user = new User();
         user.setId(1);
         user.setUsername("username");
         user.setPassword("1234");
         user.setRole(Role.STUDENT);

         Student student = new Student();
         student.setUser(user);
         student.setId(1);
        Model model = mock(Model.class);
        List<Subject> subjects = new ArrayList<>();
        Thesis thesis = new Thesis();

        when(studentService.listStudentSubjects(student.getId())).thenReturn(subjects);
        when(thesisService.findStudentThesis(student)).thenReturn(thesis);

        String result = studentController.listStudentSubjects(student, model);
        assertEquals("/student/subjectList", result);
        verify(model).addAttribute("subjects", subjects);
        verify(model).addAttribute("thesis", thesis);
    }
    @Test
    @WithMockUser(username = "username", password = "1234", roles = { "STUDENT" })
    public void testAssignToSubject() throws Exception {
        int subjectId = 1;
        Student student = new Student();
        student.setId(1);

        when(studentService.retrieveProfileFromUserID(any(Integer.class))).thenReturn(student);
        doNothing().when(studentService).applyToSubject(student.getId(), subjectId);

        StudentController controller = new StudentController();
        controller.setStudentService(studentService);

        String viewName = controller.assignToSubject(subjectId, student);

        verify(studentService).applyToSubject(student.getId(), subjectId);
        assertEquals("redirect:/student/applications", viewName);
        
    }



    @Test
    @WithMockUser(username = "username", password = "1234", roles = { "STUDENT" })
    public void testMyApplications() {
        Model model = mock(Model.class);
        Student student = new Student();
        student.setId(1);
        List<Application> applications = new ArrayList<>();

        when(studentService.listofAppications(student.getId())).thenReturn(applications);

        String result = studentController.myApplications(student, model);
        assertEquals("/student/applicationList", result);
        verify(model).addAttribute("applications", applications);
    }

    @Test
    @WithMockUser(username = "username", password = "1234", roles = { "STUDENT" })
    public void testDeleteApplication() throws Exception {
        int applicationId = 1;
        Student student = new Student();
        student.setId(1);

        doNothing().when(studentService).deleteApplication(applicationId);

        StudentController controller = new StudentController();
        controller.setStudentService(studentService);

        Model model = new ConcurrentModel();

        String viewName = controller.deleteApplication(applicationId, model);

        verify(studentService).deleteApplication(applicationId);
        assertEquals("redirect:/student/applications", viewName);
        
    }




    @Test
    @WithMockUser(username = "username", password = "1234", roles = { "STUDENT" })
    public void testStudentDiploma() {
        Model model = mock(Model.class);
        Student student = new Student();
        student.setId(1);
        Thesis thesis = new Thesis();

        when(thesisService.findStudentThesis(student)).thenReturn(thesis);

        String result = studentController.studentDiploma(student, model);
        assertEquals("/student/myDiploma", result);
        verify(model).addAttribute("thesis", thesis);
    }
    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"STUDENT"})
    public void testMoreDetails() {
        int subjectId = 1;
        int supervisorId = 0;

        Subject subject = new Subject();
        // Set up the subject object

        when(subjectService.findById(subjectId)).thenReturn(subject);

        Professor supervisor = new Professor();
        supervisor.setId(2);
        //professor.setId(supervisorId);
        // Set up the professor object
        subject.setSupervisor(supervisor);

        Model model = mock(Model.class);

        // Test the first case when supervisorId is 0
        String result = studentController.moreDetails(subjectId, supervisorId, model);
        assertEquals("/student/moreDetails", result);
        verify(model).addAttribute("subject", subject);

        // Test the second case when supervisorId is not 0
        supervisorId = 1; // Set a non-zero value for supervisorId
        result = studentController.moreDetails(subjectId, supervisorId, model);
        assertEquals("/student/moreDetails", result);
        Professor professor = subject.getSupervisor();
        verify(model).addAttribute("professor", professor);
    }



}
