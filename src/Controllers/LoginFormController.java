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


public class LoginFormController implements Initializable {

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

    public String getLocale(){
        Locale locale = Locale.getDefault();
        String lang = locale.getDisplayLanguage();
       // System.out.println("Locale: " + locale);
       // System.out.println("Language: " + lang);
        return (lang);
    }

    public void getZoneID() {
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println("ZoneId: " + zoneId);
        displayRegionText.setText(String.valueOf(zoneId));

    }

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

    Stage stage;
    Parent scene;

    @FXML
    private Button loginButton;

    @FXML
    private Button resetButton;

    @FXML
    private Separator loginSeparator;

    @FXML
    private Text loginText;

    @FXML
    private ImageView passwordIcon;

    @FXML
    private Text passwordText;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private ImageView usernameIcon;

    @FXML
    private Text usernameText;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Text regionText;

    @FXML
    private Text displayRegionText;

    @FXML
    void onActionReset(ActionEvent event) {

        usernameTextField.setText("");
        passwordTextField.setText("");

    }

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

    public void initialize(URL url, ResourceBundle rb) {
        getZoneID();
        EnglishToFrench(getLocale());

    }

}