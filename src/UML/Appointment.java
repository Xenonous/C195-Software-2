package UML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * UML class
 *
 * @author Dylan Franklin
 */
public class Appointment {

    private final ObservableList<Customer> associatedCustomer = FXCollections.observableArrayList();
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    // private String appointmentContact;
    private String appointmentType;
    private String appointmentStartDateTime;
    private String appointmentEndDateTime;
    private int customerID;
    private int userID;
    private int contactID;


    /**
     * Constructor for Appointments
     *
     * @param appointmentID
     * @param appointmentTitle
     * @param appointmentDescription
     * @param appointmentLocation
     * @param appointmentType
     * @param appointmentStartDateTime
     * @param appointmentEndDateTime
     * @param customerID
     * @param userID
     * @param contactID
     */
    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, String appointmentStartDateTime, String appointmentEndDateTime, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        // this.appointmentContact = appointmentContact;
        this.appointmentType = appointmentType;
        this.appointmentStartDateTime = appointmentStartDateTime;
        this.appointmentEndDateTime = appointmentEndDateTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    //APPOINTMENT TITLE
    public String getAppointmentTitle() {
        return appointmentTitle;
    }
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    //APPOINTMENT DESCRIPTION
    public String getAppointmentDescription() {
        return appointmentDescription;
    }
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    //APPOINTMENT LOCATION
    public String getAppointmentLocation() {
        return appointmentLocation;
    }
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    /* APPOINTMENT CONTACT
    public String getAppointmentContact() {
        return appointmentContact;
    }
    public void setAppointmentContact(String appointmentContact) {
        this.appointmentContact = appointmentContact;
    }
    */

    //APPOINTMENT TYPE
    public String getAppointmentType() {
        return appointmentType;
    }
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    //APPOINTMENT START DATE/TIME
    public String getAppointmentStartDateTime() {
        return appointmentStartDateTime;
    }
    public void setAppointmentStartDateTime(String appointmentStartDateTime) {
        this.appointmentStartDateTime = appointmentStartDateTime;
    }

    //APPOINTMENT END DATE/TIME
    public String getAppointmentEndDateTime() {
        return appointmentEndDateTime;
    }
    public void setAppointmentEndDateTime(String appointmentEndDateTime) {
        this.appointmentEndDateTime = appointmentEndDateTime;
    }

    //APPOINTMENT CUSTOMER ID
    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    //APPOINTMENT USER ID
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    //APPOINTMENT CONTACT ID
    public int getContactID() {
        return contactID;
    }
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }


    public void addAssociatedCustomer(Customer customer) {

        associatedCustomer.add(customer);

    }


    public ObservableList<Customer> getAllAssociatedCustomer() {

        return associatedCustomer;

    }


    public void deleteAssociatedCustomer(Customer customer) {

        associatedCustomer.remove(customer);

    }
}
