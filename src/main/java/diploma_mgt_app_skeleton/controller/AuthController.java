package diploma_mgt_app_skeleton.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import diploma_mgt_app_skeleton.model.Professor;
import diploma_mgt_app_skeleton.model.Role;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.User;
import diploma_mgt_app_skeleton.service.ProfessorService;
import diploma_mgt_app_skeleton.service.StudentService;
import diploma_mgt_app_skeleton.service.UserService;


@Controller
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    ProfessorService professorService;
    @Autowired
    StudentService studentService;
    
    @RequestMapping("/login")
    public String login(){
    	return "auth/signin";
    }

    @RequestMapping("/register")
    public String register(Model model){
    	model.addAttribute("user", new User());
        return "auth/signup";
    }
    @RequestMapping("/save")
    public String registerUser(@ModelAttribute("user") User user,Model model){
    	if(userService.isUserPresent(user)){
    		model.addAttribute("successMessage", "User already registered!");
            return "auth/signup";
    	}

    	userService.saveUser(user);
    	model.addAttribute("successMessage", "User registered successfully!");
    	// Associate new Professor or Student with the user based on their role
    	if (user.getRole() == Role.PROFESSOR) {
    		Professor professor = new Professor();
    		professor.setUser(user);
    		professorService.saveProfile(professor);
            
        } else if (user.getRole() == Role.STUDENT) {
        	Student student = new Student();
            student.setUser(user);
            studentService.saveProfile(student);
        }
        return "auth/signin";
    }
}
