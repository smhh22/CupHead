package com.cuphead.View;

import com.cuphead.App;
import com.cuphead.controllers.GameController;
import com.cuphead.controllers.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

public class SettingsController {
    GameController controller = GameController.getInstance();

    @FXML
    private ChoiceBox difficultyBox;

    @FXML
    private CheckBox isMute;

    @FXML
    private void initialize() {
        difficultyBox.getItems().add("1");
        difficultyBox.getItems().add("2");
        difficultyBox.getItems().add("3");
        difficultyBox.setValue(String.valueOf(controller.getDifficultyLevel()));
        isMute.setSelected(controller.isMute());
    }

    @FXML
    private void cancel() {
        App.setMenu("main_menu");
    }

    @FXML
    private void apply() {
        controller.setDifficultyLevel(Integer.parseInt((String)difficultyBox.getValue()));
        controller.setMute(isMute.isSelected());
        App.setMenu("main_menu");
    }

    @FXML
    private void logout() {
        UserController.getInstance().logout();
        App.setMenu("login_menu");
    }
}
