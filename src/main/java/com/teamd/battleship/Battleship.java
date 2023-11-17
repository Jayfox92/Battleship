package com.teamd.battleship;

import java.util.*;


public class Battleship {

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











    String[][] map;
    int mapSizeX;
    int mapSizeY;
    String water;
    String ownMessage;
    static String opponentMessage;





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



    public void decideNextAction(String message){
        message = message.trim();
        message = message.toLowerCase();
        char action = message.charAt(0);
        if (action=='i'){ //init //random
        readCoordinates(message);
        //slumpa nytt skott
        }
        else if (action=='h'){//shot hit //ai

        }
        else if (action=='m'){//shot miss //random

        }
        else if (action=='s'){//shot sänkt //random

        }
        else if (action=='g'){//game over

        }
        else System.out.println("Issue reading protocol");
    }



    public void readCoordinates(String string){ // Y-koordinat lagras i index 0, X-koordinat lagras i index 1, returneras som en arraylist
        List<Integer> listOfShotCoordinates = new ArrayList<>();
        string = string.trim();
        string = string.toLowerCase();
        Map<Character, Integer> charsMappedToInt = new HashMap<>();
        charsMappedToInt.put('a',0);
        charsMappedToInt.put('b',1);
        charsMappedToInt.put('c',2);
        charsMappedToInt.put('d',3);
        charsMappedToInt.put('e',4);
        charsMappedToInt.put('f',5);
        charsMappedToInt.put('g',6);
        charsMappedToInt.put('h',7);
        charsMappedToInt.put('i',8);
        charsMappedToInt.put('j',9);
        int indexOfY = string.length()-1;
        char findPosOfY = string.charAt(indexOfY);
        int yCoordinate = charsMappedToInt.get(findPosOfY);
        listOfShotCoordinates.add(yCoordinate);
        int indexOfX = string.length()-2;
        char findPosOfX = string.charAt(indexOfX);
        int xCooordinate = Character.getNumericValue(findPosOfX);
        listOfShotCoordinates.add(xCooordinate);
        checkCoordinates(listOfShotCoordinates);

    }



    public void checkCoordinates(List<Integer> list){
        if(Objects.equals(water, map[list.get(0)][list.get(1)])){ //innehåller koordinaterna vatten = miss

            ownMessage = "m shot";
            //kör metod för nytt skott
        }
        else if (Objects.equals("s", map[list.get(0)][list.get(1)])) { //innehåller koordinaterna s (temp sträng för skepp i skrivande stund) = träff
            //när skepp placeras i 2d array map sparas även koodrinaterna i en arraylist i Ship.java, så att vi får en koppling mellan några koordinater & ett specifikt skepp
            for (int i = 0; i < shipList.size(); i++) { //loopar igenom alla skepp

                for (int j=0; j < shipList.get(i).getCoordinatesOfShip().size(); j++) { // loopar igenom alla koordinater där skeppet 'existerar'
                    try { //try catch med tomt catch-block för att hantera out-of-bounds: (j+1)
                        if (shipList.get(i).getCoordinatesOfShip().get(j).equals(list.get(0)) && shipList.get(i).getCoordinatesOfShip().get(j+1).equals(list.get(1))) {
                            shipList.get(i).setHits(1);
                            if (shipList.get(i).isSunk()) {
                                ownMessage = "s shot";
                            } else ownMessage = "h shot";
                        }
                    } catch (Exception ignore) {}
                }

            }
        }
        else System.out.println("Error comparing coordinates to map");
    }

    public String[][] getMap(){
        return map;
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

        int reset = 0;
        int placementTries = 0;
        mapSizeX = 10;
        mapSizeY = 10;
        String water = "▓";


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
                placementTries ++;
                if (placementTries > 1000) {
                    i = -1;
                    reset ++;
                    placementTries = 0;
                    for (int j = 0; j < mapSizeY; j++) {
                        for (int k = 0; k < mapSizeX; k++) {
                            map[j][k] = water;
                        }
                    }
                    break;
                }


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
                                // map[j][k] = String.valueOf(shipList.get(i).getLength());
                                map[j][k] = "s";
                                int tempY = Integer.valueOf(Arrays.toString(map[j])); //sparar koordinaterna där skeppet existerar. index 0 = y, index 1 = x. index 2 = y, index 3 = x osv osv
                                String tempString = map[j][k];
                                int tempX = Integer.valueOf(tempString);
                                shipList.get(i).setCoordinatesOfShip(tempY,tempX);
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
                               // map[j][k] = String.valueOf(shipList.get(i).getLength());
                                map[j][k] = "s";
                                int coordinateY = Integer.valueOf(Arrays.toString(map[j])); //sparar koordinaterna där skeppet existerar i en arraylist. index 0 = y, index 1 = x. index 2 = y, index 3 = x osv osv
                                String stringOfX = map[j][k];
                                int coordinateX = Integer.valueOf(stringOfX);
                                shipList.get(i).setCoordinatesOfShip(coordinateY,coordinateX);
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
                // System.out.print(map[i][j] + " ");
            }
            // System.out.println();
        }
        // System.out.println("\nUtplacering " + "färdig efter " + (reset * 1000 + placementTries) + " st försök (" + reset + " nollställningar).");
    }

}




// Skapa fler instanser av Skepp om det behövs


// Markera skeppens positioner med bokstäver



//slumpmässigt skott



