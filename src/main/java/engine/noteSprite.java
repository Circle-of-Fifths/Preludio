package engine;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Set;

/**
 * Created by jeff on 2/16/17.
 */
public class noteSprite {

    private ImageView note = new ImageView(new
            Image("Resources/view/res/quarter_note.png", 32, 32,
            false, false));
    private TranslateTransition transition;
    private Rectangle key;
    boolean isHit;
    boolean isActive;


    public noteSprite(Rectangle rectangle, long time) {
        isHit = false;
        isActive = false;
        note.setVisible(false);
        transition = new TranslateTransition(new Duration(time), this.note);
        transition.setAutoReverse(true);
        transition.setToX(rectangle.getX() + (rectangle.getWidth() / 2.0));
        transition.setToY(rectangle.getY() + (rectangle.getHeight() / 2.0));
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                note.setVisible(false);
                if (!isHit) {
                    // print Miss
                    isActive = false;
                }
            }
        });
    }

    public ImageView getNote() {
        return note;
    }

    public TranslateTransition getTransition() {
        return transition;
    }

    public Rectangle getKey() {
        return key;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean value) {
        isActive = value;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean value) {
        isHit = value;
    }
}
