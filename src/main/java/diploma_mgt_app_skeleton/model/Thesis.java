package diploma_mgt_app_skeleton.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
@Entity
@Table(name="theses")
public class Thesis {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column(name = "implementation_grade")
    private double implementationGrade;
	@Column(name = "report_grade")
    private double reportGrade;
	@Column(name = "presentation_grade")
    private double presentationGrade;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id")
    private Professor supervisor;
    
	//DEN THELOUME NA SVISTEI TO SUBJECT
	@OneToOne(cascade = CascadeType.PERSIST) 
    @JoinColumn(name = "subject_id")
    private Subject subject;
   
	//DEN THELOUME NA SVISTEI O FOITHTHS
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "student_id")
    private Student student;

    
    public Thesis(Professor supervisor, Student student, Subject subject,double implementationGrade, double reportGrade,double presentationGrade) {
    	this.supervisor = supervisor;
    	this.student = student;
    	this.subject = subject;
    	this.implementationGrade = implementationGrade;
    	this.reportGrade = reportGrade;
    	this.presentationGrade = presentationGrade;
    }
}
