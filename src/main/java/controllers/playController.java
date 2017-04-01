package controllers;

import engine.CurrentLesson;
import engine.Preludio;
import engine.Settings;
import engine.noteSprite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.omg.CORBA.Current;

import javax.sound.midi.*;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jeff on 2/6/17.
 */
public class playController {

    @FXML
    private Pane pane;

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
    private TextField titleBox;

    @FXML
    private TextField scoreBox;

    @FXML
    private Button startButton;

    @FXML
    private ImageView noteC;

    @FXML
    private ImageView noteCSharp;

    @FXML
    private ImageView noteD;

    @FXML
    private ImageView noteDSharp;

    @FXML
    private ImageView noteE;

    @FXML
    private ImageView noteF;

    @FXML
    private ImageView noteFSharp;

    @FXML
    private ImageView noteG;

    @FXML
    private ImageView noteGSharp;

    @FXML
    private ImageView noteA;

    @FXML
    private ImageView noteASharp;

    @FXML
    private ImageView noteB;

    @FXML
    private ProgressBar scoreBar;

    @FXML
    private Label feedback;


    private static int numWhiteKeys = 8;
    private static int numBlackKeys = 5;
    private static int noteTimesIndex = 0;
    private static List<Long> startTimes = new ArrayList<>();
    private static long endTime;
    private static float bpm;
    private static int score;
    private static int numNotes;
    private static int maxScore;

    FileChooser fileChooser = new FileChooser();

    private static Map<String, noteSprite> noteNames;

    static List<Long> noteTimes = new ArrayList<>();
    static Set<noteSprite> activeNotes = new HashSet<>();

    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    Sequencer sequencer = MidiSystem.getSequencer();

    final Stage dialog = new Stage();

    final Stage scoreScreen = new Stage();

    public playController() throws MidiUnavailableException {
    }

