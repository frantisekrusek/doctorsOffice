package doctorsOffice.controller;

import doctorsOffice.data.PatientRepository;
import doctorsOffice.model.appointment.Appointment;
import doctorsOffice.model.person.patient.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/reservation/patient")
public class NewPatientController {
    {log.info("LOG: NewPatientController Objekt/Bean wird erstellt");
    }


    private PatientRepository patientRepository;

    public NewPatientController(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }


}//end class
