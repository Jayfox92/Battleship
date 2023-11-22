package com.teamd.battleship;

import java.io.*;
import java.net.Socket;

public class Client { //johann
    private Socket socket;
    private String ownMessage;
    private String opponentMessage;
    private boolean firstRound = true;
    private Battleship battleShip;
    private HelloApplication helloApplication;
    private int roundCounter = 0;
    public Client(){}
    public void setBattleShip(Battleship battleship){this.battleShip = battleship;}
    public void setHelloApplication(HelloApplication helloApplication){
        this.helloApplication = helloApplication;
    }
    public void setOwnMessage(String ownMessage){this.ownMessage = ownMessage;}


    public void connect(){ // abenezer
        try {
            socket = new Socket("localhost", 8080);
            System.out.println("Successful connection");
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);


            while (battleShip.isActiveGame()){
                if (firstRound){
                    ownMessage = "i shot ";
                    ownMessage = ownMessage.concat(battleShip.randomShot());
                    System.out.println("own message: "+ownMessage);
                    roundCounter++;
                    System.out.println("Round "+roundCounter);
                    writer.println(ownMessage);
                    firstRound=false;
                }
                opponentMessage = reader.readLine();
                System.out.println("opponent message: "+opponentMessage);
                battleShip.isServerTurn(false);
                battleShip.decideNextAction(opponentMessage);
                if (opponentMessage.equals("game over")){
                    return;
                }
                System.out.println("own message: "+ownMessage);
                writer.println(ownMessage);
                roundCounter++;
                System.out.println("Round "+roundCounter);
            }
            socket.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
