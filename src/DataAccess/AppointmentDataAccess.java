package DataAccess;

import C195.JDBC;
import UML.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.Month;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class AppointmentDataAccess {

    private static DateTimeFormatter datetimeDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static ZoneId localZoneID = ZoneId.systemDefault();
    private static ZoneId utcZoneID = ZoneId.of("UTC");

    private static int appointmentID = 0;

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    private static ObservableList<Appointment> allAppointmentsContact = FXCollections.observableArrayList();

    private static ObservableList<Users> allUsers = FXCollections.observableArrayList();

    public static int getNewAppointmentID() {

        appointmentID = appointmentID + 1;
        System.out.println(appointmentID);
        return appointmentID;
    }

    public static void addAppointment(Appointment newAppointment) throws SQLException {

        allAppointments.add(newAppointment);

    }

    public static ObservableList<Appointment> getAllAppointments() throws SQLException {

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String SQL = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            appointmentID = rs.getInt(1);
            String appointmentTitle = rs.getString(2);
            String appointmentDescription = rs.getString(3);
            String appointmentLocation = rs.getString(4);
            String appointmentType = rs.getString(5);
            String appointmentStartDateTime = rs.getString(6);
            String appointmentEndDateTime = rs.getString(7);
            int customerID = rs.getInt(12);
            int userID = rs.getInt(13);
            int contactID = rs.getInt(14);

            // System.out.println(appointmentStartDateTime + " " + appointmentEndDateTime);

            LocalDateTime utcStartDT = LocalDateTime.parse(appointmentStartDateTime, datetimeDTF);
            LocalDateTime utcEndDT = LocalDateTime.parse(appointmentEndDateTime, datetimeDTF);

            ZonedDateTime localZoneStart = utcStartDT.atZone(utcZoneID).withZoneSameInstant(localZoneID);
            ZonedDateTime localZoneEnd = utcEndDT.atZone(utcZoneID).withZoneSameInstant(localZoneID);

            String localAppointmentStartDateTime = localZoneStart.format(datetimeDTF);
            String localAppointmentEndDateTime = localZoneEnd.format(datetimeDTF);

            // System.out.println(localAppointmentStartDateTime + " " + localAppointmentEndDateTime);

            Appointment newAppointment = new Appointment(appointmentID,appointmentTitle,appointmentDescription,appointmentLocation,appointmentType,localAppointmentStartDateTime,localAppointmentEndDateTime,customerID,userID,contactID);
            allAppointments.add(newAppointment);

            // System.out.println(rs.getInt(1) + " " + (rs.getString(2)) + " " + (rs.getString(3)) + " " + (rs.getString(4)) + " " + (rs.getString(5)));

        }
        return allAppointments;
    }

    public static ObservableList<Appointment> getAllAppointmentsContact(int selectedContact) throws SQLException {

        ObservableList<Appointment> allAppointmentsContact = FXCollections.observableArrayList();

        String SQL = "SELECT * FROM APPOINTMENTS WHERE CONTACT_ID = " + selectedContact;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            appointmentID = rs.getInt(1);
            String appointmentTitle = rs.getString(2);
            String appointmentDescription = rs.getString(3);
            String appointmentLocation = rs.getString(4);
            String appointmentType = rs.getString(5);
            String appointmentStartDateTime = rs.getString(6);
            String appointmentEndDateTime = rs.getString(7);
            int customerID = rs.getInt(12);
            int userID = rs.getInt(13);
            int contactID = rs.getInt(14);

            // System.out.println(appointmentStartDateTime + " " + appointmentEndDateTime);

            LocalDateTime utcStartDT = LocalDateTime.parse(appointmentStartDateTime, datetimeDTF);
            LocalDateTime utcEndDT = LocalDateTime.parse(appointmentEndDateTime, datetimeDTF);

            ZonedDateTime localZoneStart = utcStartDT.atZone(utcZoneID).withZoneSameInstant(localZoneID);
            ZonedDateTime localZoneEnd = utcEndDT.atZone(utcZoneID).withZoneSameInstant(localZoneID);

            String localAppointmentStartDateTime = localZoneStart.format(datetimeDTF);
            String localAppointmentEndDateTime = localZoneEnd.format(datetimeDTF);

            // System.out.println(localAppointmentStartDateTime + " " + localAppointmentEndDateTime);

            Appointment newAppointment = new Appointment(appointmentID,appointmentTitle,appointmentDescription,appointmentLocation,appointmentType,localAppointmentStartDateTime,localAppointmentEndDateTime,customerID,userID,contactID);
            allAppointmentsContact.add(newAppointment);

            // System.out.println(rs.getInt(1) + " " + (rs.getString(2)) + " " + (rs.getString(3)) + " " + (rs.getString(4)) + " " + (rs.getString(5)));

        }
        return allAppointmentsContact;

    }

    public static ObservableList<Appointment> getAllAppointmentsCustomer(Customer selectedCustomer) throws SQLException {

        ObservableList<Appointment> allAppointmentsCustomer = FXCollections.observableArrayList();

        String SQL = "SELECT * FROM APPOINTMENTS WHERE CUSTOMER_ID = " + selectedCustomer;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            appointmentID = rs.getInt(1);
            String appointmentTitle = rs.getString(2);
            String appointmentDescription = rs.getString(3);
            String appointmentLocation = rs.getString(4);
            String appointmentType = rs.getString(5);
            String appointmentStartDateTime = rs.getString(6);
            String appointmentEndDateTime = rs.getString(7);
            int customerID = rs.getInt(12);
            int userID = rs.getInt(13);
            int contactID = rs.getInt(14);

            // System.out.println(appointmentStartDateTime + " " + appointmentEndDateTime);

            LocalDateTime utcStartDT = LocalDateTime.parse(appointmentStartDateTime, datetimeDTF);
            LocalDateTime utcEndDT = LocalDateTime.parse(appointmentEndDateTime, datetimeDTF);

            ZonedDateTime localZoneStart = utcStartDT.atZone(utcZoneID).withZoneSameInstant(localZoneID);
            ZonedDateTime localZoneEnd = utcEndDT.atZone(utcZoneID).withZoneSameInstant(localZoneID);

            String localAppointmentStartDateTime = localZoneStart.format(datetimeDTF);
            String localAppointmentEndDateTime = localZoneEnd.format(datetimeDTF);

            // System.out.println(localAppointmentStartDateTime + " " + localAppointmentEndDateTime);

            Appointment newAppointment = new Appointment(appointmentID,appointmentTitle,appointmentDescription,appointmentLocation,appointmentType,localAppointmentStartDateTime,localAppointmentEndDateTime,customerID,userID,contactID);
            allAppointmentsCustomer.add(newAppointment);

            // System.out.println(rs.getInt(1) + " " + (rs.getString(2)) + " " + (rs.getString(3)) + " " + (rs.getString(4)) + " " + (rs.getString(5)));

        }
        return allAppointmentsCustomer;

    }

    public static ObservableList<Appointment> getAllAppointmentsMonth() throws SQLException {

        ObservableList<Appointment> allAppointmentsMonth = FXCollections.observableArrayList();

        LocalDate currentDate = LocalDate.now();
        int currentMonthNumber = currentDate.getMonth().getValue();
        Month currentMonthName = currentDate.getMonth();
        System.out.println("Current month: " + currentMonthNumber + "(" + currentMonthName + ")");

        String SQL = "SELECT * FROM APPOINTMENTS WHERE MONTH(Start) = " + currentMonthNumber;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            appointmentID = rs.getInt(1);
            String appointmentTitle = rs.getString(2);
            String appointmentDescription = rs.getString(3);
            String appointmentLocation = rs.getString(4);
            String appointmentType = rs.getString(5);
            String appointmentStartDateTime = rs.getString(6);
            String appointmentEndDateTime = rs.getString(7);
            int customerID = rs.getInt(12);
            int userID = rs.getInt(13);
            int contactID = rs.getInt(14);


            LocalDateTime utcStartDT = LocalDateTime.parse(appointmentStartDateTime, datetimeDTF);
            LocalDateTime utcEndDT = LocalDateTime.parse(appointmentEndDateTime, datetimeDTF);

            ZonedDateTime localZoneStart = utcStartDT.atZone(utcZoneID).withZoneSameInstant(localZoneID);
            ZonedDateTime localZoneEnd = utcEndDT.atZone(utcZoneID).withZoneSameInstant(localZoneID);

            String localAppointmentStartDateTime = localZoneStart.format(datetimeDTF);
            String localAppointmentEndDateTime = localZoneEnd.format(datetimeDTF);


            Appointment newAppointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, localAppointmentStartDateTime, localAppointmentEndDateTime, customerID, userID, contactID);
            allAppointmentsMonth.add(newAppointment);
        }

        return allAppointmentsMonth;
    }

    public static ObservableList<Appointment> getAllAppointmentsWeek() throws SQLException {

        ObservableList<Appointment> allAppointmentsWeek = FXCollections.observableArrayList();

        LocalDate currentDate = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentMonthNumber = currentDate.getMonth().getValue();
        int currentWeekNumber = currentDate.get(weekFields.weekOfWeekBasedYear()) - 1;

        System.out.println(currentWeekNumber);

        String SQL = "SELECT * FROM APPOINTMENTS WHERE MONTH(Start) = " + currentMonthNumber + " AND WEEK(Start) = " + currentWeekNumber;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            appointmentID = rs.getInt(1);
            String appointmentTitle = rs.getString(2);
            String appointmentDescription = rs.getString(3);
            String appointmentLocation = rs.getString(4);
            String appointmentType = rs.getString(5);
            String appointmentStartDateTime = rs.getString(6);
            String appointmentEndDateTime = rs.getString(7);
            int customerID = rs.getInt(12);
            int userID = rs.getInt(13);
            int contactID = rs.getInt(14);


            LocalDateTime utcStartDT = LocalDateTime.parse(appointmentStartDateTime, datetimeDTF);
            LocalDateTime utcEndDT = LocalDateTime.parse(appointmentEndDateTime, datetimeDTF);

            ZonedDateTime localZoneStart = utcStartDT.atZone(utcZoneID).withZoneSameInstant(localZoneID);
            ZonedDateTime localZoneEnd = utcEndDT.atZone(utcZoneID).withZoneSameInstant(localZoneID);

            String localAppointmentStartDateTime = localZoneStart.format(datetimeDTF);
            String localAppointmentEndDateTime = localZoneEnd.format(datetimeDTF);


            Appointment newAppointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, localAppointmentStartDateTime, localAppointmentEndDateTime, customerID, userID, contactID);
            allAppointmentsWeek.add(newAppointment);

        }

        return allAppointmentsWeek;
    }

    public static ObservableList<Contacts> getAllContacts() throws SQLException {

        ObservableList<Contacts> allContacts = FXCollections.observableArrayList();

        String SQL = "SELECT CONTACT_NAME, CONTACT_ID, EMAIL FROM CONTACTS";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String contactName = rs.getString(1);
            // System.out.println(rs.getString(1));
            Contacts contact = new Contacts(contactName);
            allContacts.add(contact);
        }

        return allContacts;
    }

    public static ObservableList<Users> getAllUsers() throws SQLException {

        ObservableList<Users> allUsers = FXCollections.observableArrayList();

        String SQL = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int userID = rs.getInt(1);
            // System.out.println(rs.getString(1));
            Users users = new Users(userID);
            allUsers.add(users);
        }

        return allUsers;
    }

    public static int getContactID(String contactName) throws SQLException {
        int contactID = 0;
        String SQL = "SELECT CONTACT_ID FROM CONTACTS WHERE CONTACT_NAME = " + "'" + contactName + "'";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            contactID = rs.getInt(1);
        }

        return contactID;
    }

}
