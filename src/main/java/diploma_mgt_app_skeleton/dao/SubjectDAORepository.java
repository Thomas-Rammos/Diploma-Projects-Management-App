package diploma_mgt_app_skeleton.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import diploma_mgt_app_skeleton.model.Subject;

@Repository
public interface SubjectDAORepository extends JpaRepository<Subject, Integer>{
	@Override
	Optional<Subject> findById(Integer id);
	Subject findByTitle(String title);
}
