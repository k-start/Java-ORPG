package com.kizzington.client;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {

    private float x, y;
    private float width, height;
    private float screenX, screenY;

    private Sprite sprite;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;

        this.screenX = x * MainClient.tileSize;
        this.screenY = y * MainClient.tileSize;

        sprite = new Sprite();

        width = 32;
        height = 48;
    }

    public void render(SpriteBatch batch, float delta) {
        this.screenX = x * MainClient.tileSize;
        this.screenY = y * MainClient.tileSize;

        sprite.setX(screenX);
        sprite.setY(screenY);
        sprite.flip(false, true);

        sprite.draw(batch);
    }


    public float getX() { return x; }
    public float getY() { return y; }
    public float getHeight() { return height; }
    public float getWidth() { return width; }
    public float getScreenX() { return screenX; }
    public float getScreenY() { return screenY; }
    public Sprite getSprite() { return sprite; }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setHeight(float height) { this.height = height; }
    public void setWidth(float width) { this.width = width; }
    public void setScreenX(int screenX) { this.screenX = screenX; }
    public void setScreenY(int screenY) { this.screenY = screenY; }
    public void setSprite(Sprite sprite) { this.sprite = sprite; }
}
