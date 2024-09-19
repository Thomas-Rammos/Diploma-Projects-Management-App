package diploma_mgt_app_skeleton.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import diploma_mgt_app_skeleton.model.*;


public interface UserService {
	public void saveUser(User user);
	
	public boolean isUserPresent(User user);
	
	public User findById(Integer id);
	public void deleteUser(int userId);
	public Optional<User> getUserByUsername(String username);
}
