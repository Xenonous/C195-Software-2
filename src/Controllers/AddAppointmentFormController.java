package Controllers;
import C195.JDBC;
import DataAccess.AppointmentDataAccess;
import DataAccess.CustomerDataAccess;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddAppointmentFormController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private Text addAppointmentText;

    @FXML
    private Text contactText;

    @FXML
    private Text customerIDText;

    @FXML
    private ComboBox<Customer> customerIDComboBox;

    @FXML
    private Text descriptionText;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Text idText;

    @FXML
    private TextField idTextField;

    @FXML
    private Text locationText;

    @FXML
    private TextField locationTextField;

    @FXML
    private Text titleText;

    @FXML
    private TextField titleTextField;

    @FXML
    private Text userIDText;

    @FXML
    private ComboBox<Users> userIDComboBox;

    @FXML
    private Text typeText;

    @FXML
    private TextField typeTextField;

    @FXML
    private Text startDateText;

    @FXML
    private DatePicker startDateTimePicker;

    @FXML
    private Text startTimeText;

    @FXML
    private TextField startTimeTextField;

    @FXML
    private Text endDateText;

    @FXML
    private DatePicker endDateTimePicker;

    @FXML
    private Text endTimeText;

    @FXML
    private TextField endTimeTextField;

    @FXML
    private ComboBox<Contacts> contactComboBox;

    @FXML
    void onActionBackAppointmentsForm(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/AppointmentsForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSaveAppointment(ActionEvent event) throws IOException, SQLException {


        if (titleTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() || typeTextField.getText().isEmpty() || startDateTimePicker.toString().isEmpty() || startTimeTextField.getText().isEmpty() || endDateTimePicker.toString().isEmpty() || endTimeTextField.getText().isEmpty() || customerIDComboBox.getSelectionModel().isEmpty() || userIDComboBox.getSelectionModel().isEmpty() || contactComboBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("MISSING INFORMATION");
            alert.setContentText("Missing key information. Please double check that all fields are populated BESIDES appointment ID");
            alert.showAndWait();
        }

        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION");
            alert.setHeaderText("Appointment Addition");
            alert.setContentText("Do you really want to ADD the following appointment to the database?");
            Optional<ButtonType> OKButton = alert.showAndWait();

            if (OKButton.isPresent() && OKButton.get() == ButtonType.OK) {

                try {


                    String contactName = String.valueOf(contactComboBox.getSelectionModel().getSelectedItem());
                    int selectedContactID = AppointmentDataAccess.getContactID(contactName);
                    int selectedCustomerID = Integer.parseInt(String.valueOf(customerIDComboBox.getSelectionModel().getSelectedItem()));
                    int selectedUserID = Integer.parseInt(String.valueOf(userIDComboBox.getSelectionModel().getSelectedItem()));

                    String startDateTime = startDateTimePicker.getValue() + " " + startTimeTextField.getText();
                    String endDateTime = endDateTimePicker.getValue() + " " + endTimeTextField.getText();


                    String SQL = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
                    ps.setInt(1, AppointmentDataAccess.getNewAppointmentID());
                    ps.setString(2, titleTextField.getText());
                    ps.setString(3, descriptionTextField.getText());
                    ps.setString(4, locationTextField.getText());
                    ps.setString(5, typeTextField.getText());
                    ps.setString(6, startDateTime);
                    ps.setString(7, endDateTime);
                    ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setString(9, "admin");
                    ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
                    ps.setString(11, "admin");
                    ps.setInt(12, selectedCustomerID);
                    ps.setInt(13, selectedUserID);
                    ps.setInt(14, selectedContactID);
                    ps.execute();


                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/fxml/AppointmentsForm.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }

                catch(Exception ex) {
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
