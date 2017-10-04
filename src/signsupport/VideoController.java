package signsupport;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for playing task videos
 */
public class VideoController implements Initializable {

    private Media media;
    private MediaPlayer mediaPlayer;
    private Duration duration;
    @FXML private MediaView mediaView;
    @FXML private ComboBox speed = new ComboBox();
    @FXML private Slider timeSlider = new Slider();

    @FXML private Label title;

    @FXML private JFXButton prevButton;
    @FXML private JFXButton nextButton;

    @FXML private ImageView imageView;
    @FXML private JFXButton enlargeButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // set title of video screen when video is playing
        title.setText(MainController.clickedScreen.getVidCaption());

        // get URL path of video
        String videoURL = "src"+ MainController.clickedScreen.getVideoURL();
        String path = new File(videoURL).getAbsolutePath();
        System.out.println(MainController.clickedScreen.getVideoURL());

        // configure MediaView and MediaPlayer
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);

        // get values of media
        mediaPlayer.setOnReady(new Runnable() {
            public void run() {duration = mediaPlayer.getMedia().getDuration();
                updateValues();
            }
        });

        // disable previous button if first task clicked
        if (MainController.screenNumber == 0) {
            prevButton.setDisable(true);
        }
        // disable next button if last task clicked
        else if (MainController.screenNumber == MainController.screenArr.size()-1) {
            nextButton.setDisable(true);
        }

        // scalability for videos
        DoubleProperty width = mediaView.fitWidthProperty();
        DoubleProperty height = mediaView.fitHeightProperty();
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));

        /*System.out.println("ScreenID: " + MainController.clickedScreen.getScreenID());
        System.out.println("ScreenNum: " + MainController.screenNumber);
        System.out.println("Working controller got screen: " + MainController.clickedScreen.getVidCaption());*/

        // set speed options to ComboBox
        speed.getItems().removeAll(speed.getItems());
        speed.getItems().addAll("0.5", "Normal", "2.0");

        // handle ComboBox event
        speed.setOnAction((event) -> {
            int index = speed.getSelectionModel().getSelectedIndex();

                // if 0.5 speed is selected, set video to play at that speed
                if (index==0) {
                    mediaPlayer.setRate(.5);
                }

                // if Normal speed is selected, set video to play at that speed
                else if (index==1) {
                    mediaPlayer.setRate(1);
                }

                // if 2.0 speed is selected, set video to play at that speed
                else if (index==2) {
                    mediaPlayer.setRate(2);
                }
        });

        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                    mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
                }
            }
        });

        timeSlider.maxProperty().bind(Bindings.createDoubleBinding(() ->
                mediaPlayer.getTotalDuration().toSeconds(), mediaPlayer.totalDurationProperty()));

        // check if time has changed and if so, change timeSlider to that time
        mediaPlayer.currentTimeProperty().addListener(((observable, oldValue, newValue) -> {
            if (!timeSlider.isValueChanging()) {
                timeSlider.setValue(newValue.toSeconds());
            }
        }));

        // show Enlarge button - if there is a corresponding image for the clicked task video
        if (MainController.clickedScreen.getImagePath() != null) {
            changeImage();
            enlargeButton.setVisible(true);
        }

        // do not show Enlarge button - if there is no corresponding image for the clicked task video
        else { enlargeButton.setVisible(false);}

    }

    // for when 'Back' button is clicked on Video - transition back to TaskList
    @FXML
    public void backToTaskClicked(ActionEvent event) throws IOException {

        Parent layout = FXMLLoader.load(getClass().getResource("TaskList.fxml"));
        Scene taskList = new Scene(layout);
        Stage taskStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        taskStage.setScene(taskList);
        taskStage.show();
    }

    // for when 'Previous Task' button is clicked on Video - transition to previous task video
    @FXML
    public void previousTaskButtonClicked(ActionEvent event) throws IOException {

        MainController.clickedScreen = MainController.screenArr.get(MainController.screenNumber-1);
        MainController.screenNumber--;

        Parent layout = FXMLLoader.load(getClass().getResource("Video.fxml"));
        Scene video = new Scene(layout);
        Stage videoStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        videoStage.setScene(video);
        videoStage.show();
    }

    // for when 'Next Task' button is clicked on Video - transition to next task video
    @FXML
    public void nextTaskButtonClicked(ActionEvent event) throws IOException {

        MainController.clickedScreen = MainController.screenArr.get(MainController.screenNumber+1);
        MainController.screenNumber++;

        Parent layout = FXMLLoader.load(getClass().getResource("Video.fxml"));
        Scene video = new Scene(layout);
        Stage videoStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        videoStage.setScene(video);
        videoStage.show();
    }

    // play video when Play icon is clicked
    public void play(ActionEvent e) {
        mediaPlayer.play();
    }

    // pause video when Pause icon is clicked
    public void pause(ActionEvent e) {
        mediaPlayer.pause();
    }

    // method to change image under the video
    public void changeImage() {

        String imagePath = MainController.clickedScreen.getImagePath();

        Image image = new Image(getClass().getResourceAsStream(imagePath));
        System.out.println("Set a new Image");

        imageView.setImage(image);
        System.out.println("Displaying it");
        System.out.println(imageView.isVisible());
    }


    // update values for timeSlider
    protected void updateValues() {
        if (timeSlider != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    Duration currentTime = mediaPlayer.getCurrentTime();
                    timeSlider.setDisable(duration.isUnknown());
                    if (!timeSlider.isDisabled()
                            && duration.greaterThan(Duration.ZERO)
                            && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration).toMillis()
                                * 100.0);
                    }
                }
            });
        }
    }

    // when 'Enlarge' button is clicked next to image - enlarges the image in a popup window
    @FXML
    public void enlargeImage() throws IOException {

        Parent layout = FXMLLoader.load(getClass().getResource("ImagePopup.fxml"));
        Scene scene = new Scene(layout);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }









}
