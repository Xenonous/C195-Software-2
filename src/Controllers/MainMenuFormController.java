package Controllers;
import C195.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

public class MainMenuFormController {
    Stage stage;
    Parent scene;

    @FXML
    private Button CustomerAppointmentsButton;

    @FXML
    private Button customerRecordsButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Text loginSuccessfulText;

    @FXML
    private Text mainMenuText;

    @FXML
    private Button testButton3;

    @FXML
    private Text welcomeText;

    @FXML
    void onActionCustomerAppointments(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/AppointmentsForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionCustomerRecords(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/CustomerRecordsForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

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

}
