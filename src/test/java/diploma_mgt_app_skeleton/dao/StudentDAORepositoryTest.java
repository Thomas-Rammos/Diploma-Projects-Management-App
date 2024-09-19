package diploma_mgt_app_skeleton.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import diploma_mgt_app_skeleton.model.Role;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentDAORepositoryTest {

    @Autowired
    private StudentDAORepository studentDAORepository;

    @Autowired
    private UserDAORepository userDAORepository;
    @Test
    public void testFindById() {
        User user = new User();
        user.setUsername("username");
        user.setRole(Role.PROFESSOR);
        user.setPassword("1234");
        userDAORepository.save(user);
        Student student = new Student("test_student", 1, 2.5, 2, "student@gmail.com", 1, 20, user);
        studentDAORepository.save(student);

        Student foundStudent = studentDAORepository.findById(student.getId());

        assertThat(foundStudent).isNotNull();
        assertThat(foundStudent.getId()).isEqualTo(student.getId());
        
        Student nullStudent = studentDAORepository.findById(10);
        assertThat(nullStudent).isNull();
    }

    @Test
    public void testfindByUserId() {
    	 User user = new User();
         user.setUsername("username");
         user.setRole(Role.PROFESSOR);
         user.setPassword("1234");
         userDAORepository.save(user);
         
         Student student = new Student("test_student", 1, 2.5, 2, "student@gmail.com", 1, 20, user);
         studentDAORepository.save(student);

         Student foundStudent = studentDAORepository.findByUserId(user.getId());
         assertThat(foundStudent).isNotNull();
         assertThat(foundStudent.getUser().getId()).isEqualTo(user.getId());
         
         Student nullStudent = studentDAORepository.findByUserId(10);
         assertThat(nullStudent).isNull();
    }
}

