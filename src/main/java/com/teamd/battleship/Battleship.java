package com.teamd.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;



public class Battleship {
    private Skepp spelarensFlotta;
    private char[][] spelplan;




    public Battleship() {
        spelplan = new char[10][10];
        spelarensFlotta = new Skepp();
        skapaSpelplan();
        skrivUtSpelplan();
    }

    public static void main(String[] args) {
        Battleship game = new Battleship();
        ArrayList<Skepp> spelarensFlotta = game.skapaFlotta();
        // Här kan du använda flottan och implementera resten av spelet

        System.out.println();
        shipPlacement();
    }

    private void skapaSpelplan() {
        for (int x = 0; x < spelplan.length; x++) {
            for (int y = 0; y < spelplan[0].length; y++) {
                spelplan[x][y] = '-';
            }
        }
    }

    public void skrivUtSpelplan() {
        System.out.print("  ");
        for (int i = 1; i < 11; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int y = 0; y < 10; y++) {
            System.out.print((char) ('A' + y) + " ");
            for (int x = 0; x < 10; x++) {
                System.out.print(spelplan[x][y] + " ");
            }
            System.out.println();
        }
    }

    //lista skepp (ArrayList)
    public ArrayList<Skepp> skapaFlotta() {
        ArrayList<Skepp> flotta = new ArrayList<>();

        // Skapa ett hangarfartyg (5x1)
        Hangarfartyg hangarfartyg = new Hangarfartyg();
        flotta.add(hangarfartyg);

        // Skapa två slagskepp (4x1)
        Slagskepp slagskepp1 = new Slagskepp();
        Slagskepp slagskepp2 = new Slagskepp();
        flotta.add(slagskepp1);
        flotta.add(slagskepp2);

        // Skapa tre kryssare (3x1)
        Kryssare kryssare1 = new Kryssare();
        Kryssare kryssare2 = new Kryssare();
        Kryssare kryssare3 = new Kryssare();
        flotta.add(kryssare1);
        flotta.add(kryssare2);
        flotta.add(kryssare3);

        // Skapa fyra ubåtar (2x1)
        Ubåt ubåt1 = new Ubåt();
        Ubåt ubåt2 = new Ubåt();
        Ubåt ubåt3 = new Ubåt();
        Ubåt ubåt4 = new Ubåt();
        flotta.add(ubåt1);
        flotta.add(ubåt2);
        flotta.add(ubåt3);
        flotta.add(ubåt4);

        return flotta;
    }

