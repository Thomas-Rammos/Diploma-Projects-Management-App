package diploma_mgt_app_skeleton.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import diploma_mgt_app_skeleton.model.Student;
import diploma_mgt_app_skeleton.model.Thesis;


@Repository
@Transactional
public interface ThesisDAORepository extends JpaRepository<Thesis, Integer>{

	Thesis findByStudent(Student student); 
	Thesis findById(int id);
}
