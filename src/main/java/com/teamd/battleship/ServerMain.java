package com.teamd.battleship;

public class ServerMain {

    public static void main(String[] args) {
        Battleship serverShip = new Battleship();
        Server server = new Server("initial message",serverShip);
        serverShip.setServer(server);
        server.connect();
    }
}