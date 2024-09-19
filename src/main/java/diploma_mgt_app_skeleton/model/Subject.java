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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
@Entity
@Table(name = "subjects")
public class Subject {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
    private int id;
	
	@Column(name="title", nullable = false)
    private String title;
	
	@Column(name="objectives", nullable = false)
    private String objectives;

	@Column(name="total_months")
	private int totalMonths;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "supervisor_id")
	private Professor supervisor;
	
	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Application> applications = new ArrayList<>();
	
    @Column(name="availability")
    private boolean availability;
    
  
    public Subject(String title, String objectives, Professor supervisor,int totalMonths, boolean availability) {
		super();
		this.title = title;
		this.objectives = objectives;
		this.supervisor = supervisor;
		this.totalMonths = totalMonths;
		this.availability = availability;
	}
 
}
