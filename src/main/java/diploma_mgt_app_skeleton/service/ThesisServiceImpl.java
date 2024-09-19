package diploma_mgt_app_skeleton.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diploma_mgt_app_skeleton.dao.ProfessorDAORepository;
import diploma_mgt_app_skeleton.dao.StudentDAORepository;
import diploma_mgt_app_skeleton.dao.SubjectDAORepository;
import diploma_mgt_app_skeleton.dao.ThesisDAORepository;
import diploma_mgt_app_skeleton.model.Professor;
import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.Subject;
import diploma_mgt_app_skeleton.model.Thesis;
@Service
public class ThesisServiceImpl implements ThesisService {
	@Autowired
	private ThesisDAORepository thesisDAORepository;
	@Autowired
	private ProfessorDAORepository professorDAORepository;
	@Autowired
	private StudentDAORepository studentDAORepository;
	@Autowired
	private SubjectDAORepository subjectDAORepository;
	
	@Override
	public void save(Thesis theThesis, int supervisorId,int studentId,int subjectId) {
		Optional <Professor> supervisor = professorDAORepository.findById(supervisorId);
        Student student = studentDAORepository.findById(studentId);
        Optional <Subject> subject = subjectDAORepository.findById(subjectId);
        theThesis.setSupervisor(supervisor.get());
        theThesis.setStudent(student);
        theThesis.setSubject(subject.get());
		thesisDAORepository.save(theThesis);
	}


	@Override
	public Thesis findById(int thesisId) {
		return thesisDAORepository.findById(thesisId);
	}
	@Override
	public Thesis findStudentThesis(Student student) {
		return thesisDAORepository.findByStudent(student);
	}
	
	
}
