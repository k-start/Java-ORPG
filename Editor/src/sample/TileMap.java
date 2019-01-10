package sample;

import javafx.scene.canvas.GraphicsContext;

public class TileMap {

    public Tile[][] map;
    public int width, height;
    public int screenX = 0, screenY = 0;

    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;
        map = new Tile[width][height];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++) {
                map[i][j] = null;
            }
        }
    }

    public void render(GraphicsContext gc, int x, int y){
        screenX = x; screenY = y;
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(map[i][j] != null) {
                    map[i][j].render(gc, x/32 + i, y/32 + j);
                }
            }
        }
    }

    public void addTile(Tile tile, int x, int y){
        map[x][y] = tile;
    }
}
