package doctorsOffice.model.person.administrator;

import doctorsOffice.model.person.Person;
import doctorsOffice.model.appointment.Appointment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Administrator extends Person {
    private Appointment appointment;


    public Appointment terminFensterErzeugen(Appointment pAppointment, String pTerminBezeichnung) throws ClassNotFoundException, SQLException {
        //methode soll:
        //neues Termin Sub-Objekt erzeugen zB "Montag 9-10 Uhr"
        //alle Werte einsetzen
        //in Datenbank termin-Zeile erzeugen

        //Ziel: neue Zeilen-Einträge in Datenbank 'termindb', Tabelle 'termintabelle' machen:
        //TabellenFields: id int, termin varchar, belegt bit(1), vornamePatient varchar, nachnamePatient varchar, telNr varchar.
        //int id;
        String termin, vorname, nachname, telNr;
        boolean belegt;
        Connection con;
        //url bzw Name der Datenbank
        String sql1 = "jdbc:mysql://localhost/termindb?user=root";
        String sql2 = "jdbc:mysql://localhost/termindb";
        //Treiber starten
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(sql1);
        System.out.println("Connection errichtet");
        Statement statement1 = con.createStatement();
        statement1.executeUpdate("INSERT INTO termintabelle VALUES (null, \"" + pTerminBezeichnung + "\", 0, null, null, null)");
        statement1.close();
        con.close();

        return pAppointment;
    }//end terminFensterErzeugen

    public void terminFensterLöschen(int pId) throws ClassNotFoundException, SQLException {
        Connection con;
        String sql1 = "jdbc:mysql://localhost/termindb?user=root";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(sql1);
        System.out.println("Connection errichtet");
        Statement statement1 = con.createStatement();
        statement1.executeUpdate("DELETE FROM termintabelle WHERE id = " + pId);
        System.out.println("Terminfenster gelöscht");
        statement1.close();
        con.close();
    }//end terminFensterLöschen

    //Overloading
    public void terminFensterLöschen(String terminBezeichnung) throws ClassNotFoundException, SQLException {
        Connection con;
        String sql1 = "jdbc:mysql://localhost/termindb?user=root";
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(sql1);
        System.out.println("Connection errichtet");
        Statement statement1 = con.createStatement();
        statement1.executeUpdate("DELETE FROM termintabelle WHERE termin = " + "\"" + terminBezeichnung + "\"");
        System.out.println("Terminfenster gelöscht");
        statement1.close();
        con.close();
    }//end terminFensterLöschen


}// end class
