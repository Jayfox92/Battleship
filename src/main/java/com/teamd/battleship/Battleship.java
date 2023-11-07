package com.teamd.battleship;

import java.util.ArrayList;

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
}




// Skapa fler instanser av Skepp om det behövs


// Markera skeppens positioner med bokstäver



//slumpmässigt skott



