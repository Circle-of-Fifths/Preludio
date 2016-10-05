import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
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
