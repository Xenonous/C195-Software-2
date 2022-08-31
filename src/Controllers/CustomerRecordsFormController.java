package Controllers;
import C195.JDBC;
import DataAccess.CustomerDataAccess;
import UML.Customer;
import javafx.collections.ObservableList;
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


public class CustomerRecordsFormController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<Customer> customerTableView; //Customer

    @FXML
    private TableColumn<Customer,Integer> customerIDColumn;

    @FXML
    private TableColumn<Customer,String> customerNameColumn;

    @FXML
    private TableColumn<Customer,String> customerAddressColumn;

    @FXML
    private TableColumn<Customer,Integer> customerPostalCodeColumn;

    @FXML
    private TableColumn<Customer,String> customerPhoneNumberColumn;

    @FXML
    private TableColumn<Customer,String> customerCountryColumn;

    @FXML
    private TableColumn<Customer,String> customerFirstLevelDivisionColumn;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Button modifyCustomerButton;

    @FXML
    void onActionBackMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/MainMenuForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/AddCustomerRecordForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

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

                String SQL = "DELETE CUSTOMERS FROM CUSTOMERS WHERE Customer_ID = " + selectedCustomer.getCustomerID();
                PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
                ps.execute();

                System.out.println("Deletion Successful!");

                customerTableView.refresh();

            }
        }
    }

    public void initialize(URL url, ResourceBundle rb) {

        try {
                customerTableView.setItems(getAllCustomers());
        }

        catch (SQLException throwables) {
            throwables.printStackTrace();
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
