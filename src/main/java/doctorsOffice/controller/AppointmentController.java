package doctorsOffice.controller;

import doctorsOffice.data.AppointmentRepository;
import doctorsOffice.data.PatientRepository;
import doctorsOffice.model.person.patient.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import doctorsOffice.model.Office;
import doctorsOffice.model.appointment.Appointment;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/reservation")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private Map<String, Object> modelMap;
    private Office office;
    private Appointment selectedAppointment;
    private Patient sessionPatient;

    @Autowired
    public AppointmentController(AppointmentRepository appointmentRepository, PatientRepository patientRepository, Office office){
        log.info("LOG: AppointmentController Objekt/Bean wird erstellt");
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.office = office;
    }

    @ModelAttribute(name = "selectedappointment")
    public Appointment createAppointment() {
        log.info("LOG: AppController: createAppointment() wird aufgerufen");
        return selectedAppointment;
    }

    //methode wird wann ?? aufgerufen -->>  @ ModelAttribute -Methoden werden von Spring MVC immer aufgerufen,
    //bevor die mit @ RequestMapping gekennzeichneten Controller-Methoden aufgerufen werden.
    //(Model-Objekt soll erstellt weren, bevor Controller-Methoden verarbeitet werden)
    @ModelAttribute(name = "sessionpatient")
    public Patient createPatient(){
        log.info("LOG: AppController: createPatient() wird aufgerufen");
        return sessionPatient;}

    @GetMapping
    public String showReservierungsFormular(Model model){
        office.update(this.appointmentRepository);
        sessionPatient = new Patient();
        //model.addAttribute("praxis", office);
        int termAttr = 0;
        model.addAttribute("updatedDates", office.getUpdatedDates());
            for (Appointment appointment : office.getUpdatedDates()){
                model.addAttribute(("termin" + ++termAttr), appointment);
        }//end for
        modelMap = model.asMap();
        for (Iterator it = modelMap.keySet().iterator(); it.hasNext(); ) {
            Object key = it.next();
            String value;
            if(!(null == modelMap.get(key))){
                value = modelMap.get(key).toString();
            }else{
                value = "no Object!";
            }
            System.out.println(key.toString() + " = " + value);
        }
        return "reservierung";
    }//end showReservierungsFormular()

    @PostMapping
    public String showPatientsDataForm(Model model, Appointment appointment){
        //Attribut 'appointment' kommt von thymeleaf-template
        log.info("LOG: von thymeleaf zurückgekommen: " + appointment.getId());
        Optional<Appointment> optionalAppointmentFromDb = appointmentRepository.findById(appointment.getId());
        if (optionalAppointmentFromDb.isPresent()) {
            Appointment appointmentFromDb = optionalAppointmentFromDb.get();
            log.info("LOG: Aus Datenbank geholtes Appointment-Objekt: " + appointmentFromDb.toString());
            this.selectedAppointment = appointmentFromDb;
            appointmentFromDb.setBelegt(true);
            appointmentRepository.save(appointmentFromDb);
            log.info("LOG: Aus Datenbank geholtes Appointment-Objekt: " + appointmentFromDb.toString());
        }else{
            log.info("LOG: Termin in Datenbank nicht gefunden!" );
        }

        modelMap = model.asMap();
        for (Iterator it = modelMap.keySet().iterator(); it.hasNext(); ) {
            Object key = it.next();
            String value;
            if(!(null == modelMap.get(key))){
                value = modelMap.get(key).toString();
            }else{
                value = "no Object!";
            }
            System.out.println(key.toString() + " = " + value);
        }
        return "redirect:/reservation/patient";
    }


    @GetMapping("/patient")
    public String showPatientForm(Model model){

        model.addAttribute("selectedappointment", selectedAppointment);
        sessionPatient = new Patient();
        model.addAttribute("sessionpatient", sessionPatient);
        modelMap = model.asMap();
        for (Iterator it = modelMap.keySet().iterator(); it.hasNext(); ) {
            Object key = it.next();
            String value;
            if(!(null == modelMap.get(key))){
                value = modelMap.get(key).toString();
            }else{
                value = "no Object!";
            }
            System.out.println(key.toString() + " = " + value);
        }
        return "patientForm";
    }

    @PostMapping("/patient")
    public String processNewPatient(Model model, @ModelAttribute("sessionpatient") Patient patient){
        this.sessionPatient = patient;
        this.sessionPatient.setAppointment(selectedAppointment);
        selectedAppointment.setPatient(this.sessionPatient);

        modelMap = model.asMap();
        System.out.println("Attribute im Model:");
        for (Iterator it = modelMap.keySet().iterator(); it.hasNext(); ) {
            Object key = it.next();
            String value;
            if(!(null == modelMap.get(key))){
                value = modelMap.get(key).toString();
            }else{
                value = "no Object!";
            }
            System.out.println(key.toString() + " = " + value);
        }
        System.out.println(sessionPatient.toString());
        this.patientRepository.save(patient);
        this.appointmentRepository.save(selectedAppointment);
        return "patientForm";
    }


}//end class
