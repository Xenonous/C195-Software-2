package Controllers;

import C195.JDBC;
import DataAccess.AppointmentDataAccess;
import DataAccess.CustomerDataAccess;
import UML.Appointment;
import UML.Contacts;
import UML.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Dylan Franklin
 */
public class ReportsController implements Initializable {

    Stage stage;
    Parent scene;

    private static final DateTimeFormatter datetimeDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ZoneId localZoneID = ZoneId.systemDefault();
    private static final ZoneId utcZoneID = ZoneId.of("UTC");

    //--------------------Appointment TableView-----------------------------

    @FXML
    private TableView<Appointment> appointmentTableViewReport;

    /**
     * Appointment contact column
     */
    @FXML
    private TableColumn<Appointment, String> appointmentContactColumn;

    /**
     * Appointment ID column
     */
    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerIDColumn;

    /**
     * Appointment customerID column
     */
    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionColumn;

    /**
     * Appointment endDateTime column
     */
    @FXML
    private TableColumn<Appointment, String> appointmentEndDateTimeColumn;

    /**
     * Appointment ID column
     */
    @FXML
    private TableColumn<Appointment, Integer> appointmentIDColumn;

    /**
     * Appointment startDateTime column
     */
    @FXML
    private TableColumn<Appointment, String> appointmentStartDateTimeColumn;

    /**
     * Appointment title column
     */
    @FXML
    private TableColumn<Appointment, String> appointmentTitleColumn;

    /**
     * Appointment type column
     */
    @FXML
    private TableColumn<Appointment, String> appointmentTypeColumn;

    /**
     * TextField used to collect month
     */
    @FXML
    private TextField appointmentsMonthTextField;

    /**
     * TextField used to collect type
     */
    @FXML
    private TextField appointmentsTypeTextField;

    /**
     * ComboBox used to collect contact
     */
    @FXML
    private ComboBox<Contacts> contactComboBox;

    /**
     * ComboBox used to collect customer
     */
    @FXML
    private ComboBox<Customer> customerComboBox;

    /**
     * Returns the user to the 'MainMenuForm.fxml' form.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionBackMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/MainMenuForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Button used to search for appointments by type and populate appointmentTableView with the results.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionSearchType(ActionEvent event) throws SQLException {

        if(appointmentsTypeTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please input an appointment type");
            alert.showAndWait();
        }
        else {
            String getAppointmentType = appointmentsTypeTextField.getText();
            int numberOfAppointments = 0;

            String SQL = "SELECT TYPE FROM APPOINTMENTS WHERE TYPE = '" + getAppointmentType + "'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                numberOfAppointments++;
            }

            if (numberOfAppointments == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Type Results");
                alert.setContentText("There are ZERO appointments under the appointment type " + "'" + getAppointmentType + "'");
                alert.showAndWait();
            } else if (numberOfAppointments <= 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Type Results");
                alert.setContentText("There's " + numberOfAppointments + " appointment(s) under the type name " + "'" + getAppointmentType + "'");
                alert.showAndWait();
            }
        }
    }

    /**
     * Button used to search for appointments by month and populate appointmentTableView with the results.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionSearchMonth(ActionEvent event) throws SQLException {

        if(appointmentsMonthTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please input a month");
            alert.showAndWait();
        }
        else {
            String getAppointmentMonth = appointmentsMonthTextField.getText(); //MM
            int numberOfAppointments = 0;

            String SQL = "SELECT START FROM APPOINTMENTS";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getString(1).substring(5, 7).equals(getAppointmentMonth)) {
                    numberOfAppointments++;
                }
            }

            if (numberOfAppointments == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Month Results");
                alert.setContentText("There are ZERO appointments under the given month " + "'" + getAppointmentMonth + "'");
                alert.showAndWait();
            } else if (numberOfAppointments >= 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Month Results");
                alert.setContentText("There are " + numberOfAppointments + " appointments under the given month " + "'" + getAppointmentMonth + "'");
                alert.showAndWait();
            }
        }
    }

    /**
     * Button used to search for appointments by contact and populate appointmentTableView with the results.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionSearchContact(ActionEvent event) throws SQLException {

        Contacts selectedContact = contactComboBox.getSelectionModel().getSelectedItem();
        int contactID = AppointmentDataAccess.getContactID(String.valueOf(selectedContact));
        appointmentTableViewReport.setItems(AppointmentDataAccess.getAllAppointmentsContact(contactID));

    }

    /**
     * Button used to search for appointments by Customer and populate appointmentTableView with the results.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionSearchCustomer(ActionEvent event) throws SQLException {

        Customer selectedCustomer = customerComboBox.getSelectionModel().getSelectedItem();
        appointmentTableViewReport.setItems(AppointmentDataAccess.getAllAppointmentsCustomer(selectedCustomer));

    }

    /**
     * Setup for the appointmentTableView and populates the appointmentTableView with all appointments in the database.
     *     Also populates the ComboBoxes
     *
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {

        try {
            appointmentTableViewReport.setItems(AppointmentDataAccess.getAllAppointments());
            contactComboBox.setPromptText("Select Contact");
            contactComboBox.setItems(AppointmentDataAccess.getAllContacts());
            customerComboBox.setPromptText("Select Customer");
            customerComboBox.setItems(CustomerDataAccess.getAllCustomers());
        }

        catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDateTime"));
        appointmentEndDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDateTime"));
        appointmentCustomerIDColumn.setCellValueFactory((new PropertyValueFactory<>("customerID")));
        appointmentContactColumn.setCellValueFactory((new PropertyValueFactory<>("contactID")));
    }
}
