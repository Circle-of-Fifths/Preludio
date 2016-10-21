import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.css.Rect;

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
     * Title screen
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

        titlePlayer.play();
        stage.show();
    }

    /**
     * Method that sets up and shows
     * Main Menu screen
     */
    private void gotoMainMenu() {
        Group root = new Group();
        Pane layout = new Pane();

        Image mainMenuImg = new Image("/Resources/Backgrounds/"
                 + "circle_of_fifths_colors.png",
                800, 650, false, false);
        ImageView titleIv = new ImageView(mainMenuImg);
        root.getChildren().add(titleIv);

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
                    titlePlayer.stop();
                    buttonSound.play();
                    gotoTitleScreen();
                } else if (event.getCode() == KeyCode.LEFT) {
                    // Code for when Left Arrow pressed
                    buttonSound.play();
                } else if (event.getCode() == KeyCode.RIGHT) {
                    // Code for when Right Arrow pressed
                    buttonSound.play();
                }
            }
        });
        stage.show();
    }

    /**
     * Sets up and goes to Settings Screen
     */
    private void gotoSettings() {

    }

    /**
     * Sets up and goes to Free Play Mode
     */
    private void gotoFreePlay() {
        Group root = new Group();
        Pane layout = new Pane();

//        DropShadow shadow = new DropShadow();

        Rectangle whiteKeys[] = new Rectangle[7];
        for (int i = 0; i < whiteKeys.length; i++) {
            whiteKeys[i] = new Rectangle(50, 325, Color.BEIGE);
            setUpKey(whiteKeys[i], i, Color.BEIGE);

            whiteKeys[i].setStroke(Color.BLACK);
            whiteKeys[i].setStrokeWidth(0.5);

            layout.getChildren().add(whiteKeys[i]);
        }

        Rectangle blackKeys[] = new Rectangle[5];

        for (int i = 0; i < 6; i++) {
            if (i != 2) {
                if (i > 2) {
                    blackKeys[i-1] = new Rectangle(37.5, 214.5, Color.BLACK);
                    setUpKey(blackKeys[i-1], i, Color.BLACK);

                    layout.getChildren().add(blackKeys[i-1]);
                } else {
                    blackKeys[i] = new Rectangle(37.5, 214.5, Color.BLACK);
                    setUpKey(blackKeys[i], i, Color.BLACK);

                    layout.getChildren().add(blackKeys[i]);
                }
            }
        }


        //layout.getChildren().addAll();
        root.getChildren().add(layout);
        Scene scene = new Scene(root, 800, 650);
        stage.setScene(scene);
        stage.show();
    }

    private void setUpKey(Rectangle rect, int i, Paint paint) {
        rect.setY(300);
        rect.setArcHeight(15);
        rect.setArcWidth(15);
        if (paint == Color.BLACK) {
            rect.setX(262.5 + i*50);
        } else if (paint == Color.BEIGE) {
            rect.setX(225 + i*50);
        }

        rect.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
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
                titlePlayer.stop();
                gotoTitleScreen();
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
