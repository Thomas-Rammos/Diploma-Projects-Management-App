package diploma_mgt_app_skeleton.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import diploma_mgt_app_skeleton.model.Professor;
import diploma_mgt_app_skeleton.model.Role;
import diploma_mgt_app_skeleton.model.Subject;
import diploma_mgt_app_skeleton.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SubjectDAORepositoryTest {

    @Autowired
    private SubjectDAORepository subjectDAORepository;
   
    @Test
    public void testFindById() {
    	User user = new User("username","1234",Role.PROFESSOR);
    	
        Professor supervisor = new Professor("test_prof", "specialty", "prof@gmail.com", 1234, 40, 20, user);
    
        Subject subject = new Subject("test_sub", "objectives", supervisor, 12, true);

        subjectDAORepository.save(subject);

        Optional<Subject> foundSubject = subjectDAORepository.findById(subject.getId());

        assertThat(foundSubject).isNotEmpty();
        assertThat(foundSubject.get().getId()).isEqualTo(subject.getId());
    }

}

