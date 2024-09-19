package diploma_mgt_app_skeleton.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Professor;
import diploma_mgt_app_skeleton.model.Role;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.Subject;
import diploma_mgt_app_skeleton.model.Thesis;
import diploma_mgt_app_skeleton.model.User;
import diploma_mgt_app_skeleton.service.ProfessorService;
import diploma_mgt_app_skeleton.service.SubjectService;
import diploma_mgt_app_skeleton.service.ThesisService;
import diploma_mgt_app_skeleton.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfessorControllerTest {
    @InjectMocks
    private ProfessorController professorController;

    @Mock
    private UserService userService;

    @Mock
    private ProfessorService professorService;

    @Mock
    private SubjectService subjectService;

    @Mock
    private ThesisService thesisService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(professorController).build();
    }

    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testGetProfessorMainMenu() throws Exception {
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

        // Test when professor.getFullName() is null
        Professor professor = new Professor();
        professor.setUser(user);
        professor.setId(1);

        when(professorService.retrieveProfileFromUserID(any(Integer.class))).thenReturn(professor);

        String viewName = professorController.getProfessorMainMenu(model);
        assertEquals("professor/prof_form", viewName);
        verify(model).addAttribute("professor", professor);

        // Test when professor.getFullName() is not null
        professor.setFullName("Full Name");

        viewName = professorController.getProfessorMainMenu(model);
        assertEquals("professor/prof_homepage", viewName);
        verify(model, times(2)).addAttribute("professor", professor);
    }

    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testDeleteMyAccount() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setPassword("1234");
        user.setRole(Role.PROFESSOR);

        when(userService.getUserByUsername(any(String.class))).thenReturn(Optional.of(user));

        mockMvc.perform(get("/professor/deleteAccount"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/logout"));

        verify(userService).deleteUser(user.getId());
    }
    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testRetrieveProfessorProfile() {
        String result = professorController.retrieveProfessorProfile(mock(Model.class));
        assertEquals("professor/prof_form", result);
    }
    
    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testSaveProfessorProfile() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setPassword("1234");
        user.setRole(Role.PROFESSOR);

        Professor professor = new Professor();
        professor.setUser(user);
        professor.setId(1);
        Model model = mock(Model.class);

        String result = professorController.saveProfessorProfile(professor, model);

        assertEquals("/professor/prof_homepage", result);
    }
    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testListProfessorSubjects() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setPassword("1234");
        user.setRole(Role.PROFESSOR);

        Professor professor = new Professor();
        professor.setUser(user);
        professor.setId(1);

        List<Subject> subjects = new ArrayList<>(); // Add your subjects to the list

        when(professorService.listProfessorSubjects(professor.getId())).thenReturn(subjects);

        Model model = mock(Model.class);

        String result = professorController.listProfessorSubjects(professor, model);

        assertEquals("/professor/subjectsList", result);
        verify(model).addAttribute("subjects", subjects);
    }
    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testShowFormForAddSubject() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setPassword("1234");
        user.setRole(Role.PROFESSOR);

        Professor professor = new Professor();
        professor.setUser(user);
        professor.setId(1);

        Model model = mock(Model.class);

        String result = professorController.showFormForAdd(professor, model);

        assertEquals("professor/subject-form", result);

        ArgumentCaptor<Subject> subjectCaptor = ArgumentCaptor.forClass(Subject.class);
    
        verify(model).addAttribute(eq("subject"), subjectCaptor.capture());

        Subject subject = subjectCaptor.getValue();
        assertNotNull(subject);
        assertEquals("", subject.getTitle());
        assertEquals("", subject.getObjectives());
        assertEquals(professor, subject.getSupervisor());
        assertEquals(0, subject.getTotalMonths());
       
    }

    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testShowFormForUpdateSubject() {
        int subjectId = 1;

        Subject subject = new Subject();
        subject.setId(subjectId);

        when(subjectService.findById(subjectId)).thenReturn(subject);

        Model model = mock(Model.class);

        String result = professorController.showFormForUpdate(subjectId, model);

        assertEquals("professor/subject-form", result);

        ArgumentCaptor<Subject> subjectCaptor = ArgumentCaptor.forClass(Subject.class);
        verify(model).addAttribute(eq("subject"), subjectCaptor.capture());

        Subject capturedSubject = subjectCaptor.getValue();
        assertNotNull(capturedSubject);
        assertEquals(subject, capturedSubject);
    }
    /*
    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testAddSubject() {
        Subject subject = new Subject();
        subject.setId(1); // Set the subject ID to match the subId parameter in the test
        subject.setTitle("Machine Learning");
        subject.setObjectives("Asxolia me KNN classiffiers");
        subject.setTotalMonths(12);

        Professor professor = new Professor();
        professor.setId(1);
        professor.setFullName("user");
        professor.setSpecialty("Mechanic");
        
        // Don't add the subject to the professor's subjects yet
        // professor.getSubjects().add(subject);
        
        //subject.setSupervisor(professor);
        when(professorService.retrieveProfileById(1)).thenReturn(professor);
        when(subjectService.findById(1)).thenReturn(subject);

        String result = professorController.addSubject(subject, 1, 1);

        assertEquals("redirect:/professor/subjects", result);
        verify(professorService).addSubject(professor.getId(), subject);
        verify(professorService, times(0)).editSubject(subject, professor.getId()); // Ensure editSubject is not called
    }*/
    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testAddSubjectWhenSubjectNotFound() {
        Subject subject = new Subject();
        subject.setId(1);
        subject.setTitle("Machine Learning");
        subject.setObjectives("Asxolia me KNN classiffiers");
        subject.setTotalMonths(12);

        Professor professor = new Professor();
        professor.setId(1);
        professor.setFullName("user");
        professor.setSpecialty("Mechanic");

        when(professorService.retrieveProfileById(1)).thenReturn(professor);
        when(subjectService.findById(1)).thenThrow(new NoSuchElementException());

        String result = professorController.addSubject(subject, 1, 1);

        assertEquals("redirect:/professor/subjects", result);
        verify(professorService).addSubject(professor.getId(), subject);
        verify(professorService, times(0)).editSubject(subject, professor.getId()); // Ensure editSubject is not called
    }

    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testEditSubjectWhenSubjectFoundAndProfessorHasSubject() {
        Subject subject = new Subject();
        subject.setId(1);
        subject.setTitle("Machine Learning");
        subject.setObjectives("Asxolia me KNN classiffiers");
        subject.setTotalMonths(12);

        Professor professor = new Professor();
        professor.setId(1);
        professor.setFullName("user");
        professor.setSpecialty("Mechanic");
        professor.getSubjects().add(subject);  // Add the subject to the professor's subjects

        when(professorService.retrieveProfileById(1)).thenReturn(professor);
        when(subjectService.findById(1)).thenReturn(subject);

        String result = professorController.addSubject(subject, 1, 1);

        assertEquals("redirect:/professor/subjects", result);
        verify(professorService).editSubject(subject, professor.getId()); // Ensure editSubject is called
        verify(professorService, times(0)).addSubject(professor.getId(), subject); // Ensure addSubject is not called
    }





    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testDeleteSubject() {
        String result = professorController.deleteSubject(1);

        assertEquals("redirect:/professor/subjects", result);
        verify(professorService).deleteSubject(1);
    }

    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testListProfessorTheses() {
        Model model = mock(Model.class);
        Professor professor = new Professor();
        professor.setId(1);
        List<Thesis> theses = new ArrayList<>();

        when(professorService.listProfessorTheses(professor.getId())).thenReturn(theses);

        String result = professorController.listProfessorTheses(professor, model);

        assertEquals("/professor/thesesList", result);
        verify(model).addAttribute("theses", theses);
    }

    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testUpdateGrades() {
        int thesisId = 1;
        Thesis thesis = new Thesis();

        when(thesisService.findById(thesisId)).thenReturn(thesis);

        Model model = mock(Model.class);
        String result = professorController.updateGrades(thesisId, model);

        assertEquals("professor/theses-form", result);
        verify(model).addAttribute("thesis", thesis);
    }
    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testSaveGrades() {
        Thesis thesis = new Thesis();
        thesis.setId(1);

        professorController.saveGrades(thesis, 1, 1, 1);

        verify(thesisService).save(thesis, 1, 1, 1);
    }
    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testDeleteThesis() {
        professorController.deleteThesis(1);

        verify(professorService).deleteThesis(1);
    }
    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testListProfessorApplications() {
        Professor professor = new Professor();
        professor.setFullName("Professor Name");

        Student student = new Student();  // Create a new Student
        student.setFullName("Student Name");  // Set the student's full name

        Subject subject = new Subject();  // Create a new Subject
        subject.setTitle("Subject Title");  // Set the subject's title

        Application application = new Application();
        application.setStudent(student);  // Set the application's student
        application.setSubject(subject);  // Set the application's subject

        List<Application> applications = Collections.singletonList(application);
        when(professorService.listApplications(anyString(), anyInt())).thenReturn(applications);

        Model model = mock(Model.class);
        professorController.listProfessorApplications(1, professor, model);

        verify(model).addAttribute("applications", applications);
    }

    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testAssignStudent_noApplicant() {
        when(professorService.assignSubject(anyString(), anyInt(), anyDouble(), anyInt())).thenReturn("no applicant");

        String result = professorController.assignStudent(1, "strategy", 0.5, 2);

        assertEquals("redirect:/professor/subjects", result);
    }

    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testAssignStudent_applicant() {
        when(professorService.assignSubject(anyString(), anyInt(), anyDouble(), anyInt())).thenReturn("applicant");

        String result = professorController.assignStudent(1, "strategy", 0.5, 2);

        assertEquals("redirect:/professor/theses", result);
    }
    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testMoreDetails_subject() {
        Thesis thesis = new Thesis();
        Subject subject = new Subject();
        thesis.setSubject(subject);
        when(thesisService.findById(anyInt())).thenReturn(thesis);

        Model model = mock(Model.class);
        professorController.moreDetails(1, 0, model);

        verify(model).addAttribute("subject", subject);
    }

    @Test
    @WithMockUser(username = "username", password = "1234", roles = {"PROFESSOR"})
    public void testMoreDetails_student() {
        Thesis thesis = new Thesis();
        Student student = new Student();
        thesis.setStudent(student);
        when(thesisService.findById(anyInt())).thenReturn(thesis);

        Model model = mock(Model.class);
        professorController.moreDetails(1, 1, model);

        verify(model).addAttribute("student", student);
    }

}
