package C195;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 *
 * @author Dylan Franklin
 */

public class Main extends Application {

    /**
     * Running the "start" method finds and creates/opens the FXML file and loads the scene.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/LoginForm.fxml"));
        primaryStage.setTitle("C195 - SOFTWARE 2");
        primaryStage.setScene(new Scene(root, 325, 275));
        primaryStage.show();
    }


    /**
     * 'main' is where execution begins. A connection to the database is made first, then the application is launched.
     *
     * @param args
     */
    public static void main(String[] args) {
        JDBC.makeConnection();
        launch(args);
        JDBC.closeConnection();

    }
}
