package diploma_mgt_app_skeleton.model;


import javax.persistence.*;
import lombok.*;

@Getter @Setter @ToString @NoArgsConstructor
@Entity
@Table(name = "applications")
public class Application {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;
	
	@ManyToOne()
	@JoinColumn(name = "subject_id")
	private Subject subject;
	
	public Application(Student student, Subject subject) {
		this.student = student;
		this.subject = subject;
	}
}