package diploma_mgt_app_skeleton.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Professor;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.Subject;
import diploma_mgt_app_skeleton.model.Thesis;
import diploma_mgt_app_skeleton.service.StudentService;
import diploma_mgt_app_skeleton.service.SubjectService;
import diploma_mgt_app_skeleton.service.ThesisService;
import diploma_mgt_app_skeleton.service.UserService;
import lombok.Setter;

@Controller
@RequestMapping("/student")
@SessionAttributes("student")
@Setter
public class StudentController {
	@Autowired
    UserService userService;
	@Autowired
    StudentService studentService;
	@Autowired
    SubjectService subjectService;
	
	@Autowired
	ThesisService thesisService;
	
	
	public StudentController() {
		super();
	}
	//GET USER AND STUDENT ID'S
	@RequestMapping("/homepage")
	public String getStudentMainMenu(Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		int userId = userService.getUserByUsername(currentPrincipalName).get().getId();
		Student student = studentService.retrieveProfileFromUserID(userId);
		model.addAttribute("student", student);
		if(student.getFullName() == null) {
			return "student/student-form";
		}
		return "student/student-homepage";
	}
	
	@RequestMapping("/deleteAccount")
	public String deleteMyAccount(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println(currentPrincipalName);
		int userId = userService.getUserByUsername(currentPrincipalName).get().getId();
		userService.deleteUser(userId);
		return "redirect:/logout";
	}
	
	
	//EDIT THE PROFILE OF STUDENT
	@RequestMapping("/profile")
	public String retrieveStudentProfile(Model model) {
		return "student/student-form";
	}
	    
	//SAVE THE PROFILE OF STUDENT
	@PostMapping("/saveStudent")
	public String saveStudentProfile(@ModelAttribute("student") Student student, Model model) {
	    studentService.saveProfile(student);
	    return "/student/student-homepage";
	}
    
	//SHOW SUBJECT LISTS
    @RequestMapping("/subjects")
    public String listStudentSubjects(@ModelAttribute("student") Student student, Model model) {
    	// get subjects from db
    	List<Subject> subjects = studentService.listStudentSubjects(student.getId());	
    	Thesis thesis = thesisService.findStudentThesis(student);
    	// add to the spring model
    	model.addAttribute("subjects", subjects);
    	model.addAttribute("thesis", thesis);
    	return "/student/subjectList";
    }
    //DO THE ASSIGNMENT 
    @RequestMapping("/assignToSubject")
    public String assignToSubject(@RequestParam("subjectId") int theId, @ModelAttribute("student") Student student) {
    	studentService.applyToSubject(student.getId(), theId);
    	return "redirect:/student/applications";
    }
    //LIST OF STUDENT APLLICATIONS
    @RequestMapping("/applications")
    public String myApplications(@ModelAttribute("student") Student student, Model model) {
    	List<Application> applications = studentService.listofAppications(student.getId());
    	model.addAttribute("applications", applications);
    	return "/student/applicationList";
    }
    //DELETE A APLLICATION
    @RequestMapping("/deleteApplication")
    public String deleteApplication(@RequestParam("applicationId") int theId, Model model) {
    	studentService.deleteApplication(theId);
    	return "redirect:/student/applications";
    }
    //SEE MY DIPLOMA THESIS
    @RequestMapping("/myThesis")
    public String studentDiploma(@ModelAttribute("student") Student student,Model model) {
    	Thesis thesis = thesisService.findStudentThesis(student);
    	model.addAttribute("thesis", thesis);
    	return "/student/myDiploma";
    	
    }
    @RequestMapping("/moreDetails")
    public String moreDetails(@RequestParam("subjectId") int subjectId,@RequestParam("supervisorId") int supervisorId, Model model) {
    	Subject subject = subjectService.findById(subjectId);
    	if(supervisorId == 0) {
    		model.addAttribute("subject", subject);
    	}else {
    		Professor professor = subject.getSupervisor();
    		model.addAttribute("professor", professor);
    	}
    	return"/student/moreDetails";
    }

	
    
}
