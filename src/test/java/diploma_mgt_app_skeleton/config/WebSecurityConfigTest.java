package diploma_mgt_app_skeleton.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class WebSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAccessWithoutLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/professor/homepage"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/student/homepage"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "manhs", authorities = {"PROFESSOR"})
    public void testAccessWithProfessorRole() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/student/homepage"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "student", authorities = {"STUDENT"})
    public void testAccessWithStudentRole() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/professor/homepage"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}
