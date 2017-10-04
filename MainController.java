package signsupport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Lucia on 2017/10/02.
 */

// controller class for TaskList

public class MainController implements Initializable {

    public static ArrayList<Screen> screenArr = new ArrayList<>();

    @FXML
    public ListView<String> listView;

    String urlString;


    public static Screen clickedScreen;
    public static int screenNumber;
    //@FXML public static JFXButton prevButton;


    //private Stage primaryStage;


    @Override
    public void initialize(URL url, ResourceBundle rb) {


        XMLReader p = new XMLReader();
        screenArr = p.parse();

        ObservableList<String> taskNames = FXCollections.observableArrayList();


        for (int i=0; i<screenArr.size(); i++) {
            taskNames.add(screenArr.get(i).getVidCaption());
        }

        listView.setItems(taskNames);


        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)  {
                System.out.println("setonmouseclicked: "+ screenArr.get(listView.getSelectionModel().getSelectedIndex()));

                clickedScreen = screenArr.get(listView.getSelectionModel().getSelectedIndex());
                screenNumber = listView.getSelectionModel().getSelectedIndex();

                try {
                handleMouseClick(event); }

                catch (IOException e) {}
            }

        });

    }


    @FXML
    public void handleMouseClick(MouseEvent arg0) throws IOException {
        System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());

        Parent layout = FXMLLoader.load(getClass().getResource("Video.fxml"));
        Scene lessonList = new Scene(layout);
        Stage lessonStage = (Stage) ((Node)arg0.getSource()).getScene().getWindow();
        lessonStage.setScene(lessonList);
        lessonStage.show();
    }

    // for when 'Back' button is clicked on TaskList - transition back to LessonList
    @FXML
    public void backToLessonClicked(ActionEvent event) throws IOException {

        Parent layout = FXMLLoader.load(getClass().getResource("LessonList.fxml"));
        Scene lessonList = new Scene(layout);
        Stage lessonStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        lessonStage.setScene(lessonList);
        lessonStage.show();
    }



    /*// for when 'Lesson'  is clicked on LessonList - transition to tasks
    @FXML
    public void taskButtonClicked(ActionEvent event) throws IOException {

        Parent layout = FXMLLoader.load(getClass().getResource("TaskList.fxml"));
        Scene lessonList = new Scene(layout);
        Stage lessonStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        lessonStage.setScene(lessonList);
        lessonStage.show();

    }

    // for when 'Back' button is clicked on LessonList page - transition back to HomePage
    @FXML
    public void backToHomeClicked(ActionEvent event) throws IOException {

        //System.out.println("Button clicked");

        Parent layout = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Scene home = new Scene(layout);
        Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        homeStage.setScene(home);
        homeStage.show();
    }*/


}
