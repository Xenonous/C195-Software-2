package Controllers;
import C195.JDBC;
import DataAccess.AppointmentDataAccess;
import DataAccess.CustomerDataAccess;
import UML.Appointment;
import UML.Contacts;
import UML.Customer;
import UML.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyAppointmentFormController implements Initializable {
    Stage stage;
    Parent scene;

    private void timeFormat() {
        //DATE
        String formattedStartDate = startTimeTextField.getText().substring(0,10);
        String formattedEndDate = endTimeTextField.getText().substring(0,10);
        startDateTimePicker.setValue(LocalDate.parse(formattedStartDate));
        endDateTimePicker.setValue(LocalDate.parse(formattedEndDate));

        //TIME
        String formattedStartTime = startTimeTextField.getText().substring(11,16);
        String formattedEndTime = endTimeTextField.getText().substring(11,16);
        startTimeTextField.setText(formattedStartTime);
        endTimeTextField.setText(formattedStartTime);

    }

    @FXML
    private Text addAppointmentText;

    @FXML
    private Text contactText;

    @FXML
    private Text customerIDText;

    @FXML
    private Text descriptionText;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Text endDateText;

    @FXML
    private Text endTimeText;

    @FXML
    private Text idText;

    @FXML
    private TextField idTextField;

    @FXML
    private Text locationText;

    @FXML
    private TextField locationTextField;

    @FXML
    private Text startDateText;

    @FXML
    private Text startTimeText;

    @FXML
    private Text titleText;

    @FXML
    private TextField titleTextField;

    @FXML
    private Text typeText;

    @FXML
    private TextField typeTextField;

    @FXML
    private Text userIDText;

    @FXML
    private ComboBox<Users> userIDComboBox;

    @FXML
    private ComboBox<Contacts> contactComboBox;

    @FXML
    private ComboBox<Customer> customerIDComboBox;

    @FXML
    private DatePicker startDateTimePicker;

    @FXML
    private TextField startTimeTextField;

    @FXML
    private DatePicker endDateTimePicker;

    @FXML
    private TextField endTimeTextField;

    public void sendAppointment(Appointment selectedAppointment) throws SQLException {
        idTextField.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        titleTextField.setText(String.valueOf(selectedAppointment.getAppointmentTitle()));
        descriptionTextField.setText(String.valueOf(selectedAppointment.getAppointmentDescription()));
        locationTextField.setText(String.valueOf(selectedAppointment.getAppointmentLocation()));
        typeTextField.setText((String.valueOf(selectedAppointment.getAppointmentType())));
        startTimeTextField.setText(String.valueOf(selectedAppointment.getAppointmentStartDateTime())); // DATE AND TIME?
        endTimeTextField.setText((String.valueOf(selectedAppointment.getAppointmentEndDateTime())));
        timeFormat();
    }

    @FXML
    void onActionBackAppointmentsForm(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/AppointmentsForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionModifyAppointment(ActionEvent event) throws SQLException, IOException {

        if (idTextField.getText().isEmpty() || titleTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() || typeTextField.getText().isEmpty() || startDateTimePicker.toString().isEmpty() || startTimeTextField.getText().isEmpty() || endDateTimePicker.toString().isEmpty() || endTimeTextField.getText().isEmpty() || contactComboBox.getSelectionModel().isEmpty() || userIDComboBox.getSelectionModel().isEmpty() || customerIDComboBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("MISSING INFORMATION");
            alert.setContentText("Missing key information. Please double check that all fields are populated BESIDES Appointment ID.");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION");
            alert.setHeaderText("Appointment Modification");
            alert.setContentText("Do you really want to MODIFY the following Appointment?");
            Optional<ButtonType> OKButton = alert.showAndWait();

            if (OKButton.isPresent() && OKButton.get() == ButtonType.OK) {

                try {

                    String contactName = String.valueOf(contactComboBox.getSelectionModel().getSelectedItem());
                    int selectedContactID = AppointmentDataAccess.getContactID(contactName);
                    int selectedCustomerID = Integer.parseInt(String.valueOf(customerIDComboBox.getSelectionModel().getSelectedItem()));
                    int selectedUserID = Integer.parseInt(String.valueOf(userIDComboBox.getSelectionModel().getSelectedItem()));

                    String startDateTime = startDateTimePicker.getValue() + " " + startTimeTextField.getText();
                    String endDateTime = endDateTimePicker.getValue() + " " + endTimeTextField.getText();


                    String SQL = "UPDATE APPOINTMENTS SET TITLE = ?, DESCRIPTION = ?, LOCATION = ?, TYPE = ?, START = ?, END = ?, CREATE_DATE = ?, CREATED_BY = ?, LAST_UPDATE = ?, LAST_UPDATED_BY = ?, CUSTOMER_ID = ?, USER_ID = ?, CONTACT_ID = ? WHERE Appointment_ID = " + idTextField.getText();
                    PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
                    ps.setString(1, titleTextField.getText());
                    ps.setString(2, descriptionTextField.getText());
                    ps.setString(3, locationTextField.getText());
                    ps.setString(4, typeTextField.getText());
                    ps.setString(5, startDateTime);
                    ps.setString(6, endDateTime);
                    ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setString(8, "admin");
                    ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setString(10, "admin");
                    ps.setInt(11, selectedCustomerID);
                    ps.setInt(12, selectedUserID);
                    ps.setInt(13, selectedContactID);
                    ps.execute();

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/fxml/AppointmentsForm.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                } catch (Exception ex) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("DATE/TIME FORMAT ERROR.");
                    alert.setContentText("Please make sure your start date/time AND end date/time are in the correct formats. \n" + "DATE = 'YYYY-MM-DD' \n" + "TIME = 'HH:MM'");
                    alert.showAndWait();
                }
            }
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        try {
            contactComboBox.setPromptText("Select Contact");
            contactComboBox.setItems(AppointmentDataAccess.getAllContacts());
            customerIDComboBox.setPromptText(("Select CustomerID"));
            customerIDComboBox.setItems(CustomerDataAccess.getAllCustomers());
            userIDComboBox.setPromptText(("Select UserID"));
            userIDComboBox.setItems(AppointmentDataAccess.getAllUsers());
        }

        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}


