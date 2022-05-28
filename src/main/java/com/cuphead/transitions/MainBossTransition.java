package com.cuphead.transitions;

import com.cuphead.View.GameViewController;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.time.LocalDateTime;

public class MainBossTransition extends Transition {
    private static int lastDeployment = 0;
    private GameViewController controller;
    private ImageView mainBoss;
    private double healthPoint = 200;

    public MainBossTransition(GameViewController controller, ImageView mainBoss) {
        this.controller = controller;
        this.mainBoss = mainBoss;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(800));
    }

    public double getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(double newHealthPoint) {
        healthPoint = newHealthPoint;
    }

    @Override
    public void interpolate(double v) {
        int phase = (int)Math.floor(v * 9);
        if (phase > 8) return;
        int time = LocalDateTime.now().getSecond();
        if (time != lastDeployment) {
            controller.setMiniBoss();
            lastDeployment = time;
        }
        String address = "/com/cuphead/frames/Phase 1/Feather Attack/Flap/Loop/bird_mad_flap_001" + phase + ".png";
        mainBoss.setImage(new Image(getClass().getResource(address).toExternalForm()));
    }

    public ImageView getMainBoss() {
        return mainBoss;
    }
}
