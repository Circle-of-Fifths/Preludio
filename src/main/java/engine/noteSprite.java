package engine;

import javafx.animation.Interpolator;
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

    private ImageView sprite;
    private TranslateTransition transition;
    private Rectangle key;
    boolean isHit;
    boolean isActive;


    public noteSprite(Rectangle rectangle, ImageView note) {
        key = rectangle;
        sprite = note;
        double x = sprite.getX();
        double y = sprite.getY();
        isActive = false;
        sprite.setVisible(false);
        transition = new TranslateTransition();
        transition.setNode(sprite);
        transition.setAutoReverse(true);
        transition.setFromX(x);
        transition.setFromY(y);
        transition.setToX(x);
        // had to adjust this; want the note to end past the line, so there is
        // the opportunity to hit it before and after, but the note should
        // sound when reaches the line
        transition.setToY(385 - sprite.getFitHeight());
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sprite.setVisible(false);
                sprite.setTranslateX(x);
                sprite.setTranslateY(y);
            }
        });
    }

    public ImageView getNote() {
        return sprite;
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
}
