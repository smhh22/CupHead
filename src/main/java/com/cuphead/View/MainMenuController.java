package com.cuphead.View;

import com.cuphead.App;
import com.cuphead.controllers.GameController;
import com.cuphead.controllers.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;

public class MainMenuController {
    @FXML
    private Button profileButton;

    @FXML
    private void initialize() {
        if (UserController.getInstance().getUser() != null) profileButton.setDisable(false);
//        System.out.println(getClass().getResource("/com/cuphead/mp3/main.mp3").toExternalForm());
//        Media music = new Media(getClass().getResource("/com/cuphead/mp3/main.mp3").toExternalForm());
    }

    @FXML
    private void startNewGame() {
        App.setMenu("game_view");
    }

    @FXML
    private void showSettings() {
        App.setMenu("settings");
    }

    @FXML
    private void showProfile() {
        App.setMenu("profile_menu");
    }

    @FXML
    private void showLeaderboard() {
        App.setMenu("leaderboard");
    }

    @FXML
    private void exitGame(MouseEvent mouseEvent) {
        App.exit();
    }
}