package diploma_mgt_app_skeleton.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ApplicationDAORepositoryTest {
	 @Autowired
	 private SubjectDAORepository subjectDAORepository;
	 @Autowired
	 private ProfessorDAORepository professorDAORepository;
	 @Autowired
	 private UserDAORepository userDAORepository;
	 @Autowired
	 private ApplicationDAORepository applicationDAORepository;
	 
	 
	    
}
