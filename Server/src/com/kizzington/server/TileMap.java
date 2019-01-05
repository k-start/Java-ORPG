package com.kizzington.server;

import java.io.Serializable;
import java.util.ArrayList;

public class TileMap implements Serializable {

    ArrayList<ArrayList<Tile>> map;
    int height, width;

    public TileMap(int height, int width) {
        this.height = height;
        this.width = width;

        map = new ArrayList<>();

        for(int x = 0; x < width; x++){
            map.add(new ArrayList<Tile>());
            for(int y = 0; y < height; y++){
                map.get(x).add(new Tile("Tileset1.png", 4,0));
            }
        }
    }

}
