package diploma_mgt_app_skeleton.service;

import java.util.List;

import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.Subject;


public interface StudentService {
	public void saveProfile(Student Student);
	public Student retrieveProfileFromUserID(int userdId);
	public Student retrieveProfile(int studentId);
	public List<Subject> listStudentSubjects(int studentId);
	public void applyToSubject(int studentId, Integer subjectId);
	public List<Application> listofAppications(int studentId);
	public void deleteApplication(int applicationId); 
}
