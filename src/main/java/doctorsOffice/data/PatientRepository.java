package doctorsOffice.data;

import doctorsOffice.model.person.patient.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, String> {
}
