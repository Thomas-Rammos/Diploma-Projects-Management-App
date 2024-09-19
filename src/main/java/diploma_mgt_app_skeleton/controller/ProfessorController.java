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
import diploma_mgt_app_skeleton.service.ProfessorService;
import diploma_mgt_app_skeleton.service.SubjectService;
import diploma_mgt_app_skeleton.service.ThesisService;
import diploma_mgt_app_skeleton.service.UserService;

@Controller
@RequestMapping("/professor")
@SessionAttributes("professor")
public class ProfessorController {
	@Autowired
    UserService userService;
	@Autowired
    ProfessorService professorService;
	@Autowired
    SubjectService subjectService;
	@Autowired
    ThesisService thesisService;
	
	//GET USER AND PROFESSOR ID'S
	@RequestMapping("/homepage")
	public String getProfessorMainMenu(Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		int userId = userService.getUserByUsername(currentPrincipalName).get().getId();
		Professor professor = professorService.retrieveProfileFromUserID(userId);
		model.addAttribute("professor", professor);
		if(professor.getFullName() == null) {
			return "professor/prof_form";
		}
		return "professor/prof_homepage";
	}
	
	@RequestMapping("/deleteAccount")
	public String deleteMyAccount(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		int userId = userService.getUserByUsername(currentPrincipalName).get().getId();
		userService.deleteUser(userId);
		return "redirect:/logout";
	}
	
	//EDIT THE PROFILE OF PROFESSOR
	@RequestMapping("/profile")
    public String retrieveProfessorProfile(Model model) {
		return "professor/prof_form";
    }
    
	//SAVE THE PROFILE OF PROFESSOR
    @PostMapping("/saveProf")
    public String saveProfessorProfile(@ModelAttribute("professor") Professor professor, Model model) {
    	professorService.saveProfile(professor);
    	return "/professor/prof_homepage";
    }
    
    //SHOW SUBJECT LISTS
    @RequestMapping("/subjects")
    public String listProfessorSubjects(@ModelAttribute("professor") Professor professor, Model model) {
    	// get subjects from db
    	List<Subject> subjects = professorService.listProfessorSubjects(professor.getId());		
    	// add to the spring model
    	model.addAttribute("subjects", subjects);
    	return "/professor/subjectsList";
    }
    
    //ADD A NEW SUBJECT
    @RequestMapping("/showFormForAddSubject")
	public String showFormForAdd(@ModelAttribute("professor") Professor professor,Model theModel) {
		
		// create model attribute to bind form data
    	Subject theSubject= new Subject("","",professor,0,true);
    	
		theModel.addAttribute("subject", theSubject);
		
		return "professor/subject-form";
	}
    
    //EDIT A CURRRENT SUBJECT
    @RequestMapping("/showFormForUpdateSubject")
	public String showFormForUpdate(@RequestParam("subjectId") int theId, Model theModel) {
		
		// get the subject from the service
		Subject theSubject = subjectService.findById(theId);
		
		// set subject as a model attribute to pre-populate the form
		theModel.addAttribute("subject", theSubject);
		
		// send over to our form
		return "professor/subject-form";			
	}
    
    @PostMapping("/saveSubject")
    public String addSubject(@ModelAttribute("subject") Subject subject, @RequestParam("supervisor_id") int supervisorId, @RequestParam("id") int subId) {
    	Professor professor = professorService.retrieveProfileById(supervisorId);
    	try {
    	    if (professor.getSubjects().contains(subjectService.findById(subId))) {
    	        professorService.editSubject(subject, supervisorId);
    	    }
    	} catch (Exception e) {	    
    	    professorService.addSubject(professor.getId(), subject);
    	    return "redirect:/professor/subjects";
    	} 
    	return "redirect:/professor/subjects";
    }


    //DELTE A SUBJECT
    @RequestMapping("/deleteSubject")
	public String deleteSubject(@RequestParam("subjectId") int theId) {
		// delete the subject
    	professorService.deleteSubject(theId);
		// redirect to /professor/list ACTION
		return "redirect:/professor/subjects";
		
	}
    // SHOW LIST OF THESES
    @RequestMapping("/theses")
    public String listProfessorTheses(@ModelAttribute("professor") Professor professor, Model model) {
    	// get theses from db
    	List<Thesis> theses = professorService.listProfessorTheses(professor.getId());		
    	// add to the spring model
    	model.addAttribute("theses", theses);
    	return "/professor/thesesList";
    }
    
    // SAVE A NEW OR THE UPDATED SUBJECT
    @RequestMapping("/updateGrades")
    public String updateGrades(@RequestParam("thesistId") int thesisId, Model theModel) {
    	// get the subject from the service
	    Thesis thesis = thesisService.findById(thesisId);
	    theModel.addAttribute("thesis", thesis);
    	return "professor/theses-form";
    }
    @PostMapping("/saveGrades")
    public String saveGrades(@ModelAttribute("thesis") Thesis thesis,
            @RequestParam("supervisor_id") int supervisorId,
            @RequestParam("student_id") int studentId,@RequestParam("subject_id") int subjectId) {
    	
    	thesisService.save(thesis,supervisorId,studentId,subjectId);
    	return "redirect:/professor/theses";
    }
    // Delete current thesis
    @RequestMapping("/deleteThesis")
	public String deleteThesis(@RequestParam("thesistId") int theId) {
		// delete the subject
    	professorService.deleteThesis(theId);
		
		// redirect to /professor/list ACTION
		return "redirect:/professor/theses";
	}
    
    
   //SHOW LIST OF APPLICATIONS
    @RequestMapping("/applications")
    public String listProfessorApplications(@RequestParam("subjectId") int subjectId, @ModelAttribute("professor") Professor professor,Model model) {
    	// get theses from db
    	List<Application> applications = professorService.listApplications(professor.getFullName(), subjectId);
    	System.out.println(applications.get(0).getStudent().getFullName());
     	System.out.println(applications.get(0).getSubject().getTitle());
    	// add to the spring model
    	model.addAttribute("applications", applications);
    	return "/professor/applicationsList";
    }
    
    @RequestMapping("/assigned")
    public String assignStudent(@RequestParam("subjectId") int subjectId, @RequestParam("strategyType") String strategyType,
    		 @RequestParam(value = "threshold1", required = false) Double threshold1,
             @RequestParam(value = "threshold2", required = false) Integer threshold2) {
    	
    	String message = professorService.assignSubject(strategyType, subjectId,threshold1,threshold2);
    	if (message.equals("no applicant")) {
    		return"redirect:/professor/subjects";
    	}
    	return "redirect:/professor/theses";
    }
    @RequestMapping("/moreDetails")
    public String moreDetails(@RequestParam("thesisId") int thesisId,@RequestParam("studentId") int studentID, Model model) {
    	Thesis thesis = thesisService.findById(thesisId);
    	if(studentID == 0) {
    		Subject subject = thesis.getSubject();
    		model.addAttribute("subject", subject);
    	}else {
    		Student student = thesis.getStudent();
    		model.addAttribute("student", student);
    	}
    	return"/professor/moreDetails";
    }
    
}
