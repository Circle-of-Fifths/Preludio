import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Preludio extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Project Preludio 2017");
        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are you sure you want to quit?");
                alert.showAndWait().filter(response ->
                        response == ButtonType.OK).ifPresent(
                            response -> System.exit(0));
                event.consume();
            }
        }); // Note: Fix issue with hitting cancel closes program anyway

        Controller controller = new Controller(primaryStage);
        controller.gotoTitleScreen();
    }


    /**
     * Main method to launch the application
     * @param args initial arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}