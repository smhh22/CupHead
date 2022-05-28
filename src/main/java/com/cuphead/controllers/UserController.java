package com.cuphead.controllers;

import com.cuphead.models.User;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class UserController {
    private static UserController instance = null;

    public static UserController getInstance() {
        if (instance == null) instance = new UserController();
        return instance;
    }

    private ArrayList<User> allUsers = new ArrayList<>();
    private User user = null;

    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public User getUser() {
        return user;
    }

    public User getUserByUsername(String username) {
        for (User user: allUsers) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public void login(User user, String password) {
        if (user.checkPassword(password)) this.user = user;
    }

    public void logout() {
        user = null;
    }

    public void register(String username, String password) {
        allUsers.add(new User(username, password));
    }

    public void removeAccount(User user) {
        allUsers.remove(user);
        this.user = null;
    }

    public boolean checkRepeatPassword(String password, String repeatPassword) {
        return password.equals(repeatPassword);
    }

    public boolean checkStrength(String password) {
        return password.length() >= 5;
    }

    public boolean isUsernameValid(String username) {
        return username.matches("^[a-zA-Z][a-zA-Z\\d]*$");
    }

    public boolean isRegisterValid(String username, String password, String repeatPassword) {
        return checkRepeatPassword(password, repeatPassword) &&
                checkStrength(password) &&
                isUsernameValid(username) &&
                getUserByUsername(username) == null;
    }

    public boolean isLoginValid(String username, String password) {
        return  getUserByUsername(username) != null &&
                getUserByUsername(username).checkPassword(password);
    }
}
