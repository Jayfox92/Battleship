package com.teamd.battleship;

import javafx.application.Application;
import javafx.application.Platform;

public class ClientMain {
    public static void main(String[] args) {
        Battleship clientShip = new Battleship();
       // HelloApplication javafx = new HelloApplication();
        Client client = new Client("initial message", clientShip);

        client.setHelloApplication(javafxApp);

        // Start JavaFX in its own thread
        Thread javafxThread = new Thread(() -> {
            javafxApp = HelloApplication.launch(HelloApplication.class);
        });
        javafxThread.setDaemon(true);
        javafxThread.start();


        // Wait efficiently for JavaFX to initialize
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



        // Get JavaFX application instance
        HelloApplication javafx = HelloApplication.getApplication();

        // Set up relationships
        clientShip.setClient(client);
        clientShip.setHelloApplication(javafx);
        javafx.setBattleship(clientShip);
        client.setHelloApplication(javafx);
        //Thread thread = new Thread();
        //javafx.setThread();
        //client.setThread(thread);
        //thread.start();
        //Application.launch(HelloApplication.class, args); //startar javafx, inget annat görs förens javafx fönstret är stängt

        //Application.launch(HelloApplication.class, args);

        // Continue with client operations
        Thread clientThread = new Thread(client::connect);
        clientThread.start();

    }
    //public void run(Client client){
     //   client.connect();
  //  }
}