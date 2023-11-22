package com.teamd.battleship;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server { //johann

    private ServerSocket serverSocket;
    private Socket socket;
    private String ownMessage;
    private String opponentMessage;
    private Battleship battleShip;
    private HelloApplication helloApplication;
    private int roundCounter = 0;
    public Server(){}
    public void setOwnMessage(String message){this.ownMessage = message;}
    public void setHelloApplication(HelloApplication helloApplication){
        this.helloApplication = helloApplication;
    }
    public void setBattleShip(Battleship battleship){this.battleShip = battleship;}

    public void connect(){ // abenezer
        try {
            serverSocket = new ServerSocket(8080);
            socket = serverSocket.accept();
            System.out.println("Successful connection");
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            while (battleShip.isActiveGame()){
                opponentMessage = reader.readLine();
                System.out.println("opponent message: "+opponentMessage);
                battleShip.isServerTurn(true);
                battleShip.decideNextAction(opponentMessage);
                System.out.println("own message: "+ownMessage);
                writer.println(ownMessage);
                roundCounter++;
                System.out.println("Round "+roundCounter);

            }

            serverSocket.close();





        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}
