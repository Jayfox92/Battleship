package com.teamd.battleship;

import java.io.*;
import java.net.Socket;

public class Client {
    Socket socket;

    public void connect(){
        try {
            socket = new Socket("localhost", 8080);
            System.out.println("Successful connection");
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("i shot 5b");
            String message = reader.readLine();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }



}
