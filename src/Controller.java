import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.URL;

public class Controller {

    private Stage stage;

    /**
     * Constructor for Controller
     * @param stage Primary stage from main method
     */
    public Controller(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method that sets up and shows
     * Title screen
     */
    public void gotoTitleScreen() {
        Group root = new Group();
        FlowPane layout = new FlowPane(10, 10);
        layout.setAlignment(Pos.CENTER);
        Image titleImg = new Image("/Resources/Backgrounds/violin.jpg",
                500, 375, false, false);
        ImageView titleIv = new ImageView(titleImg);
        Button start = new Button("START");
        Label title = new Label("Project Preludio 2017");
        String titleSongStr = "/Resources/Music/Allegro.mp3";
        URL titleSongUrl = getClass().getResource(titleSongStr);
        Media titleSong = new Media(titleSongUrl.toString());
        MediaPlayer player = new MediaPlayer(titleSong);

        stage.setTitle("Project Preludio 2017");
        Scene scene = new Scene(root, 500, 375);
        stage.setScene(scene);

        root.getChildren().add(titleIv);
        layout.getChildren().addAll(title, start);
        root.getChildren().add(layout);
        stage.setResizable(false);
        player.play();
        stage.show();
    }

    /**
     * Method that sets up and shows
     * Main Menu screen
     */
    public void gotoMainMenu() {
        // code to make a new scene to change
        // the primary stage (takes you to new screen)
    }
}
