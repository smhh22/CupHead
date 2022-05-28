package com.cuphead.transitions;

import com.cuphead.View.GameViewController;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class BulletTransition extends Transition {
    private final double SPEED = 10;
    private GameViewController controller;
    private ImageView bullet;

    public BulletTransition(GameViewController controller, ImageView bullet) {
        this.bullet = bullet;
        this.controller = controller;
        setCycleCount(-1);
        setCycleDuration(Duration.millis(1800));
    }

    @Override
    public void interpolate(double v) {
        bullet.setX(bullet.getX() + SPEED);
        if (bullet.getX() > 1400) {
            destroyBullet();
        }
        if (bullet.getBoundsInParent().intersects(controller.getMainBoss().getBoundsInParent())) {
            fadeTo0();
            controller.setScore(controller.getScore() + 1);
            controller.getMainBossTransition().setHealthPoint(controller.getMainBossTransition().getHealthPoint() - controller.DAMAGE_ON_BOSS);
            if (controller.getMainBossTransition().getHealthPoint() <= 0) controller.mainBossDied();
            return;
        }
        for (MiniBossTransition miniBossTransition : controller.getMiniBossTransitions()) {
            if (bullet.getBoundsInParent().intersects(miniBossTransition.getMiniBoss().getBoundsInParent())) {
                fadeTo0();
                controller.setScore(controller.getScore() + 1);
                miniBossTransition.setHealthPoint(miniBossTransition.getHealthPoint() - controller.DAMAGE_ON_BOSS);
                if (miniBossTransition.getHealthPoint() <= 0) {
                    miniBossTransition.finish();
                    controller.setScore(controller.getScore() + 1);
                }
                return;
            }
        }
    }

    private void destroyBullet() {
        this.stop();
        controller.getPane().getChildren().remove(bullet);
        controller.getBullets().remove(this);
    }

    private void fadeTo0() {
        FadeTransition fade = new FadeTransition(Duration.millis(500), bullet);
        fade.setToValue(0);
        fade.play();
        this.stop();
        fade.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                destroyBullet();
            }
        });
    }
}
