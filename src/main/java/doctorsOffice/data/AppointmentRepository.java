package doctorsOffice.data;

import doctorsOffice.model.appointment.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, String> {
}
