package patientservice;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;
	
	private List<Patient> patient = new ArrayList<>(Arrays.asList());


	public List<Patient> getallPatient() {
		return (List<Patient>) this.patientRepository.findAll();
	}

	public Optional<Patient> getPatient(long id) {
		return this.patientRepository.findById(id);
	}

	public void addPatient(Patient patient) {
		this.patientRepository.save(patient);
	}

	public void updatePatient(String id, Patient patient) {
		this.patientRepository.save(patient);
	}

	public void deletePatient(long id) {
		this.patientRepository.deleteById(id);
}


}