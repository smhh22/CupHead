package com.cuphead.transitions;

import com.cuphead.View.GameViewController;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class MiniBossDeath extends Transition {
    ImageView imageView;
    double x, y;

    public MiniBossDeath(GameViewController controller, double x, double y) {
        this.x = x;
        this.y = y;
        imageView = new ImageView(new Image(getClass().getResource(
                "/com/cuphead/frames/Phase 1/Flappy Birds/Pink/Death/flappy_bird_death_pink_0001.png")
                .toExternalForm()));
        imageView.setX(x - imageView.getImage().getWidth() / 2);
        imageView.setY(y - imageView.getImage().getHeight() / 2);
        imageView.setTranslateX(85);
        imageView.setTranslateY(50);
        imageView.setScaleX(0.5);
        imageView.setScaleY(0.5);
        controller.getPane().getChildren().add(imageView);
        setCycleDuration(Duration.millis(500));
        setCycleCount(1);
        setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.getPane().getChildren().remove(imageView);
            }
        });
    }

    @Override
    public void interpolate(double v) {
        int phase = (int)Math.floor(v * 11) + 1;
        if (phase > 11) return;
        String phaseString = new String(String.valueOf(phase));
        if (phaseString.length() == 1) phaseString = new String("0" + phaseString);
        String address = "/com/cuphead/frames/Phase 1/Flappy Birds/Pink/Death/flappy_bird_death_pink_00" + phaseString + ".png";
        imageView.setImage(new Image(getClass().getResource(address).toExternalForm()));
        imageView.setX(x - imageView.getImage().getWidth() / 2);
        imageView.setY(y - imageView.getImage().getHeight() / 2);
    }
}
