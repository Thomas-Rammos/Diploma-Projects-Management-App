package diploma_mgt_app_skeleton.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomSecuritySuccessHandlerTest {
    private CustomSecuritySuccessHandler successHandler;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private UsernamePasswordAuthenticationToken authentication;

    @BeforeEach
    public void setUp() {
        successHandler = new CustomSecuritySuccessHandler();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void testHandleWithProfessorRole() throws Exception {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("PROFESSOR"));
        authentication = new UsernamePasswordAuthenticationToken("user", "password", authorities);
        successHandler.handle(request, response, authentication);
        assertEquals("/professor/homepage", response.getRedirectedUrl());
    }

    @Test
    public void testHandleWithStudentRole() throws Exception {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("STUDENT"));
        authentication = new UsernamePasswordAuthenticationToken("user", "password", authorities);
        successHandler.handle(request, response, authentication);
        assertEquals("/student/homepage", response.getRedirectedUrl());
    }

    @Test
    public void testHandleWithNoRole() throws Exception {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authentication = new UsernamePasswordAuthenticationToken("user", "password", authorities);
        successHandler.handle(request, response, authentication);
        assertEquals("/login?error=true", response.getRedirectedUrl());
    }
    @Test
    public void testHandleWithUnknownRole() throws Exception {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("UNKNOWN"));
        authentication = new UsernamePasswordAuthenticationToken("user", "password", authorities);
        successHandler.handle(request, response, authentication);
        assertEquals("/login?error=true", response.getRedirectedUrl());
    }
 


}
