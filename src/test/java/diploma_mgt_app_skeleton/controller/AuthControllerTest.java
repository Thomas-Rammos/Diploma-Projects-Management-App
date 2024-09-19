package diploma_mgt_app_skeleton.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import diploma_mgt_app_skeleton.model.User;
import diploma_mgt_app_skeleton.service.ProfessorService;
import diploma_mgt_app_skeleton.service.StudentService;
import diploma_mgt_app_skeleton.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private ProfessorService professorService;

    @MockBean
    private StudentService studentService;
    
    
    @Test
    public void loginPageTest() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/signin"));
    }

    @Test
    public void registerPageTest() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("auth/signup"));
    }

    @Test
    public void saveProfessorNewUserTest() throws Exception {
    	
        when(userService.isUserPresent(any(User.class))).thenReturn(false);
        

        mockMvc.perform(post("/save")
                .param("username", "test")
                .param("password", "test")
                .param("role", "PROFESSOR"))
                .andExpect(status().is2xxSuccessful()) 
                .andExpect(view().name("auth/signin"));
    }
    @Test
    public void saveNewStudentUserTest() throws Exception {
        
        when(userService.isUserPresent(any(User.class))).thenReturn(false);
        
        mockMvc.perform(post("/save")
                .param("username", "test")
                .param("password", "test")
                .param("role", "STUDENT"))
                .andExpect(status().is2xxSuccessful()) 
                .andExpect(view().name("auth/signin"));

       
    }

    @Test
    public void saveExistingUserTest() throws Exception {
        when(userService.isUserPresent(any(User.class))).thenReturn(true);

        mockMvc.perform(post("/save")
                .param("username", "test")
                .param("password", "test")
                .param("role", "PROFESSOR"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("successMessage"))
                .andExpect(view().name("auth/signup"));
    }
}
