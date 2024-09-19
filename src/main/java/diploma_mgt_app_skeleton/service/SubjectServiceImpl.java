package diploma_mgt_app_skeleton.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import diploma_mgt_app_skeleton.dao.SubjectDAORepository;
import diploma_mgt_app_skeleton.model.Subject;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService{
	@Autowired
	private SubjectDAORepository subjectDAORepository;
	
	
	@Override
	public Subject findById(Integer subjectId) {
	    return subjectDAORepository.findById(subjectId).get();
	}

}
