package com.teamd.battleship;

import java.io.*;
import java.net.Socket;

public class Client {
    Socket socket;
    String ownMessage;
    String opponentMessage;
    boolean firstRound = true;
    Battleship battleShip;
    int roundCounter = 0;

    public Client(String message, Battleship battleShip){
        this.ownMessage = message;
        this.battleShip = battleShip;
    }

    public void connect(){
        try {
            socket = new Socket("localhost", 8080);
            System.out.println("Successful connection");
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            battleShip.shipPlacement();

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
                System.out.println(ownMessage);
                writer.println(ownMessage);
                System.out.println("Round "+roundCounter);
                roundCounter++;
            }while (Battleship.activeGame);




        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }



}
