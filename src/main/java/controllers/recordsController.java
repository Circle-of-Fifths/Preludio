package controllers;

import engine.Preludio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by jeff on 3/31/17.
 */
public class recordsController {
    @FXML
    Button backButton;

    @FXML
    TableView<String> table;

    @FXML
    public void initialize() {
        File scoreFile = new File("/scores.csv");
        table = new TableView<>();

        List<String> names = new ArrayList<>();
        List<String> scores = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new FileReader(scoreFile));
            scanner.useDelimiter(", ");
            while(scanner.hasNext()) {
                String items = scanner.next();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        Preludio.getInstance().buttonSound.play();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit Records?");
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
}
