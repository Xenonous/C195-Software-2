package Controllers;
import C195.JDBC;
import UML.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import static DataAccess.CustomerDataAccess.getAllCustomers;


/**
 * FXML Controller class
 *
 * @author Dylan Franklin
 */
public class CustomerRecordsFormController implements Initializable {
    Stage stage;
    Parent scene;

    /**
     * Foreign Key checker that is executed before a user attempts to remove a Customer. Checks to see if there are any
     *     Appointments associated with a Customer.
     *
     * @param customerID
     * @return
     * @throws SQLException
     */
    private boolean foreignKeyCheck(int customerID) throws SQLException {
        String SQL = "SELECT CUSTOMER_ID FROM APPOINTMENTS WHERE CUSTOMER_ID = " + customerID;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

                if(rs.next()) {
                    System.out.println("There is a foreign key connected to this Customer.");
                    return false;
                }
                else {
                    return true;
                }
    }

    //--------------------Customer TableView-----------------------------

    @FXML
    private TableView<Customer> customerTableView; //Customer

    /**
     * Customer ID column
     */
    @FXML
    private TableColumn<Customer,Integer> customerIDColumn;

    /**
     * Customer name column
     */
    @FXML
    private TableColumn<Customer,String> customerNameColumn;

    /**
     * Customer address column
     */
    @FXML
    private TableColumn<Customer,String> customerAddressColumn;

    /**
     * Customer postal code column
     */
    @FXML
    private TableColumn<Customer,Integer> customerPostalCodeColumn;

    /**
     * Customer phone number column
     */
    @FXML
    private TableColumn<Customer,String> customerPhoneNumberColumn;

    /**
     * Customer country column
     */
    @FXML
    private TableColumn<Customer,String> customerCountryColumn;

    /**
     * Customer first-level division column
     */
    @FXML
    private TableColumn<Customer,String> customerFirstLevelDivisionColumn;

    /**
     * Buttons
     */
    @FXML
    private Button addCustomerButton;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Button modifyCustomerButton;

    /**
     * Returns the user to the 'MainMenuForm.fxml' menu.
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
     * Brings the user to the 'AddCustomerForm.fxml' menu.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/AddCustomerRecordForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Brings the user to the 'ModifyCustomerForm.fxml' menu.
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException, SQLException {

        if (customerTableView.getSelectionModel().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Selection Error");
            alert.setContentText("Make sure you have a Customer selected that you want to modify");
            alert.showAndWait();

        }
        if(!customerTableView.getSelectionModel().isEmpty()) {

            FXMLLoader Loader = new FXMLLoader(); //Setting up the FXMLLoader to transfer data.
            Loader.setLocation(getClass().getResource("/fxml/ModifyCustomerRecordForm.fxml"));
            Loader.load();

            ModifyCustomerRecordFormController ADMController = Loader.getController(); //Setting up the FXMLLoader to transfer data.
            ADMController.sendCustomer(customerTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow(); //Showing stage.
            Parent scene = Loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.showAndWait(); // "stage.show()" isn't used here, "stage.showAndWait" makes it so that code can be read after this line if needed.

        }
    }

    /**
     * Deletes a selected Customer upon confirming that you want to do so. Checks to see if th selected Customer
     *     has any appointments associated with them. If they do, an alert will show.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {

        if(customerTableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("SELECTION ERROR");
            alert.setContentText("Please select the Customer you wish to delete from the table.");
            alert.showAndWait();
        }

        else if (!customerTableView.getSelectionModel().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMATION");
            alert.setHeaderText("Customer Deletion");
            alert.setContentText("Do you really want to DELETE the selected Customer from the database?");
            Optional<ButtonType> OKButton = alert.showAndWait();

            if (OKButton.isPresent() && OKButton.get() == ButtonType.OK) {

                Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

                if(foreignKeyCheck(selectedCustomer.getCustomerID())) {
                    String SQL = "DELETE CUSTOMERS FROM CUSTOMERS WHERE Customer_ID = " + selectedCustomer.getCustomerID();
                    PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
                    ps.execute();

                    System.out.println("Deletion Successful!");

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Customer Deletion");
                    alert.setContentText("The selected Customer has been successfully deleted from the database.");

                    customerTableView.refresh();




                }
                else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("FOREIGN KEY ERROR");
                    alert.setContentText("Please delete corresponding appointments attached to this customer first before deleting the customer.");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * Setup for the customerTableView and populates the customerTableView with all customers in the database.
     *
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {

        try {
                customerTableView.setItems(getAllCustomers());
        }

        catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        customerPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
        customerCountryColumn.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        customerFirstLevelDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("customerFirstLevelDivision"));
    }
}
