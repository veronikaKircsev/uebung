package patientservice;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Patient {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	private String patient;
	private String diagnosis;
	
	public Patient() {
	}

	public Patient(long id, String patient, String diagnosis) {
		super();
		this.id = id;
		this.patient = patient;
		this.diagnosis = diagnosis;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
}
