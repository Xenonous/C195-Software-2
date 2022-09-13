package Controllers;
import C195.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Dylan Franklin
 */
public class LoginFormController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * Method that tracks user login attempts under the file name 'login_activity.txt'.
     *     Records the date and time of the attempted login and whether it was successful or not.
     *
     * @param isSuccessful
     * @throws IOException
     */
    private void trackUserLoginActivity(boolean isSuccessful) throws IOException {

        LocalTime time = LocalTime.now();
        // System.out.println("Current time: "+ time);

        LocalDate date = LocalDate.now();
        // System.out.println("Current date: " + date);

        if(!isSuccessful) {

            String fileName = "login_activity.txt";
            FileWriter fileWriter = new FileWriter(fileName, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.write("USER ATTEMPTED LOGIN AT " + time + " ON " + date + ". THE LOGIN WAS UNSUCCESSFUL. \n" );
            printWriter.close();
        }

        else if (isSuccessful) {
            String fileName = "login_activity.txt";
            FileWriter fileWriter = new FileWriter(fileName, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.write("USER ATTEMPTED LOGIN AT " + time + " ON " + date + ". THE LOGIN WAS SUCCESSFUL. \n" );
            printWriter.close();
        }

    }

    /**
     * Login error messages that depending on the language setting, will be displayed in either English or French.
     *
     * @param lang
     * @throws IOException
     */
    public void LoginErrorMessages(String lang) throws IOException {

        if (lang.equals("français")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERREUR");
            alert.setHeaderText("Erreur d'identification");
            alert.setContentText("ÉCHEC DE LA CONNEXION. MAUVAISE COMBINAISON NOM D'UTILISATEUR/MOT DE PASSE");
            alert.showAndWait();
        }
        else if (lang.equals("English")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Login Error");
            alert.setContentText("LOGIN FAILED. WRONG COMBINATION OF USERNAME/PASSWORD");
            alert.showAndWait();
        }

    }

    /**
     * Method that gets the systems default location and language.
     *
     * @return
     */
    public String getLocale(){
        Locale locale = Locale.getDefault();
        String lang = locale.getDisplayLanguage();
       // System.out.println("Locale: " + locale);
       // System.out.println("Language: " + lang);
        return (lang);
    }

    /**
     * Method that gets the systems default zoneId. Used to changed displayRegionText to show the users zoneId.
     */
    public void getZoneID() {
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println("ZoneId: " + zoneId);
        displayRegionText.setText(String.valueOf(zoneId));

    }

    /**
     * getLocale() is done first to get 'lang'. This method translates the Login form to French if needed.
     *
     * @param lang
     */
    public void EnglishToFrench(String lang) {

        if(lang.equals("français")) {
            System.out.println("Your system/location/language is in French.");

            loginButton.setText("Connexion");
            resetButton.setText("Réinitialiser");
            loginText.setText("Connexion");
            passwordText.setText("Mot de passe");
            usernameText.setText("Nom d'utilisateur");
            regionText.setText("Région:");

        }
        else if (lang.equals("English")) {
            System.out.println("Your system/location/language is in English");
        }
        else {
            System.out.println("Your system/location/language is neither in English or French");
        }

    }


    /**
     * Information text /  Buttons
     */
    @FXML
    private Text regionText;

    @FXML
    private Text displayRegionText;

    @FXML
    private Text loginText;

    @FXML
    private Text usernameText;

    @FXML
    private Text passwordText;

    @FXML
    private Button loginButton;

    @FXML
    private Button resetButton;

    @FXML
    private Separator loginSeparator;

    @FXML
    private ImageView passwordIcon;

    @FXML
    private ImageView usernameIcon;

    /**
     * TextField for password
     */
    @FXML
    private PasswordField passwordTextField;

    /**
     * TextField for username
     */
    @FXML
    private TextField usernameTextField;

    /**
     * Resets the 'usernameTextField' and 'passwordTextField' back to empty inputs.
     *
     * @param event
     */
    @FXML
    void onActionReset(ActionEvent event) {

        usernameTextField.setText("");
        passwordTextField.setText("");

    }

    /**
     * Takes username and password inputs and checks the database to see if they're valid or not. If they're valid the
     *     user will be able to continue to the 'MainMenuForm.fxml' form. If they're not an alert will show. Login activity
     *     is also tracked for every attempt at logging in whether successful or not.
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        String SQL = "SELECT USER_NAME, PASSWORD FROM USERS";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String validUsername = rs.getString("USER_NAME");
        String validPassword = rs.getString("PASSWORD");
        rs.next();
        String validUsername2 = rs.getString("USER_NAME");
        String validPassword2 = rs.getString("PASSWORD");

            if (username.equals(validUsername) && password.equals(validPassword)) {
                trackUserLoginActivity(true);
                System.out.println("LOGIN SUCCESSFUL!");
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/fxml/MainMenuForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

            else if (username.equals(validUsername2) && password.equals(validPassword2)) {
                trackUserLoginActivity(true);
                System.out.println("LOGIN SUCCESSFUL!");
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/fxml/MainMenuForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

                }

            else {
                trackUserLoginActivity(false);
                System.out.println("LOGIN UNSUCCESSFUL!");
                LoginErrorMessages(getLocale());
            }
    }

    /**
     * Checks language and zoneId to see whether translation is needed.
     *
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        getZoneID();
        EnglishToFrench(getLocale());

    }

}