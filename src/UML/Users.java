package UML;

public class Users {
    private int userID;

    // private String username;
    // private String password;


    public Users(int userID /* , String username, String password */) {
        this.userID = userID;
        // this.username = username;
        // this.password = password;
    }

    //USER ID
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /*

    //USERNAME
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    //PASSWORD
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


     */

    @Override
    public String toString() {
        return String.valueOf(userID);
    }

}
