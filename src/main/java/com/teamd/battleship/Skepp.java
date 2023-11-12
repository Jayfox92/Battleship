package com.teamd.battleship;


import java.util.ArrayList;

class Skepp {
    // Implementera koden för Skepp-klassen här
    private String namn;
    private int längd;
    private int skada;
    private ArrayList<String> positioner;
    private Skepp [] skepps;

    public Skepp(String namn, int längd) {
        this.namn = namn;
        this.längd = längd;
        this.skada = 0;
        this.positioner = new ArrayList<>();
    }

    public Skepp() {

    }

    public String getNamn() {
        return namn;
    }

    public int getLängd() {
        return längd;
    }

    public int getSkada() {
        return skada;
    }
    public boolean GameOver(){

        if(ärSänkt()) {
            return true;
        }
        return false;
    }

    /**
     *
     */
    public void träffad() {
        skada++;
    }

    public boolean ärSänkt() {
        return skada >= längd;
    }

    public void läggTillPosition(String position) {
        positioner.add(position);
    }

    public ArrayList<String> getPositioner() {
        return positioner;
    }
}