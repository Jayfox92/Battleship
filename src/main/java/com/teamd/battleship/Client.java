package com.teamd.battleship;

import java.io.IOException;
import java.net.Socket;

public class Client {
    Socket socket;

    public void connect(){
        try {
            socket = new Socket("localhost", 8080);
            System.out.println("Success");
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }



}