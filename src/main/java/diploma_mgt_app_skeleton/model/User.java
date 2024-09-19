package diploma_mgt_app_skeleton.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
//import javax.persistence.*;
import lombok.*;

@Getter @Setter @ToString  @NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails{
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id;
	@Column(name = "username", unique=true, nullable = false)
	private String username;
	@Column(name = "password", nullable= false)
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;
	
	
	public User(String username,String password, Role role ) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
	     return Collections.singletonList(authority);
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
