package com.cuphead.View;

import com.cuphead.App;
import com.cuphead.controllers.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LoginMenuController {
    @FXML
    private TextField loginUsername;

    @FXML
    private TextField registerUsername;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private PasswordField registerRepeatPassword;

    @FXML
    private Button registerButton;

    @FXML
    private Label registerLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private void login(MouseEvent mouseEvent) {
        clearMessages();
        checkLoginPassword(null);
        checkLoginUsername(null);
        String username = loginUsername.getText();
        String password = loginPassword.getText();
        if (UserController.getInstance().isLoginValid(username, password)) {
            UserController.getInstance().login(UserController.getInstance().getUserByUsername(username), password);
            loginLabel.setText("Login successful.");
            loginLabel.setStyle("-fx-text-fill: #00ff00;");
            App.setMenu("main_menu");
        }
        else {
            loginLabel.setText("Wrong username or password.");
            loginLabel.setStyle("-fx-text-fill: #ff0000;");
        }
    }

    @FXML
    private void register(MouseEvent mouseEvent) {
        clearMessages();
        checkRepeatPassword(null);
        checkRegisterPassword(null);
        checkRegisterUsername(null);
        String username = registerUsername.getText();
        String password = registerPassword.getText();
        if (UserController.getInstance().isRegisterValid(username, password, registerRepeatPassword.getText())) {
            UserController.getInstance().register(username, password);
            registerButton.setStyle("-fx-border-color: #00ff00;");
            registerPassword.setText("");
            registerUsername.setText("");
            registerRepeatPassword.setText("");
            registerLabel.setText("registered successfully.");
            registerLabel.setStyle("-fx-text-fill: #00ff00;");
        }
        else {
            registerLabel.setText("either username exists or is invalid, or password is weak or repeat password is wrong.");
            registerLabel.setStyle("-fx-text-fill: #ff0000;");
        }
    }

    @FXML
    private void enterAsGuest(MouseEvent mouseEvent) {
        App.setMenu("main_menu");
    }

    @FXML
    private void checkRepeatPassword(KeyEvent keyEvent) {
        clearMessages();
        if (!UserController.getInstance().checkRepeatPassword(registerPassword.getText(), registerRepeatPassword.getText())) {
            registerRepeatPassword.setStyle("-fx-border-color: #ff0000;");
        } else registerRepeatPassword.setStyle("-fx-border-width: 0;");
    }

    @FXML
    private void checkRegisterPassword(KeyEvent keyEvent) {
        clearMessages();
        if (!UserController.getInstance().checkStrength(registerPassword.getText())) {
            registerPassword.setStyle("-fx-border-color: #ff0000;");
        } else registerPassword.setStyle("-fx-border-width: 0;");
        checkRepeatPassword(keyEvent);
    }

    @FXML
    private void checkLoginPassword(KeyEvent keyEvent) {
        clearMessages();
        String password = loginPassword.getText();
        if (password.length() == 0) {
            loginPassword.setStyle("-fx-border-color: #ff0000;");
        } else loginPassword.setStyle("-fx-border-width: 0;");
    }

    @FXML
    private void checkRegisterUsername(KeyEvent keyEvent) {
        clearMessages();
        String username = registerUsername.getText();
        UserController controller = UserController.getInstance();
        if (!controller.isUsernameValid(username) || controller.getUserByUsername(username) != null) {
            registerUsername.setStyle("-fx-border-color: #ff0000;");
        } else registerUsername.setStyle("-fx-border-width: 0;");
    }

    @FXML
    private void checkLoginUsername(KeyEvent keyEvent) {
        clearMessages();
        String username = loginUsername.getText();
        if (!UserController.getInstance().isUsernameValid(username)) {
            loginUsername.setStyle("-fx-border-color: #ff0000;");
        } else loginUsername.setStyle("-fx-border-width: 0;");
    }

    @FXML
    private void checkRegisterEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("Enter")) register(null);
    }

    private void clearMessages() {
        registerLabel.setText("");
        loginLabel.setText("");
    }
}