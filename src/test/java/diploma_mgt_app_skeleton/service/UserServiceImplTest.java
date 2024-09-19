package diploma_mgt_app_skeleton.service;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import diploma_mgt_app_skeleton.dao.UserDAORepository;
import diploma_mgt_app_skeleton.model.Role;
import diploma_mgt_app_skeleton.model.User;

public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserDAORepository userDAORepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setPassword("testPassword");
        when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        userService.saveUser(user);

        verify(userDAORepository).save(any(User.class));
    }

    @Test
    public void testGetUserByUsername() {
        when(userDAORepository.findByUsername("manhs")).thenReturn(Optional.of(new User()));

        userService.getUserByUsername("manhs");

        verify(userDAORepository).findByUsername("manhs");
    }

    @Test
    public void testIsUserPresent() {
        when(userDAORepository.findByUsername("manhs")).thenReturn(Optional.of(new User()));

        userService.isUserPresent(new User("manhs","1234",Role.PROFESSOR));

        verify(userDAORepository).findByUsername("manhs");
    }

    @Test
    public void testFindById() {
        when(userDAORepository.findById(1)).thenReturn(Optional.of(new User()));

        userService.findById(1);

        verify(userDAORepository).findById(1);
    }

    
    @Test
    public void testLoadUserByUsername_UserFound() {
        when(userDAORepository.findByUsername("manhs")).thenReturn(Optional.of(new User()));

        userService.loadUserByUsername("manhs");

        verify(userDAORepository).findByUsername("manhs");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userDAORepository.findByUsername("manhs")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("manhs");
        });

        verify(userDAORepository).findByUsername("manhs");
    }

    @Test
    public void testDeleteUser() {
        userService.deleteUser(1);

        verify(userDAORepository).deleteById(1);
    }


}
