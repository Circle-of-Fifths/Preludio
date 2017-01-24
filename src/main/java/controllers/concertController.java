package controllers;

import engine.Preludio;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;

import org.jfugue.midi.MidiParser;
import org.jfugue.parser.ParserListenerAdapter;
import org.jfugue.theory.Note;

/**
 * Created by jeff on 1/18/17.
 */
public class concertController {
    @FXML
    private Button select_button;

    FileChooser fileChooser = new FileChooser();

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


    private static int numWhiteKeys = 8;
    private static int numBlackKeys = 5;

    private HashMap<Rectangle, MediaPlayer> whiteKeys = new HashMap();
    private HashMap<Rectangle, MediaPlayer> blackKeys = new HashMap();

    MidiParser parser = new MidiParser();
    MyParserListener listener = new MyParserListener();


    /**
     * Sets up the Free Play screen and the interactive piano keys.
     */
    @FXML
    public void initialize() {
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


    @FXML
    void selectSong(ActionEvent event) throws InvalidMidiDataException, IOException {
        final File[] midiFile = new File[1];
        fileChooser.setTitle("Project Preludio 2017: Open MIDI File");
        //fileChooser.setInitialDirectory(startDir);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Please Select a MIDI File to Play");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                midiFile[0] = fileChooser.showOpenDialog(select_button.getScene().getWindow());
            }
        });

        if (midiFile[0] != null && midiFile[0].getName().contains(".midi")) {
            // Do jFugue stuff here
            parser.addParserListener(listener);
            parser.parse(MidiSystem.getSequence(midiFile[0]));
        }
    }

    /**
     * Makes the button glow when the mouse is put over it.
     *
     * @param event Event caused when the mouse is placed over the button.
     */
    @FXML
    void mouseEnter(MouseEvent event) {
        ((Button) event.getSource()).setEffect(new Glow());
    }


    /**
     * Gives the button a drop shadow when the mouse is no longer over it.
     *
     * @param event Event caused when the mouse is removed from the button.
     */
    @FXML
    void mouseExit(MouseEvent event) {
        ((Button) event.getSource()).setEffect(new DropShadow());
    }

}

class MyParserListener extends ParserListenerAdapter {

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

    @Override
    public void onNoteParsed(Note note) {
        if (note.getPositionInOctave() == 0) { // C note
            white0.setFill(Color.DARKBLUE);
            white0.setFill(Color.BEIGE);
        } else if (note.getPositionInOctave() == 1) { // D note
            white1.setFill(Color.DARKBLUE);
            white1.setFill(Color.BEIGE);
        } else if (note.getPositionInOctave() == 2) { // E note
            white2.setFill(Color.DARKBLUE);
            white2.setFill(Color.BEIGE);
        } else if (note.getPositionInOctave() == 3) { // F note
            white3.setFill(Color.DARKBLUE);
            white3.setFill(Color.BEIGE);
        } else if (note.getPositionInOctave() == 4) { // G note
            white4.setFill(Color.DARKBLUE);
            white4.setFill(Color.BEIGE);
        } else if (note.getPositionInOctave() == 5) { // A note
            white5.setFill(Color.DARKBLUE);
            white5.setFill(Color.BEIGE);
        } else if (note.getPositionInOctave() == 6) { // B note
            white6.setFill(Color.DARKBLUE);
            white6.setFill(Color.BEIGE);
        } else if (note.getPositionInOctave() == 7) { // High C note
            white7.setFill(Color.DARKBLUE);
            white7.setFill(Color.BEIGE);
        }
    }
}
