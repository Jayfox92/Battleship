package com.teamd.battleship;

import javafx.application.Application;

public class ClientMain {
    public static void main(String[] args) {
        Battleship clientShip = new Battleship();
        HelloApplication javafx = new HelloApplication();
        Client client = new Client("initial message", clientShip);
        clientShip.setClient(client);
        clientShip.setHelloApplication(javafx);
        javafx.setBattleship(clientShip);

        Application.launch(HelloApplication.class, args);

        client.connect();
        //Application.launch(HelloApplication.class, args);

    }
}