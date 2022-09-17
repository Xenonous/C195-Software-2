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

/**
 * DataAccess class
 *
 * @author Dylan Franklin
 */
public class AppointmentDataAccess {

    private static final DateTimeFormatter datetimeDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ZoneId localZoneID = ZoneId.systemDefault();
    private static final ZoneId utcZoneID = ZoneId.of("UTC");

    private static int appointmentID = 0;

    /**
     * An ObservableList of all Appointments fetched.
     */
    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * An ObservableList of all Appointment contacts fetched.
     */
    private static final ObservableList<Appointment> allAppointmentsContact = FXCollections.observableArrayList();

    /**
     * An ObservableList of all Users fetched.
     */
    private static final ObservableList<Users> allUsers = FXCollections.observableArrayList();

    /**
     * Starting ID for Appointment.
     *
     * @return
     */
    public static int getNewAppointmentID() {

        appointmentID = appointmentID + 1;
        System.out.println(appointmentID);
        return appointmentID;
    }

    /**
     * Method that gets all Appointments from the database and translates them from the time that they were stored in the
     *     database (UTC), to LocalTime.
     *
     * @return
     * @throws SQLException
     */
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

    /**
     * Method that gets all Appointment contacts from the database.
     *
     * @param selectedContact
     * @return
     * @throws SQLException
     */
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

    /**
     * Method that gets all Appointments given the 'selectedCustomer' ID from the database.
     *
     * @param selectedCustomer
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllAppointmentsCustomer(String selectedCustomer) throws SQLException {

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

    /**
     * Method that gets all Appointments in the current month from the database.
     *
     * @return
     * @throws SQLException
     */
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

    /**
     * Method that gets all Appointments in the current week from the database.
     *
     * @return
     * @throws SQLException
     */
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

    /**
     * Method that gets all contacts from the database, which is part of Appointments.
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList<String> getAllContacts() throws SQLException {

        ObservableList<String> allContacts = FXCollections.observableArrayList();

        String SQL = "SELECT CONTACT_NAME, CONTACT_ID, EMAIL FROM CONTACTS";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String contactName = rs.getString(1);
            // System.out.println(rs.getString(1));
            Contacts contact = new Contacts(contactName);
            allContacts.add(String.valueOf(contact));
        }

        return allContacts;
    }

    /**
     * Method that gets all users from the database, which is part of Appointments.
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList<String> getAllUsers() throws SQLException {

        ObservableList<String> allUsers = FXCollections.observableArrayList();

        String SQL = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int userID = rs.getInt(1);
            // System.out.println(rs.getString(1));
            Users users = new Users(userID);
            allUsers.add(String.valueOf(users));
        }

        return allUsers;
    }

    /**
     * Method that given the contactName, will fetch the associated contactID from the database.
     *
     * @param contactName
     * @return
     * @throws SQLException
     */
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

    public static String getContactName (int contactID) throws SQLException {

        String contactName = null;
        String SQL = "SELECT CONTACT_NAME FROM CONTACTS WHERE CONTACT_ID = " + contactID;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            contactName = rs.getString(1);
        }
        return contactName;
    }

}
