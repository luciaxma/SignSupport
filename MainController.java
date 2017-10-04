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
 * Main controller class
 */

public class MainController implements Initializable {

    // create array of screen objects
    public static ArrayList<Screen> screenArr = new ArrayList<>();

    @FXML
    public ListView<String> listView;

    public static Screen clickedScreen; // screen which was clicked
    public static int screenNumber;     // number of the screen

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // extract data that was read in from XML document
        // data read in consists of Screen objects, put these Screen objects in a Screen array
        XMLReader data = new XMLReader();
        screenArr = data.parse();

        // observable list of all tasks to put into a ListView
        ObservableList<String> taskNames = FXCollections.observableArrayList();

        // for loop to get the video caption of all the tasks - for the creation of the list of tasks
        for (int i=0; i<screenArr.size(); i++) {
            taskNames.add(screenArr.get(i).getVidCaption());
        }

        // set all task names into a list using ListView
        listView.setItems(taskNames);

        // event handler to detect which task was clicked on
        // updates clickedScreen and screenNumber variable
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event)  {
                clickedScreen = screenArr.get(listView.getSelectionModel().getSelectedIndex());
                screenNumber = listView.getSelectionModel().getSelectedIndex();

                try {
                handleMouseClick(event);
                }

                catch (IOException e) {
                    System.out.println(e);
                }
            }
        });

    }

    // switch to Video screen from TaskList to play task video - corresponds to specific task clicked
    @FXML
    public void handleMouseClick(MouseEvent arg0) throws IOException {
        //System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());

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


}
