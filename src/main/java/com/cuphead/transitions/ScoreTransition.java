package com.cuphead.transitions;

import com.cuphead.View.GameViewController;
import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ScoreTransition extends Transition {
    private GameViewController controller;
    private Label label;

    public ScoreTransition(GameViewController controller, Label label) {
        this.controller = controller;
        this.label = label;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(500));
    }

    @Override
    public void interpolate(double v) {
        label.setText(String.valueOf(controller.getScore()));
    }
}
