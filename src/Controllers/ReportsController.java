package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<?> appointmentTableView;

    @FXML
    private TableColumn<?, ?> appointmentContactColumn;

    @FXML
    private TableColumn<?, ?> appointmentCustomerIDColumn;

    @FXML
    private TableColumn<?, ?> appointmentDescriptionColumn;

    @FXML
    private TableColumn<?, ?> appointmentEndDateTimeColumn;

    @FXML
    private TableColumn<?, ?> appointmentIDColumn;

    @FXML
    private TableColumn<?, ?> appointmentStartDateTimeColumn;

    @FXML
    private TableView<?> smallAppointmentTableView;

    @FXML
    private TableColumn<?, ?> appointmentTitleColumn;

    @FXML
    private TableColumn<?, ?> appointmentTypeColumn;

    @FXML
    private TextField appointmentsMonthTextField;

    @FXML
    private TextField appointmentsTypeTextField;

    @FXML
    private ComboBox<?> contactComboBox;

    @FXML
    private ComboBox<?> customerComboBox;

    @FXML
    void onActionBackMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/MainMenuForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSearchContact(ActionEvent event) {

    }

    @FXML
    void onActionSearchCustomer(ActionEvent event) {

    }

    @FXML
    void onActionSearchMonth(ActionEvent event) {

    }

    @FXML
    void onActionSearchType(ActionEvent event) {

    }

    public void initialize(URL url, ResourceBundle rb) {


    }


}
