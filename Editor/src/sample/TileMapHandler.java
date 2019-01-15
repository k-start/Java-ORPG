package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class TileMapHandler {

    private Canvas canvas;
    private GraphicsContext gc;

    private TileMap tileMap;

    public TileMapHandler(Canvas canvas, int width, int height){
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();

        tileMap = new TileMap(width, height);

        new AnimationTimer() {

            public void handle(long currentNanoTime) {
                render(gc);
            }
        }.start();
    }

    public void render(GraphicsContext gc){
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);

        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

        int x = (int)canvas.getWidth()/2 - tileMap.width*16;
        int y = (int)canvas.getHeight()/2 - tileMap.height*16;

        gc.setStroke(Color.RED);
        gc.strokeLine(((int)x/32)*32, ((int)y/32)*32, ((int)x/32)*32+tileMap.width*32, ((int)y/32)*32);
        gc.strokeLine(((int)x/32)*32, ((int)y/32)*32, ((int)x/32)*32, ((int)y/32)*32+tileMap.height*32);

        gc.strokeLine(((int)x/32)*32, ((int)y/32)*32+tileMap.height*32, ((int)x/32)*32+tileMap.width*32, ((int)y/32)*32+tileMap.height*32);
        gc.strokeLine(((int)x/32)*32+tileMap.width*32, ((int)y/32)*32, ((int)x/32)*32+tileMap.width*32, ((int)y/32)*32+tileMap.height*32);

        tileMap.render(gc, x, y);
    }

    public void clicked(String tileset, int selectedX, int selectedY, int clickX, int clickY){
        tileMap.addTile(new Tile(tileset, selectedX, selectedY), clickX - tileMap.screenX / 32, clickY - tileMap.screenY / 32);
    }

    public void clicked(String tileset, Point selectedPoint, int clickX, int clickY){
        tileMap.addTile(new Tile(tileset, selectedPoint.x, selectedPoint.y), clickX - tileMap.screenX / 32, clickY - tileMap.screenY / 32);
    }

    public void clicked(int clickX, int clickY){
        tileMap.addTile(null, clickX - tileMap.screenX / 32, clickY - tileMap.screenY / 32);
    }
}
