package com.kizzington.server;

public class Entity {

    private int x, y;
    private int width, height;

    public Entity(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void move(int dir){
        if(dir == 0) {
            x -= 1;
        } else if(dir == 1) {
            x += 1;
        } else if(dir == 2) {
            y -= 1;
        } else if(dir == 3) {
            y += 1;
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
}
