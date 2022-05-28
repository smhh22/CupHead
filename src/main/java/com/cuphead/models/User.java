package com.cuphead.models;

import javafx.scene.image.Image;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Random;

public class User implements Comparable<User> {
    private String username = "";
    private String password = "";

//    Credit: random images created by jdenticon.com
    private Image avatar = null;

    private int score = 0;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        Random random = new Random(LocalDateTime.now().getNano());
        URL address = null;
        try {
            address = new URL(getClass().getResource("/com/cuphead/avatars/" + random.nextInt(10) + ".png").toExternalForm());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        avatar = new Image(address.toExternalForm());
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPassword(String oldPassword, String newPassword) {
        if (checkPassword(oldPassword)) password = newPassword;
    }

    public Image getAvatar() {
        return avatar;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public int compareTo(User other) {
        return other.score - this.score;
    }
}