    /**
     * Sets up the Free Play screen and the interactive piano keys.
     */
    @FXML
    public void initialize() throws MidiUnavailableException, InvalidMidiDataException, IOException {
        createPauseMenu();
        setupKeyListener();
        scoreBar = new ProgressBar();
        score = 0;

        noteNames = new HashMap<>();

        noteSprite cSprite = new noteSprite(white0, noteC);
        noteNames.put("C", cSprite);

        noteSprite cSharpSprite = new noteSprite(black0, noteCSharp);
        noteNames.put("C#", cSharpSprite);
        noteNames.put("Db", cSharpSprite);

        noteSprite dSprite = new noteSprite(white1, noteD);
        noteNames.put("D", dSprite);

        noteSprite dSharpSprite = new noteSprite(black1, noteDSharp);
        noteNames.put("D#", dSharpSprite);
        noteNames.put("Eb", dSharpSprite);

        noteSprite eSprite = new noteSprite(white2, noteE);
        noteNames.put("E", eSprite);

        noteSprite fSprite = new noteSprite(white3, noteF);
        noteNames.put("F", fSprite);

        noteSprite fSharpSprite = new noteSprite(black2, noteFSharp);
        noteNames.put("F#", fSharpSprite);
        noteNames.put("Gb", fSharpSprite);

        noteSprite gSprite = new noteSprite(white4, noteG);
        noteNames.put("G", gSprite);

        noteSprite gSharpSprite = new noteSprite(black3, noteGSharp);
        noteNames.put("G#", gSharpSprite);
        noteNames.put("Ab", gSharpSprite);

        noteSprite aSprite = new noteSprite(white5, noteA);
        noteNames.put("A", aSprite);

        noteSprite aSharpSprite = new noteSprite(black4, noteASharp);
        noteNames.put("A#", aSharpSprite);
        noteNames.put("Bb", aSharpSprite);

        noteSprite bSprite = new noteSprite(white6, noteB);
        noteNames.put("B", bSprite);

        if (CurrentLesson.playLesson()) {
            startButton.setText("Play lesson");
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
        Button restartButton = new Button("Restart");
        restartButton.setShape(new Circle(6.0));
        Button optionsButton = new Button("Options");
        optionsButton.setShape(new Circle(6.0));
        Button quitButton = new Button("Quit");
        quitButton.setShape(new Circle(6.0));

        resumeButton.setLayoutX(175); resumeButton.setLayoutY(50);
        restartButton.setLayoutX(175); restartButton.setLayoutY(100);
        optionsButton.setLayoutX(175); optionsButton.setLayoutY(150);
        quitButton.setLayoutX(185); quitButton.setLayoutY(200);

        newPane.getChildren().addAll(resumeButton, restartButton, optionsButton, quitButton);

        resumeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (sequencer.isOpen()) {
                    sequencer.start();
                }
                dialog.close();
            }
        });

        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (sequencer.isOpen()) {
                    sequencer.setTickPosition(0);
                    sequencer.start();
                }
                dialog.close();
            }
        });

        optionsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (sequencer.isOpen()) {
                        sequencer.close();
                    }

                    Preludio.getInstance().setNewScene("/view/fxml/settings.fxml");
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
                // Stop parser and other stuff
                try {
                    if (sequencer.isOpen()) {
                        sequencer.close();
                    }
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
                if (command == ShortMessage.NOTE_OFF
                        || command == 0x58
                        || (command == ShortMessage.NOTE_ON
                        && sm.getData2() == 0)) {
                    com = 2;
                    endTime = me.getTick();
                    float tickLen = (60000.0f / (bpm * 192.0f));
                    for (long startTime : startTimes) {
                        // assumption is that when there are multiple entries
                        // All Notes Off is used and not a specific Note Off
                        long time = (long) tickLen * (endTime - startTime);
                        //System.out.println(time + " ms");
                        noteTimes.add(time);
                    }
                    startTimes.clear();
                } else if (command == ShortMessage.NOTE_ON) {
                    com = 1;
                    startTimes.add(me.getTick());
                    numNotes++;
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
        score = 0;
        maxScore = 0;
        numNotes = 0;
        scoreBox.setText(String.valueOf(score));
        final File[] midiFile = new File[1];
        fileChooser.setTitle("Project Preludio 2017: Open MIDI File");
        //fileChooser.setInitialDirectory(startDir);

        if (CurrentLesson.playLesson()) {
            midiFile[0] = CurrentLesson.getMidi();
        } else {
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
        }

        if (midiFile[0] != null && midiFile[0].getName().contains(".mid")) {
            System.out.println("got the midi file");
            titleBox.setText(midiFile[0].getName());
            scoreBox.setText(String.valueOf(score));

            Sequence sequence = MidiSystem.getSequence(midiFile[0]);
            bpm = sequencer.getTempoInBPM();
            System.out.println("BPM: " + bpm);

            Track[] tracks = sequence.getTracks();
            Track trk = sequence.createTrack();
            for (Track track : tracks) {
                addNotesToTrack(track, trk);
            }

            System.out.println("Starting Level");

            maxScore = 500 * numNotes;

            sequencer.open();
            MetaEventListener play = new MetaEventListener() {
                @Override
                public void meta(MetaMessage meta) {
                    final int type = meta.getType();
                    if (type == 1) {
                        //System.out.printf("Note: %s, Octave: %d\n", NOTE_NAMES[meta.getData()[1] % 12], (meta.getData()[1] / 12) - 1);
                        if (((meta.getData()[1] / 12) - 1) >= 4) {
                            String noteName = NOTE_NAMES[meta.getData()[1] % 12];
                            noteNames.get(noteName).getKey().setEffect(new Glow());
                            //System.out.println("Key X: " + noteNames.get(noteName).getLayoutX() + " Key Y: " + noteNames.get(noteName).getLayoutY());
                            noteNames.get(noteName).getNote().setVisible(true);
                            noteNames.get(noteName).getTransition().setDuration(new Duration(noteTimes.get(noteTimesIndex) - 100));
                            noteTimesIndex++;
                            activeNotes.add(noteNames.get(noteName));
                            noteNames.get(noteName).getTransition().play();
                        }
                    } else if (type == 2) {
                        String noteName = NOTE_NAMES[meta.getData()[1] % 12];
                        activeNotes.remove(noteNames.get(noteName));
                        scoreBox.setText(String.valueOf(score));
                        //System.out.println("Score " + score);
                        float scoreF = (float) (score / maxScore);
                        scoreBar.setProgress(scoreF);
                    } else if (type == 0x2F) {
                        System.out.println("Song Over");

                        sequencer.stop();

                        sequencer.close();
                    }
                }
            };

            sequencer.addMetaEventListener(play);
            sequencer.setSequence(sequence);

            sequencer.start();
        }

//        createScoreScreen(midiFile[0].getName(), String.valueOf(score));
    }

    public void createScoreScreen(String songName, String scoreStr) {
        System.out.println("Creating Score Screen");
        scoreScreen.setResizable(false);
        scoreScreen.setTitle("Results");
        scoreScreen.initModality(Modality.APPLICATION_MODAL);
        scoreScreen.initOwner(Preludio.getInstance().getStage());
        Pane newPane = new Pane();

        Label title = new Label("Results");
        title.setLayoutX(223); title.setLayoutY(27); title.setFont(new Font(24));

        Label name = new Label("Song Name:");
        name.setLayoutX(93); name.setLayoutY(121); name.setFont(new Font(18));

        Label score = new Label("Score:");
        score.setLayoutX(120); score.setLayoutY(190); score.setFont(new Font(18));

        Label nameValue = new Label(songName);
        nameValue.setLayoutX(263); nameValue.setLayoutY(124); nameValue.setFont(new Font(18));

        Label scoreValue = new Label(scoreStr);
        scoreValue.setLayoutX(232); scoreValue.setLayoutY(193); scoreValue.setFont(new Font(18));

        Button saveExitButton = new Button("Save and Exit");
        saveExitButton.setLayoutX(203); saveExitButton.setLayoutY(283);
        saveExitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saveScore(songName, scoreStr);
                scoreScreen.close();
            }
        });

        newPane.getChildren().addAll(title, name, score, nameValue, scoreValue, saveExitButton);
        Scene scoreScene = new Scene(newPane, 500, 400);
        scoreScreen.setScene(scoreScene);
        scoreScreen.show();
        System.out.println("Score Screen Shown");
    }

    @FXML
    public void saveScore(String name, String score) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
        FileWriter writer = null;
        try {
            writer = new FileWriter("scores.csv", true);
            System.out.println("File being written");
            writer.write(timeStamp + ", " + name + ", " + score + "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                System.out.println("Writer closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setupKeyListener() {
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    // Pause key pressed
                    if (sequencer.isRunning()) {
                        sequencer.stop();
                    }
                    dialog.show();
                } else if (event.getCode().equals(Settings.getKeyC())) {
                    // C key pressed
                    noteNames.get("C").getKey().setFill(Color.BLUE);
                    //Preludio.getInstance().noteSound.play();
                    noteCollisionCheck();
                } else if (event.getCode().equals(Settings.getKeyCSharp())) {
                    // C# key pressed
                    noteNames.get("C#").getKey().setFill(Color.BLUE);
                    //Preludio.getInstance().noteSound.play();
                    noteCollisionCheck();
                } else if (event.getCode().equals(Settings.getKeyD())) {
                    // D key pressed
                    noteNames.get("D").getKey().setFill(Color.BLUE);
                    //Preludio.getInstance().noteSound.play();
                    noteCollisionCheck();
                } else if (event.getCode().equals(Settings.getKeyDSharp())) {
                    // D# key pressed
                    noteNames.get("D#").getKey().setFill(Color.BLUE);
                    //Preludio.getInstance().noteSound.play();
                    noteCollisionCheck();
                } else if (event.getCode().equals(Settings.getKeyE())) {
                    // E key pressed
                    noteNames.get("E").getKey().setFill(Color.BLUE);
                    //Preludio.getInstance().noteSound.play();
                    noteCollisionCheck();
                } else if (event.getCode().equals(Settings.getKeyF())) {
                    // F key pressed
                    noteNames.get("F").getKey().setFill(Color.BLUE);
                    //Preludio.getInstance().noteSound.play();
                    noteCollisionCheck();
                } else if (event.getCode().equals(Settings.getKeyFSharp())) {
                    // F# key pressed
                    noteNames.get("F#").getKey().setFill(Color.BLUE);
                    //Preludio.getInstance().noteSound.play();
                    noteCollisionCheck();
                } else if (event.getCode().equals(Settings.getKeyG())) {
                    // G key pressed
                    noteNames.get("G").getKey().setFill(Color.BLUE);
                    //Preludio.getInstance().noteSound.play();
                    noteCollisionCheck();
                } else if (event.getCode().equals(Settings.getKeyGSharp())) {
                    // G# key pressed
                    noteNames.get("G#").getKey().setFill(Color.BLUE);
                    //Preludio.getInstance().noteSound.play();
                    noteCollisionCheck();
                } else if (event.getCode().equals(Settings.getKeyA())) {
                    // A key pressed
                    noteNames.get("A").getKey().setFill(Color.BLUE);
                    //Preludio.getInstance().noteSound.play();
                    noteCollisionCheck();
                } else if (event.getCode().equals(Settings.getKeyASharp())) {
                    // A# key pressed
                    noteNames.get("A#").getKey().setFill(Color.BLUE);
                    //Preludio.getInstance().noteSound.play();
                    noteCollisionCheck();
                } else if (event.getCode().equals(Settings.getKeyB())) {
                    // B key pressed
                    noteNames.get("B").getKey().setFill(Color.BLUE);
                    //Preludio.getInstance().noteSound.play();
                    noteCollisionCheck();
                }
            }
        });

        pane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(Settings.getKeyC())) {
                    // C key pressed
                    // noteCollisionCheck()
                    noteNames.get("C").getKey().setFill(Color.BEIGE);
                } else if (event.getCode().equals(Settings.getKeyCSharp())) {
                    // C# key pressed
                    noteNames.get("C#").getKey().setFill(Color.BLACK);
                } else if (event.getCode().equals(Settings.getKeyD())) {
                    // D key pressed
                    noteNames.get("D").getKey().setFill(Color.BEIGE);
                } else if (event.getCode().equals(Settings.getKeyDSharp())) {
                    // D# key pressed
                    noteNames.get("D#").getKey().setFill(Color.BLACK);
                } else if (event.getCode().equals(Settings.getKeyE())) {
                    // E key pressed
                    noteNames.get("E").getKey().setFill(Color.BEIGE);
                } else if (event.getCode().equals(Settings.getKeyF())) {
                    // F key pressed
                    noteNames.get("F").getKey().setFill(Color.BEIGE);
                } else if (event.getCode().equals(Settings.getKeyFSharp())) {
                    // F# key pressed
                    noteNames.get("F#").getKey().setFill(Color.BLACK);
                } else if (event.getCode().equals(Settings.getKeyG())) {
                    // G key pressed
                    noteNames.get("G").getKey().setFill(Color.BEIGE);
                } else if (event.getCode().equals(Settings.getKeyGSharp())) {
                    // G# key pressed
                    noteNames.get("G#").getKey().setFill(Color.BLACK);
                } else if (event.getCode().equals(Settings.getKeyA())) {
                    // A key pressed
                    noteNames.get("A").getKey().setFill(Color.BEIGE);
                } else if (event.getCode().equals(Settings.getKeyASharp())) {
                    // A# key pressed
                    noteNames.get("A#").getKey().setFill(Color.BLACK);
                } else if (event.getCode().equals(Settings.getKeyB())) {
                    // B key pressed
                    noteNames.get("B").getKey().setFill(Color.BEIGE);
                }
            }
        });
    }

    public void noteCollisionCheck() {
        for (noteSprite sprite : activeNotes) {
            //double deltaX = sprite.getTransition().getToX() - sprite.getNote().getX();
            double deltaY = sprite.getTransition().getToY() - sprite.getNote().getY();
            //System.out.println("DeltaX " + deltaX);
            //System.out.println("DeltaY " + deltaY);

            if ((deltaY > 300 && deltaY < 600)) {
                score += 100;
                activeNotes.remove(sprite);
                sprite.getNote().setVisible(false);
                //feedback.setTextFill(Color.RED);
                //feedback.setText("BAD");
            } else if ((deltaY > 100 && deltaY < 300)) {
                score += 300;
                activeNotes.remove(sprite);
                sprite.getNote().setVisible(false);
                //feedback.setTextFill(Color.GREEN);
                //feedback.setText("FINE");
            } else if ((deltaY > 0 && deltaY < 100)) {
                score += 500;
                activeNotes.remove(sprite);
                sprite.getNote().setVisible(false);
                //feedback.setTextFill(Color.ALICEBLUE);
                //feedback.setText("EXCELLENT");
            } else {
                score += 0;
            }
        }
    }
}
