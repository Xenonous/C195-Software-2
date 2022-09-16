package Controllers;
import C195.JDBC;
import DataAccess.AppointmentDataAccess;
import UML.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Dylan Franklin
 */
public class AppointmentsFormController implements Initializable {
    Stage stage;
    Parent scene;

    /**
     * Gets the systems default zoneId and displays it on the current form.
     */
    public void getZoneID() {
        ZoneId zoneId = ZoneId.systemDefault();
        displayRegionText.setText(String.valueOf(zoneId));
    }

    //--------------------Appointment TableView-----------------------------

    @FXML
    private TableView<Appointment> appointmentTableView;

    /**
     * Appointment contact column
     */
    @FXML
    private TableColumn<Appointment,String> appointmentContactColumn;

    /**
     * Appointment description column
     */
    @FXML
    private TableColumn<Appointment,String> appointmentDescriptionColumn;

    /**
     * Appointment endDateTime column
     */
    @FXML
    private TableColumn<Appointment,String> appointmentEndDateTimeColumn;

    /**
     * Appointment ID column
     */
    @FXML
    private TableColumn<Appointment,Integer> appointmentIDColumn;

    /**
     * Appointment location column
     */
    @FXML
    private TableColumn<Appointment,String> appointmentLocationColumn;

    /**
     * Appointment startDateTime column
     */
    @FXML
    private TableColumn<Appointment,String> appointmentStartDateTimeColumn;

    /**
     * Appointment title column
     */
    @FXML
    private TableColumn<Appointment,String> appointmentTitleColumn;

    /**
     * Appointment type column
     */
    @FXML
    private TableColumn<Appointment,String> appointmentTypeColumn;

    /**
     * Appointment userID column
     */
    @FXML
    private TableColumn<Appointment,Integer> appointmentUserID;

    /**
     * Appointment customerID column
     */
    @FXML
    private TableColumn<Appointment,Integer> appointmentCustomerIDColumn;

    /**
     * Text that is changed to whatever the zoneId is.
     */
    @FXML
    private Text displayRegionText;

    /**
     * Buttons / Information Text
     */
    @FXML
    private Button backButton;

    @FXML
    private Button deleteAppointmentButton;

    @FXML
    private Button modifyAppointmentButton;

    @FXML
    private Button addAppointmentButton;

    @FXML
    private Text regionText;

    /**
     * Returns the user to the 'MainMenuForm.fxml' menu
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
     * Brings the user to the 'AddAppointmentForm.fxml' menu
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {

        appointmentTableView.getSortOrder().add(appointmentIDColumn);


        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/AddAppointmentForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Gets the users selected Appointment that's to be modified and carries over the information
     *     to the 'ModifyAppointmentForm.fxml' menu where it will auto-populate the TextFields/ComboBox.
     *     If there is no selected Appointment, an alert will show.
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException, SQLException {

        if (appointmentTableView.getSelectionModel().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Selection Error");
            alert.setContentText("Make sure you have an Appointment selected that you want to modify");
            alert.showAndWait();

        }
        if(!appointmentTableView.getSelectionModel().isEmpty()) {

            FXMLLoader Loader = new FXMLLoader(); //Setting up the FXMLLoader to transfer data.
            Loader.setLocation(getClass().getResource("/fxml/ModifyAppointmentForm.fxml"));
            Loader.load();

            ModifyAppointmentFormController ADMController = Loader.getController(); //Setting up the FXMLLoader to transfer data.
            ADMController.sendAppointment(appointmentTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow(); //Showing stage.
            Parent scene = Loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.showAndWait(); // "stage.show()" isn't used here, "stage.showAndWait" makes it so that code can be read after this line if needed.

        }
    }

    /**
     * Deletes a selected Appointment upon confirming that you want to do so.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {

        if(appointmentTableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("SELECTION ERROR");
            alert.setContentText("Please select the Appointment you wish to delete from the table.");
            alert.showAndWait();
        }

        else if (!appointmentTableView.getSelectionModel().isEmpty()) {

            int appointmentID = appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentID();
            String appointmentType = appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentType();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION");
            alert.setHeaderText("Appointment Deletion");
            alert.setContentText("Do you really want to DELETE the selected Appointment from the database? \n APPOINTMENT ID: " + appointmentID + "\n APPOINTMENT TYPE: " + appointmentType);
            Optional<ButtonType> OKButton = alert.showAndWait();

            if (OKButton.isPresent() && OKButton.get() == ButtonType.OK) {

                Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

                String SQL = "DELETE APPOINTMENTS FROM APPOINTMENTS WHERE Appointment_ID = " + selectedAppointment.getAppointmentID();
                PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
                ps.execute();

                System.out.println("Deletion Successful!");


                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Appointment Deletion");
                alert.setContentText("The selected Appointment has been successfully deleted from the database. \n APPOINTMENT ID: " + appointmentID + " \n APPOINTMENT TYPE: " + appointmentType);
                alert.showAndWait();

                appointmentTableView.setItems(AppointmentDataAccess.getAllAppointments());



            }
        }
    }

    /**
     * RadioButton that shows all Appointments.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionAllAppointmentsRadioButton(ActionEvent event) throws SQLException {

        appointmentTableView.setItems(AppointmentDataAccess.getAllAppointments());

    }

    /**
     * RadioButton that shows/filters all Appointments in the current month
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionAppointmentMonthRadioButton(ActionEvent event) throws SQLException {

        appointmentTableView.setItems(AppointmentDataAccess.getAllAppointmentsMonth());

    }

    /**
     * RadioButton that shows/filters all Appointments in the current week.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionAppointmentWeekRadioButton(ActionEvent event) throws SQLException {

        appointmentTableView.setItems(AppointmentDataAccess.getAllAppointmentsWeek());

    }


    /**
     * Setup for the appointmentTableView and populates the appointmentTableView with all appointments in the database.
     *     Also fetches the zoneId to display it to the user on the current form.
     *
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {

        try {
            appointmentTableView.setItems(AppointmentDataAccess.getAllAppointments());
            appointmentTableView.getSortOrder().add(appointmentIDColumn);
        }

        catch (SQLException throwable) {
            throwable.printStackTrace();
        }



        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        appointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentStartDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDateTime"));
        appointmentEndDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDateTime"));
        appointmentCustomerIDColumn.setCellValueFactory((new PropertyValueFactory<>("customerID")));
        appointmentUserID.setCellValueFactory((new PropertyValueFactory<>("userID")));
        appointmentContactColumn.setCellValueFactory((new PropertyValueFactory<>("contactID")));

        getZoneID();

    }

}
