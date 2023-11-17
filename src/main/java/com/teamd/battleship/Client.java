package com.teamd.battleship;

import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket socket;

    public static void main(String[] args) {
        connectToServer();
    }

    public static void connectToServer() {
        try {
            socket = new Socket("localhost", 8080);
            System.out.println("Ansluten till servern");
            startListening();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void startListening() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Lyssna på meddelanden från servern
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Mottaget från servern: " + message);
                // Uppdatera Battleship baserat på mottaget meddelande
                // Exempel: Battleship.decideNextAction(message.charAt(0));
            }
        } catch (IOException e) {
            System.out.println("Fel vid läsning från servern: " + e.getMessage());
        }
    }

    // Lägg till metoder för att skicka meddelanden till servern
}
