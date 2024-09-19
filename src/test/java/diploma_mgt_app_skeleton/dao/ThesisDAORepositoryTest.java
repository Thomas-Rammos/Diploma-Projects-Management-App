package diploma_mgt_app_skeleton.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import diploma_mgt_app_skeleton.model.Application;
import diploma_mgt_app_skeleton.model.Professor;
import diploma_mgt_app_skeleton.model.Role;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.Subject;
import diploma_mgt_app_skeleton.model.Thesis;
import diploma_mgt_app_skeleton.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ThesisDAORepositoryTest {

    @Autowired
    private ThesisDAORepository thesisDAORepository;
    @Autowired
    private StudentDAORepository studentDAORepository;
    @Autowired
	private ProfessorDAORepository professorDAORepository;
    @Autowired
	private SubjectDAORepository subjectDAORepository;
    @Autowired
   	private ApplicationDAORepository applicationDAORepository;
    @Autowired
    private UserDAORepository userDAORepository;
    @Test
    public void testFindByStudent() {

        Professor supervisor = new Professor();
        supervisor.setFullName("test_prof");
        supervisor.setSpecialty("Specialty");
        supervisor.setYearsOfTeaching(10);
        supervisor.setEmail("prof@test.com");
        supervisor.setAM(123);
        supervisor.setAge(45);
        supervisor.setUser(new User("user", "password", Role.PROFESSOR));
        professorDAORepository.save(supervisor);

        Student student = new Student();
        student.setFullName("test_student");
        student.setYearOfStudies(3);
        student.setCurrentAverageGrade(3.5);
        student.setRemainingCourses(5);
        student.setEmail("student@test.com");
        student.setAM(456);
        student.setAge(20);
        student.setUser(new User("user2", "password2", Role.STUDENT));
        studentDAORepository.save(student);

        Subject subject = new Subject();
        subject.setTitle("tst_title");
        subject.setObjectives("objectives");
        subject.setTotalMonths(6);
        subject.setSupervisor(supervisor);
        subject.setAvailability(true);
        subjectDAORepository.save(subject); 

        Application application = new Application();
        application.setStudent(student);
        application.setSubject(subject);
        //applicationDAORepository.save(application);
        subject.getApplications().add(application);
        student.getApplications().add(application);
        Thesis thesis = new Thesis();
        thesis.setSupervisor(supervisor);
        thesis.setStudent(student);
        thesis.setSubject(subject);
        thesis.setImplementationGrade(80);
        thesis.setReportGrade(85);
        thesis.setPresentationGrade(90);
        //thesisDAORepository.save(thesis);
        //thesisDAORepository.save(thesis);
        //studentDAORepository.save(student);
        professorDAORepository.save(supervisor);
        Thesis foundThesis = thesisDAORepository.findByStudent(student);
        assertThat(foundThesis).isNotNull();
        assertThat(foundThesis.getStudent()).isEqualTo(student);
    }




    @Test
    public void testFindById() {
    	
        Thesis thesis = new Thesis();
        thesisDAORepository.save(thesis);
    

        Thesis foundThesis = thesisDAORepository.findById(thesis.getId());

        assertThat(foundThesis).isNotNull();
        assertThat(foundThesis.getId()).isEqualTo(thesis.getId());
    }
}
