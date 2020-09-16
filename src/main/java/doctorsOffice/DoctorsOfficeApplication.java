package doctorsOffice;

import doctorsOffice.data.AppointmentRepository;
import doctorsOffice.model.appointment.Appointment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.SpringVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
@SpringBootApplication
public class DoctorsOfficeApplication {

    {log.info("LOG: DoctorsOfficeApplication Objekt/Bean wird erstellt");
    }
    public static void main(String[] args) {
        SpringApplication.run(DoctorsOfficeApplication.class, args);

        System.out.println("LOG: Spring-Version: " + SpringVersion.getVersion());
    }//end main

    @Bean
    public CommandLineRunner updateAppointments(AppointmentRepository appointmentRepository){

        return args -> {
        };
    }//end updateAppointments()


}
