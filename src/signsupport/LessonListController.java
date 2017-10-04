package signsupport;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *  Controller for LessonList screen
 */
public class LessonListController {

    @FXML public JFXButton closeButton;

    // for when 'Lesson'  is clicked on LessonList - transition to tasks
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

        Parent layout = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Scene home = new Scene(layout);
        Stage homeStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        homeStage.setScene(home);
        homeStage.show();
    }

    @FXML
    public void otherTaskButtonsClicked() throws IOException {

        Parent layout = FXMLLoader.load(getClass().getResource("NoLessonMsgPopup.fxml"));
        Scene scene = new Scene(layout);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void closePopup() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
