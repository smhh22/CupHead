package com.cuphead.transitions;

import com.cuphead.View.GameViewController;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PlaneTransition extends Transition {
    private ImageView plane;
    private GameViewController controller;
    private double healthPoint;

    public PlaneTransition(GameViewController controller, ImageView plane, double healthPower) {
        this.controller = controller;
        this.plane = plane;
        this.healthPoint = healthPower;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(250));
    }

    @Override
    public void interpolate(double v) {
        int phase = (int)Math.floor(v * 4) + 1;
        if (phase > 4) return;
        String address = "/com/cuphead/frames/Plane/Idle/mugman_plane_idle_straight_000" + phase + ".png";
        plane.setImage(new Image(getClass().getResource(address).toExternalForm()));
        for (MiniBossTransition miniBossTransition : controller.getMiniBossTransitions()) {
            if (plane.getBoundsInParent().intersects(miniBossTransition.getMiniBoss().getBoundsInParent())) {
                miniBossTransition.finish();
                damage();
                return;
            }
        }
        if (plane.getBoundsInParent().intersects(controller.getMainBoss().getBoundsInParent())) damage();
    }

    public double getHealthPoint() {
        return healthPoint;
    }

    private void damage() {
        healthPoint -= controller.DAMAGE_ON_PLANE;
        if (healthPoint <= 0) controller.planeDied();
        else blink();
    }

    private void blink() {
        this.stop();
        Blink blink = new Blink(controller, plane);
        blink.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                PlaneTransition.this.play();
                plane.setOpacity(1);
            }
        });
        blink.play();
    }
}
