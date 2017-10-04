package signsupport;

/**
 * Created by Lucia on 2017/09/05.
 */

public class Screen {

    private String screenID;
    private String videoURL;
    private String vidCaption;
    private String imagePath;

    public String getScreenID() {
        return screenID;
    }

    public void setScreenID(String screenID) {
        this.screenID = screenID;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getVidCaption() {
        return vidCaption;
    }

    public void setVidCaption(String vidCaption) {
        this.vidCaption = vidCaption;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return vidCaption;
    }
}
