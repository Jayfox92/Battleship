package com.teamd.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Battleship {

    String[][] map;
    int mapSizeX;
    int mapSizeY;
    String water;

    public static void main(String[] args) {
        Battleship game = new Battleship();
        game.startGame();
    }

    public void startGame() {
        // Placera skepp
        shipPlacement();

        // Skicka spelet till alla klienter
        broadcastGameState();
    }

    private void broadcastGameState() {
        // Skicka aktuell spelstatus till alla klienter
        String gameState = convertGameStateToString();
        Server.broadcast(gameState);
    }

    private String convertGameStateToString() {
        // Konvertera aktuell spelstatus till en sträng
        // Beroende på hur du organiserar Battleship-datastrukturen
        // kan det vara nödvändigt att implementera denna metod
        return ""; // Exempel: getMap();
    }

    public void shipPlacement() {
        // Din befintliga kod för att placera skepp
    }

    // ... annan befintlig kod ...

    public String[][] getMap() {
        return map;
    }
}
