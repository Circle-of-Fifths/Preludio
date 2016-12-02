package controllers;


import engine.Preludio;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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
    void resetVolumes(ActionEvent event) {
        Preludio.getInstance().buttonSound.play();
        musVolSlider.setValue(75);
        buttVolSlider.setValue(20);
    }


    @FXML
    void goToMainMenu(ActionEvent event) {
        try {
            Preludio.getInstance().setNewScene(
                    "/view/fxml/mainMenu.fxml");
            Preludio.getInstance().titlePlayer.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
