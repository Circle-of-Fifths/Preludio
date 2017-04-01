package controllers;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import engine.Preludio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class titleScreenController {

    private String registerURL =
            "http://circleoffifths.000webhostapp.com/register.php";

    @FXML
    private AnchorPane pane;

    @FXML
    private Button start_button;

    @FXML
    private Button loginButton;

    /**
     * Makes the Start Button glow when the mouse is over it.
     *
     * @param event Event caused when the mouse is placed over the Start Button.
     */
    @FXML
    void mouseEnter(MouseEvent event) {
        start_button.setEffect(new Glow());
    }

    /**
     * Gives the Start Button a drop shadow when the mouse is no longer over it.
     *
     * @param event Event caused when the mouse is removed from the
     *              Start Button.
     */
    @FXML
    void mouseExit(MouseEvent event) {
        start_button.setEffect(new DropShadow());
    }


    /**
     * Sets up and shows the Title Screen.
     *
     * @param event Event caused by the user clicking on the Start Button.
     *
     * @throws IOException if the Title Screen fxml file cannot be found.
     */
    @FXML
    void start(ActionEvent event) throws IOException {
        Preludio.getInstance().buttonSound.play();
        Preludio.getInstance().setNewScene("/view/fxml/mainMenu.fxml");
        Preludio.getInstance().titlePlayer.play();
    }

    @FXML
    void login() throws IOException {
        Preludio.getInstance().buttonSound.play();
        Preludio.getInstance().setNewScene("/view/fxml/login.fxml");
    }

    @FXML
    void register() throws URISyntaxException, IOException {
        if(Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(new URI(registerURL));
            } else {
                // Desktop has bad support for linux
                Runtime.getRuntime().exec("xdg-open " + registerURL);
            }
        }
    }

}

