package com.teamd.battleship;

import javafx.application.Application;

public class ClientMain {
    public static void main(String[] args) {
        Battleship clientShip = new Battleship();
        Client client = new Client("initial message", clientShip);
        clientShip.setClient(client);

        client.connect();
        //Application.launch(HelloApplication.class, args);

    }
}