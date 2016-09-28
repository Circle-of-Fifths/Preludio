import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URI;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        FlowPane layout = new FlowPane(10,10);
        layout.setAlignment(Pos.CENTER);
        Image titleImg = new Image("/Resources/Backgrounds/violin.jpg",500,375,false,false);
        ImageView titleIv = new ImageView(titleImg);
        Button start = new Button("START");
        Label title = new Label("Project Preludio 2017");
        String titleSongStr = "/Resources/Music/Allegro.mp3";
        URL titleSongUrl = getClass().getResource(titleSongStr);
        Media titleSong = new Media(titleSongUrl.toString());
        MediaPlayer player = new MediaPlayer(titleSong);

        primaryStage.setTitle("Project Preludio 2017");
        Scene scene = new Scene(root,500,375);
        primaryStage.setScene(scene);

        root.getChildren().add(titleIv);
        layout.getChildren().addAll(title,start);
        root.getChildren().add(layout);
        primaryStage.setResizable(false);
        player.play();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
