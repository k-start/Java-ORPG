package com.kizzington.server;

public class Tile {

    private String location;
    private int x, y;

    public Tile(String location, int x, int y){
        this.location = location;
        this.x = x;
        this.y = y;
    }

    public String getLocation() {
        return location;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
