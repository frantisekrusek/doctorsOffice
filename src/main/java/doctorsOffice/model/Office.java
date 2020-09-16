package doctorsOffice.model;

import doctorsOffice.data.AppointmentRepository;
import doctorsOffice.model.appointment.Appointment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class Office {

    private List<Appointment> updatedDates = new ArrayList<>();
    private AppointmentRepository appointmentRepository;

    protected Office(){
        log.info("LOG: Office Objekt/Bean wird erstellt");
    }
    //ctr

    @Bean
    public List<Appointment> createDates(AppointmentRepository appointmentRepository){
        log.info("LOG: Office: createDates() wird aufgerufen");

        //hole Termine aus Lager
        ArrayList<Appointment> datesFromDb = (ArrayList<Appointment>) appointmentRepository.findAll();
        //sortiere vergangene aus
        ArrayList<Appointment> pastDates = (ArrayList<Appointment>) datesFromDb.stream().filter(this::isBeforeNow).collect(Collectors.toList());
        ArrayList<Appointment> upcomingDates = (ArrayList<Appointment>) datesFromDb.stream().filter(this::isAfterNow).collect(Collectors.toList());
        log.info("LOG: Office: pastDates:" + pastDates.toString());
        log.info("LOG: Office: upcomingDates:" + upcomingDates.toString());
        //Vergleichsliste/Raster
        ArrayList<Appointment> gridDates = new ArrayList<>();
        gridDates.add(new Appointment(Appointment.calcNextMonday(),Appointment.calcNextMonday() + " Monday 8-9h", Appointment.calcNextMonday() + " 1 Monday 8-9h" ));
        gridDates.add(new Appointment(Appointment.calcNextTuesday(),Appointment.calcNextTuesday() + " Tuesday 8-9h", Appointment.calcNextTuesday() + " 2 Tuesday 8-9h" ));

        gridDates.add(new Appointment(Appointment.calcMondayInAWeek(),Appointment.calcMondayInAWeek() + " Monday 8-9h", Appointment.calcMondayInAWeek() + " 1 Monday 8-9h" ));
        gridDates.add(new Appointment(Appointment.calcTuesdayInAWeek(),Appointment.calcTuesdayInAWeek() + " Tuesday 8-9h", Appointment.calcTuesdayInAWeek() + " 2 Tuesday 8-9h" ));

        gridDates.add(new Appointment(Appointment.calcMondayIn2Weeks(),Appointment.calcMondayIn2Weeks() + " Monday 8-9h", Appointment.calcMondayIn2Weeks() + " 1 Monday 8-9h" ));
        gridDates.add(new Appointment(Appointment.calcTuesdayIn2Weeks(),Appointment.calcTuesdayIn2Weeks() + " Tuesday 8-9h", Appointment.calcTuesdayIn2Weeks() + " 2 Tuesday 8-9h" ));

        gridDates.add(new Appointment(Appointment.calcMondayIn3Weeks(),Appointment.calcMondayIn3Weeks() + " Monday 8-9h", Appointment.calcMondayIn3Weeks() + " 1 Monday 8-9h" ));
        gridDates.add(new Appointment(Appointment.calcTuesdayIn3Weeks(),Appointment.calcTuesdayIn3Weeks() + " Tuesday 8-9h", Appointment.calcTuesdayIn3Weeks() + " 2 Tuesday 8-9h" ));

        gridDates.add(new Appointment(Appointment.calcMondayIn4Weeks(),Appointment.calcMondayIn4Weeks() + " Monday 8-9h", Appointment.calcMondayIn4Weeks() + " 1 Monday 8-9h" ));
        gridDates.add(new Appointment(Appointment.calcTuesdayIn4Weeks(),Appointment.calcTuesdayIn4Weeks() + " Tuesday 8-9h", Appointment.calcTuesdayIn4Weeks() + " 2 Tuesday 8-9h" ));

        //aktuelle Liste
        updatedDates = new ArrayList<>();
        updatedDates.addAll(upcomingDates);
        for (Appointment ap:gridDates) {
            if (!(updatedDates.contains(ap))){
                updatedDates.add(ap);
            }
        }//end for
        Collections.sort(updatedDates);
        for (Appointment ap:updatedDates) {
                appointmentRepository.save(ap);
            log.info("LOG: Office: updatedDates-Liste (wird persistiert: " + ap.toString());
        }

        //Prüfe alle 24h, ob Termin in 4 Wochen hinzugefügt werden soll.
        // nach:
        //https://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ArrayList<Appointment> datesFromDb = (ArrayList<Appointment>) appointmentRepository.findAll();

                ArrayList<Appointment> pastDates = (ArrayList<Appointment>) datesFromDb.stream().filter(Office.this::isBeforeNow).collect(Collectors.toList());
                ArrayList<Appointment> upcomingDates = (ArrayList<Appointment>) datesFromDb.stream().filter(Office.this::isAfterNow).collect(Collectors.toList());

                ArrayList<Appointment> gridDates = new ArrayList<>();
                gridDates.add(new Appointment(Appointment.calcMondayIn4Weeks(),Appointment.calcMondayIn4Weeks() + " Monday 8-9h", Appointment.calcMondayIn4Weeks() + " 1 Monday 8-9h" ));
                gridDates.add(new Appointment(Appointment.calcTuesdayIn4Weeks(),Appointment.calcTuesdayIn4Weeks() + " Tuesday 8-9h", Appointment.calcTuesdayIn4Weeks() + " 2 Tuesday 8-9h" ));
                //aktuelle Liste
                ArrayList<Appointment> updatedDates = new ArrayList<>();
                updatedDates.addAll(upcomingDates);
                for (Appointment ap:gridDates) {
                    if (!(updatedDates.contains(ap))){
                        updatedDates.add(ap);
                    }
                }//end for
                Collections.sort(updatedDates);
                for (Appointment ap:updatedDates) {
                    appointmentRepository.save(ap);
                    log.info("LOG: Office: updatedDates-Liste (wird persistiert: " + ap.toString());
                }
               // wiederhole dieses Update alle 24h:
            }
        }, 1000*60*60*24, 1000*60*60*24);
//
//       kommendeTermine = (ArrayList<Appointment>) appointmentRepository.findAll();
//
//        System.out.println("LOG Office 'nach Persistenz' Liste 'kommendeTermine:" + kommendeTermine);
//        for (Appointment appointment:kommendeTermine) {
//            System.out.println(appointment.toString());
//        }
        return updatedDates;
    }//end createDates()

    public List<Appointment> update(AppointmentRepository appointmentRepository) {
                updatedDates = (List<Appointment>) appointmentRepository.findAll();
        return updatedDates;
    }

    public boolean isBeforeNow(Appointment appointment){
        LocalDate appDate = appointment.getLocalDate();
        return appDate.isBefore(LocalDate.now());
    }

    public boolean isAfterNow(Appointment appointment){
        LocalDate appDate = appointment.getLocalDate();
        return !(appDate.isBefore(LocalDate.now()));
    }

    public List<Appointment> getUpdatedDates() {
        return updatedDates;
    }
}//end class
