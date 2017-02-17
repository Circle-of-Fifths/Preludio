package controllers;

import engine.Preludio;
import engine.noteSprite;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by jeff on 2/6/17.
 */
public class playController {

    @FXML
    private Pane pane;

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

    @FXML
    private TextArea titleBox;

    @FXML
    private TextArea scoreBox;

    @FXML
    private Button startButton;

    @FXML
    private ImageView note;


    private static int numWhiteKeys = 8;
    private static int numBlackKeys = 5;
    private static int noteTimesIndex = 0;
    private static long startTime;
    private static long endTime;
    private static float bpm;
    private static long ppq;

    FileChooser fileChooser = new FileChooser();

    Object[] whiteKeysArr, blackKeysArr;
    private Map<String, Rectangle> noteNames;

    static List<Long> noteTimes = new ArrayList<>();
    static Set<noteSprite> activeNotes = new HashSet<>();

    private HashMap<Rectangle, MediaPlayer> whiteKeys = new HashMap();
    private HashMap<Rectangle, MediaPlayer> blackKeys = new HashMap();

    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    Sequencer sequencer = MidiSystem.getSequencer();

    final Stage dialog = new Stage();

    public playController() throws MidiUnavailableException {
    }

    /**
     * Sets up the Free Play screen and the interactive piano keys.
     */
    @FXML
    public void initialize() throws MidiUnavailableException, InvalidMidiDataException, IOException {
        createPauseMenu();
        setupKeyListener();
        int counter = 0;
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
                ((Rectangle) key).setX(250.0 + (counter * 50.0));
                ((Rectangle) key).setY(160.0);
                counter++;
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
                ((Rectangle) key).setX(250.0 + (counter * 50.0) + (37.5 / 2.0));
                ((Rectangle) key).setY(160.0);
                counter++;
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

        /*noteNames.put("C", white0);
        noteNames.put("C#", black0);
        noteNames.put("Db", black0);
        noteNames.put("D", white1);
        noteNames.put("D#", black1);
        noteNames.put("Eb", black1);
        noteNames.put("E", white2);
        noteNames.put("F", white3);
        noteNames.put("F#", black2);
        noteNames.put("Gb", black2);
        noteNames.put("G", white4);
        noteNames.put("G#", black3);
        noteNames.put("Ab", black3);
        noteNames.put("A", white5);
        noteNames.put("A#", black4);
        noteNames.put("Bb", black4);
        noteNames.put("B", white6);
        */
    }

    public void createPauseMenu() {
        dialog.setResizable(false);
        dialog.setTitle("Paused");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(Preludio.getInstance().getStage());
        Pane newPane = new Pane();

        Button resumeButton = new Button("Resume");
        resumeButton.setShape(new Circle(6.0));
        Button restartButton = new Button("Restart");
        restartButton.setShape(new Circle(6.0));
        Button optionsButton = new Button("Options");
        optionsButton.setShape(new Circle(6.0));
        Button quitButton = new Button("Quit");
        quitButton.setShape(new Circle(6.0));
        resumeButton.setLayoutX(175);
        resumeButton.setLayoutY(50);
        restartButton.setLayoutX(175);
        restartButton.setLayoutY(100);
        optionsButton.setLayoutX(175);
        optionsButton.setLayoutY(150);
        quitButton.setLayoutX(185);
        quitButton.setLayoutY(200);
        newPane.getChildren().addAll(resumeButton, restartButton, optionsButton, quitButton);

        resumeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });

        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
                    startTime = me.getTick();
                } else if (command == ShortMessage.NOTE_OFF) {
                    com = 2;
                    endTime = me.getTick();
                    float tickLen = (60000.0f / (bpm * 192.0f));
                    long time = (long) tickLen * (endTime - startTime);
                    //System.out.println(time + " ms");
                    noteTimes.add(time);
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

    public void selectSong() throws InvalidMidiDataException, IOException, MidiUnavailableException {
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
                midiFile[0] = fileChooser.showOpenDialog(startButton.getScene().getWindow());
            }
        });

        if (midiFile[0] != null && midiFile[0].getName().contains(".mid")) {
            System.out.println("got the midi file");

            Sequence sequence = MidiSystem.getSequence(midiFile[0]);
            bpm = sequencer.getTempoInBPM();
            System.out.println("BPM: " + bpm);

            Track[] tracks = sequence.getTracks();
            Track trk = sequence.createTrack();
            for (Track track : tracks) {
                addNotesToTrack(track, trk);
            }

            System.out.println("Starting Level");

            sequencer.open();
            MetaEventListener play = new MetaEventListener() {
                @Override
                public void meta(MetaMessage meta) {
                    final int type = meta.getType();
                    if (type == 1) {
                        //System.out.println("Note On recieved");
                        System.out.printf("Note: %s, Octave: %d\n", NOTE_NAMES[meta.getData()[1] % 12], (meta.getData()[1] / 12) - 1);
                        String noteName = NOTE_NAMES[meta.getData()[1] % 12];
                        System.out.println("Key X: " + noteNames.get(noteName).getX() + " Key Y: " + noteNames.get(noteName).getY());
                        note.setVisible(true);
                        TranslateTransition transition = new TranslateTransition(new Duration(noteTimes.get(noteTimesIndex)), note);
                        noteTimesIndex++;
                        transition.setAutoReverse(true);
                        transition.setFromX(note.getX());
                        transition.setFromY(note.getY());
                        transition.setToX(noteNames.get(noteName).getX() + (noteNames.get(noteName).getWidth() / 2.0));
                        transition.setToY(noteNames.get(noteName).getY() + (noteNames.get(noteName).getHeight() / 2.0));
                        transition.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                note.setVisible(false);
                                note.setTranslateX(445);
                                note.setTranslateY(20);
                            }
                        });
                        //activeNotes.add(sprite);
                        transition.play();
                    }
                }
            };

            sequencer.addMetaEventListener(play);

            sequencer.setSequence(sequence);
            sequencer.start();
        }
    }

    public void setupKeyListener() {
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    // Pause key pressed
                    dialog.show();
                } else if (event.getCode().equals(KeyCode.A)) {
                    // C key pressed
                    // noteCollisionCheck()
                } else if (event.getCode().equals((KeyCode.W))) {
                    // C# key pressed
                } else if (event.getCode().equals(KeyCode.S)) {
                    // D key pressed
                } else if (event.getCode().equals(KeyCode.E)) {
                    // D# key pressed
                } else if (event.getCode().equals(KeyCode.D)) {
                    // E key pressed
                } else if (event.getCode().equals(KeyCode.F)) {
                    // F key pressed
                } else if (event.getCode().equals(KeyCode.T)) {
                    // F# key pressed
                } else if (event.getCode().equals(KeyCode.G)) {
                    // G key pressed
                } else if (event.getCode().equals(KeyCode.Y)) {
                    // G# key pressed
                } else if (event.getCode().equals(KeyCode.H)) {
                    // A key pressed
                } else if (event.getCode().equals(KeyCode.U)) {
                    // A# key pressed
                } else if (event.getCode().equals(KeyCode.J)) {
                    // B key pressed
                }
            }
        });
    }

    public void noteCollisionCheck() {

    }
}
