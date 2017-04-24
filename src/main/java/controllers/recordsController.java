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
    TableColumn<scoreValue, String> col3;

    @FXML
    TableColumn<scoreValue, String> col4;

    @FXML
    TableColumn<scoreValue, String> col5;


    @FXML
    public void initialize() {
        int iteration = 0;
        File scoreFile = new File(Preludio.getInstance().getSaveFilePath());

        ObservableList<scoreValue> scores = FXCollections.observableArrayList();

        if (scoreFile.exists()) {
            try {
                Scanner scanner = new Scanner(new FileReader(scoreFile));

                while(scanner.hasNextLine()) {
                    if (iteration == 0) {
                        String input = scanner.nextLine();
                    } else {
                        String input = scanner.nextLine();
                        String[] data = input.split(",");
                        scores.add(new scoreValue(data[0], data[1], data[2], data[3], data[4]));
                    }
                    iteration++;
                }

                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        table.setItems(scores);

        col1.setCellValueFactory(new PropertyValueFactory("UserName"));
        col2.setCellValueFactory(new PropertyValueFactory("Name"));
        col3.setCellValueFactory(new PropertyValueFactory("Score"));
        col4.setCellValueFactory(new PropertyValueFactory("Rating"));
        col5.setCellValueFactory(new PropertyValueFactory("Time"));

        table.getColumns().setAll(col1, col2, col3, col4, col5);
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
