package com.cuphead.transitions;

import com.cuphead.View.GameViewController;
import javafx.animation.Transition;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class ProgressBarTransition extends Transition {
    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private GameViewController controller;

    public ProgressBarTransition(GameViewController controller, ProgressBar progressBar1, ProgressBar progressBar2) {
        this.controller = controller;
        this.progressBar1 = progressBar1;
        this.progressBar2 = progressBar2;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(500));
    }

    @Override
    public void interpolate(double v) {
        progressBar1.setProgress(controller.getPlane().getHealthPoint() / controller.INITIAL_HEALTH_POINT);
        progressBar2.setProgress(controller.getMainBossTransition().getHealthPoint() / 200);
        if (progressBar1.getProgress() <= 0.5) progressBar1.setStyle("-fx-border-color: red;");
        if (progressBar2.getProgress() <= 0.1) progressBar2.setStyle("-fx-border-color: red;");
    }
}