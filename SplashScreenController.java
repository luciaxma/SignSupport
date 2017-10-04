package signsupport;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashScreenController implements Initializable {


    @FXML
    private AnchorPane root;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!Main.isSplashLoaded) {
            loadSplashScreen();
        }


    }

    private void loadSplashScreen() {
        try {
            Main.isSplashLoaded = true;

            StackPane pane = FXMLLoader.load(getClass().getResource(("SplashScreen.fxml")));
            root.getChildren().setAll(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {
                    Parent layout = FXMLLoader.load(getClass().getResource(("HomePage.fxml")));
                    root.getChildren().setAll(layout);

                    Scene scene = new Scene(layout);
                    Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
