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

public class AppointmentsFormController implements Initializable {
    Stage stage;
    Parent scene;

    public void getZoneID() {
        ZoneId zoneId = ZoneId.systemDefault();
        displayRegionText.setText(String.valueOf(zoneId));
    }

    @FXML
    private Button addAppointmentButton;

    @FXML
    private TableView<Appointment> appointmentTableView; //Appointment

    @FXML
    private TableColumn<Appointment,String> appointmentContactColumn;

    @FXML
    private TableColumn<Appointment,String> appointmentDescriptionColumn;

    @FXML
    private TableColumn<Appointment,String> appointmentEndDateTimeColumn;

    @FXML
    private TableColumn<Appointment,Integer> appointmentIDColumn;

    @FXML
    private TableColumn<Appointment,String> appointmentLocationColumn;

    @FXML
    private TableColumn<Appointment,String> appointmentStartDateTimeColumn;

    @FXML
    private TableColumn<Appointment,String> appointmentTitleColumn;

    @FXML
    private TableColumn<Appointment,String> appointmentTypeColumn;

    @FXML
    private TableColumn<Appointment,Integer> appointmentUserID;

    @FXML
    private TableColumn<Appointment,Integer> appointmentCustomerIDColumn; //Maybe change to Customer type?

    @FXML
    private Text regionText;

    @FXML
    private Text displayRegionText;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteAppointmentButton;

    @FXML
    private Button modifyAppointmentButton;

    @FXML
    void onActionBackMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/MainMenuForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/AddAppointmentForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

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

    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {

        if(appointmentTableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("SELECTION ERROR");
            alert.setContentText("Please select the Appointent you wish to delete from the table.");
            alert.showAndWait();
        }

        else if (!appointmentTableView.getSelectionModel().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION");
            alert.setHeaderText("Appointment Deletion");
            alert.setContentText("Do you really want to DELETE the selected Appointment from the database?");
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
                alert.setContentText("The selected Appointment has been successfully deleted from the database.");

                appointmentTableView.refresh();



            }
        }
    }

    @FXML
    void onActionAllAppointmentsRadioButton(ActionEvent event) throws SQLException {

        appointmentTableView.setItems(AppointmentDataAccess.getAllAppointments());

    }

    @FXML
    void onActionAppointmentMonthRadioButton(ActionEvent event) {
        appointmentStartDateTimeColumn.setSortType(TableColumn.SortType.ASCENDING);
        appointmentTableView.getSortOrder().add(appointmentStartDateTimeColumn);
        appointmentTableView.sort();
    }

    @FXML
    void onActionAppointmentWeekRadioButton(ActionEvent event) {
        appointmentStartDateTimeColumn.setSortType(TableColumn.SortType.ASCENDING);
        appointmentTableView.getSortOrder().add(appointmentStartDateTimeColumn);
        appointmentTableView.sort();
    }


    public void initialize(URL url, ResourceBundle rb) {

        try {
            appointmentTableView.setItems(AppointmentDataAccess.getAllAppointments());
        }

        catch (SQLException throwables) {
            throwables.printStackTrace();
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
