package com.cuphead.transitions;

import com.cuphead.View.GameViewController;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Blink extends Transition {
    ImageView plane;
    GameViewController controller;

    public Blink(GameViewController controller, ImageView plane) {
        this.controller = controller;
        this.plane = plane;
        setCycleCount(1);
        setCycleDuration(Duration.seconds(1));
    }

    public void interpolate(double v) {
        int phase = (int)Math.floor(v * 15);
        if (phase % 2 == 0) plane.setOpacity(0);
        else plane.setOpacity(1);
    }
}
