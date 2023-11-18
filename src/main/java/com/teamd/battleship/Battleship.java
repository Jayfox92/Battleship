package com.teamd.battleship;

import java.util.*;


public class Battleship {
    public Battleship(String message) {
        this.opponentMessage = message;
    }

    public Battleship (Server server){
        this.server = server;
    }
    public Battleship (Client client){
        this.client = client;
    }

    public Battleship() {
    }


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


    List<Ship> shipList = new ArrayList<>();
    Set<String> coordinatesThatHaveBeenShot = new HashSet<>();
    Client client;
    Server server;

    String[][] map;
    int mapSizeX;
    int mapSizeY;
    String water;
    String ownMessage = "";
    String opponentMessage;
    static boolean activeGame = true;
    boolean serverTurn = true;


    public static void main(String[] args) {
        Battleship game = new Battleship();
        // Här kan du använda flottan och implementera resten av spelet

        System.out.println();
        //shipPlacement();
    }

    /*{
            A{"0","1","2","3","4","5","6","7","8","9",},
            B{"","","","","","","","","","",},
            C{"","","","","","","","","","",},
            D{"","","","","","","","","","",},
            E{"","","","","","","","","","",},
            F{"","","","","","","","","","",},
            G{"","","","","","","","","","",},
            H{"","","","","","","","","","",},
            I{"","","","","","","","","","",},
            J{"","","","","","","","","","",},
    };*/

    public void setClient(Client client){
        this.client = client;
    }
    public void setServer(Server server){
        this.server = server;
    }
    public void decideNextAction(String message) {

        if (!activeGame) {
            System.out.println("Game over. No further actions can be taken.");
            return;
        }

        ownMessage = "";
        message = message.trim();
        message = message.toLowerCase();
        char action = message.charAt(0);
        if (action == 'i') { //init //random
            readCoordinates(message);

                ownMessage = ownMessage.concat(randomShot());



            if (serverTurn) {
                server.ownMessage = ownMessage;
                serverTurn = false;
            } else {
                client.ownMessage = ownMessage;
                serverTurn = true;

            }

        } else if (action == 'h') {//shot hit //ai
            readCoordinates(message);
            ownMessage = ownMessage.concat(randomShot());
            if (serverTurn) {
                server.ownMessage = ownMessage;
                serverTurn = false;
            } else {
                client.ownMessage = ownMessage;
                serverTurn = true;

            }
        } else if (action == 'm') {//shot miss //random
            readCoordinates(message);
            ownMessage = ownMessage.concat(randomShot());
            if (serverTurn) {
                server.ownMessage = ownMessage;
                serverTurn = false;
            } else {
                client.ownMessage = ownMessage;
                serverTurn = true;

            }

        } else if (action == 's') {//shot sänkt //random
            readCoordinates(message);
            ownMessage = ownMessage.concat(randomShot());
            if (serverTurn) {
                server.ownMessage = ownMessage;
                serverTurn = false;

            } else {
                client.ownMessage = ownMessage;
                serverTurn = true;

            }

        } else if (action == 'g') {//game over
            if (serverTurn) {
                System.out.println("You win, congratulations!");
                serverTurn = false;
                activeGame = false;

            } else {
                System.out.println("You win, congratulations!");
                serverTurn = true;
                activeGame = false;

            }


        } else System.out.println("Issue reading protocol");
    }


