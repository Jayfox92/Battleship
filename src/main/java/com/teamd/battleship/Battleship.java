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
    String opponentMessage;
    Ship localShip;





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
    private boolean gameActive = false;
    public void readFirstLetter(String message) { // detta läser första bokstaven som enl. protokoll kommer kunna identifiera vilken 'action' som görs
        message = message.trim();
        message = message.toLowerCase();
        decideNextAction(message.charAt(0));
    }


    public void decideNextAction(char action){
        Random random = new Random();
        if (action=='i'){ //init //random

            System.out.println("The client sho at "+"random coordinates");

        }
        else if (action=='h'){//shot hit //ai
            System.out.println("The ");
        }
        else if (action=='m'){//shot miss //random

        }
        else if (action=='s'){//shot sänkt //random

        }
        else if (action=='g'){//game over

        }
        else System.out.println("Issue reading protocol");
    }



    public List<Integer> readCoordinates(String string){ // Y-koordinat lagras i index 0, X-koordinat lagras i index 1, returneras som en arraylist
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
        char findPosOfY = string.charAt((string.length()-1));
        int yCoordinate = charsMappedToInt.get(findPosOfY);
        listOfShotCoordinates.add(yCoordinate);
        char findPosOfX = string.charAt((string.length()-2));
        int xCooordinate = Character.getNumericValue(findPosOfX);
        listOfShotCoordinates.add(xCooordinate);
        checkCoordinates(listOfShotCoordinates);
        return listOfShotCoordinates;

    }



    public void checkCoordinates(List<Integer> list){
        if(Objects.equals(water, map[list.get(0)][list.get(1)])){
            //innehåller koordinaterna vatten = miss
            ownMessage = "m shot ";
            //kör metod för nytt skott
        }
        else if (Objects.equals("s", map[list.get(0)][list.get(1)])) {
            //innehåller koordinaterna s som innebär skepp = träff
            //kontroll om skepp är sänkt
            // vi har en lista (för varje objekt av Ship.java) som sparar koordinaterna där skeppen existerar
            //första koordinaten y lagrax i listan på index 0, första koodrinaten x lagras i listan på index 1, andra koordinaten där skeppet 'existerar' lagras y koordinat i index 2, x koordinat i index 3 osv osv osv
            Ship tempShip;

            for (int i = 0; i < shipList.size(); i++) { //loopar igenom listan av alla skepp
                List listofYCoordinates = shipList.get(i).getCoordinatesOfShip();
                int tempY = 0;
                /*if (shipList.get(i).getCoordinatesOfShip().contains(list.get(0)) && shipList.get(i).getCoordinatesOfShip().get(1) == list.get(1)) {
                    int indexOfYInCoordinateList = shipList.get(i).getCoordinatesOfShip().contains(list.get(0));
                    for ()
                }*/
                //tempShip = shipList.get(i);
                //}
                for (int j=0; j < shipList.get(i).getCoordinatesOfShip().size(); j++){ // loopar igenom alla koordinater där skeppet 'existerar'
                    int removeXCoordinates = 1;
                    listofYCoordinates.remove(removeXCoordinates);
                    if (removeXCoordinates+1 == shipList.get(i).getCoordinatesOfShip().size()){ //failsafe, om storleken på Getcoordinatesofship är av size 6, kommer removeXcoordinate nå värdet 5, och for loopen körs igen för storleken på listan
                    int removeLast = listofYCoordinates.size();  // index 0 1 2
                    listofYCoordinates.remove((removeLast-1));   // size  1 2 3
                    break;
                    }
                    removeXCoordinates = removeXCoordinates+2;
                    if (listofYCoordinates.contains(list.get(0)) && shipList.get(i).getCoordinatesOfShip().get(1) == list.get(1))){

                    }

                }

                //alla koordinater där ett skepp existerar lagras i en lista som nås genom Ship.getCordinates()
                //listan är av typen int
                //listan vet inte om den lagrar en koordinat som motsvarar Y eller X
                //det blir en 'dålig' for-loop om den skall gå igenom

                tempY++;

                if (tempShip.isSunk()) {
                    ownMessage = "s shot";
                } else ownMessage = "h shot";


                Ship exampleShip = new Ship(4, "ExampleShip", "NotReal"); //rad skall bort, får ej skapa nytt objekt
                boolean temp = exampleShip.isSunk();
                if (temp)  //if-condition skall kontrollera om objektet (Ship) på koordinaterna blir sänkt eller ej
                    ownMessage = "s shot";
                else {
                    ownMessage = "h shot";
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
                                int tempY = Integer.valueOf(Arrays.toString(map[j]));
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