    public static void shipPlacement() {

        // Skapa objekt

        Ship ship1 = new Ship(5, "s0", "hangarfartyg");
        Ship ship2 = new Ship(4, "s1", "slagskepp");
        Ship ship3 = new Ship(4, "s2", "slagskepp");
        Ship ship4 = new Ship(3, "s3", "kryssare");
        Ship ship5 = new Ship(3, "s4", "kryssare");
        Ship ship6 = new Ship(3, "s5", "kryssare");
        Ship ship7 = new Ship(2, "s6", "ubåt");
        Ship ship8 = new Ship(2, "s7", "ubåt");
        Ship ship9 = new Ship(2, "s8", "ubåt");
        Ship ship10 = new Ship(2, "s9", "ubåt");


        // Skapa lista + addera objekt

        List<Ship> shipList = new ArrayList<>();
        shipList.add(ship1);
        shipList.add(ship2);
        shipList.add(ship3);
        shipList.add(ship4);
        shipList.add(ship5);
        shipList.add(ship6);
        shipList.add(ship7);
        shipList.add(ship8);
        shipList.add(ship9);
        shipList.add(ship10);


        // Varibler

        int totalTries = 0;
        int tries = 0;
        int mapSizeX = 10;
        int mapSizeY = 10;
        String water = "▓";


        // Skapa karta (2D-array)

        String[][] map = new String[mapSizeY][mapSizeX];


        // Skriva ut tecken för vatten på kartan

        for (int i = 0; i < mapSizeY; i++) {
            for (int j = 0; j < mapSizeX; j++) {
                map[i][j] = water;
            }
        }


        // Placera Skepp

        for (int i = 0; i < shipList.size(); i++) {

            Random random = new Random();

            int shipX;
            int shipY;
            int checkXStart;
            int checkXEnd;
            int checkYStart;
            int checkYEnd;
            boolean successfulPlacement = false;

            while (!successfulPlacement) {
                int collision = 0;
                boolean horizontalAlignment = random.nextBoolean();


                // Kodfält vid horisontell utplacering

                if (horizontalAlignment) {


                    // Slumpmässig placering och grundsökområde

                    shipX = random.nextInt(mapSizeX-shipList.get(i).getLength() + 1);           // Kan ej placera ett skepp utanför kartan i X-led.
                    shipY = random.nextInt(mapSizeY);
                    checkXStart = shipX;
                    checkXEnd = shipX + 1 + (shipList.get(i).getLength() - 1);
                    checkYStart = shipY;
                    checkYEnd = shipY + 1;


                    // Eventuell utökning av sökområdet beroende på placering

                    if (shipX > 0) checkXStart --;
                    if (shipX + (shipList.get(i).getLength() - 1) < mapSizeX - 1) checkXEnd ++;
                    if (shipY > 0) checkYStart --;
                    if (shipY < mapSizeY - 1) checkYEnd ++;


                    // Kontrollerar sökområdet

                    for (int j = checkYStart; j < checkYEnd; j++) {
                        for (int k = checkXStart; k < checkXEnd; k++) {
                            if (!Objects.equals(map[j][k], "▓")) collision++;
                        }
                    }


                    // Placerar skepp om det inte är någon kollision

                    if (collision == 0) {
                        for (int j = shipY; j < shipY+1; j++) {
                            for (int k = shipX; k < (shipX + 1) + (shipList.get(i).getLength()-1); k++) {
                                map[j][k] = String.valueOf(shipList.get(i).getLength());
                            }
                        }
                        successfulPlacement = true;
                    }


                    // Kodfält vid vertikal utplacering

                } else {


                    // Slumpmässig placering och grundsökområde

                    shipX = random.nextInt(mapSizeX);
                    shipY = random.nextInt(mapSizeY-shipList.get(i).getLength() + 1);           // Kan ej placera ett skepp utanför kartan i Y-led.
                    checkXStart = shipX;
                    checkXEnd = shipX + 1;
                    checkYStart = shipY;
                    checkYEnd = shipY + 1 + (shipList.get(i).getLength() - 1);


                    // Eventuell utökning av sökområdet beroende på placering

                    if (shipX > 0) checkXStart --;
                    if (shipX < (mapSizeX - 1)) checkXEnd ++;
                    if (shipY > 0) checkYStart --;
                    if (shipY + (shipList.get(i).getLength() - 1) < mapSizeY - 1) checkYEnd ++;


                    // Kontrollerar sökområdet

                    for (int j = checkYStart; j < checkYEnd; j++) {
                        for (int k = checkXStart; k < checkXEnd; k++) {
                            if (!Objects.equals(map[j][k], "▓")) collision++;
                        }
                    }


                    // Placerar skepp om det inte är någon kollision

                    if (collision == 0) {
                        for (int j = shipY; j < shipY + 1 + (shipList.get(i).getLength() - 1); j++) {
                            for (int k = shipX; k < shipX + 1; k++) {
                                map[j][k] = String.valueOf(shipList.get(i).getLength());
                            }
                        }
                        successfulPlacement = true;
                    }


                }
            }
        }


        // Skriva ut kartan

        for (int i = 0; i < mapSizeY; i++) {
            for (int j = 0; j < mapSizeX; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

}




// Skapa fler instanser av Skepp om det behövs


// Markera skeppens positioner med bokstäver



//slumpmässigt skott



