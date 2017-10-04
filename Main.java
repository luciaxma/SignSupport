package signsupport;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import javafx.stage.Screen;
import java.io.*;

/**
 * Main class that starts execution
 */

public class Main extends Application {

    Stage window;
    public static Boolean isSplashLoaded = false;


    @Override
    public void start(Stage primaryStage) throws Exception {

        // load splash screen
        Parent layout = FXMLLoader.load(getClass().getResource("StartSplashScreen.fxml"));

        primaryStage.setTitle("SignSupport");
        primaryStage.setScene(new Scene(layout));

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        // sets the primary stage boundaries to visible bounds of the main screen
        primaryStage.setX(primaryScreenBounds.getMaxX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
