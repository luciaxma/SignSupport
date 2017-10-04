package signsupport;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class to handle enlarging images and its pop up dialog
 */
public class PopupController implements Initializable {

    @FXML private ImageView enlargedImageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String imagePath = MainController.clickedScreen.getImagePath();
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        enlargedImageView.setImage(image);
        centreImage(image);
    }

    public void centreImage(Image img) {

            if (img != null) {
                System.out.println("enlarge image view");
                double w = 0;
                double h = 0;

                double ratioX = enlargedImageView.getFitWidth() / img.getWidth();
                double ratioY = enlargedImageView.getFitHeight() / img.getHeight();

                double reducCoeff = 0;
                if(ratioX >= ratioY) {
                    reducCoeff = ratioY;
                } else {
                    reducCoeff = ratioX;
                }

                w = img.getWidth() * reducCoeff;
                h = img.getHeight() * reducCoeff;

                enlargedImageView.setX((enlargedImageView.getFitWidth() - w) / 2);
                enlargedImageView.setY((enlargedImageView.getFitHeight() - h) / 2);

            }

    }


}
