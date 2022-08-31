package DataAccess;

import C195.JDBC;
import UML.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDataAccess {

    private static int appointmentID = 0;

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

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


        String SQL = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        while (rs.next()) {
            appointmentID = rs.getInt(1);
            String appointmentTitle = rs.getString(2);
            String appointmentDescription = rs.getString(3);
            String appointmentLocation = rs.getString(4);
            String appointmentType = rs.getString(5);
            String appointmentStartDateTime = rs.getString(6);
            String appointmentEndDateTIme = rs.getString(7);
            int customerID = rs.getInt(12);
            int userID = rs.getInt(13);
            int contactID = rs.getInt(14);




            Appointment newAppointment = new Appointment(appointmentID,appointmentTitle,appointmentDescription,appointmentLocation,appointmentType,appointmentStartDateTime,appointmentEndDateTIme,customerID,userID,contactID);
            allAppointments.add(newAppointment);

            // System.out.println(rs.getInt(1) + " " + (rs.getString(2)) + " " + (rs.getString(3)) + " " + (rs.getString(4)) + " " + (rs.getString(5)));

        }
        return allAppointments;
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
