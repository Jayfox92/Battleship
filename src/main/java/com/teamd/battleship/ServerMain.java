package com.teamd.battleship;

import javafx.application.Application;
import javafx.application.Platform;

public class ServerMain {

    public static void main(String[] args) {
        Battleship serverShip = new Battleship();
        Server server = new Server("initial message",serverShip);
        serverShip.setServer(server);
        HelloApplication javafx = new HelloApplication();
        serverShip.setHelloApplication(javafx);
        javafx.setBattleship(serverShip);
        server.setHelloApplication(javafx);
        Application.launch(HelloApplication.class, args);
        server.connect();
    }
}
