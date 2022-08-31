package UML;

public class Customer {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private String customerCountry;
    private String customerFirstLevelDivision;

    public Customer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, String customerCountry, String customerFirstLevelDivision) {

        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerCountry = customerCountry;
        this.customerFirstLevelDivision = customerFirstLevelDivision;

    }

    //CUSTOMER ID
    public int getCustomerID() {
        return customerID;
    }
    public void setCustomerID() {
        this.customerID = customerID;
    }

    //CUSTOMER NAME
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName() {
        this.customerName = customerName;
    }

    //CUSTOMER ADDRESS
    public String getCustomerAddress() {
        return customerAddress;
    }
    public void setCustomerAddress() {
        this.customerAddress = customerAddress;
    }

    //CUSTOMER POSTAL CODE
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }
    public void setCustomerPostalCode() {
        this.customerPostalCode = customerPostalCode;
    }

    //CUSTOMER PHONE NUMBER
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }
    public void setCustomerPhoneNumber() {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    //CUSTOMER COUNTRY
    public String getCustomerCountry() {
        return customerCountry;
    }
    public void setCustomerCountry() {
        this.customerCountry = customerCountry;
    }

    //CUSTOMER FIRST-LEVEL DIVISION
    public String getCustomerFirstLevelDivision() {
        return customerFirstLevelDivision;
    }
    public void setCustomerFirstLevelDivision() {
        this.customerFirstLevelDivision = customerFirstLevelDivision;
    }

    @Override
    public String toString() {
        return String.valueOf(customerID);
    }
}
