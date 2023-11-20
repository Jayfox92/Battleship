package com.teamd.battleship;

import javafx.application.Application;
import javafx.application.Platform;

public class ClientMain {
    public static void main(String[] args) {

        System.setProperty("appMode","client");
        new Thread(()->{
        Application.launch(HelloApplication.class, args);}).start();





    }


}

