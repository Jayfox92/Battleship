package com.teamd.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket serverSocket;
    Socket socket;

    public void connect() {
        try {
            serverSocket = new ServerSocket(8080);
            socket = serverSocket.accept();
            System.out.println("Success");
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String message = reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
