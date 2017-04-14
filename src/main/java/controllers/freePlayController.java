package controllers;

import engine.CurrentLesson;
import engine.Preludio;
import engine.Settings;
import engine.noteSprite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class freePlayController {

    @FXML
    private BorderPane pane;

    @FXML
    private Button back_button;

    @FXML
    private GridPane keys_gridPane;

    @FXML
    private Rectangle white0;

    @FXML
    private Rectangle white1;

    @FXML
    private Rectangle white2;

    @FXML
    private Rectangle white3;

    @FXML
    private Rectangle white4;

    @FXML
    private Rectangle white5;

    @FXML
    private Rectangle white6;

    @FXML
    private Rectangle white7;

    @FXML
    private Rectangle black0;

    @FXML
    private Rectangle black1;

    @FXML
    private Rectangle black2;

    @FXML
    private Rectangle black3;

    @FXML
    private Rectangle black4;


    private static int numWhiteKeys = 7;
    private static int numBlackKeys = 5;

    private HashMap<Rectangle, MediaPlayer> whiteKeys = new HashMap();
    private HashMap<Rectangle, MediaPlayer> blackKeys = new HashMap();

    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    final Stage dialog = new Stage();


    /**
     * Sets up the Free Play screen and the interactive piano keys.
     */
    @FXML
    public void initialize() {
        createPauseMenu();
        setupKeyListener();
        for (Node key : keys_gridPane.getChildren()) {
            int i;
            int col = keys_gridPane.getColumnIndex(key);
            String path;
            if (col % 2 == 0) {
                i = col / 2;
                path = "/sound/notes/white" + i + ".mp3";
                whiteKeys.put((Rectangle)key,
                        Preludio.getInstance().createMusicPlayer(
                                path, 1, false));
                whiteKeys.get(key).setStartTime(new Duration(200));
            } else {
                if (col > 3) {
                    i = (col - 3) / 2;
                } else {
                    i = (col - 1) / 2;
                }
                path = "/sound/notes/black" + i + ".mp3";
                blackKeys.put((Rectangle)key,
                        Preludio.getInstance().createMusicPlayer(
                                path, 1, false));
                blackKeys.get(key).setStartTime(new Duration(200));
            }
        }
    }


    /**
     * Asks user if they are sure if they want to exit Free Play and, if yes,
     * sets up and shows the Main Menu Screen.
     *
     * @param event if the Main Menu fxml file cannot be found.
     */
    @FXML
    void goBack(ActionEvent event) {
        Preludio.getInstance().buttonSound.play();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit Free Play?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        alert.showAndWait().filter(response ->
                response == buttonTypeYes).ifPresent(
                response -> {
                    try {
                        Preludio.getInstance().setNewScene(
                                "/view/fxml/mainMenu.fxml");
                        Preludio.getInstance().titlePlayer.play();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }


    /**
     * Plays the note associated with the clicked key.
     *
     * @param event Event caused by clicking a piano key.
     */
    @FXML
    void playNote(MouseEvent event) {
        Rectangle key = (Rectangle)event.getSource();
        if (whiteKeys.containsKey(key)) {
            whiteKeys.get(key).play();
            key.setFill(Color.DARKBLUE);
        } else {
            blackKeys.get(key).play();
            key.setFill(Color.DARKGRAY);
        }
    }


    /**
     * Stops the note from being played when the user releases the mouse button.
     *
     * @param event Event caused by releasing the mouse button on a piano key.
     */
    @FXML
    void releaseNote(MouseEvent event) {
        Rectangle key = (Rectangle)event.getSource();
        if (whiteKeys.containsKey(key)) {
            whiteKeys.get(key).stop();
            key.setFill(Color.BEIGE);
        } else {
            blackKeys.get(key).play();
            key.setFill(Color.BLACK);
        }
    }

    public void createPauseMenu() {
        dialog.setResizable(false);
        dialog.setTitle("Paused");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Preludio.getInstance().getStage());
        Pane newPane = new Pane();

        Button resumeButton = new Button("Resume");
        resumeButton.setShape(new Circle(6.0));
        Button optionsButton = new Button("Options");
        optionsButton.setShape(new Circle(6.0));
        Button quitButton = new Button("Quit");
        quitButton.setShape(new Circle(6.0));

        resumeButton.setLayoutX(175); resumeButton.setLayoutY(75);
        optionsButton.setLayoutX(175); optionsButton.setLayoutY(150);
        quitButton.setLayoutX(185); quitButton.setLayoutY(200);

        newPane.getChildren().addAll(resumeButton, optionsButton, quitButton);

        resumeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });

        optionsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Preludio.getInstance().setNewScene("/view/fxml/settings.fxml");
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        quitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialog.close();
                // Stop parser and other stuff
                try {
                    Preludio.getInstance().setNewScene(
                            "/view/fxml/mainMenu.fxml");
                    Preludio.getInstance().titlePlayer.play();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Scene dialogScene = new Scene(newPane, 400, 300);
        dialog.setScene(dialogScene);
    }

    public void setupKeyListener() {
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    // Pause key pressed
                    dialog.show();
                } else if (event.getCode().equals(Settings.getKeyC())) {
                    // C key pressed
                    Rectangle key = white0;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).play();
                        key.setFill(Color.DARKBLUE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.DARKGRAY);
                    }
                } else if (event.getCode().equals(Settings.getKeyCSharp())) {
                    // C# key pressed
                    Rectangle key = black0;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).play();
                        key.setFill(Color.DARKBLUE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.DARKGRAY);
                    }
                } else if (event.getCode().equals(Settings.getKeyD())) {
                    // D key pressed
                    Rectangle key = white1;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).play();
                        key.setFill(Color.DARKBLUE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.DARKGRAY);
                    }
                } else if (event.getCode().equals(Settings.getKeyDSharp())) {
                    // D# key pressed
                    Rectangle key = black1;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).play();
                        key.setFill(Color.DARKBLUE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.DARKGRAY);
                    }
                } else if (event.getCode().equals(Settings.getKeyE())) {
                    // E key pressed
                    Rectangle key = white2;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).play();
                        key.setFill(Color.DARKBLUE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.DARKGRAY);
                    }
                } else if (event.getCode().equals(Settings.getKeyF())) {
                    // F key pressed
                    Rectangle key = white3;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).play();
                        key.setFill(Color.DARKBLUE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.DARKGRAY);
                    }
                } else if (event.getCode().equals(Settings.getKeyFSharp())) {
                    // F# key pressed
                    Rectangle key = black2;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).play();
                        key.setFill(Color.DARKBLUE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.DARKGRAY);
                    }
                } else if (event.getCode().equals(Settings.getKeyG())) {
                    // G key pressed
                    Rectangle key = white4;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).play();
                        key.setFill(Color.DARKBLUE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.DARKGRAY);
                    }
                } else if (event.getCode().equals(Settings.getKeyGSharp())) {
                    // G# key pressed
                    Rectangle key = black3;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).play();
                        key.setFill(Color.DARKBLUE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.DARKGRAY);
                    }
                } else if (event.getCode().equals(Settings.getKeyA())) {
                    // A key pressed
                    Rectangle key = white5;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).play();
                        key.setFill(Color.DARKBLUE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.DARKGRAY);
                    }
                } else if (event.getCode().equals(Settings.getKeyASharp())) {
                    // A# key pressed
                    Rectangle key = black4;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).play();
                        key.setFill(Color.DARKBLUE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.DARKGRAY);
                    }
                } else if (event.getCode().equals(Settings.getKeyB())) {
                    // B key pressed
                    Rectangle key = white6;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).play();
                        key.setFill(Color.DARKBLUE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.DARKGRAY);
                    }
                }
            }
        });

        pane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(Settings.getKeyC())) {
                    // C key pressed
                    Rectangle key = white0;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).stop();
                        key.setFill(Color.BEIGE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.BLACK);
                    }
                } else if (event.getCode().equals(Settings.getKeyCSharp())) {
                    // C# key pressed
                    Rectangle key = black0;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).stop();
                        key.setFill(Color.BEIGE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.BLACK);
                    }
                } else if (event.getCode().equals(Settings.getKeyD())) {
                    // D key pressed
                    Rectangle key = white1;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).stop();
                        key.setFill(Color.BEIGE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.BLACK);
                    }
                } else if (event.getCode().equals(Settings.getKeyDSharp())) {
                    // D# key pressed
                    Rectangle key = black1;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).stop();
                        key.setFill(Color.BEIGE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.BLACK);
                    }
                } else if (event.getCode().equals(Settings.getKeyE())) {
                    // E key pressed
                    Rectangle key = white2;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).stop();
                        key.setFill(Color.BEIGE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.BLACK);
                    }
                } else if (event.getCode().equals(Settings.getKeyF())) {
                    // F key pressed
                    Rectangle key = white3;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).stop();
                        key.setFill(Color.BEIGE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.BLACK);
                    }
                } else if (event.getCode().equals(Settings.getKeyFSharp())) {
                    // F# key pressed
                    Rectangle key = black2;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).stop();
                        key.setFill(Color.BEIGE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.BLACK);
                    }
                } else if (event.getCode().equals(Settings.getKeyG())) {
                    // G key pressed
                    Rectangle key = white4;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).stop();
                        key.setFill(Color.BEIGE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.BLACK);
                    }
                } else if (event.getCode().equals(Settings.getKeyGSharp())) {
                    // G# key pressed
                    Rectangle key = black3;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).stop();
                        key.setFill(Color.BEIGE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.BLACK);
                    }
                } else if (event.getCode().equals(Settings.getKeyA())) {
                    // A key pressed
                    Rectangle key = white5;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).stop();
                        key.setFill(Color.BEIGE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.BLACK);
                    }
                } else if (event.getCode().equals(Settings.getKeyASharp())) {
                    // A# key pressed
                    Rectangle key = black4;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).stop();
                        key.setFill(Color.BEIGE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.BLACK);
                    }
                } else if (event.getCode().equals(Settings.getKeyB())) {
                    // B key pressed
                    Rectangle key = white6;
                    if (whiteKeys.containsKey(key)) {
                        whiteKeys.get(key).stop();
                        key.setFill(Color.BEIGE);
                    } else {
                        blackKeys.get(key).play();
                        key.setFill(Color.BLACK);
                    }
                }
            }
        });
    }
}


