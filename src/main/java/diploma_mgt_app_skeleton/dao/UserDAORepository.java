package diploma_mgt_app_skeleton.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import diploma_mgt_app_skeleton.model.*;

@Repository
public interface UserDAORepository extends JpaRepository<User, Integer>{
	 Optional <User> findByUsername(String username);
	 Optional <User>  findById(Integer id);
}
