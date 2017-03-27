package controllers;


import engine.Preludio;
import engine.Settings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

import java.awt.event.KeyEvent;
import java.io.IOException;


public class settingsController {

    @FXML
    private Slider musVolSlider;

    @FXML
    private Slider buttVolSlider;

    @FXML
    private Label buttVolCaption;

    @FXML
    private HBox buttonBox;

    @FXML
    private Button okButton;

    @FXML
    private Label musVolCaption;

    @FXML
    private Label title;

    @FXML
    private Button musMuteButton;

    @FXML
    private Label buttVolValue;

    @FXML
    private ImageView buttMuteIV;

    @FXML
    private ImageView musMuteIV;

    @FXML
    private Button buttMuteButton;

    @FXML
    private Label musVolValue;

    @FXML
    private Button resetButton;

    @FXML
    private TextField cKeyField;

    @FXML
    private TextField csKeyField;

    @FXML
    private TextField dKeyField;

    @FXML
    private TextField dsKeyField;

    @FXML
    private TextField eKeyField;

    @FXML
    private TextField fKeyField;

    @FXML
    private TextField fsKeyField;

    @FXML
    private TextField gKeyField;

    @FXML
    private TextField gsKeyField;

    @FXML
    private TextField aKeyField;

    @FXML
    private TextField asKeyField;

    @FXML
    private TextField bKeyField;

    private Image muteImg;
    private Image unmuteImg;

    @FXML
    public void initialize() {
        muteImg = new Image(
                "view/res/muteIcon.jpg", 20, 20, true, true);
        unmuteImg = new Image(
                "view/res/unmuteIcon.png", 20, 20, true, true);
        musMuteIV.setImage(unmuteImg);
        buttMuteIV.setImage(unmuteImg);

        double musVolume = Preludio.getInstance().titlePlayer.getVolume() * 100;
        double buttVolume = Preludio.getInstance().buttonSound.getVolume() * 100;

        musVolValue.setText(String.valueOf((int)musVolume));
        musVolSlider.setValue(musVolume);

        buttVolValue.setText(String.valueOf((int)buttVolume));
        buttVolSlider.setValue(buttVolume);
        System.out.println(buttVolValue.getText());

        musVolSlider.valueProperty().addListener((a0, a1, a2) -> {
            musVolValue.textProperty().setValue(
                    String.valueOf((int) musVolSlider.getValue()));
            Preludio.getInstance().titlePlayer.volumeProperty().setValue(
                    musVolSlider.getValue() / 100);
            if (musVolSlider.getValue() == 0) {
                musMuteIV.setImage(muteImg);
            } else {
                musMuteIV.setImage(unmuteImg);
            }
        });

        buttVolSlider.valueProperty().addListener((a0, a1, a2) -> {
            buttVolValue.textProperty().setValue(
                    String.valueOf((int) buttVolSlider.getValue()));
            Preludio.getInstance().buttonSound.volumeProperty().setValue(
                    buttVolSlider.getValue() / 100);
            if (buttVolSlider.getValue() == 0) {
                buttMuteIV.setImage(muteImg);
            } else {
                buttMuteIV.setImage(unmuteImg);
            }
        });
        reloadKeys();
    }


    @FXML
    void muteMusic(ActionEvent event) {
        Preludio.getInstance().buttonSound.play();
        if (musVolSlider.getValue() == 0) {
            musVolSlider.valueProperty().setValue(50);
        } else {
            musVolSlider.valueProperty().setValue(0);
        }
    }


    @FXML
    void muteButtons(ActionEvent event) {
        Preludio.getInstance().buttonSound.play();
        if (buttVolSlider.getValue() == 0) {
            buttVolSlider.valueProperty().setValue(20);
        } else {
            buttVolSlider.valueProperty().setValue(0);
        }
    }


    @FXML
    void resetSettings(ActionEvent event) {
        Preludio.getInstance().buttonSound.play();
        musVolSlider.setValue(75);
        buttVolSlider.setValue(20);
        Settings.resetKeys();
        reloadKeys();
    }


    @FXML
    void goToMainMenu(ActionEvent event) {
//        Settings.setcKey(KeyEvent.getExtendedKeyCodeForChar(
//                cKeyField.getCharacters().charAt(0)));
        Settings.setKeyC(
                KeyCode.getKeyCode(cKeyField.getCharacters().toString().toUpperCase()));
        Settings.setKeyCSharp(
                KeyCode.getKeyCode(csKeyField.getCharacters().toString().toUpperCase()));
        Settings.setKeyD(
                KeyCode.getKeyCode(dKeyField.getCharacters().toString().toUpperCase()));
        Settings.setKeyDSharp(
                KeyCode.getKeyCode(dsKeyField.getCharacters().toString().toUpperCase()));
        Settings.setKeyE(
                KeyCode.getKeyCode(eKeyField.getCharacters().toString().toUpperCase()));
        Settings.setKeyF(
                KeyCode.getKeyCode(fKeyField.getCharacters().toString().toUpperCase()));
        Settings.setKeyFSharp(
                KeyCode.getKeyCode(fsKeyField.getCharacters().toString().toUpperCase()));
        Settings.setKeyG(
                KeyCode.getKeyCode(gKeyField.getCharacters().toString().toUpperCase()));
        Settings.setKeyGSharp(
                KeyCode.getKeyCode(gsKeyField.getCharacters().toString().toUpperCase()));
        Settings.setKeyA(
                KeyCode.getKeyCode(aKeyField.getCharacters().toString().toUpperCase()));
        Settings.setKeyASharp(
                KeyCode.getKeyCode(asKeyField.getCharacters().toString().toUpperCase()));
        Settings.setKeyB(
                KeyCode.getKeyCode(bKeyField.getCharacters().toString().toUpperCase()));
        try {
            Preludio.getInstance().setNewScene(
                    "/view/fxml/mainMenu.fxml");
            Preludio.getInstance().titlePlayer.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reloadKeys() {
        cKeyField.setText(Settings.getKeyC().toString().toLowerCase());
        csKeyField.setText(Settings.getKeyCSharp().toString().toLowerCase());
        dKeyField.setText(Settings.getKeyD().toString().toLowerCase());
        dsKeyField.setText(Settings.getKeyDSharp().toString().toLowerCase());
        eKeyField.setText(Settings.getKeyE().toString().toLowerCase());
        fKeyField.setText(Settings.getKeyF().toString().toLowerCase());
        fsKeyField.setText(Settings.getKeyFSharp().toString().toLowerCase());
        gKeyField.setText(Settings.getKeyG().toString().toLowerCase());
        gsKeyField.setText(Settings.getKeyGSharp().toString().toLowerCase());
        aKeyField.setText(Settings.getKeyA().toString().toLowerCase());
        asKeyField.setText(Settings.getKeyASharp().toString().toLowerCase());
        bKeyField.setText(Settings.getKeyB().toString().toLowerCase());

    }

}
