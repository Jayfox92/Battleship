package com.teamd.battleship;

import javafx.application.Application;
import javafx.application.Platform;

public class ServerMain {

    public static void main(String[] args) {
        System.setProperty("appMode","server");
        Application.launch(HelloApplication.class, args);

    }
}