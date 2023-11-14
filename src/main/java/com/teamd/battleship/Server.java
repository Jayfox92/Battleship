package com.teamd.battleship;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket serverSocket;
    Socket socket;

    public void connect(){
        try {
            serverSocket = new ServerSocket(8080);
            socket = serverSocket.accept();
            System.out.println("Successful connection");
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            String message = reader.readLine();
            Battleship serverShip = new Battleship();
            serverShip.readFirstLetter(message);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}