    public void readCoordinates(String string) { // Y-koordinat lagras i index 0, X-koordinat lagras i index 1, returneras som en arraylist
        List<Integer> listOfShotCoordinates = new ArrayList<>();
        string = string.trim();
        string = string.toLowerCase();
        Map<Character, Integer> charsMappedToInt = new HashMap<>();
        charsMappedToInt.put('a', 0);
        charsMappedToInt.put('b', 1);
        charsMappedToInt.put('c', 2);
        charsMappedToInt.put('d', 3);
        charsMappedToInt.put('e', 4);
        charsMappedToInt.put('f', 5);
        charsMappedToInt.put('g', 6);
        charsMappedToInt.put('h', 7);
        charsMappedToInt.put('i', 8);
        charsMappedToInt.put('j', 9);
        int indexOfY = string.length() - 1;
        char findPosOfY = string.charAt(indexOfY);
        int yCoordinate = charsMappedToInt.get(findPosOfY);
        listOfShotCoordinates.add(yCoordinate);
        int indexOfX = string.length() - 2;
        char findPosOfX = string.charAt(indexOfX);
        int xCooordinate = Character.getNumericValue(findPosOfX);
        listOfShotCoordinates.add(xCooordinate);
        checkCoordinates(listOfShotCoordinates);

    }


    public void checkCoordinates(List<Integer> list) {

        if (!activeGame) {
            System.out.println("Game over. No further actions can be taken.");
            return;
        }

        if (map[list.get(0)][list.get(1)].equals("▓")) {
            ownMessage = "m shot ";
            System.out.println("Found water on map");
        } else if (map[list.get(0)][list.get(1)].equals("s")) {
            System.out.println("Found ship on map");

            for (int i = 0; i < shipList.size(); i++) {
                //List<Integer> shipCoordinates = shipList.get(i).getCoordinatesOfShip();
                for (int j = 0; j < shipList.get(i).getCoordinatesOfShip().size()-1; j += 2) {
                    if (j + 1 < shipList.get(i).getCoordinatesOfShip().size()) {
                        if (shipList.get(i).getCoordinatesOfShip().get(j).equals(list.get(0)) && shipList.get(i).getCoordinatesOfShip().get(j + 1).equals(list.get(1))) {
                            shipList.get(i).setHits(1);
                            try{shipList.get(i).getCoordinatesOfShip().remove(j+0);
                            shipList.get(i).getCoordinatesOfShip().remove(j+0);
                            }catch (IndexOutOfBoundsException e){
                                System.out.println(e.getMessage());
                            }
                            if (shipList.get(i).isSunk()){
                                ownMessage = "s shot ";
                            System.out.println("Ship "+shipList.get(i).getName()+" is sunk");
                            int amountOfSunkShips = 0;
                            for (int k = 0; k < shipList.size(); k++) {
                                if (shipList.get(k).isSunk()) {
                                    amountOfSunkShips++;

                                }
                            }
                            System.out.println("Amount of sunken ships is currently "+amountOfSunkShips+" out of 10");
                            if (amountOfSunkShips >= shipList.size()) {
                                ownMessage = "game over";
                                System.out.println("All your ships are sunk, you lose. Sorry!");
                            }

                            }
                        } else {
                            ownMessage = "h shot ";
                        }
                    }
                }
            }
        } else {
            System.out.println("Error comparing coordinates to map");
        }
    }


    public String[][] getMap() {
        return map;
    }

    public List<Ship> getShipList() {
        return shipList;
    }

