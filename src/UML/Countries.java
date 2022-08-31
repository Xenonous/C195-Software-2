package UML;

public class Countries {
    private String countryName;
    // private int countryID;

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

