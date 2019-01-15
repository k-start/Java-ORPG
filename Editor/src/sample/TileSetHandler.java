package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.*;

public class TileSetHandler {
    private Canvas canvas;
    private GraphicsContext gc;

    private String tileset;
    private Image tilesetImage;
    private Point selectedPoint = new Point();

    public TileSetHandler(Canvas canvas, String currentTileset){
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        selectedPoint.setLocation(0, 0);

        setTileset(currentTileset);
    }

    public void render(GraphicsContext gc){
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(tilesetImage, 0,0 );
        gc.setStroke(Color.RED);

        gc.strokeRect(selectedPoint.getX()*32, selectedPoint.getY()*32, 32, 32);
    }

    public void clicked(int x, int y){
        selectedPoint.setLocation(x, y);
        render(gc);
    }

    public String getTileset() {
        return tileset;
    }

    public void setTileset(String tileset) {
        this.tileset = tileset;
        tilesetImage = new Image("File:tilesets/" + tileset);

        canvas.setHeight(tilesetImage.getHeight());
        canvas.setWidth(tilesetImage.getWidth());

        render(gc);
    }

    public Point getSelectedPoint(){
        return selectedPoint;
    }
}