    public void shipPlacement() {

        // Skapa objekt

        /*Ship ship1 = new Ship(5, "s0", "hangarfartyg");
        Ship ship2 = new Ship(4, "s1", "slagskepp");
        Ship ship3 = new Ship(4, "s2", "slagskepp");
        Ship ship4 = new Ship(3, "s3", "kryssare");
        Ship ship5 = new Ship(3, "s4", "kryssare");
        Ship ship6 = new Ship(3, "s5", "kryssare");
        Ship ship7 = new Ship(2, "s6", "ubåt");
        Ship ship8 = new Ship(2, "s7", "ubåt");
        Ship ship9 = new Ship(2, "s8", "ubåt");
        Ship ship10 = new Ship(2, "s9", "ubåt");*/


        // Skapa lista + addera objekt

        //List<Ship> shipList = new ArrayList<>();
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
        mapSizeX = 10;
        mapSizeY = 10;
        water = "▓";


        // Skapa karta (2D-array)

        map = new String[mapSizeY][mapSizeX];


        // Skriva ut tecken för vatten på kartan

        for (int i = 0; i < mapSizeY; i++) {
            for (int j = 0; j < mapSizeX; j++) {
                map[i][j] = water;
            }
        }


        // Placera skepp

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

                    shipX = random.nextInt(mapSizeX - shipList.get(i).getLength() + 1);           // Kan ej placera ett skepp utanför kartan i X-led.
                    shipY = random.nextInt(mapSizeY);
                    checkXStart = shipX;
                    checkXEnd = shipX + 1 + (shipList.get(i).getLength() - 1);
                    checkYStart = shipY;
                    checkYEnd = shipY + 1;


                    // Eventuell utökning av sökområdet beroende på placering

                    if (shipX > 0) checkXStart--;
                    if (shipX + (shipList.get(i).getLength() - 1) < mapSizeX - 1) checkXEnd++;
                    if (shipY > 0) checkYStart--;
                    if (shipY < mapSizeY - 1) checkYEnd++;


                    // Kontrollerar sökområdet

                    for (int j = checkYStart; j < checkYEnd; j++) {
                        for (int k = checkXStart; k < checkXEnd; k++) {
                            if (!Objects.equals(map[j][k], "▓")) collision++;
                        }
                    }


                    // Placerar skepp om det inte är någon kollision

                    if (collision == 0) {
                        for (int j = shipY; j < shipY + 1; j++) {
                            for (int k = shipX; k < (shipX + 1) + (shipList.get(i).getLength() - 1); k++) {
                                // map[j][k] = String.valueOf(shipList.get(i).getLength());
                                map[j][k] = "s";
                                shipList.get(i).setCoordinatesOfShip(j, k);
                            }
                        }
                        successfulPlacement = true;
                    }


                    // Kodfält vid vertikal utplacering

                } else {


                    // Slumpmässig placering och grundsökområde

                    shipX = random.nextInt(mapSizeX);
                    shipY = random.nextInt(mapSizeY - shipList.get(i).getLength() + 1);           // Kan ej placera ett skepp utanför kartan i Y-led.
                    checkXStart = shipX;
                    checkXEnd = shipX + 1;
                    checkYStart = shipY;
                    checkYEnd = shipY + 1 + (shipList.get(i).getLength() - 1);


                    // Eventuell utökning av sökområdet beroende på placering

                    if (shipX > 0) checkXStart--;
                    if (shipX < (mapSizeX - 1)) checkXEnd++;
                    if (shipY > 0) checkYStart--;
                    if (shipY + (shipList.get(i).getLength() - 1) < mapSizeY - 1) checkYEnd++;


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
                                // map[j][k] = String.valueOf(shipList.get(i).getLength());
                                map[j][k] = "s";
                                shipList.get(i).setCoordinatesOfShip(j, k);

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
                //System.out.print(map[i][j] + " ");
            }
            //System.out.println();
        }
    }

    public String randomShot() {
        Random random = new Random();
        Map<Integer, String> intsMappedToChar = new HashMap<>();
        intsMappedToChar.put(0, "a");
        intsMappedToChar.put(1, "b");
        intsMappedToChar.put(2, "c");
        intsMappedToChar.put(3, "d");
        intsMappedToChar.put(4, "e");
        intsMappedToChar.put(5, "f");
        intsMappedToChar.put(6, "g");
        intsMappedToChar.put(7, "h");
        intsMappedToChar.put(8, "i");
        intsMappedToChar.put(9, "j");
        String randomCoordinates;
        do {
            int randomY = random.nextInt(10);
            String coordinateY = intsMappedToChar.get(randomY);
            int randomX = random.nextInt(10);
            String xAsString = String.valueOf(randomX);
            randomCoordinates = xAsString + coordinateY;  // Coordinate format: "a0", "b1", etc.
        } while (coordinatesThatHaveBeenShot.contains(randomCoordinates));

        coordinatesThatHaveBeenShot.add(randomCoordinates);
        return randomCoordinates;
    }
}






// Skapa fler instanser av Skepp om det behövs


// Markera skeppens positioner med bokstäver



//slumpmässigt skott



