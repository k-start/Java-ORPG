package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tile {

    private String tileset;
    private Image tilesetImg;
    private int x, y;

    public Tile(String tileset, int x, int y){
        this.tileset = tileset;
        this.x = x;
        this.y = y;
        tilesetImg = new Image("file:tilesets/" + tileset);
    }

    public void render(GraphicsContext gc, int screenX, int screenY){
        gc.drawImage(tilesetImg, x*32, y*32, 32, 32, screenX*32, screenY*32, 32, 32);
    }

    public String getTileset() {
        return tileset;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
