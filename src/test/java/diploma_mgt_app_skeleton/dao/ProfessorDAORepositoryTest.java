package diploma_mgt_app_skeleton.dao;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import diploma_mgt_app_skeleton.model.Professor;
import diploma_mgt_app_skeleton.model.Role;
import diploma_mgt_app_skeleton.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProfessorDAORepositoryTest {
	  @Autowired
	  private ProfessorDAORepository professorDAORepository;
	  @Autowired
	  private UserDAORepository userDAORepository;
	  @Test
	  public void testFindById() {
		  User user = new User();
	      user.setUsername("username");
	      user.setRole(Role.PROFESSOR);
	      user.setPassword("1234");
	      userDAORepository.save(user);
	      Professor professor = new Professor("test_prof", "special", "prof@gmail.com", 1, 30, 10, user);
	      professorDAORepository.save(professor);
	      
	      Optional<Professor> foundProfessor = professorDAORepository.findById(professor.getId());
	      assertThat(foundProfessor).isNotNull();
	      assertThat(foundProfessor.get().getId()).isEqualTo(foundProfessor.get().getId());
	      
	      Optional<Professor> nullProfessor = professorDAORepository.findById(10);
	      assertThat(nullProfessor).isEmpty();
	  }
	  
	  @Test
	  public void testfindByUserID() {
		  User user = new User();
	      user.setUsername("username");
	      user.setRole(Role.PROFESSOR);
	      user.setPassword("1234");
	      userDAORepository.save(user);
	      
	      Professor professor = new Professor("test_prof", "special", "prof@gmail.com", 1, 30, 10, user);
	      professorDAORepository.save(professor);
	      Professor foundProfessor = professorDAORepository.findByUserId(user.getId());
	        
	      assertThat(foundProfessor).isNotNull();
	      assertThat(foundProfessor.getUser().getId()).isEqualTo(user.getId());
	      
	      Professor nullProfessor = professorDAORepository.findByUserId(10);
	      assertThat(nullProfessor).isNull();
	    }
}
