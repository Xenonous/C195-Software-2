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

/**
 * FXML Controller class
 *
 * @author Dylan Franklin
 */
public class ModifyCustomerRecordFormController implements Initializable {
    Stage stage;
    Parent scene;

    /**
     * Information Text
     */
    @FXML
    private Text IDText;

    @FXML
    private Text addressText;

    @FXML
    private Button backButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Text modifyCustomerText;

    @FXML
    private Text nameText;

    @FXML
    private Text phoneNumberText;

    @FXML
    private Text postalCodeText;

    @FXML
    private Text countryText;

    @FXML
    private Text firstlevelDivisionText;

    /**
     * TextField used to collect the Customer name
     */
    @FXML
    private TextField nameTextField;

    /**
     * TextField used to collect the Customer ID
     */
    @FXML
    private TextField IDTextField;

    /**
     * TextField used to collect the Customer address
     */
    @FXML
    private TextField addressTextField;

    /**
     * TextField used to collect the Customer phone number
     */
    @FXML
    private TextField phoneNumberTextField;

    /**
     * TextField used to collect the Customer postal code
     */
    @FXML
    private TextField postalNumberTextField;

    /**
     * ComboBox used to collect the Customer country
     */
    @FXML
    private ComboBox<String> countryComboBox;

    /**
     * ComboBox used to collect the Customer first-level division
     */
    @FXML
    private ComboBox<String> firstlevelDivisionComboBox;

    /**
     * Returns the user to the 'CustomerRecords.fxml' menu
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionBackCustomersRecordForm(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/CustomerRecordsForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Checks for an input from the 'countryComboBox' and uses that to populate the 'firstlevelDivisionsComboBox'.
     *     If input is missing, an alert is shown.
     *
     * @param mouseEvent
     * @throws SQLException
     */
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

    /**
     * Logic checks to see if all TextFields are filled. If there's any missing information, an alert will show telling
     *     the user that the error is. If all information is entered correctly, the Customer will be modified in the database and the
     *     user will be sent back to the 'CustomerRecordsForm.fxml' form.
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException, SQLException {
        if (nameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() || postalNumberTextField.getText().isEmpty() || phoneNumberTextField.getText().isEmpty() || countryComboBox.getSelectionModel().isEmpty() || firstlevelDivisionComboBox.getSelectionModel().getSelectedItem().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("MISSING INFORMATION");
            alert.setContentText("Missing key information. Please double check that all fields are populated BESIDES Customer ID.");
            alert.showAndWait();
        }

        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION");
            alert.setHeaderText("Customer Modification");
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

    /**
     * Populates the TextFields with values given from the Customer form.
     *
     * @param selectedCustomer
     * @throws SQLException
     */
    public void sendCustomer(Customer selectedCustomer) throws SQLException {

        IDTextField.setText(String.valueOf(selectedCustomer.getCustomerID()));
        nameTextField.setText(String.valueOf(selectedCustomer.getCustomerName()));
        addressTextField.setText(String.valueOf(selectedCustomer.getCustomerAddress()));
        postalNumberTextField.setText(String.valueOf(selectedCustomer.getCustomerPostalCode()));
        phoneNumberTextField.setText(String.valueOf(selectedCustomer.getCustomerPhoneNumber()));
        countryComboBox.setValue(selectedCustomer.getCustomerCountry());
        firstlevelDivisionComboBox.setValue(CustomerDataAccess.getFirstLevelDivisionName(Integer.parseInt(selectedCustomer.getCustomerFirstLevelDivision())));
    }

    /**
     * ComboBox setups.
     *
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {

        try {
            countryComboBox.setPromptText("Select Country...");
            firstlevelDivisionComboBox.setPromptText("Select Country First...");
            countryComboBox.setVisibleRowCount(3);
            countryComboBox.setItems(CustomerDataAccess.getAllCountries());
        }

        catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

}


