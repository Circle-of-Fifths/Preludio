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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sound.midi.*;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.midi.MidiParser;
import org.jfugue.parser.ParserListenerAdapter;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
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

    Object[] whiteKeysArr, blackKeysArr;
    private Map<String, Rectangle> noteNames;

    private HashMap<Rectangle, MediaPlayer> whiteKeys = new HashMap();
    private HashMap<Rectangle, MediaPlayer> blackKeys = new HashMap();

    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    Sequencer sequencer = MidiSystem.getSequencer();

    public concertController() throws MidiUnavailableException {
    }
    //MidiParser parser = new MidiParser();
    //MyParserListener listener;
    Player player = new Player();

    /*
    Sets up the midi file to be played
     */
    public static final void addNotesToTrack(
            Track track,
            Track trk) throws InvalidMidiDataException {
        for (int ii = 0; ii < track.size(); ii++) {
            MidiEvent me = track.get(ii);
            MidiMessage mm = me.getMessage();
            if (mm instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) mm;
                int command = sm.getCommand();
                int com = -1;
                if (command == ShortMessage.NOTE_ON) {
                    com = 1;
                } else if (command == ShortMessage.NOTE_OFF) {
                    com = 2;
                }
                if (com > 0) {
                    byte[] b = sm.getMessage();
                    int l = (b == null ? 0 : b.length);
                    MetaMessage metaMessage = new MetaMessage(com, b, l);
                    MidiEvent me2 = new MidiEvent(metaMessage, me.getTick());
                    trk.add(me2);
                }
            }
        }
    }


    /**
     * Sets up the Free Play screen and the interactive piano keys.
     */
    @FXML
    public void initialize() {
        System.out.println("i'm initializing");
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

        whiteKeysArr = whiteKeys.keySet().toArray();
        blackKeysArr = blackKeys.keySet().toArray();

        noteNames = new HashMap<>();
        noteNames.put("C", (Rectangle) whiteKeysArr[0]);
        noteNames.put("C#", (Rectangle) blackKeysArr[0]);
        noteNames.put("Db", (Rectangle) blackKeysArr[0]);
        noteNames.put("D", (Rectangle) whiteKeysArr[1]);
        noteNames.put("D#", (Rectangle) blackKeysArr[1]);
        noteNames.put("Eb", (Rectangle) blackKeysArr[1]);
        noteNames.put("E", (Rectangle) whiteKeysArr[2]);
        noteNames.put("F", (Rectangle) whiteKeysArr[3]);
        noteNames.put("F#", (Rectangle) blackKeysArr[2]);
        noteNames.put("Gb", (Rectangle) blackKeysArr[2]);
        noteNames.put("G", (Rectangle) whiteKeysArr[4]);
        noteNames.put("G#", (Rectangle) blackKeysArr[3]);
        noteNames.put("Ab", (Rectangle) blackKeysArr[3]);
        noteNames.put("A", (Rectangle) whiteKeysArr[5]);
        noteNames.put("A#", (Rectangle) blackKeysArr[4]);
        noteNames.put("Bb", (Rectangle) blackKeysArr[4]);
        noteNames.put("B", (Rectangle) whiteKeysArr[6]);
    }


    /**
     * Asks user if they are sure if they want to exit Free Play and, if yes,
     * sets up and shows the Main Menu Screen.
     *
     * @param event if the Main Menu fxml file cannot be found.
     */
    @FXML
    void goBack(ActionEvent event) {
        player.getManagedPlayer().finish();
        Preludio.getInstance().buttonSound.play();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit the Concert Hall?");
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        alert.showAndWait().filter(response ->
                response == buttonTypeYes).ifPresent(
                response -> {
                    try {
                        if (sequencer.isRunning()) {
                            sequencer.stop();
                            sequencer.close();
                        }
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
    void selectSong(ActionEvent event) throws InvalidMidiDataException, IOException, MidiUnavailableException {
        player.getManagedPlayer().finish();
        final File[] midiFile = new File[1];
        fileChooser.setTitle("Project Preludio 2017: Open MIDI File");
        //fileChooser.setInitialDirectory(startDir);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Please Select a MIDI File to Play");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (sequencer.isRunning()) {
                    sequencer.stop();
                    sequencer.close();
                }
                midiFile[0] = fileChooser.showOpenDialog(select_button.getScene().getWindow());
            }
        });

        if (midiFile[0] != null && midiFile[0].getName().contains(".mid")) {
            System.out.println("got the midi file");

            Sequence sequence = MidiSystem.getSequence(midiFile[0]);

            Track[] tracks = sequence.getTracks();
            Track trk = sequence.createTrack();
            for (Track track : tracks) {
                addNotesToTrack(track, trk);
            }

            sequencer.open();
            MetaEventListener mel = new MetaEventListener() {
                @Override
                public void meta(MetaMessage meta) {
                    final int type = meta.getType();
                    if (type == 1) {
                        noteNames.get(NOTE_NAMES[meta.getData()[1] % 12]).setFill(Color.DARKBLUE);
                        System.out.printf("Note: %s, Octave: %d\n", NOTE_NAMES[meta.getData()[1] % 12], (meta.getData()[1] / 12) - 1);
                    } else if (type == 2) {
                        if (NOTE_NAMES[meta.getData()[1] % 12].contains("#")) {
                            noteNames.get(NOTE_NAMES[meta.getData()[1] % 12]).setFill(Color.BLACK);
                        } else {
                            noteNames.get(NOTE_NAMES[meta.getData()[1] % 12]).setFill(Color.BEIGE);
                        }
                        System.out.printf("Note off: %s, Octave: %d\n", NOTE_NAMES[meta.getData()[1] % 12], (meta.getData()[1] / 12) - 1);
                    }
                }
            };

            sequencer.addMetaEventListener(mel);

            sequencer.setSequence(sequence);
            sequencer.start();


//            // Do jFugue stuff here
//            new Thread() {
//                public void run() {
//                    //Pattern pattern = MidiFileManager.loadPatternFromMidi(midiFile[0]);
//                    Sequence sequence2 = null;
//                    try {
//                        sequence2 = MidiFileManager.load(midiFile[0]);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (InvalidMidiDataException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("playing");
//                    if (sequence2 != null) {
//                        player.play(sequence2);
//                    }
//                }
//            }.start();
//
//            new Thread() {
//                public void run() {
//                    listener = new MyParserListener(this, whiteKeys,blackKeys);
//                    Sequence sequence = null;
//                    try {
//                        sequence = MidiFileManager.load(midiFile[0]);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (InvalidMidiDataException e) {
//                        e.printStackTrace();
//                    }
//                    parser.addParserListener(listener);
//                    parser.parse(sequence);
//                }
//            }.start();
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

    Thread thread;
    Object[] whiteKeys, blackKeys;
    private Map<String, Rectangle> noteNames;

    public MyParserListener(Thread thread, HashMap<Rectangle, MediaPlayer> white, HashMap<Rectangle, MediaPlayer> black) {
        super();
        this.thread = thread;
        whiteKeys = white.keySet().toArray();
        blackKeys = black.keySet().toArray();

        noteNames = new HashMap<>();
        noteNames.put("C", (Rectangle) whiteKeys[0]);
        noteNames.put("C#", (Rectangle) blackKeys[0]);
        noteNames.put("Db", (Rectangle) blackKeys[0]);
        noteNames.put("D", (Rectangle) whiteKeys[1]);
        noteNames.put("D#", (Rectangle) blackKeys[1]);
        noteNames.put("Eb", (Rectangle) blackKeys[1]);
        noteNames.put("E", (Rectangle) whiteKeys[2]);
        noteNames.put("F", (Rectangle) whiteKeys[3]);
        noteNames.put("F#", (Rectangle) blackKeys[2]);
        noteNames.put("Gb", (Rectangle) blackKeys[2]);
        noteNames.put("G", (Rectangle) whiteKeys[4]);
        noteNames.put("G#", (Rectangle) blackKeys[3]);
        noteNames.put("Ab", (Rectangle) blackKeys[3]);
        noteNames.put("A", (Rectangle) whiteKeys[5]);
        noteNames.put("A#", (Rectangle) blackKeys[4]);
        noteNames.put("Bb", (Rectangle) blackKeys[4]);
        noteNames.put("B", (Rectangle) whiteKeys[6]);

    }

    @Override
    public void onNotePressed(Note note) {
        String tone = note.getToneString();
        noteNames.get(tone).setFill(Color.DARKBLUE);
        try {
            thread.sleep(125);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.printf("tone: %s\n", tone);
    }

    @Override
    public void onNoteReleased(Note note) {
        String tone = note.getToneString();
        if (tone.contains("#") || tone.contains("b")) {
            noteNames.get(tone).setFill(Color.BLACK);
        } else {
            noteNames.get(tone).setFill(Color.BEIGE);
        }

        try {
            thread.sleep(125);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.printf("tone: %s\n", tone);
    }
}
