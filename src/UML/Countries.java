package UML;

/**
 * UML class
 *
 * @author Dylan Franklin
 */
public class Countries {
    private String countryName;
    // private int countryID;

    /**
     * Constructor for Countries
     *
     * @param countryName
     */
     public Countries(String countryName/* , int countryID */) {

         this.countryName = countryName;
         // this.countryID = countryID;
     }

    //COUNTRY NAME
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName() {
        this.countryName = countryName;
    }

    /*
    //COUNTRY ID
    public int getCountryID() {
         return countryID;
    }
    public void setCountryID() {
         this.countryID = countryID;
    }
    */


    @Override
    public String toString() {
        return countryName;
    }
    
}

