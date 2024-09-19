package diploma_mgt_app_skeleton.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
@Entity
@Table(name = "professors")
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="specialty")
	private String specialty;

	@Column(name="years_of_teaching")
	private int yearsOfTeaching;
	
	@Column(name="email")
	private String email;

	@Column(name="AM")
	private int AM;
	
	@Column(name="age")
	private int age;
	
	@OneToOne()
	private User user;

	@OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL,orphanRemoval = true,fetch=FetchType.EAGER)
	private List<Subject> subjects = new ArrayList<>();
	
	@OneToMany(mappedBy = "supervisor", orphanRemoval = true,cascade=CascadeType.ALL)
	private List<Thesis> theses = new ArrayList<>();
   
	
	public Professor(String fullName, String specialty,String email,int AM, int age,int years0fTeaching, User user) {
		super();
		this.fullName = fullName;
		this.specialty = specialty;
		this.user = user;
		this.age = age;
		this.AM =AM;
		this.email = email;
		this.yearsOfTeaching = years0fTeaching;
	}

}
