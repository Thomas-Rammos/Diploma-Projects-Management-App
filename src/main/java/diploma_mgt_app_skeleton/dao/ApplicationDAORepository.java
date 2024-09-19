package diploma_mgt_app_skeleton.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import diploma_mgt_app_skeleton.model.Application;

@Repository
public interface ApplicationDAORepository extends JpaRepository<Application, Integer>{
 
}
