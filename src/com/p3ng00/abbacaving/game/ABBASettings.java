package com.p3ng00.abbacaving.game;

public class ABBASettings {

    public int minutes;
    public int displayFreq;

    public ABBASettings() {
        minutes = 30; // save and load this from config
        displayFreq = 5;
    }

    public ABBASettings getSettings() {
        return this;
    }
}
