package com.cuphead.controllers;

public class GameController {
    private static GameController instance = null;

    private int difficultyLevel = 2;
    private boolean mute = false;

    public static GameController getInstance() {
        if (instance == null) instance = new GameController();
        return instance;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public boolean isMute() {
        return mute;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }
}
