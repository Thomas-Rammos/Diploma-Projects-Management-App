package diploma_mgt_app_skeleton.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import diploma_mgt_app_skeleton.model.*;
import diploma_mgt_app_skeleton.dao.*;
@Service
public class UserServiceImpl implements  UserService, UserDetailsService{
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserDAORepository userDAORepository;

	public UserServiceImpl(){
		super();
	}
	
	@Override
	public void saveUser(User user) {
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDAORepository.save(user);	
        
    }
	 @Override
	 public Optional<User> getUserByUsername(String username) {
	        return  userDAORepository.findByUsername(username);
	    }
	@Override
	public boolean isUserPresent(User user) {
		Optional<User> storedUser = userDAORepository.findByUsername(user.getUsername());
		return storedUser.isPresent();
	}
	
	@Override
	public User findById(Integer id) {
		Optional<User> storedUser = userDAORepository.findById(id);
		return storedUser.get();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 return userDAORepository.findByUsername(username).orElseThrow(
	                ()-> new UsernameNotFoundException(
	                        String.format("USER_NOT_FOUND", username)
	                ));
	}
	
	@Override
	public void deleteUser(int userId) {
		userDAORepository.deleteById(userId);
	}
}
