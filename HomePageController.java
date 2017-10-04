package signsupport;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller class for HomePage screen
 */

public class HomePageController implements Initializable {

    private Media welcomeMedia;
    private MediaPlayer welcomeMediaPlayer;

    @FXML private MediaView welcomeView;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        // set path to Welcome Screen video homepage
        String videoURL = "src/video/Welcome screen.mp4";
        String path = new File(videoURL).getAbsolutePath();

        // play Welcome Screen video on homepage
        welcomeMedia = new Media(new File(path).toURI().toString());
        welcomeMediaPlayer = new MediaPlayer(welcomeMedia);
        welcomeView.setMediaPlayer(welcomeMediaPlayer);
        welcomeMediaPlayer.setAutoPlay(true);

    }


    // for when 'Lesson' button is clicked on HomePage - transition to LessonList screen
    @FXML
    public void lessonButtonClicked(ActionEvent event) throws IOException {

        Parent layout = FXMLLoader.load(getClass().getResource("LessonList.fxml"));
        Scene lessonList = new Scene(layout);
        Stage lessonStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        lessonStage.setScene(lessonList);
        lessonStage.show();
    }






}
