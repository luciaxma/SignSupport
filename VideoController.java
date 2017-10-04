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
 * Created by Lucia on 2017/10/03.
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
    //@FXML private ImageView enlargedImageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // set title of video screen when video is playing
        title.setText(MainController.clickedScreen.getVidCaption());

        String videoURL = "src"+ MainController.clickedScreen.getVideoURL();
        String path = new File(videoURL).getAbsolutePath();
        System.out.println(MainController.clickedScreen.getVideoURL());

        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);

        mediaPlayer.setOnReady(new Runnable() {
            public void run() {duration = mediaPlayer.getMedia().getDuration();
                updateValues();
            }
        });

        // disable previous button if first task
        if (MainController.screenNumber == 0) {
            prevButton.setDisable(true);
        }
        // disable next button if last task
        else if (MainController.screenNumber == MainController.screenArr.size()-1) {
            nextButton.setDisable(true);
        }

        //scalability for videos
        DoubleProperty width = mediaView.fitWidthProperty();
        DoubleProperty height = mediaView.fitHeightProperty();
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));

        //mp.play();

        System.out.println("ScreenID: " + MainController.clickedScreen.getScreenID());
        System.out.println("ScreenNum: " + MainController.screenNumber);
        System.out.println("Working controller got screen: " + MainController.clickedScreen.getVidCaption());

        speed.getItems().removeAll(speed.getItems());
        speed.getItems().addAll("0.5", "Normal", "2.0");

        // Handle ComboBox event.
        speed.setOnAction((event) -> {
            int index = speed.getSelectionModel().getSelectedIndex();

                if (index==0) {
                    mediaPlayer.setRate(.5);
                }

                else if (index==1) {
                    mediaPlayer.setRate(1);
                }

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

        timeSlider.maxProperty().bind(Bindings.createDoubleBinding(() -> mediaPlayer.getTotalDuration().toSeconds(), mediaPlayer.totalDurationProperty()));


        mediaPlayer.currentTimeProperty().addListener(((observable, oldValue, newValue) -> {
            if (!timeSlider.isValueChanging()) {
                timeSlider.setValue(newValue.toSeconds());
            }
        }));

        if (MainController.clickedScreen.getImagePath() != null) {
            changeImage();
            enlargeButton.setVisible(true);
        }

        else { enlargeButton.setVisible(false);}

        /*imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageView.setStyle("-fx-background-color:#dae7f3;");
            }
        });*/

        /*imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageView.setStyle("-fx-background-color:transparent;'");
            }
        });*/

    }

   /* @FXML
    public void handle1() {
        imageView.setStyle("-fx-background-color:#dae7f3;");
    }*/

    // for when 'Back' button is clicked on Video - transition back to TaskList
    @FXML
    public void backToTaskClicked(ActionEvent event) throws IOException {

        Parent layout = FXMLLoader.load(getClass().getResource("TaskList.fxml"));
        Scene taskList = new Scene(layout);
        Stage taskStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        taskStage.setScene(taskList);
        taskStage.show();
    }

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

    @FXML
    public void nextTaskButtonlicked(ActionEvent event) throws IOException {

        MainController.clickedScreen = MainController.screenArr.get(MainController.screenNumber+1);
        MainController.screenNumber++;

        Parent layout = FXMLLoader.load(getClass().getResource("Video.fxml"));
        Scene video = new Scene(layout);
        Stage videoStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        videoStage.setScene(video);
        videoStage.show();
    }

    public void play(ActionEvent e) {
        mediaPlayer.play();

    }

    public void pause(ActionEvent e) {
        mediaPlayer.pause();
    }

    public void changeImage() {

        String imagePath = MainController.clickedScreen.getImagePath();
        //String path = new File(imagePath).getAbsolutePath();
        //System.out.println(path);


        //Image image = new Image(getClass().getResourceAsStream("/shared_images/word_icon.png"));
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        System.out.println("Set a new Image");

        //imageView = new ImageView(image);
        imageView.setImage(image);
        System.out.println("Displaying it");
        System.out.println(imageView.isVisible());
    }

    protected void updateValues() {
        if (timeSlider != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    Duration currentTime = mediaPlayer.getCurrentTime();
                    //playTime.setText(formatTime(currentTime, duration));
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

 /*   @FXML
    public void enlargeButtonClicked(ActionEvent event) throws IOException {

        Parent layout = FXMLLoader.load(getClass().getResource("ImagePopup.fxml"));
        Scene scene = new Scene(layout);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        *//*String imagePath = MainController.clickedScreen.getImagePath();

        Image image = new Image(getClass().getResourceAsStream(imagePath));
        System.out.println("Set a new Image");

        enlargedImageView.setImage(image);
        System.out.println("Displaying enlarged");
        System.out.println(enlargedImageView.isVisible());*//*


    }*/

    @FXML
    public void enlargeImage() throws IOException {

        Parent layout = FXMLLoader.load(getClass().getResource("ImagePopup.fxml"));
        Scene scene = new Scene(layout);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }









}
