package DataAccess;
import C195.JDBC;
import UML.Countries;
import UML.Customer;
import UML.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDataAccess {

    private static int customerID = 10;

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    private static ObservableList<Countries> allCountries = FXCollections.observableArrayList();

    private static ObservableList<FirstLevelDivisions> allDivisions = FXCollections.observableArrayList();

    public static int getNewCustomerID() {

        customerID = customerID + 1;
        System.out.println(customerID);
        return customerID;
    }

    public static void addCustomer(Customer newCustomer) throws SQLException {

        allCustomers.add(newCustomer);

    }

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        String SQL = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        while (rs.next()) {
            customerID = rs.getInt(1);
            String customerName = rs.getString(2);
            String customerAddress =  rs.getString(3);
            String customerPostalCode = rs.getString(4);
            String customerPhoneNumber = rs.getString(5);
            String customerFirstLevelDivision = rs.getString(10);


            Customer newCustomer =  new Customer(customerID,customerName,customerAddress,customerPostalCode,customerPhoneNumber,null,customerFirstLevelDivision);
            allCustomers.add(newCustomer);

            // System.out.println(rs.getInt(1) + " " + (rs.getString(2)) + " " + (rs.getString(3)) + " " + (rs.getString(4)) + " " + (rs.getString(5)));
        }



        return allCustomers;
    }

    public static void updateCustomer(int customerID, Customer customer) {

        allCustomers.set(customerID, customer);

    }

    public static void deleteCustomer(Customer selectedCustomer) {

        allCustomers.remove(selectedCustomer);

    }

    public static ObservableList<Countries> getAllCountries() throws SQLException {
        ObservableList<Countries> allCountries = FXCollections.observableArrayList();

        String SQL = "SELECT COUNTRY,COUNTRY_ID FROM COUNTRIES";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String countryName = rs.getString(1);
            // System.out.println(rs.getString(1));
            Countries country = new Countries(countryName);
            allCountries.add(country);
        }

        return allCountries;
    }

    public static ObservableList<FirstLevelDivisions> getFirstLevelDivisions(String selectedCountry) throws SQLException {
        ObservableList<FirstLevelDivisions> allFirstLevelDivisions = FXCollections.observableArrayList();

        if (selectedCountry.equals("U.S")) {
            System.out.println("United States");
            String SQL = "SELECT DIVISION FROM FIRST_LEVEL_DIVISIONS WHERE COUNTRY_ID = 1";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // System.out.println(rs.getString(1));
                String firstLevelDivisionName = rs.getString(1);
                FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(firstLevelDivisionName);
                allFirstLevelDivisions.add(firstLevelDivisions);
            }

        }
        if (selectedCountry.equals("UK")) {
            System.out.println("United Kingdom");
            String SQL = "SELECT DIVISION FROM FIRST_LEVEL_DIVISIONS WHERE COUNTRY_ID = 2";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // System.out.println(rs.getString(1));
                String firstLevelDivisionName = rs.getString(1);
                FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(firstLevelDivisionName);
                allFirstLevelDivisions.add(firstLevelDivisions);
            }
        }
        if (selectedCountry.equals("Canada")) {
            System.out.println("Canada");
            String SQL = "SELECT DIVISION FROM FIRST_LEVEL_DIVISIONS WHERE COUNTRY_ID = 3";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // System.out.println(rs.getString(1));
                String firstLevelDivisionName = rs.getString(1);
                FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(firstLevelDivisionName);
                allFirstLevelDivisions.add(firstLevelDivisions);
            }
        }

        return allFirstLevelDivisions;
    }

    public static int getDivisionID(String firstLevelDivisionName) throws SQLException {
        int divisionID = 0;
        String SQL = "SELECT DIVISION_ID FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION = " + "'" + firstLevelDivisionName + "'";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            divisionID = rs.getInt(1);
        }
        return divisionID;
    }
}
