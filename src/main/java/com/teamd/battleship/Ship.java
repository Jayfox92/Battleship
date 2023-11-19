package com.teamd.battleship;


import java.util.ArrayList;
import java.util.List;

public class Ship {

    private int length;
    private int shipX;
    private int shipY;
    private int hits = 0;
    private String name;
    private String description;
    private List<Integer> coordinatesOfShip;

    public Ship(int length, String name, String description) {
        this.length = length;
        this.name = name;
        coordinatesOfShip = new ArrayList<>();
    }

    public int getLength() {
        return length;
    }

    public boolean GameOver(){

        if(isSunk()) {
            return true;
        }
        return false;
    }

    public boolean isSunk() {
        return hits >= length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits+this.hits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShipX() {
        return shipX;
    }

    public void setShipX(int shipX) {
        this.shipX = shipX;
    }

    public int getShipY() {
        return shipY;
    }

    public void setShipY(int shipY) {
        this.shipY = shipY;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCoordinatesOfShip(int y, int x){ //sparar koordinaterna där skeppet existerar. index 0 = y, index 1 = x. index 2 = y, index 3 = x osv osv
        coordinatesOfShip.add(y);
        coordinatesOfShip.add(x);
    }
    public List<Integer> getCoordinatesOfShip(){
        return coordinatesOfShip;

    }
}