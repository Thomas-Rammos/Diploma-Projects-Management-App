package diploma_mgt_app_skeleton.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import diploma_mgt_app_skeleton.dao.ProfessorDAORepository;
import diploma_mgt_app_skeleton.dao.SubjectDAORepository;
import diploma_mgt_app_skeleton.dao.ThesisDAORepository;
import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Professor;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.Subject;
import diploma_mgt_app_skeleton.model.Thesis;
import diploma_mgt_app_skeleton.model.strategies.BestApplicantStrategy;
import diploma_mgt_app_skeleton.model.strategies.BestApplicantStrategyFactory;
@SpringBootTest
public class ProfessorServiceImplTest {

	@InjectMocks
	private ProfessorServiceImpl professorService;

	@Mock
	private ProfessorDAORepository professorDAORepository;
	@Mock
	private SubjectDAORepository subjectDAORepository;
	@Mock
	private ThesisDAORepository thesisDAORepository;
	@Mock
    private BestApplicantStrategyFactory bestApplicantStrategyFactory;
	@Mock
    private BestApplicantStrategy bestApplicantStrategy;
	
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveProfileById() {
        int professorId = 1;
        Professor professor = new Professor();
        professor.setId(professorId);
        
        when(professorDAORepository.findById(professorId)).thenReturn(Optional.of(professor));
        
        Professor retrievedProfessor = professorService.retrieveProfileById(professorId);
        
        assertEquals(professorId, retrievedProfessor.getId());
    }

    @Test
    public void testAddSubject() {
        int professorId = 1;
        Professor professor = new Professor();
        professor.setId(professorId);
        Subject subject = new Subject();

        when(professorDAORepository.findById(professorId)).thenReturn(Optional.of(professor));

        professorService.addSubject(professorId, subject);

        assertEquals(1, professor.getSubjects().size());
        assertEquals(professor, subject.getSupervisor());
        verify(professorDAORepository, times(1)).save(professor);
    }
    @Test
    public void testRetrieveProfileFromUserID() {
        int userId = 1;
        Professor professor = new Professor();
        professor.setId(userId);
        
        when(professorDAORepository.findByUserId(userId)).thenReturn(professor);
        
        Professor retrievedProfessor = professorService.retrieveProfileFromUserID(userId);
        
        assertEquals(userId, retrievedProfessor.getId());
    }


    @Test
    public void testSaveProfile() {
        Professor professor = new Professor();

        professorService.saveProfile(professor);

        verify(professorDAORepository, times(1)).save(professor);
    }

    @Test
    public void testEditSubject() {
        int supervisorId = 1;
        Professor professor = new Professor();
        professor.setId(supervisorId);
        Subject subject = new Subject();

        when(professorDAORepository.findById(supervisorId)).thenReturn(Optional.of(professor));

        professorService.editSubject(subject, supervisorId);

        assertEquals(professor, subject.getSupervisor());
        verify(subjectDAORepository, times(1)).save(subject);
    }



    @Test
    public void testListProfessorSubjects() {
        int professorId = 1;
        Professor professor = new Professor();
        professor.setId(professorId);
        Subject subject1 = new Subject();
        Subject subject2 = new Subject();
        professor.setSubjects(Arrays.asList(subject1, subject2));

        when(professorDAORepository.findById(professorId)).thenReturn(Optional.of(professor));

        List<Subject> subjects = professorService.listProfessorSubjects(professorId);

        assertEquals(2, subjects.size());
        verify(professorDAORepository, times(1)).findById(professorId);
    }
    @Test
    public void testDeleteSubject() {
        int subjectId = 1;
        Subject subject = new Subject();
        Professor professor = new Professor();
        subject.setSupervisor(professor);
        professor.getSubjects().add(subject);

        when(subjectDAORepository.findById(subjectId)).thenReturn(Optional.of(subject));

        professorService.deleteSubject(subjectId);

        assertEquals(null, subject.getSupervisor());
        verify(professorDAORepository, times(1)).save(professor);
        verify(subjectDAORepository, times(1)).deleteById(subjectId);
    }

    @Test
    public void testDeleteThesis() {
        int thesisId = 1;
        Thesis thesis = new Thesis();
        Professor professor = new Professor();
        Subject subject = new Subject(); // create a new Subject
        thesis.setSupervisor(professor);
        thesis.setSubject(subject); // set the Subject to the Thesis
        professor.getTheses().add(thesis);

        when(thesisDAORepository.findById(thesisId)).thenReturn(thesis);

        professorService.deleteThesis(thesisId);

        assertEquals(null, thesis.getSupervisor());
        assertEquals(true, thesis.getSubject().isAvailability());
        assertEquals(null, thesis.getStudent());
        verify(professorDAORepository, times(1)).save(professor);
        verify(thesisDAORepository, times(1)).deleteById(thesisId);
    }


    @Test
    public void testListApplications() {
        Integer subjectId = 1;
        Subject subject = new Subject();
        Application app1 = new Application();
        Application app2 = new Application();
        subject.setApplications(Arrays.asList(app1, app2));

        when(subjectDAORepository.findById(subjectId)).thenReturn(Optional.of(subject));

        List<Application> applications = professorService.listApplications("professor", subjectId);

        assertEquals(2, applications.size());
        verify(subjectDAORepository, times(1)).findById(subjectId);
    }

    @Test
    public void testListProfessorTheses() {
        int professorId = 1;
        Professor professor = new Professor();
        Thesis thesis1 = new Thesis();
        Thesis thesis2 = new Thesis();
        professor.setTheses(Arrays.asList(thesis1, thesis2));

        when(professorDAORepository.findById(professorId)).thenReturn(Optional.of(professor));

        List<Thesis> theses = professorService.listProfessorTheses(professorId);

        assertEquals(2, theses.size());
        verify(professorDAORepository, times(1)).findById(professorId);
    }
    @Test
    public void testAssignSubject_WithThresholdStrategy() {
        // Mock data
        int subjectId = 1;
        double th1 = 5.0;
        int th2 = 10;

     // Create and set up your entities
        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setAvailability(true);

        Professor professor = new Professor();
        subject.setSupervisor(professor);

        Student student1 = new Student();
        student1.setCurrentAverageGrade(85);
        
        Student student2 = new Student();
        student2.setCurrentAverageGrade(90);

        Application app1 = new Application(student1, subject);
        Application app2 = new Application(student2, subject);

        List<Application> applications = new ArrayList<>();
        applications.add(app1);
        applications.add(app2);

        subject.setApplications(applications);

        Student selectedStudent = new Student();

        // Mock repository methods
        when(subjectDAORepository.findById(eq(subjectId))).thenReturn(Optional.of(subject));
        when(bestApplicantStrategy.findBestApplicant(eq(applications), eq(th1), eq(th2))).thenReturn(selectedStudent);

        // Invoke the method
        String result = professorService.assignSubject("Threshold", subjectId, th1, th2);

        // Verify interactions and assertions
        verify(subjectDAORepository).findById(eq(subjectId));
        verify(bestApplicantStrategy).findBestApplicant(eq(applications), eq(th1), eq(th2));
        verify(subjectDAORepository).save(eq(subject));

        assertEquals("applicant", result);
        //assertEquals(subject.getSupervisor(), selectedStudent.getSupervisor());
        //assertEquals(subject, selectedStudent.getThesis().getSubject());
        assertEquals(false, subject.isAvailability());
        assertEquals(0, subject.getApplications().size());
        assertEquals(0, selectedStudent.getApplications().size());
    }



}
