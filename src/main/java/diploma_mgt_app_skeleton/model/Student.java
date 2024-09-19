package diploma_mgt_app_skeleton.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="students")
public class Student {
		
		@Id
		@Column(name="id")
		private int id;
		
		@Column(name="full_name")
		private String fullName;
		
		@Column(name="year_of_studies")
		private int yearOfStudies;
		
		@Column(name="current_average_grade")
		private double currentAverageGrade;
		
		@Column(name="remaining_courses")
		private int remainingCourses;
		
		@Column(name="email")
		private String email;
		
		@Column(name="AM")
		private int AM;

		@Column(name="age")
		private int age;
		
		@OneToOne()
		private User user;
		
		@OneToMany(mappedBy = "student", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
		private List<Application> applications = new ArrayList<>();
		
		
		public Student(String fullName, int yearOfStudies, double currentAverageGrade, int remainingCourses, String email, int AM, int age,User user) {
			super();
			this.fullName = fullName;
			this.yearOfStudies = yearOfStudies;
			this.currentAverageGrade = currentAverageGrade;
			this.remainingCourses = remainingCourses;
			this.email = email;
			this.age = age;
			this.AM = AM;
			this.user = user;
		}
		
}

