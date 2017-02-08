package engine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class Preludio extends Application {
    private static Preludio instance;

    public MediaPlayer titlePlayer = this.createMusicPlayer(
            "/sound/BBC5_I.mp3", .75, true);
    public MediaPlayer buttonSound = this.createMusicPlayer(
            "/sound/buttonSound.mp3", .20, false);

    private static Stage stage;

    /**
     * Constructor for Preludio Application.
     */
    public Preludio() {
        instance = this;
    }


    /**
     * Starts the Preludio Application.
     *
     * @param args command line arguments given to program.
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Sets up the Application and sets up and shows the Title Screen.
     *
     * @param primaryStage the Stage object used to display the screens used
     *                     throughout the program.
     *
     * @throws Exception if Title Screen fxml file cannot be found.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Project Preludio 2017");
        //stage.setResizable(false);

        primaryStage.setOnCloseRequest((WindowEvent e) ->{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to quit?");
                alert.showAndWait().filter(response ->
                        response == ButtonType.OK).ifPresent(
                        response -> System.exit(0));
                e.consume();});

        setNewScene("/view/fxml/titleScreen.fxml");
        stage.show();
    }


    /**
     * Changes the scene used on the Stage to the given fxml file.
     *
     * @param newScene location of the fxml file to be used as the new scene.
     *
     * @throws IOException if the given fxml file cannot be found.
     */
    public void setNewScene(String newScene) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(
                Preludio.class.getResource(newScene)), 850, 600);
        stage.setScene(scene);
    }

    /**
     * Static method for Controller classes to access instance methods of the
     * main application.
     *
     * @return the running Preludio object.
     */
    public static Preludio getInstance() {
        return instance;
    }


    /**
     * Creates a media player for the given song string representation.
     *
     * @param songStr String representation of song.
     * @param isLooping boolean determining if song will loop.
     * @param volume volume that the music will play.
     *
     * @return Media Player of song passed in.
     */
    public MediaPlayer createMusicPlayer(String songStr,
                                          double volume, boolean isLooping) {
        URL songUrl = getClass().getResource(songStr);
        Media song = new Media(songUrl.toString());
        MediaPlayer player = new MediaPlayer(song);
        player.setVolume(volume);
        if (isLooping) {
            player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));
        } else {
            player.setOnEndOfMedia(player::stop);
        }
        return player;
    }
}
