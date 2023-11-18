package com.teamd.battleship;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket serverSocket;
    Socket socket;
    String ownMessage;
    String opponentMessage;
    Battleship battleShip;
    int roundCounter = 0;
    public Server(String message, Battleship battleship){
        this.ownMessage = message;
        this.battleShip = battleship;
    }
    public Server(){}

    public void connect(){
        try {
            serverSocket = new ServerSocket(8080);
            socket = serverSocket.accept();
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
                opponentMessage = reader.readLine();
                System.out.println(opponentMessage);
                battleShip.serverTurn = true;
                battleShip.decideNextAction(opponentMessage);
                System.out.println(ownMessage);
                writer.println(ownMessage);
                System.out.println("Round "+roundCounter);
                roundCounter++;
            } while (Battleship.activeGame);



        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}
