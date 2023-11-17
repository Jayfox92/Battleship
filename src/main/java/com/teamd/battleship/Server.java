package com.teamd.battleship;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Battleship battleship;

    public void connect() {
        try {
            serverSocket = new ServerSocket(8080);
            socket = serverSocket.accept();
            System.out.println("Klient ansluten: " + socket.getInetAddress());


            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            OutputStream outputStream = socket.getOutputStream();
            writer = new PrintWriter(outputStream, true);

            battleship = new Battleship(); // Skapa ett Battleship-objekt för att koppla
            battleship.shipPlacement();

            try {
                handleMessages();
            } catch (IOException e) {
                System.out.println("Fel: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Anslutningsfel: " + e.getMessage());
        }
    }

    private void handleMessages() throws IOException {
        String message;
        while ((message = reader.readLine()) != null) {
            System.out.println("Meddelande från klient: " + message);

            if (message.equals("START_GAME")) {

                String[][] map = battleship.getMap();
                sendMap(map);

            } else if (message.startsWith("SHOT")) {
                String[] parts = message.split(" ");
                if (parts.length == 2) {
                    String coordinate = parts[1];

                    String[][] updatedMap = battleship.getMap();
                    sendMap(updatedMap);
                }
            }

        }
    }

    private void sendMap(String[][] map) {
        // Skicka spelplanen till klienten
        for (int i = 0; i < map.length; i++) {
            String row = String.join(",", map[i]);
            writer.println(row);
        }

        writer.println("END_MAP");
    }

}
