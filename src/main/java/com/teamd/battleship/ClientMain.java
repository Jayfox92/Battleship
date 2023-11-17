package com.teamd.battleship;

import javafx.application.Application;

public class ClientMain {
    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
        //Application.launch(HelloApplication.class, args);

    }
}
