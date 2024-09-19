package diploma_mgt_app_skeleton.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import diploma_mgt_app_skeleton.model.Professor;

@Repository
public interface ProfessorDAORepository extends JpaRepository<Professor, Integer>{
	Optional <Professor> findById(int id);
	Professor findByUserId(int userId);
}
