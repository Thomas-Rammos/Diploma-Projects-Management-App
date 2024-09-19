package diploma_mgt_app_skeleton.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diploma_mgt_app_skeleton.dao.ApplicationDAORepository;
import diploma_mgt_app_skeleton.dao.StudentDAORepository;
import diploma_mgt_app_skeleton.dao.SubjectDAORepository;
import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.Subject;

@Service
public class StudentServiceImpl implements StudentService{
	@Autowired
	private StudentDAORepository studentDAORepository;
	@Autowired
	private SubjectDAORepository subjectDAORepository;
	@Autowired
	private ApplicationDAORepository applicationDAORepository;
	
	public StudentServiceImpl() {super();}
	
	@Override
	public void saveProfile(Student Student) {
		studentDAORepository.save(Student);
	}
	
	@Override
    public Student retrieveProfileFromUserID(int userdId){
        return studentDAORepository.findByUserId(userdId);
    }

	@Override
	public Student retrieveProfile(int studentId) {
		return studentDAORepository.findById(studentId);
	}
	
	public void editprofile(int studentId, String fullName, int yearOfStudies, double currentAverageGrade, int numberOfRemainingCourses) {
		Student student = studentDAORepository.findById(studentId);
		student.setFullName(fullName);
		student.setYearOfStudies(yearOfStudies);
		student.setCurrentAverageGrade(currentAverageGrade);
		student.setRemainingCourses(numberOfRemainingCourses);
		saveProfile(student);
	}
	
	@Override
	public List<Subject> listStudentSubjects(int studentId) {
		List<Subject> stud_subjects = new ArrayList<>();
        List<Subject> subjects = subjectDAORepository.findAll();
        for(Subject sub: subjects) {
        	if (sub.isAvailability()) {
        		stud_subjects.add(sub);
        	}
        }
        List<Application> applications = listofAppications(studentId);
    	for(Application app:applications) {	
    		stud_subjects.remove(app.getSubject());
	    		
    		}
        return stud_subjects;
	}
	
	
	public Optional<Subject> retriveSubjectProfile(Integer subjectId) {
		Optional<Subject> subject = subjectDAORepository.findById(subjectId);
		return subject; 
	} 
	
	

	@Override
	public void applyToSubject(int studentId, Integer subjectId) {
		Student student = retrieveProfile(studentId);
		Optional<Subject> subject = retriveSubjectProfile(subjectId);
		Application application = new Application(student, subject.get());
		student.getApplications().add(application);
		saveProfile(student);
		
	}
	
	@Override
	public List<Application> listofAppications(int studentId){
		Student student = retrieveProfile(studentId);
		return student.getApplications();
	}
	
	@Override
	public void deleteApplication(int applicationId) {
		Application application = applicationDAORepository.findById(applicationId).get();
		Student student = application.getStudent();
		student.getApplications().remove(application);
		saveProfile(student);
	}

}
