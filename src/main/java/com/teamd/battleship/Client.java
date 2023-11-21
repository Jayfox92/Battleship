package com.teamd.battleship;

import javafx.application.Application;

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
    private Thread thread;

    public Client(String message, Battleship battleShip){
        this.ownMessage = message;
        this.battleShip = battleShip;
    }
    public void setHelloApplication(HelloApplication helloApplication){
        this.helloApplication = helloApplication;
    }
    public void setThread(Thread thread){
        this.thread = thread;
    }

    public void connect(){
        try {
            socket = new Socket("localhost", 8080);
            System.out.println("Successful connection");
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            //battleShip.shipPlacement();


            for (int i=0; i < 10; i++) {
                for (int j=0; j < 10; j++){
                    System.out.print(battleShip.getMap()[i][j]);
                }
                System.out.println();
            }

            while (battleShip.isActiveGame()){
                if (firstRound){
                    ownMessage = "i shot ";
                    ownMessage = ownMessage.concat(battleShip.randomShot());
                    System.out.println("own message: "+ownMessage);
                    System.out.println("Round "+roundCounter);
                    writer.println(ownMessage);
                    roundCounter++;
                    firstRound=false;
                }
                opponentMessage = reader.readLine();
                System.out.println("opponent message: "+opponentMessage);
                battleShip.serverTurn = false;
                battleShip.decideNextAction(opponentMessage);
                System.out.println("own message: "+ownMessage);
                writer.println(ownMessage);
                System.out.println("Round "+roundCounter);
                roundCounter++;
            }




        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }



}
