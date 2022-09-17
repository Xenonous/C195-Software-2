package Controllers;
import C195.JDBC;
import DataAccess.CustomerDataAccess;
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
public class AddCustomerRecordFormController implements Initializable {
    Stage stage;
    Parent scene;

    /**
     * Information text.
     */
    @FXML
    private Text addCustomerText;

    @FXML
    private Text addressText;

    @FXML
    private Text IDText;

    @FXML
    private TextField IDTextField;

    @FXML
    private Text nameText;

    @FXML
    private Text phoneNumberText;

    @FXML
    private Text postalCodeText;

    @FXML
    private Text firstlevelDivisionText;

    @FXML
    private Text countryText;

    /**
     * TextField used to collect the Customer name.
     */
    @FXML
    private TextField nameTextField;

    /**
     * TextField used to collect the Customer phone-number.
     */
    @FXML
    private TextField phoneNumberTextField;

    /**
     * TextField used to collect the Customer postal code
     */
    @FXML
    private TextField postalNumberTextField;

    /**
     * TextField used to collect the Customer address
     */
    @FXML
    private TextField addressTextField;

    /**
     * ComboBox used to collect the Customers country. Used to determine what will be shown in the First-Level Division ComboBox.
     */
    @FXML
    private ComboBox<String> countryComboBox;

    /**
     * ComboBox used to collect the Customer First-Level Division. Pre-populates based on what the user selects in the country ComboBox.
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
     *     the user that the error is. If all information is entered correctly, the Customer will be added to the database and the
     *     user will be sent back to the 'CustomerRecordsForm.fxml' form.
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException, SQLException {

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
            alert.setContentText("Do you really want to ADD the following Customer to the database?");
            Optional<ButtonType> OKButton = alert.showAndWait();

            if (OKButton.isPresent() && OKButton.get() == ButtonType.OK) {

                String firstLevelDivisionName = String.valueOf(firstlevelDivisionComboBox.getSelectionModel().getSelectedItem());
                int divisionID = CustomerDataAccess.getDivisionID(firstLevelDivisionName);

                String SQL = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
                ps.setInt(1, CustomerDataAccess.getNewCustomerID());
                ps.setString(2, nameTextField.getText());
                ps.setString(3, addressTextField.getText());
                ps.setString(4, postalNumberTextField.getText());
                ps.setString(5, phoneNumberTextField.getText());
                ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(7, "admin");
                ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(9, "admin");
                ps.setInt(10, divisionID);
                ps.execute();

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/fxml/CustomerRecordsForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        }
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

