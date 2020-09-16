package doctorsOffice.model.appointment;

import doctorsOffice.model.person.patient.Patient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

@Slf4j
@Data
@Entity
public class Appointment implements Comparable<Appointment>, Serializable{

    private static final long version = 1L;

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //private long id;
    private String id;
    //Dauer eines Termins ist immer 1h.
    //name wird verwendet, um das Zeitfenster näher zu beschreiben.
    private String name;
    private boolean belegt;
    private LocalDate localDate;
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    private Date updateAt;
    @ManyToOne
    private Patient patient;

    //ohne default-ctr:
    //org.hibernate.InstantiationException: No default constructor for entity:  : doctorsOffice.model.appointment.Appointment
    //ctr
    public Appointment() {}
    //ctr

    public Appointment(LocalDate pLocalDate, String pName, String pId){
        this.localDate = pLocalDate;
        this.name = pName;
        this.id = pId;
        //this.id = ++counter;
    } //end ctr

    //finde jeweils nächsten Montag
    //gefunden auf https://stackoverflow.com/questions/24177516/get-first-next-monday-after-certain-date
    public static LocalDate calcNextMonday(){
    LocalDate localDate = LocalDate.now();
    localDate = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        return localDate;
    }
    public static LocalDate calcMondayInAWeek(){
        return Appointment.calcNextMonday().plusDays(7);
    }
    public static LocalDate calcMondayIn2Weeks(){
        return Appointment.calcNextMonday().plusDays(14);
    }
    public static LocalDate calcMondayIn3Weeks(){
        return Appointment.calcNextMonday().plusDays(21);
    }
    public static LocalDate calcMondayIn4Weeks(){
        return Appointment.calcNextMonday().plusDays(28);
    }
    //für Testzweck Datenbank
    public static LocalDate calcLastMonday(){
        return Appointment.calcNextMonday().plusDays(-7);
    }


    public static LocalDate calcNextTuesday(){
        LocalDate localDate = LocalDate.now();
        localDate = localDate.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
        return localDate;
    }
    public static LocalDate calcTuesdayInAWeek() {
        return Appointment.calcNextTuesday().plusDays(7);
    }
    public static LocalDate calcTuesdayIn2Weeks() {
        return Appointment.calcNextTuesday().plusDays(14);
    }
    public static LocalDate calcTuesdayIn3Weeks() {
        return Appointment.calcNextTuesday().plusDays(21);
    }
    public static LocalDate calcTuesdayIn4Weeks() {
        return Appointment.calcNextTuesday().plusDays(28);
    }


    @Override
    public boolean equals(Object object){
        Appointment appointment = (Appointment) object;
        String string1 = this.id;
        String string2 = appointment.getId();
        return string1.equals(string2);
    }

    @Override
    public int compareTo(Appointment o) {
        return this.id.compareTo(o.getId());
    }
}//end class
