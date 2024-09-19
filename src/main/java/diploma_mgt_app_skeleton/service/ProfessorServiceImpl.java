package diploma_mgt_app_skeleton.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import diploma_mgt_app_skeleton.dao.ProfessorDAORepository;
import diploma_mgt_app_skeleton.dao.SubjectDAORepository;
import diploma_mgt_app_skeleton.dao.ThesisDAORepository;
import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Professor;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.Subject;
import diploma_mgt_app_skeleton.model.Thesis;
import diploma_mgt_app_skeleton.model.strategies.BestApplicantStrategy;
import diploma_mgt_app_skeleton.model.strategies.BestApplicantStrategyFactory;


@Service
@Transactional
public class ProfessorServiceImpl implements ProfessorService{
	@Autowired
	private ProfessorDAORepository professorDAORepository;
	@Autowired
	private SubjectDAORepository subjectDAORepository;
	@Autowired
	private ThesisDAORepository thesisDAORepository;
	
	public ProfessorServiceImpl(){
		super();
	}
	
	@Override
    public Professor retrieveProfileFromUserID(int userdId) {
        return professorDAORepository.findByUserId(userdId);
    }
	
	@Override
    public Professor retrieveProfileById(int professorId) {
		return professorDAORepository.findById(professorId).get();
    }
	
	@Override
	public void saveProfile(Professor professor) {
		professorDAORepository.save(professor);
	}

	@Override
	public void editSubject(Subject subject, int supervisorId) {
		Professor professor = professorDAORepository.findById(supervisorId).get();
		subject.setSupervisor(professor);
		subjectDAORepository.save(subject);
	}
	@Override
	public List<Subject> listProfessorSubjects(int id) {
		Professor professor = retrieveProfileById(id);
        return professor.getSubjects();
	}

	@Override
	public void addSubject(int professorId, Subject subject) {
		Professor professor = retrieveProfileById(professorId);
        subject.setSupervisor(professor);
        professor.getSubjects().add(subject);
        saveProfile(professor);	
	}
	
	@Override
	public void deleteSubject(int subjectId) {
		Optional<Subject> subject = subjectDAORepository.findById(subjectId);
	    Professor professor = subject.get().getSupervisor();
	    professor.getSubjects().remove(subject.get());
	    subject.get().setSupervisor(null);
	    saveProfile(professor);
	    subjectDAORepository.deleteById(subjectId);
	}
	
	@Override
	public void deleteThesis(int thesisId) {
		Thesis thesis = thesisDAORepository.findById(thesisId);
	    Professor professor = thesis.getSupervisor();
	    professor.getTheses().remove(thesis);
	    thesis.setSupervisor(null);
	    thesis.getSubject().setAvailability(true);
	    thesis.setStudent(null);
	    saveProfile(professor);
	    thesisDAORepository.deleteById(thesisId);
	}
	
	@Override
	public List<Application> listApplications(String professor, Integer subjectId) {
	    Optional<Subject> subject = subjectDAORepository.findById(subjectId);
	    return subject.get().getApplications();
	}

	@Override
	public List<Thesis> listProfessorTheses(int profId) {
		Professor professor = retrieveProfileById(profId);
        return professor.getTheses();
	}


    @Override
    public String assignSubject( String strategyType, Integer subjectId,double th1,int th2) {
    	Optional<Subject> subject = subjectDAORepository.findById(subjectId);
        List<Application> applications = subject.get().getApplications();
        BestApplicantStrategy strategy = BestApplicantStrategyFactory.createStrategy(strategyType);
        Student selectedStudent;
        try {
            if (strategyType.equals("Threshold")) {
                selectedStudent = strategy.findBestApplicant(applications, th1, th2);
            } else {
                selectedStudent = strategy.findBestApplicant(applications, 0.0, 0);
            }
        } catch (Exception e) {
            selectedStudent = null; // or assign a default value
        }
        if(selectedStudent == null){
        	return "no applicant";
        }
        Thesis thesis = new Thesis(subject.get().getSupervisor(),selectedStudent,subject.get(), 0,0,0);
        
        subject.get().getSupervisor().getTheses().add(thesis);
        subject.get().setAvailability(false);
        applications.clear();
        selectedStudent.getApplications().clear();
        saveProfile(subject.get().getSupervisor());
        subjectDAORepository.save(subject.get());
        
        return "applicant";
    }
    

}
