package doctorsOffice.model.person.patient;

import lombok.AccessLevel;
import lombok.Data;
import doctorsOffice.model.person.Person;
import doctorsOffice.model.appointment.Appointment;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.sql.*;

@Slf4j
@Data
@Entity
@NoArgsConstructor
public class Patient {


    private String firstName, lastName;
    private String telNo;
    @OneToOne(targetEntity = Appointment.class)
    private Appointment appointment;
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;
    @Id
    private String email;

    {log.info("LOG: Patient Objekt/Bean wird erstellt");
    }

    //WICHTIG für JPA !!!
    //trägt appointment_id in die Datenbank ein.
    public void setAppointment(Appointment appointment){
        this.appointment=appointment;
    }

    public Appointment terminStornieren(Appointment pAppointment) throws ClassNotFoundException, SQLException {
        pAppointment.setBelegt(false);
        String termin = pAppointment.getName();
        Connection con;
        String sql2 = "jdbc:mysql://localhost/termindb";
        //int id;
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(sql2, "root", "");
        System.out.println("Connection errichtet");
        Statement statement = con.createStatement();
        //wähle Spalten aus Tabelle
        statement.executeUpdate("UPDATE termintabelle " +
                "SET belegt = false, vornamePatient = null, nachnamePatient = null, telNr = null" +
                "  WHERE termin = " + "\"" + termin + "\"");


        statement.close();
        con.close();
        return pAppointment;
    }//end terminReservieren()



    @Override
    public String toString() {
        String termin;
        if(!(null == appointment)){
            termin = appointment.getName();
        }else {
            termin = "none";
        }
        return this.getFirstName() + " " + this.getLastName()
                + " Tel " + telNo + ", Appointment: " +  termin + "/" + email;
    }
}
