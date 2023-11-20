package com.teamd.battleship;

import javafx.application.Application;
import javafx.application.Platform;

public class ServerMain {

    public static void main(String[] args) {
        Battleship serverShip = new Battleship();
        Server server = new Server("initial message",serverShip);

        // Set server in battleship
        serverShip.setServer(server);

        // Start JavaFX in its own thread
        Thread javafxThread = new Thread(() -> {
            HelloApplication.launch(HelloApplication.class);
        });
        javafxThread.setDaemon(true);
        javafxThread.start();
// Wait efficiently for JavaFX to initialize
        // Start JavaFX in its own thread
        new Thread(() -> HelloApplication.launch(HelloApplication.class)).start();

// Busy wait for JavaFX initialization
        while (!HelloApplication.isInitialized()) {
            try {
                Thread.sleep(100); // Wait a bit before checking again
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted: " + e.getMessage());
                return;
            }
        }


    }


        // Get JavaFX application instance
        HelloApplication javafx = HelloApplication.getApplication();

        //serverShip.setServer(server);
        //HelloApplication javafx = new HelloApplication();
        //serverShip.setHelloApplication(javafx);

          // Set battleship in JavaFX application
        javafx.setBattleship(serverShip);
        serverShip.setHelloApplication(javafx);
        //Application.launch(HelloApplication.class, args);

        //continue with server operations
        server.setHelloApplication(javafx);
        server.connect();
    }
}
