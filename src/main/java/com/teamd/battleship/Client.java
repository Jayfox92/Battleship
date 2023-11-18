package com.teamd.battleship;

import java.io.*;
import java.net.Socket;

public class Client {
    Socket socket;
    String message;
    boolean firstRound = true;

    public Client(String message){
        this.message = message;
    }
    public Client(){}

    public void connect(){
        try {
            socket = new Socket("localhost", 8080);
            System.out.println("Successful connection");
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Battleship clientShip = new Battleship();
            clientShip.shipPlacement();
            int roundCounter = 0;
            while (Battleship.activeGame) {
                if (firstRound){
                    message = "i shot ";
                    message = message.concat(clientShip.randomShot());
                    System.out.println(roundCounter);
                    writer.println(message);
                    roundCounter++;
                    firstRound=false;
                }
                clientShip.decideNextAction(reader.readLine());
                writer.println(message);
                roundCounter++;


            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }



}