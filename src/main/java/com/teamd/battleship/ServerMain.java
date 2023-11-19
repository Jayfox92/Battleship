package com.teamd.battleship;

import javafx.application.Application;

public class ServerMain {

    public static void main(String[] args) {
        Battleship serverShip = new Battleship();
        Server server = new Server("initial message",serverShip);
        serverShip.setServer(server);
        HelloApplication javafx = new HelloApplication();
        serverShip.setHelloApplication(javafx);
        javafx.setBattleship(serverShip);
        Application.launch(HelloApplication.class, args);
        server.connect();
    }
}
