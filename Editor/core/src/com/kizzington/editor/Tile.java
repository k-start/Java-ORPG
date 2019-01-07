package com.kizzington.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;

public class Tile {

    private Texture tilemap;
    private Sprite tile;
    private int x, y;
    private String location;

    public Tile(String tilemapLocation, int x, int y){
        tilemap = new Texture(Gdx.files.internal("tilesets/" + tilemapLocation));
        this.x = x;
        this.y = y;

        location = tilemapLocation;

        TextureRegion[][] split = TextureRegion.split(tilemap, 32, 32);
        tile = new Sprite(split[y][x]);
        tile.flip(false, true);
    }

    public void render(SpriteBatch batch, float delta, int x, int y) {
        tile.setX(x * 32);
        tile.setY(y * 32);

        tile.draw(batch);
    }

    public String getLocation(){
        return location;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
