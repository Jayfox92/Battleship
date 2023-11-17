package com.teamd.battleship;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static ServerSocket serverSocket;
    private static List<ClientHandler> clients;

    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        clients = new ArrayList<>();

        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("Server startad. Väntar på klienter...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Ny klient ansluten");

                // Skapa en ny tråd för att hantera klienten
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Fel vid start av servern: " + e.getMessage());
        }
    }



    public static void broadcast(String message) {
        // Skicka ett meddelande till alla anslutna klienter
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;

        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Fel vid skapande av in- och utströmmar för klient: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Mottaget från klient: " + message);
                // Hantera det mottagna meddelandet, uppdatera spelstatus, etc.

                // Exempel: Broadcasta meddelandet till alla klienter
                Server.broadcast(message);
            }
        } catch (IOException e) {
            System.out.println("Fel vid läsning från klient: " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Fel vid skickande av meddelande till klient: " + e.getMessage());
        }
    }
}
