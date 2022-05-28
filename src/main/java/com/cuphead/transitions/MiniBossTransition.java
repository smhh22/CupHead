package com.cuphead.transitions;

import com.cuphead.View.GameViewController;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class MiniBossTransition extends Transition {
    private final double SPEED = 5;

    private GameViewController controller;

    private ImageView miniBoss;
    private double healthPoint = 2;

    public MiniBossTransition(GameViewController controller, ImageView miniBoss) {
        this.miniBoss = miniBoss;
        this.controller = controller;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(800));
    }

    @Override
    public void interpolate(double v) {
        int phase = (int)Math.floor(v * 17) + 1;
        if (phase > 17) return;
        String phaseString = new String(String.valueOf(phase));
        if (phaseString.length() == 1) phaseString = new String("0" + phaseString);
        String address = "/com/cuphead/frames/Phase 1/Flappy Birds/Pink/Fly/flappy_bird_fly_pink_00" + phaseString + ".png";
        miniBoss.setImage(new Image(getClass().getResource(address).toExternalForm()));
        miniBoss.setX(miniBoss.getX() - SPEED);
        if (miniBoss.getX() < -100) finish();
    }

    public ImageView getMiniBoss() {
        return miniBoss;
    }

    public double getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(double healthPoint) {
        this.healthPoint = healthPoint;
    }

    public void finish() {
        this.stop();
        controller.getMiniBossTransitions().remove(this);
        MiniBossDeath death = new MiniBossDeath(controller, miniBoss.getX(), miniBoss.getY());
        death.play();
        FadeTransition fade = new FadeTransition(Duration.millis(500), miniBoss);
        fade.setToValue(0);
        fade.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.getPane().getChildren().remove(miniBoss);
            }
        });
        fade.play();
    }
}
