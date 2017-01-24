package controllers;

import engine.Preludio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class mainMenuController {

    @FXML
    private Button freePlay_button;

    @FXML
    private Button play_button;

    @FXML
    private Button records_button;

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

    /**
     * Sets up and shows the Free Play Screen.
     *
     * @param event Event caused by the user clicking the Free Play Button.
     *
     * @throws IOException if the Free Play fxml file cannot be found.
     */
    @FXML
    void goToFreePlay(ActionEvent event) throws IOException {
        Preludio.getInstance().buttonSound.play();
        Preludio.getInstance().setNewScene("/view/fxml/freePlay.fxml");
        Preludio.getInstance().titlePlayer.stop();
    }

    /**
     * Sets up and shows the Main Game Screen.
     *
     * @param event Event caused by the user clicking the Play Button.
     *
     * @throws IOException if the Play fxml file cannot be found.
     */
    @FXML
    void goToPlay(ActionEvent event) throws IOException {
        Preludio.getInstance().buttonSound.play();
        Preludio.getInstance().setNewScene("/view/fxml/play.fxml");
    }


    /**
     * Sets up and shows the Records Screen.
     *
     * @param event Event caused by the user clicking the Records Button.
     *
     * @throws IOException if the Records fxml file cannot be found.
     */
    @FXML
    void goToRecords(ActionEvent event) throws IOException {
        Preludio.getInstance().buttonSound.play();
        Preludio.getInstance().setNewScene("/view/fxml/records.fxml");
    }


    /**
     * Sets up and shows the Concert Screen.
     *
     * @param event Event caused by the user clicking the Concert Button.
     *
     * @throws IOException if the Concert fxml file cannot be found.
     */
    @FXML
    void goToConcert(ActionEvent event) throws IOException {
        Preludio.getInstance().buttonSound.play();
        Preludio.getInstance().setNewScene("/view/fxml/concert.fxml");
        Preludio.getInstance().titlePlayer.stop();
    }


    /**
     * Sets up and shows the Settings Screen.
     *
     * @param event Event caused by the user clicking the Settings Button.
     *
     * @throws IOException if the Settings fxml file cannot be found.
     */
    @FXML
    void goToSettings(ActionEvent event) throws IOException {
        Preludio.getInstance().buttonSound.play();
        Preludio.getInstance().setNewScene("/view/fxml/settings.fxml");
    }


    /**
     *  Asks user if they are sure they want to leave, then if yes, sets up
     *  and shows the Title Screen.
     *
     * @param event Event caused by the user hitting the ESCAPE key.
     *
     * @throws IOException if the Title Screen fxml file cannot be found.
     */
    @FXML
    void goBack(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to return to Title?");
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().filter(response ->
                    response == buttonTypeYes).ifPresent(response -> {
                try {
                    Preludio.getInstance().setNewScene(
                            "/view/fxml/titleScreen.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}

