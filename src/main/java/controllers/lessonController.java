package controllers;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import engine.CurrentLesson;
import engine.Preludio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Fox Kiester on 3/09/17.
 */
public class lessonController {
    private DirectoryChooser dirChooser = new DirectoryChooser();
    private File lessonFile;
    private File midiFile;

    @FXML
    private Button play_button;

    @FXML
    private Button preview_button;

    @FXML
    private Button select_button;

    @FXML
    private Button back_button;

    @FXML
    private TextArea lesson_text;

    @FXML
    void initialize() throws FileNotFoundException {
        lesson_text.setWrapText(true);
        lesson_text.setEditable(false);
        String text = "";
        if (CurrentLesson.getText() != null) {
            text = new Scanner(CurrentLesson.getText()).useDelimiter("\\Z").next();
        }
        lesson_text.setText(text);
    }

    @FXML
    void selectLesson(ActionEvent event) throws IOException {
        dirChooser.setTitle("Project Preludio 2017: Open Lesson Folder");
        // dirChooser.setInitialDirectory(startDir);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Please Select a Lesson");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                File dir = dirChooser.showDialog(select_button.getScene().getWindow());
                for (File file : dir.listFiles()) {
                    if (file.getName().endsWith(".txt")) {
                        lessonFile = file;
                    } else if (file.getName().contains(".mid")) {
                        midiFile = file;
                    }
                }
                CurrentLesson.setMidi(midiFile);
                CurrentLesson.setText(lessonFile);
                if (lessonFile != null) {
                    try {
                        String text = new Scanner(lessonFile).useDelimiter("\\Z").next();
                        lesson_text.setText(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @FXML
    void previewLesson(ActionEvent event) throws IOException {
        if (CurrentLesson.getMidi() != null) {
            CurrentLesson.setPlayLesson(true);
            Preludio.getInstance().setNewScene("/view/fxml/concert.fxml");
        }
    }

    @FXML
    void playLesson(ActionEvent event) throws IOException {
        if (CurrentLesson.getMidi() != null) {
            CurrentLesson.setPlayLesson(true);
            Preludio.getInstance().setNewScene("/view/fxml/rhythmGame.fxml");
        }
    }

    /**
     * Asks user if they are sure if they want to exit Lessons and, if yes,
     * sets up and shows the Main Menu Screen.
     *
     * @param event if the Main Menu fxml file cannot be found.
     */
    @FXML
    void goBack(ActionEvent event) {
        Preludio.getInstance().buttonSound.play();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit Lessons?");
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
