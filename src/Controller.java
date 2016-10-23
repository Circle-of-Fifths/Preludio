import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
//import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
//import org.w3c.dom.css.Rect;

import java.net.URL;

public class Controller {

    private static Stage stage;
    private MediaPlayer titlePlayer = this.createMusicPlayer(
            "/Resources/Music/BBC5_I.mp3", .75, true);
    private MediaPlayer buttonSound = this.createMusicPlayer(
            "/Resources/Music/buttonSound.mp3", .20, false);

    /**
     * Constructor for Controller
     * @param stage Primary stage from main method
     */
    public Controller(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method that sets up and shows
     * title screen
     */
    public void gotoTitleScreen() {
        Group root = new Group();
        Pane layout = new Pane();

        Image titleImg = new Image("/Resources/Backgrounds/title.jpg",
                800, 650, false, false);
        ImageView titleIv = new ImageView(titleImg);

        Button start = new Button("START");
        start.setLayoutX(345);
        start.setLayoutY(350);
        start.setStyle("-fx-font: 32 serif; -fx-base: #b6e7c9;");
        start.setShape(new Circle(1.0));

        this.setButtonGraphics(start);

        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonSound.play();
                gotoMainMenu();
            }
        });

        root.getChildren().add(titleIv);
        layout.getChildren().addAll(start);
        root.getChildren().add(layout);

        Scene scene = new Scene(root, 800, 650);
        stage.setScene(scene);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    buttonSound.play();
                    gotoMainMenu();
                }
            }
        });

        titlePlayer.stop();
        titlePlayer.play();
        stage.show();
    }

    /**
     * Method that sets up and shows
     * Main Menu screen
     */
    private void gotoMainMenu() {
        titlePlayer.play();
        Group root = new Group();
        Pane layout = new Pane();

        Image mainMenuImg = new Image("/Resources/Backgrounds/"
                 + "mainMenu.jpg",
                800, 650, false, false);
        ImageView titleView = new ImageView(mainMenuImg);
        root.getChildren().add(titleView);

        Image freePlayImage = new Image("/Resources/Backgrounds/"
                + "freePlay.jpg", 32, 32, false, false);

        Button freePlay = new Button("Free Play", new ImageView(freePlayImage));
        freePlay.setStyle("-fx-font: 24 serif; -fx-base: #b6e7c9;");
        freePlay.setLayoutX(550);
        freePlay.setLayoutY(300);
        this.setButtonGraphics(freePlay);

        freePlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonSound.play();
                titlePlayer.pause();
                gotoFreePlay();
            }
        });

        Image playImage = new Image("/Resources/Backgrounds/"
                + "play.jpg", 32, 32, false, false);

        Button play = new Button("Play", new ImageView(playImage));
        play.setStyle("-fx-font: 24 serif; -fx-base: #b6e7c9;");
        play.setLayoutX(340);
        play.setLayoutY(100);
        this.setButtonGraphics(play);

        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonSound.play();
                gotoMainGame();
            }
        });

        Image recordsImage = new Image("/Resources/Backgrounds/"
                + "records.jpg", 32, 32, false, false);

        Button records = new Button("Records", new ImageView(recordsImage));
        records.setStyle("-fx-font: 24 serif; -fx-base: #b6e7c9;");
        records.setLayoutX(320);
        records.setLayoutY(525);
        this.setButtonGraphics(records);

        records.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonSound.play();
                gotoRecords();
            }
        });

        Image concertImage = new Image("/Resources/Backgrounds/"
                + "concert.jpg", 32, 32, false, false);

        Button concert = new Button("Concert", new ImageView(concertImage));
        concert.setStyle("-fx-font: 24 serif; -fx-base: #b6e7c9;");
        concert.setLayoutX(90);
        concert.setLayoutY(300);
        this.setButtonGraphics(concert);

        concert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonSound.play();
                gotoConcert();
            }
        });

        MenuBar menuBar = createMenu();

        layout.getChildren().addAll(freePlay, play,
                records, concert);
        root.getChildren().addAll(layout, menuBar);

        Scene scene = new Scene(root, 800, 650);
        stage.setScene(scene);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure you want to return to Title?");
                    alert.showAndWait().filter(response ->
                            response == ButtonType.OK).ifPresent(
                                response -> gotoTitleScreen());
                }
            }
        });
        stage.show();
    }

    /**
     * Sets up and goes to Settings Screen
     */
    private void gotoSettings() {
        Group root = new Group();
        GridPane layout = new GridPane();
        layout.setVgap(20);
        layout.setHgap(10);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(15);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(10);
        col2.setHalignment(HPos.CENTER);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(65);
        col3.setHalignment(HPos.CENTER);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(10);
        col4.setHalignment(HPos.CENTER);
        layout.getColumnConstraints().addAll(col1, col2, col3, col4);
        layout.setPadding(new Insets(10, 10, 10, 10));

        Label title = new Label("General Settings");
        title.setStyle("-fx-font: 40 serif; -fx-underline: true;");
        layout.add(title, 0, 0, 4, 1);
        layout.setHalignment(title, HPos.CENTER);

        Label musVolCaption = new Label("Music Volume:");
        layout.add(musVolCaption, 0, 1);

        Label musVolValue = new Label(
                String.valueOf((int) (titlePlayer.getVolume() * 100)));
        layout.add(musVolValue, 1, 1);

        Image muteImg = new Image(
                "/Resources/Backgrounds/muteIcon.jpg", 20, 20, true, true);
        Image unmuteImg = new Image(
                "/Resources/Backgrounds/unmuteIcon.png", 20, 20, true, true);

        ImageView musMuteIv = new ImageView(muteImg);
        ImageView musUnmuteIv = new ImageView(unmuteImg);
        Button musMuteButton = new Button();
        musMuteButton.setGraphic(musMuteIv);
        layout.add(musMuteButton, 3, 1);

        Slider musVolSlider = new Slider(0, 100, titlePlayer.getVolume() * 100);
        musVolSlider.setShowTickLabels(true);
        musVolSlider.setShowTickMarks(true);
        musVolSlider.setMajorTickUnit(10);
        musVolSlider.setMinorTickCount(1);
        musVolSlider.setBlockIncrement(1);
        musVolSlider.setPrefWidth(500);
        layout.add(musVolSlider, 2, 1);

        musVolSlider.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue a0, Object a1, Object a2) {
                musVolValue.textProperty().setValue(
                        String.valueOf((int) musVolSlider.getValue()));
                titlePlayer.volumeProperty().setValue(
                        musVolSlider.getValue() / 100);
                if (musVolSlider.getValue() == 0) {
                    musMuteButton.setGraphic(musUnmuteIv);
                } else {
                    musMuteButton.setGraphic(musMuteIv);
                }
            }
        });

        musMuteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonSound.play();
                if (musVolSlider.getValue() == 0) {
                    musVolSlider.valueProperty().setValue(50);
                } else {
                    musVolSlider.valueProperty().setValue(0);
                }
            }
        });

        Label buttVolCaption = new Label("SFX Volume:");
        layout.add(buttVolCaption, 0, 2);

        Label buttVolValue = new Label(
                String.valueOf((int) (buttonSound.getVolume() * 100)));
        layout.add(buttVolValue, 1, 2);

        ImageView buttMuteIv = new ImageView(muteImg);
        ImageView buttUnmuteIv = new ImageView(unmuteImg);
        Button buttMuteButton = new Button();
        buttMuteButton.setGraphic(buttMuteIv);
        layout.add(buttMuteButton, 3, 2);

        Slider buttVolSlider = new Slider(0, 100, buttonSound.getVolume() * 100);
        buttVolSlider.setShowTickLabels(true);
        buttVolSlider.setShowTickMarks(true);
        buttVolSlider.setMajorTickUnit(10);
        buttVolSlider.setMinorTickCount(1);
        buttVolSlider.setBlockIncrement(1);
        buttVolSlider.setPrefWidth(500);
        layout.add(buttVolSlider, 2, 2);

        buttVolSlider.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue a0, Object a1, Object a2) {
                buttVolValue.textProperty().setValue(
                        String.valueOf((int) buttVolSlider.getValue()));
                buttonSound.volumeProperty().setValue(
                        buttVolSlider.getValue() / 100);
                if (buttVolSlider.getValue() == 0) {
                    buttMuteButton.setGraphic(buttUnmuteIv);
                } else {
                    buttMuteButton.setGraphic(buttMuteIv);
                }
            }
        });

        buttMuteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonSound.play();
                if (buttVolSlider.getValue() == 0) {
                    buttVolSlider.valueProperty().setValue(20);
                } else {
                    buttVolSlider.valueProperty().setValue(0);
                }
            }
        });

        HBox buttonBox = new HBox();

        Button resetButton = new Button("Reset to Default");
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonSound.play();
                musVolSlider.setValue(75);
                buttVolSlider.setValue(20);
            }
        });
        buttonBox.getChildren().add(resetButton);

        Button okButton = new Button("OK");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                buttonSound.play();
                gotoMainMenu();
            }
        });
        buttonBox.getChildren().add(okButton);

        layout.add(buttonBox, 0, 3, 4, 1);


        root.getChildren().add(layout);

        Scene scene = new Scene(root, 800, 650);
        stage.setScene(scene);
    }

    /**
     * Sets up and goes to Free Play Mode
     */
    private void gotoFreePlay() {
        Group root = new Group();
        Pane layout = new Pane();

        Image background = new Image("/Resources/Backgrounds/"
                + "freePlayBackground.jpg", 800, 650, false, false);
        ImageView view = new ImageView(background);
        layout.getChildren().add(view);

        MediaPlayer[] whiteSounds = new MediaPlayer[8];
        for (int i = 0; i < whiteSounds.length; i++) {
            String path = "/Resources/Music/notes/white" + i + ".mp3";
            whiteSounds[i] = createMusicPlayer(path, 1, false);
            whiteSounds[i].setStartTime(new Duration(200));
        }

        Rectangle[] whiteKeys = new Rectangle[8];
        for (int i = 0; i < whiteKeys.length; i++) {
            whiteKeys[i] = new Rectangle(50, 325, Color.BEIGE);
            setUpKey(whiteKeys[i], i, Color.BEIGE, whiteSounds[i]);

            whiteKeys[i].setStroke(Color.BLACK);
            whiteKeys[i].setStrokeWidth(0.5);

            layout.getChildren().add(whiteKeys[i]);
        }

        MediaPlayer[] blackSounds = new MediaPlayer[5];
        for (int i = 0; i < blackSounds.length; i++) {
            String path = "/Resources/Music/notes/black" + i + ".mp3";
            blackSounds[i] = createMusicPlayer(path, 1, false);
            blackSounds[i].setStartTime(new Duration(200));
        }

        Rectangle[] blackKeys = new Rectangle[5];
        for (int i = 0; i < 6; i++) {
            if (i != 2) {
                if (i > 2) {
                    blackKeys[i - 1] = new Rectangle(37.5, 214.5, Color.BLACK);
                    this.setUpKey(blackKeys[i - 1], i, Color.BLACK, blackSounds[i - 1]);

                    layout.getChildren().add(blackKeys[i - 1]);
                } else {
                    blackKeys[i] = new Rectangle(37.5, 214.5, Color.BLACK);
                    this.setUpKey(blackKeys[i], i, Color.BLACK, blackSounds[i]);

                    layout.getChildren().add(blackKeys[i]);
                }
            }
        }

        root.getChildren().add(layout);
        Scene scene = new Scene(root, 800, 650);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure you want to return to the Main Menu?");
                    alert.showAndWait().filter(response ->
                            response == ButtonType.OK).ifPresent(
                                response -> gotoMainMenu());
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Private helper method to set up individual
     * piano keys
     * @param rect rectangle object being passed in
     * @param i index of the rectangle object in the keys array
     * @param paint color of the rectangle object
     * @param mediaPlayer sound connceted to key
     */
    private void setUpKey(Rectangle rect, int i, Paint paint, MediaPlayer mediaPlayer) {
        rect.setY(300);
        rect.setArcHeight(15);
        rect.setArcWidth(15);
        if (paint == Color.BLACK) {
            rect.setX(262.5 + i * 50);
        } else if (paint == Color.BEIGE) {
            rect.setX(225 + i * 50);
        }

        /* rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.play();
                if (paint == Color.BLACK) {
                    rect.setFill(Color.DARKBLUE);
                } else {
                    rect.setFill(Color.DARKGREY);
                }
            }
        }); */

        rect.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.play();
                if (paint == Color.BLACK) {
                    rect.setFill(Color.DARKBLUE);
                } else {
                    rect.setFill(Color.DARKGREY);
                }
            }
        });

        rect.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.stop();
                rect.setFill(paint);
            }
        });
    }

    /**
     * Sets up and goes to Records Screen
     */
    private void gotoRecords() {

    }

    /**
     * Sets up and goes to Concert Mode
     */
    private void gotoConcert() {

    }

    /**
     * Sets up and goes to Main Game Mode
     */
    private void gotoMainGame() {

    }

    /**
     * Creates a media player for the given
     * song string representation
     * @param songStr String representation of song
     * @param isLooping boolean determining if song will loop
     * @param volume volume that the music will play
     * @return Media Player of song passed in
     */
    private MediaPlayer createMusicPlayer(String songStr,
                                          double volume, boolean isLooping) {
        URL songUrl = getClass().getResource(songStr);
        Media song = new Media(songUrl.toString());
        MediaPlayer player = new MediaPlayer(song);
        player.setVolume(volume);
        if (isLooping) {
            player.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    player.seek(Duration.ZERO);
                }
            });
        } else {
            player.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    player.stop();
                }
            });
        }
        return player;
    }

    /**
     * Private Helper method to create a screen's menu
     * @return returns screen's menu
     */
    private MenuBar createMenu() {
        Image saveImage = new Image("/Resources/Backgrounds/"
                + "save.png", 32, 32, false, false);
        Image loadImage = new Image("/Resources/Backgrounds/"
                + "load.png", 32, 32, false, false);

        Image settingsImage = new Image("/Resources/Backgrounds/"
                + "settings.png", 32, 32, false, false);

        Menu menuFile = new Menu("File");
        MenuItem save = new MenuItem("Save  ctrl+s",
                new ImageView(saveImage));
        MenuItem load = new MenuItem("Load  ctrl+l",
                new ImageView(loadImage));
        MenuItem title = new MenuItem("Return to Title  Esc");
        MenuItem exit = new MenuItem("Exit");

        title.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to return to Title?");
                alert.showAndWait().filter(response ->
                        response == ButtonType.OK).ifPresent(
                            response -> gotoTitleScreen());
            }
        });

        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to quit?");
                alert.showAndWait().filter(response ->
                        response == ButtonType.OK).ifPresent(
                            response -> System.exit(0));
            }
        });
        menuFile.getItems().addAll(save, load, title, exit);

        Menu menuOptions = new Menu("Options");
        MenuItem settings = new MenuItem("Settings",
                new ImageView(settingsImage));
        settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gotoSettings();
            }
        });
        menuOptions.getItems().add(settings);

        Menu menuHelp = new Menu("Help");

        MenuBar menuBar = new MenuBar();
        menuBar.setPrefWidth(800.0);

        menuBar.getMenus().addAll(menuFile, menuOptions, menuHelp);

        return menuBar;
    }

    /**
     * Sets up the button graphics
     * @param button button passed in to add effects to
     */
    private void setButtonGraphics(Button button) {
        Glow glowEffect = new Glow();
        DropShadow shadow = new DropShadow();
        button.setEffect(shadow);
        button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        button.setEffect(glowEffect);
                    }
                });

        button.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        button.setEffect(shadow);
                    }
                });
    }
}
