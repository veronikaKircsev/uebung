package patientservice;
import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PatientController {


	@GetMapping ("/test")
	@Operation(summary = "GET operation for test endpoint")
	@ResponseStatus(HttpStatus.OK)
    public String serviceTest(){
		return " Service works!";
    }

	@Autowired
	private PatientService patientService;

	@GetMapping("/patient")
	public List<Patient> getallPatient() {
		return patientService.getallPatient();
	}


	@GetMapping("/patient/{id}")
	public Optional<Patient> getPatient(@PathVariable long id) {
		return patientService.getPatient(id);
	}

	@PostMapping("/patient")
	@ResponseStatus(HttpStatus.CREATED)
	public String addPatient(@RequestBody Patient patient) {
		patientService.addPatient (patient);
		String response = "{\"Patient has been added successfully }";
		return response;
	}
	

	@PutMapping("/patient/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public String updatePatient(@RequestBody Patient patient, @PathVariable String id) {
		patientService.updatePatient(id, patient);
		String response = "{\"Patient has been updated successfully.}";
		return response;
	}


	@DeleteMapping("/patient/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String deletePatient(@PathVariable Integer id) {
		patientService.deletePatient(id);
		String response = "{\"Patient has been deleted successfully.}";
		return response;
	}


}
