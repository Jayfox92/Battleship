package com.teamd.battleship;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket serverSocket;
    Socket socket;
    String message;
    public Server(String message){
        this.message = message;
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
            Battleship serverShip = new Battleship(message);
            serverShip.shipPlacement();
            int roundCounter = 0;

            while (Battleship.activeGame) {
                message = reader.readLine();
                System.out.println(message);
                serverShip.decideNextAction(message);
                writer.println(message);
                System.out.println(message);
                System.out.println(roundCounter);
                roundCounter++;
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}
