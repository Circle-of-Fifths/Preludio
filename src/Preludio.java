import javafx.application.Application;
import javafx.stage.Stage;

public class Preludio extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Project Preludio 2017");
        primaryStage.setResizable(false);

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