package diploma_mgt_app_skeleton.service;

import java.util.List;

import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Professor;
import diploma_mgt_app_skeleton.model.Subject;
import diploma_mgt_app_skeleton.model.Thesis;


public interface ProfessorService {
	public Professor retrieveProfileFromUserID(int userdId);
	public void saveProfile(Professor professor);
	public List<Subject> listProfessorSubjects(int id);
	public Professor retrieveProfileById(int professorId);
	public void editSubject(Subject subject, int supervisorId);
	public void addSubject(int professorId, Subject subject);
	public void deleteSubject(int subjectId);
	public List<Thesis> listProfessorTheses(int profId);
	public void deleteThesis(int thesisId);
	public List<Application> listApplications(String professor, Integer subject);
	public String assignSubject(String subject,Integer student,double th1,int th2);
	
}
