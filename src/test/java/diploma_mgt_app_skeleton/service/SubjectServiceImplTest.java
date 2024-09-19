package diploma_mgt_app_skeleton.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import diploma_mgt_app_skeleton.dao.SubjectDAORepository;
import diploma_mgt_app_skeleton.model.Subject;

@SpringBootTest
public class SubjectServiceImplTest {
	
    @Mock
    private SubjectDAORepository subjectDAORepository;

    @InjectMocks
    private SubjectServiceImpl subjectService;
    
    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testFindById_SubjectFound() {
        Subject mockSubject = new Subject();
        mockSubject.setId(1);
        mockSubject.setTitle("Math");
        mockSubject.setObjectives("Learn mathematics");
        mockSubject.setAvailability(true);
       
        doReturn(Optional.of(mockSubject)).when(subjectDAORepository).findById(mockSubject.getId());

        Subject result = subjectService.findById(mockSubject.getId());

        verify(subjectDAORepository).findById(mockSubject.getId());

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Math", result.getTitle());
        assertEquals("Learn mathematics", result.getObjectives());
        assertTrue(result.isAvailability());
    }
}

