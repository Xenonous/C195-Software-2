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

/**
 * DataAccess class
 *
 * @author Dylan Franklin
 */
public class CustomerDataAccess {

    /**
     * ObservableList for all Customers
     */
    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**
     * ObservableList for all countries
     */
    private static final ObservableList<Countries> allCountries = FXCollections.observableArrayList();

    /**
     * ObservableList for all divisions
     */
    private static final ObservableList<FirstLevelDivisions> allDivisions = FXCollections.observableArrayList();

    /**
     * Starting ID for Customer
     *
     * @return
     */
    public static int getNewCustomerID() throws SQLException {
        int customerID = 1;
        String SQL = "SELECT CUSTOMER_ID FROM CUSTOMERS ORDER BY CAST(CUSTOMER_ID AS unsigned)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            if (rs.getInt(1) == customerID) {
                customerID++;
            } else if (rs.getInt(1) != customerID) {
                break;
            }
        }
        return customerID;
    }

    /**
     * Method that gets all Customers from the database.
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {

        int customerID = 0;

        String SQL = "SELECT cu.CUSTOMER_ID, cu.CUSTOMER_NAME, cu.ADDRESS, cu.POSTAL_CODE, cu.PHONE, cu.DIVISION_ID, co.COUNTRY FROM customers cu " +
                "INNER JOIN FIRST_LEVEL_DIVISIONS fld ON cu.DIVISION_ID = fld.DIVISION_ID INNER JOIN COUNTRIES co ON fld.COUNTRY_ID = co.COUNTRY_ID ORDER BY CAST(CUSTOMER_ID AS unsigned)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        while (rs.next()) {
            customerID = rs.getInt(1);
            String customerName = rs.getString(2);
            String customerAddress =  rs.getString(3);
            String customerPostalCode = rs.getString(4);
            String customerPhoneNumber = rs.getString(5);
            String customerFirstLevelDivision = rs.getString(6);
            String customerCountry = rs.getString(7);


            Customer newCustomer =  new Customer(customerID,customerName,customerAddress,customerPostalCode,customerPhoneNumber,customerCountry,customerFirstLevelDivision);
            allCustomers.add(newCustomer);

            // System.out.println(rs.getInt(1) + " " + (rs.getString(2)) + " " + (rs.getString(3)) + " " + (rs.getString(4)) + " " + (rs.getString(5)));
        }


        return allCustomers;
    }

    /**
     *
     * Method that gets all Countries from the database.
     * @return
     * @throws SQLException
     */
    public static ObservableList<String> getAllCountries() throws SQLException {
        ObservableList<String> allCountries = FXCollections.observableArrayList();

        String SQL = "SELECT COUNTRY,COUNTRY_ID FROM COUNTRIES";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String countryName = rs.getString(1);
            // System.out.println(rs.getString(1));
            Countries country = new Countries(countryName);
            allCountries.add(String.valueOf(country));
        }

        return allCountries;
    }

    /**
     * Method that gets all first-level divisions from the database.
     *
     * @param selectedCountry
     * @return
     * @throws SQLException
     */
    public static ObservableList<String> getFirstLevelDivisions(String selectedCountry) throws SQLException {
        ObservableList<String> allFirstLevelDivisions = FXCollections.observableArrayList();

        if (selectedCountry.equals("U.S")) {
            System.out.println("United States");
            String SQL = "SELECT DIVISION FROM FIRST_LEVEL_DIVISIONS WHERE COUNTRY_ID = 1";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // System.out.println(rs.getString(1));
                String firstLevelDivisionName = rs.getString(1);
                FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(firstLevelDivisionName);
                allFirstLevelDivisions.add(String.valueOf(firstLevelDivisions));
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
                allFirstLevelDivisions.add(String.valueOf(firstLevelDivisions));
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
                allFirstLevelDivisions.add(String.valueOf(firstLevelDivisions));
            }
        }

        return allFirstLevelDivisions;
    }

    /**
     * Method that fetches the division ID from the database given the first-level division name.
     *
     * @param firstLevelDivisionName
     * @return
     * @throws SQLException
     */
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

    public static String getFirstLevelDivisionName(int firstLevelDivisionNum) throws SQLException {

        String firstLevelDivisionName = null;
        String SQL = "SELECT DIVISION FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION_ID = " + firstLevelDivisionNum;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            firstLevelDivisionName = rs.getString(1);
        }
        return firstLevelDivisionName;
    }
}
