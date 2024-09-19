package diploma_mgt_app_skeleton.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import diploma_mgt_app_skeleton.model.Student;

@Repository
public interface StudentDAORepository extends JpaRepository<Student, Integer>{
	Student findById(int id); 
	Student findByUserId(int userId);
}
