package diploma_mgt_app_skeleton.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserTest {

    @Test
    public void testEmptyConstructor() {
        User user = new User();
        assertEquals(0, user.getId());
    }

    @Test
    public void testSetters() {
        User user = new User();
        user.setId(1);
        user.setUsername("test_user");
        user.setPassword("test_password");
        user.setRole(Role.STUDENT);

        assertEquals(1, user.getId());
        assertEquals("test_user", user.getUsername());
        assertEquals("test_password", user.getPassword());
        assertEquals(Role.STUDENT, user.getRole());
    }

    @Test
    public void testToString() {
        User user = new User("test_user", "test_password", Role.STUDENT);
        user.setId(1);

        String expected = "User(id=1, username=test_user, password=test_password, role=STUDENT)";
        assertEquals(expected, user.toString());
    }
    @Test
    public void testRoleGetValue() {
        Role role = Role.STUDENT;
        assertEquals("STUDENT", role.getValue());
        
        role = Role.PROFESSOR;
        assertEquals("PROFESSOR", role.getValue());
    }

    
    @Test
    public void testGetAuthorities() {
        User user = new User("test_user", "test_password", Role.STUDENT);
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertEquals(1, authorities.size());
        assertEquals(new SimpleGrantedAuthority(Role.STUDENT.name()), authorities.iterator().next());
    }

    @Test
    public void testAccountStatusMethods() {
        User user = new User("test_user", "test_password", Role.STUDENT);

        assertEquals(true, user.isAccountNonExpired());
        assertEquals(true, user.isAccountNonLocked());
        assertEquals(true, user.isCredentialsNonExpired());
        assertEquals(true, user.isEnabled());
    }
}
