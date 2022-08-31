package UML;

public class FirstLevelDivisions {
    private String divisionName;
    // private int divisionID;

    public FirstLevelDivisions(String divisionName/* , int divisionID */) {

        this.divisionName = divisionName;
        // this.divisionID = divisionID;
    }

    //COUNTRY NAME
    public String getDivisionName() {
        return divisionName;
    }
    public void setDivisionName() {
        this.divisionName = divisionName;
    }

    /*
    //COUNTRY ID
    public int getDivisionID() {
         return divisionID;
    }
    public void setDivisionID() {
         this.divisionID = divisionID;
    }
    */


    @Override
    public String toString() {
        return divisionName;
    }

}

