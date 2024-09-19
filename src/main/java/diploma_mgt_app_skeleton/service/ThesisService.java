package diploma_mgt_app_skeleton.service;

import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.Thesis;

public interface ThesisService {
	public void save(Thesis theThesis, int supervisorId,int studentId,int subjectId);
	public Thesis findById(int thesisId);
	public Thesis findStudentThesis(Student student);
}
