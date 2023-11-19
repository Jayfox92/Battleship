package com.teamd.battleship;

import javafx.application.Application;
import javafx.application.Platform;

public class ClientMain {
    public static void main(String[] args) {
        Battleship clientShip = new Battleship();
        HelloApplication javafx = new HelloApplication();
        Client client = new Client("initial message", clientShip);
        clientShip.setClient(client);
        clientShip.setHelloApplication(javafx);
        javafx.setBattleship(clientShip);
        client.setHelloApplication(javafx);
        Thread thread = new Thread();
        javafx.setThread();
        client.setThread(thread);
        thread.start();
        Application.launch(HelloApplication.class, args); //startar javafx, inget annat görs förens javafx fönstret är stängt

        //Application.launch(HelloApplication.class, args);

    }
    public void run(Client client){
        client.connect();
    }
}