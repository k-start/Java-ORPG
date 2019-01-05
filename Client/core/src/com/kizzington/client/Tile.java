package com.kizzington.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile {

    private Texture tilemap;
    private Sprite tile;
    private int x, y;

    public Tile(String tilemapLocation, int x, int y){
        tilemap = new Texture(Gdx.files.internal("tilesets/" + tilemapLocation));
        this.x = x;
        this.y = y;

        TextureRegion[][] split = TextureRegion.split(tilemap, 32, 32);
        tile = new Sprite(split[y][x]);
        tile.flip(false, true);
    }

    public void render(SpriteBatch batch, float delta, int x, int y) {
        tile.setX(x * 32);
        tile.setY(y * 32);

        tile.draw(batch);
    }
}
