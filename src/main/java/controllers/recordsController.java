package controllers;

import engine.Preludio;
import engine.scoreValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by jeff on 3/31/17.
 */
public class recordsController {
    @FXML
    Button backButton;

    @FXML
    TableView<scoreValue> table;

    @FXML
    TableColumn<scoreValue, String> col1;
    @FXML
    TableColumn<scoreValue, String> col2;

    @FXML
    public void initialize() {
        File scoreFile = new File("scores.csv");

        ObservableList<scoreValue> scores = FXCollections.observableArrayList();

        try {
            Scanner scanner = new Scanner(new FileReader(scoreFile));
            scanner.useDelimiter(",");
            while(scanner.hasNext()) {
                String item = scanner.next();
                //System.out.println(item);

                String item2 = scanner.next();
                //System.out.println(item2);

                String item3 = scanner.next();
                //System.out.println(item3);

                scores.add(new scoreValue(item2, item3));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        table.setItems(scores);

        col1.setCellValueFactory(new PropertyValueFactory("Name"));
        col2.setCellValueFactory(new PropertyValueFactory("Score"));

        table.getColumns().setAll(col1, col2);
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

    public void saveExit(MouseEvent mouseEvent) {
    }
}
