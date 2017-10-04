package signsupport;

import java.util.ArrayList;

/**
 * Class to model a lesson that contains a list of screen objects
 */
public class Lesson {
    private String id;
    private String title;
    private String category;
    private ArrayList<Screen> screens;
    private Screen screen;


    public ArrayList<Screen> getScreens() {
        return screens;
    }


    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void setScreens(ArrayList<Screen> screens) {
        this.screens = screens;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return title;
    }
}
