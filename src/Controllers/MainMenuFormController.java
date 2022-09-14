package Controllers;
import C195.JDBC;
import java.time.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Dylan Franklin
 */
public class MainMenuFormController implements Initializable {
    Stage stage;
    Parent scene;

    final private static DateTimeFormatter datetimeDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    final private static ZoneId localZoneID = ZoneId.systemDefault();
    final private static ZoneId utcZoneID = ZoneId.of("UTC");

    /**
     * Information Text / Buttons
     */
    @FXML
    private Text loginSuccessfulText;

    @FXML
    private Text mainMenuText;

    @FXML
    private Text welcomeText;

    @FXML
    private Button CustomerAppointmentsButton;

    @FXML
    private Button ReportsButton;

    @FXML
    private Button customerRecordsButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button testButton3;

    /**
     * Brings the user to the 'AppointmentsForm.fxml' menu.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCustomerAppointments(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/AppointmentsForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Brings the user to the 'CustomersRecordsForm.fxml' menu
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCustomerRecords(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/CustomerRecordsForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Brings the user to the 'ReportsForm.fxml' menu
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionReports(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/ReportsForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Brings the user to the 'LoginForm.fxml' menu
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionLogout(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING");
        alert.setHeaderText("Logout Confirmation");
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> OKButton = alert.showAndWait();
        if (OKButton.isPresent() && OKButton.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/fxml/LoginForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }

    }

    /**
     * Closes the application entirely.
     *
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING");
        alert.setHeaderText("Exit Confirmation");
        alert.setContentText("Are you sure you want to exit the program?");
        Optional<ButtonType> OKButton = alert.showAndWait();
        if (OKButton.isPresent() && OKButton.get() == ButtonType.OK) {

            JDBC.closeConnection();
            System.exit(0);

        }
    }

    /**
     * Urgent Appointment reminder. Grabs the users LocalTime and compares it with the database appointment start/end times.
     *     Displays an alert if there is an appointment within 15min of the users LocalTime.
     *     Also displays an alert if there is no appointment within 15min.
     *
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {


        try {

            boolean isAppointment = false;
            LocalDateTime localDateTime = LocalDateTime.now(localZoneID);
            System.out.println(localDateTime);

            String SQL = "SELECT START,APPOINTMENT_ID FROM APPOINTMENTS";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                LocalDateTime utcStartDT = LocalDateTime.parse(rs.getString(1), datetimeDTF);
                ZonedDateTime localZoneStart = utcStartDT.atZone(utcZoneID).withZoneSameInstant(localZoneID);

                //System.out.println(localZoneStart);
                //System.out.println("THE DIFFERENCE BETWEEN THE TWO ARE :" + localDateTime.until(localZoneStart, ChronoUnit.MINUTES));

                long minUntilAppointment = localDateTime.until(localZoneStart, ChronoUnit.MINUTES);
                if(minUntilAppointment <=15 && minUntilAppointment >= 0) {

                    isAppointment = true;
                    int appointmentID = rs.getInt(2);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setHeaderText("Appointment Soon!");
                    alert.setContentText("There is an appointment within the next 15min!\n" + "Appointment Information:----------\n" + "Appointment ID: " + appointmentID +"\nAppointment Date and Time: " + localZoneStart);
                    alert.showAndWait();

                }
                else {

                    isAppointment = false;
                    continue;
                }

            }

            if(!isAppointment) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("No upcoming appointments.");
                alert.setContentText("There are no upcoming appointments.");
                alert.showAndWait();
            }
        }

        catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

}
