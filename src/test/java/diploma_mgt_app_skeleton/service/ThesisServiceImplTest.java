package diploma_mgt_app_skeleton.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import diploma_mgt_app_skeleton.dao.ProfessorDAORepository;
import diploma_mgt_app_skeleton.dao.StudentDAORepository;
import diploma_mgt_app_skeleton.dao.SubjectDAORepository;
import diploma_mgt_app_skeleton.dao.ThesisDAORepository;
import diploma_mgt_app_skeleton.model.Professor;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.Subject;
import diploma_mgt_app_skeleton.model.Thesis;

public class ThesisServiceImplTest {
    @Mock
    private ThesisDAORepository thesisDAORepository;
    
    @Mock
    private ProfessorDAORepository professorDAORepository;
    
    @Mock
    private StudentDAORepository studentDAORepository;
    
    @Mock
    private SubjectDAORepository subjectDAORepository;
    
    @InjectMocks
    private ThesisServiceImpl thesisService;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testSave() {
        Professor supervisor = new Professor();
        supervisor.setId(1);
        
        Student student = new Student();
        student.setId(2);
        
        Subject subject = new Subject();
        subject.setId(3);
        
        Thesis thesis = new Thesis(supervisor, student, subject, 9.5, 8.8, 7.2);
        
        when(professorDAORepository.findById(1)).thenReturn(Optional.of(supervisor));
        when(studentDAORepository.findById(2)).thenReturn(student);
        when(subjectDAORepository.findById(3)).thenReturn(Optional.of(subject));
        
        thesisService.save(thesis, 1, 2, 3);
        
        verify(professorDAORepository).findById(1);
        verify(studentDAORepository).findById(2);
        verify(subjectDAORepository).findById(3);
        verify(thesisDAORepository).save(thesis);
    }
    
    @Test
    public void testFindById() {
        Thesis thesis = new Thesis();
        thesis.setId(1);
        
        when(thesisDAORepository.findById(1)).thenReturn(thesis);
        
        Thesis result = thesisService.findById(1);
        
        verify(thesisDAORepository).findById(1);
        
        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    
    @Test
    public void testFindStudentThesis() {
        Student student = new Student();
        student.setId(2);
        
        Thesis thesis = new Thesis();
        thesis.setId(1);
        thesis.setStudent(student);
        
        when(thesisDAORepository.findByStudent(student)).thenReturn(thesis);
        
        Thesis result = thesisService.findStudentThesis(student);
        
        verify(thesisDAORepository).findByStudent(student);
        
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(student, result.getStudent());
    }
}
