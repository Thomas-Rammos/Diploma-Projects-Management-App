package diploma_mgt_app_skeleton.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import diploma_mgt_app_skeleton.model.Role;
import diploma_mgt_app_skeleton.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserDAORepositoryTest {

    @Autowired
    private UserDAORepository userDAORepository;

    @Test
    public void testFindByUsername() {
        String username = "username";
        User user = new User();
        user.setUsername(username);
        user.setRole(Role.PROFESSOR);
        user.setPassword("1234");
        userDAORepository.save(user);

        Optional<User> foundUser = userDAORepository.findByUsername(username);

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(username);
    }

    @Test
    public void testFindById() {
        User user = new User();
        user.setUsername("username");
        user.setRole(Role.STUDENT);
        user.setPassword("1234");
        userDAORepository.save(user);

        Optional<User> foundUser = userDAORepository.findById(user.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(user.getId());
    }
}
