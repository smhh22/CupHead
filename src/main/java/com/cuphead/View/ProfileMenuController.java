package com.cuphead.View;

import com.cuphead.App;
import com.cuphead.controllers.UserController;
import com.cuphead.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ProfileMenuController {
    @FXML
    private VBox mainBox;

    @FXML
    private TextField newUsername;

    @FXML
    private PasswordField oldPassword;

    @FXML
    private PasswordField newPassword;

    @FXML
    private Label messageLabel;

    @FXML
    private void initialize() {
        User user = UserController.getInstance().getUser();
        ImageView imageView = new ImageView(user.getAvatar());
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        mainBox.getChildren().add(0, imageView);
        Label username = new Label(user.getUsername());
        username.setId("title");
        mainBox.getChildren().add(1, username);
    }

    @FXML
    private void back() {
        App.setMenu("main_menu");
    }

    @FXML
    private void changeUsername() {
        clearMessages();
        User user = UserController.getInstance().getUser();
        String username = newUsername.getText();
        if (UserController.getInstance().getUserByUsername(username) == null && UserController.getInstance().isUsernameValid(username)) {
            user.setUsername(username);
            App.setMenu("profile_menu");
        }
        else {
            if (user.getUsername().equals(username)) messageLabel.setText("This is already your username.");
            else if (!UserController.getInstance().isUsernameValid(username)) messageLabel.setText("Invalid username");
            else messageLabel.setText("Username already exists");
            messageLabel.setStyle("-fx-text-fill: #ff0000;");
        }
    }

    @FXML
    private void changePassword() {
        clearMessages();
        User user = UserController.getInstance().getUser();
        if (UserController.getInstance().checkStrength(newPassword.getText()) &&
                user.checkPassword(oldPassword.getText())) {
            user.setPassword(oldPassword.getText(), newPassword.getText());
            messageLabel.setText("Password changed successfully.");
            messageLabel.setStyle("-fx-text-fill: #00ff00;");
            oldPassword.setText("");
            newPassword.setText("");
        }
        else {
            messageLabel.setText("either wrong old password or weak new password.");
            messageLabel.setStyle("-fx-text-fill: #ff0000");
        }
    }

    @FXML
    private void logout() {
        UserController.getInstance().logout();
        App.setMenu("login_menu");
    }

    @FXML
    private void removeAccount() {
        UserController.getInstance().removeAccount(UserController.getInstance().getUser());
        App.setMenu("login_menu");
    }

    private void clearMessages() {
        messageLabel.setText("");
    }
}
