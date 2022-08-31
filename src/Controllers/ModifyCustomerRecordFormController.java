package Controllers;
import C195.JDBC;
import DataAccess.CustomerDataAccess;
import UML.Countries;
import UML.Customer;
import UML.FirstLevelDivisions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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

public class ModifyCustomerRecordFormController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private Text IDText;

    @FXML
    private TextField IDTextField;

    @FXML
    private Text addressText;

    @FXML
    private TextField addressTextField;

    @FXML
    private Button backButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Text modifyCustomerText;

    @FXML
    private Text nameText;

    @FXML
    private TextField nameTextField;

    @FXML
    private Text phoneNumberText;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private Text postalCodeText;

    @FXML
    private TextField postalNumberTextField;

    @FXML
    private Text firstlevelDivisionText;

    @FXML
    private ComboBox<FirstLevelDivisions> firstlevelDivisionComboBox;

    @FXML
    private Text countryText;

    @FXML
    private ComboBox<Countries> countryComboBox;

    @FXML
    public void onActionDisplayFirstLevelDivisions(javafx.scene.input.MouseEvent mouseEvent) throws SQLException {
        // System.out.println("Divisions has been clicked.");
        if (countryComboBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Missing Country");
            alert.setContentText("Make sure a country is selected before selecting a division.");
            alert.showAndWait();
        }
        else if (!countryComboBox.getSelectionModel().isEmpty()) {
            //System.out.println("A country is selected");
            String selectedCountry = String.valueOf(countryComboBox.getSelectionModel().getSelectedItem());
            firstlevelDivisionComboBox.setItems((CustomerDataAccess.getFirstLevelDivisions(selectedCountry)));

        }
    }

    @FXML
    void onActionBackCustomersRecordForm(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/CustomerRecordsForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException, SQLException {
        if (nameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() || postalNumberTextField.getText().isEmpty() || phoneNumberTextField.getText().isEmpty() || countryComboBox.getSelectionModel().isEmpty() || firstlevelDivisionComboBox.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("MISSING INFORMATION");
            alert.setContentText("Missing key information. Please double check that all fields are populated BESIDES Customer ID.");
            alert.showAndWait();
        }

        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION");
            alert.setHeaderText("Customer Addition");
            alert.setContentText("Do you really want to MODIFY the following Customer?");
            Optional<ButtonType> OKButton = alert.showAndWait();

            if (OKButton.isPresent() && OKButton.get() == ButtonType.OK) {

                String firstLevelDivisionName = String.valueOf(firstlevelDivisionComboBox.getSelectionModel().getSelectedItem());
                int divisionID = CustomerDataAccess.getDivisionID(firstLevelDivisionName);

                String SQL = "UPDATE CUSTOMERS SET CUSTOMER_NAME = ?, ADDRESS = ?, POSTAL_CODE = ?, PHONE = ?, CREATE_DATE = ?, CREATED_BY = ?, LAST_UPDATE = ?, LAST_UPDATED_BY = ?, DIVISION_ID = ? WHERE CUSTOMER_ID = " + IDTextField.getText();
                PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
                ps.setString(1, nameTextField.getText());
                ps.setString(2, addressTextField.getText());
                ps.setString(3, postalNumberTextField.getText());
                ps.setString(4, phoneNumberTextField.getText());
                ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(6, "admin");
                ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(8, "admin");
                ps.setInt(9, divisionID);
                ps.execute();

                System.out.println("Customer #" + IDTextField.getText() + " (" + nameTextField.getText() + ") has been updated.");

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/fxml/CustomerRecordsForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        }
        // UPDATE CUSTOMERS SET CUSTOMER_ID = 8, CUSTOMER_NAME = 'Justice Franklin'
    }

    public void sendCustomer(Customer selectedCustomer) throws SQLException {


        IDTextField.setText(String.valueOf(selectedCustomer.getCustomerID()));
        nameTextField.setText(String.valueOf(selectedCustomer.getCustomerName()));
        addressTextField.setText(String.valueOf(selectedCustomer.getCustomerAddress()));
        postalNumberTextField.setText(String.valueOf(selectedCustomer.getCustomerPostalCode()));
        phoneNumberTextField.setText(String.valueOf(selectedCustomer.getCustomerPhoneNumber()));
        // Country and First-Level ComboBox?
    }


    public void initialize(URL url, ResourceBundle rb) {

        try {
            countryComboBox.setPromptText("Select Country...");
            firstlevelDivisionComboBox.setPromptText("Select Country First...");
            countryComboBox.setVisibleRowCount(3);
            countryComboBox.setItems(CustomerDataAccess.getAllCountries());
        }

        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}


