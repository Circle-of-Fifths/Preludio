//import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.layout.FlowPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

public class Controller {

    private static Stage stage;
    private MediaPlayer titlePlayer = this.createMusicPlayer(
            "/Resources/Music/Allegro.mp3");

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
        Pane layout = new Pane();

        Image titleImg = new Image("/Resources/Backgrounds/Title.jpg",
                800, 650, false, false);
        ImageView titleIv = new ImageView(titleImg);

        Glow glowEffect = new Glow();
        DropShadow shadow = new DropShadow();
        Button start = new Button("START");
        start.setEffect(shadow);
        start.setLayoutX(345);
        start.setLayoutY(350);
        start.setStyle("-fx-font: 32 serif; -fx-base: #b6e7c9;");
        start.setShape(new Circle(1.0));

        start.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        start.setEffect(glowEffect);
                    }
                });

        start.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        start.setEffect(shadow);
                    }
                });

        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                titlePlayer.stop();
                gotoMainMenu();
            }
        });

        root.getChildren().add(titleIv);
        layout.getChildren().addAll(start);
        root.getChildren().add(layout);

        stage.setTitle("Project Preludio 2017");
        Scene scene = new Scene(root, 800, 650);
        stage.setScene(scene);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    titlePlayer.pause();
                    gotoMainMenu();
                }
            }
        });

        stage.setResizable(false);
        titlePlayer.play();
        stage.show();
    }

    /**
     * Method that sets up and shows
     * Main Menu screen
     */
    private void gotoMainMenu() {
        // code to make a new scene to change
        // the primary stage (takes you to new screen)
        Group root = new Group();
        Pane layout = new Pane();

        Image mainMenuImg = new Image("/Resources/Backgrounds/"
                 + "circle_of_fifths_colors.png",
                800, 650, false, false);
        ImageView titleIv = new ImageView(mainMenuImg);
        root.getChildren().add(titleIv);
        root.getChildren().add(layout);

        stage.setTitle("Project Preludio 2017");
        Scene scene = new Scene(root, 800, 650);
        stage.setScene(scene);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    titlePlayer.stop();
                    gotoTitleScreen();
                }
            }
        });

        stage.setResizable(false);
        titlePlayer.play();
        stage.show();
    }

    /**
     * Creates a media player for the given
     * song string representation
     * @param songStr String representation of song
     * @return Media Player of song passed in
     */
    private MediaPlayer createMusicPlayer(String songStr) {
        URL songUrl = getClass().getResource(songStr);
        Media song = new Media(songUrl.toString());
        MediaPlayer player = new MediaPlayer(song);
        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                player.seek(Duration.ZERO);
            }
        });
        return player;
    }
}
