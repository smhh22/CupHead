package com.cuphead.View;

import com.cuphead.App;
import com.cuphead.controllers.UserController;
import com.cuphead.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class LeaderboardController {
    @FXML
    private GridPane table;

    @FXML
    private void initialize() {
        ArrayList<User> users = UserController.getInstance().getAllUsers();
        Collections.sort(users);
        Label index = new Label("#");
        index.getStyleClass().add("index");
        Label username = new Label("Username");
        username.getStyleClass().add("username");
        Label score = new Label("HighScore");
        score.getStyleClass().add("score");
        table.add(index, 0, 0);
        table.add(new Label(""), 1, 0);
        table.add(username, 2, 0);
        table.add(score, 3, 0);
        for (int i = 0; i < 10; i++) {
            if (i >= users.size()) break;
            User user = users.get(i);
            ImageView imageView = new ImageView(user.getAvatar());
            index = new Label(String.valueOf(i + 1));
            username = new Label(user.getUsername());
            score = new Label(String.valueOf(user.getScore()));
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            String styleClass = "";
            if (i == 0) styleClass = "gold";
            else if (i == 1) styleClass = "silver";
            else if (i == 2) styleClass = "bronze";
            else if (i % 2 == 0) styleClass = "even";
            else styleClass = "odd";
            imageView.getStyleClass().add(styleClass);
            index.getStyleClass().add(styleClass);
            username.getStyleClass().add(styleClass);
            score.getStyleClass().add(styleClass);
            index.getStyleClass().add("index");
            username.getStyleClass().add("username");
            score.getStyleClass().add("score");
            table.add(index, 0, i + 1 );
            table.add(imageView, 1, i + 1);
            table.add(username, 2, i + 1);
            table.add(score, 3, i + 1);
        }
        if (users.size() > 10) table.add(new Label("..."), 2, 11);
    }

    @FXML
    private void back() {
        App.setMenu("main_menu");
    }
}
