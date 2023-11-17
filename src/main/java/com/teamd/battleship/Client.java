package com.teamd.battleship;

import java.io.*;
import java.net.Socket;
public class Client {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public void connect() {
        try {
            socket = new Socket("localhost", 8080);
            System.out.println("Ansluten till servern: " + socket.getInetAddress());


            try (
                    InputStream inputStream = socket.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(inputStreamReader);

            OutputStream outputStream = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(outputStream, true))
            {

                writer.println("START_GAME");

                handleMessages();
            }
        } catch (IOException e) {
            System.out.println("Anslutningsfel: " + e.getMessage());
        }
    }

    private void handleMessages() throws IOException {
        String message;
        while ((message = reader.readLine()) != null) {
            System.out.println("Meddelande från servern: " + message);


            if (message.equals("END_MAP")) {

                System.out.println("Slut på spelplanen");
            } else {

                System.out.println("Spelplan från servern: " + message);
            }

        }

    }
}
