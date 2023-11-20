package com.teamd.battleship;

import javafx.application.Application;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    String ownMessage;
    private String opponentMessage;
    private boolean firstRound = true;
    private Battleship battleShip;
    private HelloApplication helloApplication;
    private int roundCounter = 0;
    //private Thread thread;

    public Client(String message, Battleship battleShip){
        this.ownMessage = message;
        this.battleShip = battleShip;
    }
    public void setHelloApplication(HelloApplication helloApplication){
        this.helloApplication = helloApplication;
    }
   // public void setThread(Thread thread){
    //    this.thread = thread;
    //}

    public void connect(){
        try {
            socket = new Socket("localhost", 8080);
            System.out.println("Successful connection");
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            battleShip.shipPlacement();


            for (int i=0; i < 10; i++) {
                for (int j=0; j < 10; j++){
                    System.out.print(battleShip.getMap()[i][j]);
                }
                System.out.println();
            }

            do{
                if (firstRound){
                    ownMessage = "i shot ";
                    ownMessage = ownMessage.concat(battleShip.randomShot());
                    System.out.println(ownMessage);
                    System.out.println("Round "+roundCounter);
                    writer.println(ownMessage);
                    roundCounter++;
                    firstRound=false;
                }
                opponentMessage = reader.readLine();
                System.out.println(opponentMessage);
                battleShip.serverTurn = false;
                battleShip.decideNextAction(opponentMessage);

                ownMessage = "Next action"; // Replace with actual next action
                System.out.println(ownMessage);
                writer.println(ownMessage);
                System.out.println("Round "+roundCounter);
                roundCounter++;
            }while (battleShip.isActiveGame());




        } catch (IOException e){
            System.err.println("Error in client: " + e.getMessage());
        } finally {
            closeResources();
        }
    }
    private void closeResources() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing socket: " + e.getMessage());
        }
    }

    public void updateGameBoard(char action, int x, int y) {
        Platform.runLater(() -> {
            helloApplication.externalUpdateMethod(action, x, y);
        });
    }


    // Utility method to convert game results to action chars
    private char convertResultToAction(String result) {
        // Implement this based on your game logic
        // Same as in the Server class, or different if needed
        switch (result) {
            case "hit":
                return 'h';
            case "miss":
                return 'm';
            case "sunk":
                return 's';
            default:
                return ' '; // Some default or error handling
        }
    }



}
