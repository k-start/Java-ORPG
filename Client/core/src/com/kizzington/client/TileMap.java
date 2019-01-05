package com.kizzington.client;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileMap {

    public Tile[][] map;

    public TileMap() {

    }

    public void render(SpriteBatch batch, float delta){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] != null) {
                    map[i][j].render(batch, delta, i, j);
                }
            }
        }
    }
}
