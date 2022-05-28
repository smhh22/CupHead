package com.cuphead.View;

import com.cuphead.App;
import com.cuphead.controllers.GameController;
import com.cuphead.controllers.UserController;
import com.cuphead.models.User;
import com.cuphead.transitions.*;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class GameViewController {
    public final double INITIAL_HEALTH_POINT;
    public final double DAMAGE_ON_PLANE;
    public final double DAMAGE_ON_BOSS;
    private final double PLANE_SPEED = 10;

    private double healthPoint;

    private TranslateTransition groundTransition;
    private PlaneTransition planeTransition;
    private MainBossTransition mainBossTransition;
    private ImageView planeView;
    private boolean finished = false;
    private boolean paused = false;

    private int score = 0;

    private ArrayList<BulletTransition> bulletTransitions = new ArrayList<>();
    private ArrayList<MiniBossTransition> miniBossTransitions = new ArrayList<>();

    @FXML
    private Pane pane;

    {
        switch (GameController.getInstance().getDifficultyLevel()) {
            case (1) :
                INITIAL_HEALTH_POINT = 10;
                DAMAGE_ON_BOSS = 1.5;
                DAMAGE_ON_PLANE = 0.5;
                break;
            case (2) :
                INITIAL_HEALTH_POINT = 5;
                DAMAGE_ON_BOSS = 1;
                DAMAGE_ON_PLANE = 1;
                break;
            default:
                INITIAL_HEALTH_POINT = 2;
                DAMAGE_ON_BOSS = 0.5;
                DAMAGE_ON_PLANE = 1.5;
        }
    }

    @FXML
    private void initialize() {
        setBackground();
        setPlane();
        setMainBoss();
        showScore();
        setProgressBars();
    }

    public ImageView getMainBoss() {
        return mainBossTransition.getMainBoss();
    }

    public MainBossTransition getMainBossTransition() {
        return mainBossTransition;
    }

    public Pane getPane() {
        return pane;
    }

    public ArrayList<BulletTransition> getBullets() {
        return bulletTransitions;
    }

    public ArrayList<MiniBossTransition> getMiniBossTransitions() {
        return miniBossTransitions;
    }

    public int getScore() {
        return score;
    }

    public PlaneTransition getPlane() {
        return planeTransition;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @FXML
    private void handleKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode().getName()) {
            case("A"):
            case ("Left"):
                if (!paused) planeView.setX(planeView.getX() - PLANE_SPEED);
                break;
            case("D"):
            case ("Right"):
                if (!paused) planeView.setX(planeView.getX() + PLANE_SPEED);
                break;
            case("W"):
            case ("Up"):
                if (!paused) planeView.setY(planeView.getY() - PLANE_SPEED);
                break;
            case("S"):
            case ("Down"):
                if (!paused) planeView.setY(planeView.getY() + PLANE_SPEED);
                break;
            case ("Space"):
                if (!paused) setBullet();
                break;
            case("Esc"):
                if (!paused) pause();
                else unpause();
                break;
        }
        planeView.setX(Math.max(10, planeView.getX()));
        planeView.setX(Math.min(1150, planeView.getX()));
        planeView.setY(Math.max(10, planeView.getY()));
        planeView.setY(Math.min(570, planeView.getY()));
    }

    @FXML
    private void handleKeyRelease(KeyEvent event) {
//        if (event.getCode().getName().equals("Space")) bulletAllowed = true;
    }

    private void setBackground() {
        pane.setBackground(new Background(new BackgroundImage(
                new Image(getClass().getResource("/com/cuphead/frames/Background/birdhouse_bg_0008.png")
                        .toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        ImageView ground = new ImageView(
                new Image(getClass().getResource("/com/cuphead/frames/Background/birdhouse_bg_0004.png")
                        .toExternalForm()));
        ground.setY(410);
        pane.getChildren().add(ground);
        groundTransition = new TranslateTransition(Duration.minutes(3), ground);
        groundTransition.setByX(-750);
        groundTransition.setInterpolator(Interpolator.LINEAR);
        groundTransition.play();
    }

    private void setPlane() {
        planeView = new ImageView(new Image(getClass().getResource(
                "/com/cuphead/frames/Plane/Idle/mugman_plane_idle_straight_0001.png").toExternalForm()));
        planeView.setX(100);
        planeView.setY(100);
        pane.getChildren().add(planeView);
        planeTransition = new PlaneTransition(this, planeView, INITIAL_HEALTH_POINT);
        planeTransition.play();
        planeView.setFocusTraversable(true);
        planeView.requestFocus();
        planeView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                handleKeyEvent(keyEvent);
            }
        });
        planeView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                handleKeyRelease(keyEvent);
            }
        });
    }

    private void setBullet() {
        if (finished) return;
//        bulletAllowed = false;
        ImageView bullet = new ImageView(new Image(getClass().getResource(
                "/com/cuphead/frames/Plane/EX/Bullet/mm_shmup_ex_bullet_0001.png").toExternalForm()));
        bullet.setX(planeView.getX());
        bullet.setY(planeView.getY());
        bullet.setScaleX(0.75);
        bullet.setScaleY(0.75);
        BulletTransition bulletTransition = new BulletTransition(this, bullet);
        bulletTransitions.add(bulletTransition);
        bulletTransition.play();
        pane.getChildren().add(bullet);
    }

    private void setMainBoss() {
        ImageView mainBoss = new ImageView(new Image(getClass().getResource(
                "/com/cuphead/frames/Phase 1/Feather Attack/Flap/Loop/bird_mad_flap_0010.png").toExternalForm()));
        mainBoss.setX(600);
        mainBoss.setY(100);
        mainBoss.setScaleX(0.5);
        mainBoss.setScaleY(0.5);
        mainBossTransition = new MainBossTransition(this, mainBoss);
        mainBossTransition.play();
        pane.getChildren().add(mainBoss);
    }

    public void setMiniBoss() {
        ImageView miniBoss = new ImageView(new Image(getClass().getResource(
                "/com/cuphead/frames/Phase 1/Flappy Birds/Pink/Fly/flappy_bird_fly_pink_0001.png").toExternalForm()));
        miniBoss.setX(1280);
        miniBoss.setScaleX(0.5);
        miniBoss.setScaleY(0.5);
        miniBoss.setY(new Random(LocalDateTime.now().getNano()).nextInt(500) + 100);
        MiniBossTransition miniBossTransition = new MiniBossTransition(this, miniBoss);
        miniBossTransition.play();
        miniBossTransitions.add(miniBossTransition);
        pane.getChildren().add(miniBoss);
    }

    private void setProgressBars() {
        ProgressBar planeHP = new ProgressBar(1);
        ProgressBar bossHP = new ProgressBar(1);
        planeHP.setLayoutX(320);
        bossHP.setLayoutX(960);
        planeHP.setLayoutY(50);
        bossHP.setLayoutY(50);
        pane.getChildren().add(planeHP);
        pane.getChildren().add(bossHP);
        ProgressBarTransition progressBarTransition = new ProgressBarTransition(this, planeHP, bossHP);
        progressBarTransition.play();
    }

    private void showScore() {
        Label scoreLabel = new Label(String.valueOf(score));
        scoreLabel.setLayoutX(640);
        scoreLabel.setLayoutY(50);
        ScoreTransition scoreTransition = new ScoreTransition(this, scoreLabel);
        scoreTransition.play();
        pane.getChildren().add(scoreLabel);
    }

    public void mainBossDied() {
        finished = true;
        pauseAllTransitions();
        score += 200;
        mainBossTransition.stop();
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), getMainBoss());
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                win();
            }
        });
        fadeTransition.play();
    }

    public void planeDied() {
        finished = true;
        pauseAllTransitions();
        planeTransition.stop();
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), planeView);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lose();
            }
        });
        fadeTransition.play();
    }

    private void win() {
        finishGame();
        setResult("You Won!");
        setButtons(false);
    }

    private void lose() {
        finishGame();
        setResult("You Lost!");
        setButtons(true);
    }

    private void setResult(String result) {
        Label label = new Label(result);
        label.setLayoutX(520);
        label.setLayoutY(200);
        label.setStyle("-fx-text-fill: orange; -fx-font-size: 50;");
        pane.getChildren().add(label);
        label = new Label(String.valueOf(score));
        label.setLayoutX(630);
        label.setLayoutY(300);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 20;");
        pane.getChildren().add(label);
    }

    private void setButtons(boolean hasRetry) {
        if (hasRetry) {
            Button retry = new Button("Retry");
            retry.setLayoutX(515);
            retry.setLayoutY(460);
            retry.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    App.setMenu("game_view");
                }
            });
            pane.getChildren().add(retry);
        }
        Button back = new Button("Back to Main Menu");
        back.setId("back");
        back.setLayoutX(515);
        back.setLayoutY(400);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                App.setMenu("main_menu");
            }
        });
        pane.getChildren().add(back);
    }

    private void finishGame() {
        score += 50 * healthPoint;
        User user = UserController.getInstance().getUser();
        if (user != null) {
            user.setScore(Math.max(user.getScore(), score));
        }
        Rectangle background = new Rectangle(0, 0, 1280, 720);
        background.setOpacity(0.50);
        pane.getChildren().add(background);
    }

    private void pause() {
        paused = true;
        pauseAllTransitions();
        Button restart = new Button("Restart Game");
        Button exit = new Button("Exit to Main Menu");
        exit.setId("exit");
        restart.setLayoutX(515);
        exit.setLayoutX(515);
        restart.setLayoutY(200);
        exit.setLayoutY(280);
        restart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                App.setMenu("game_view");
            }
        });
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                App.setMenu("main_menu");
            }
        });
        pane.getChildren().add(restart);
        pane.getChildren().add(exit);
    }

    private void unpause() {
        paused = false;
        unpauseAllTransitions();
        ArrayList<Button> toBeRemoved = new ArrayList<>();
        for (Node node : pane.getChildren()) {
            if (node instanceof Button) toBeRemoved.add((Button)node);
        }
        pane.getChildren().removeAll(toBeRemoved);
    }

    public void pauseAllTransitions() {
        mainBossTransition.pause();
        planeTransition.pause();
        for (BulletTransition bulletTransition : bulletTransitions) {
            bulletTransition.pause();
        }
        for (MiniBossTransition miniBossTransition : miniBossTransitions) {
            miniBossTransition.pause();
        }
    }

    private void unpauseAllTransitions() {
        mainBossTransition.play();
        planeTransition.play();
        for (BulletTransition bulletTransition : bulletTransitions) {
            bulletTransition.play();
        }
        for (MiniBossTransition miniBossTransition : miniBossTransitions) {
            miniBossTransition.play();
        }
    }
}
