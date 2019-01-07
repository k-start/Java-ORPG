package com.kizzington.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kizzington.packets.PacketMap;

public class MapEditor implements Screen, InputProcessor {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    public Texture tileset;
    public TextureRegion[][] splitMap;

    public int selectedX = 0, selectedY = 0;

    public int lastClickX = 0, lastClickY = 0;

    public TileMap tileMap;

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        tileMap = new TileMap(26, 20);

        tileset = new Texture(Gdx.files.internal("tilesets/Tileset1.png"));

        splitMap = TextureRegion.split(tileset, 32, 32);
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(MainClient.cam.combined);
        batch.begin();

        Sprite tileSprite = new Sprite(tileset);
        tileSprite.flip(false, true);
        tileSprite.draw(batch);

        tileMap.render(batch, delta);

        batch.end();

        shapeRenderer.setProjectionMatrix(MainClient.cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(selectedX*32, selectedY*32, 32, 32);

        int xStart = 512;

        for(int i = 0; i < 20; i++) {
            shapeRenderer.line(xStart, i*32, 1280, i*32);
        }
        for(int i = 0; i < 26; i++) {
            shapeRenderer.line(i*32 + xStart, 0, i*32 + xStart, 19*32);
        }

        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.S){
            PacketMap packet = tileMap.createPacket();
            MainClient.client.sendTCP(packet);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(button == 0){
            Rectangle rect = new Rectangle(0, 0, tileset.getWidth(), tileset.getHeight());
            if(rect.contains(screenX, screenY)){
                selectedX = screenX/32;
                selectedY = screenY/32;
            }
            Rectangle rect2 = new Rectangle(512, 0, 25*32, 19*32);
            if(rect2.contains(screenX, screenY) && (lastClickX != (screenX-512)/32 || lastClickY != screenY/32)){
                lastClickX = (screenX-512)/32;
                lastClickY = screenY/32;
                Tile tile = new Tile("Tileset1.png", selectedX, selectedY);
                tileMap.addTile(tile, lastClickX, lastClickY);
            }
        }else if(button == 1){
            Rectangle rect2 = new Rectangle(512, 0, 25*32, 19*32);
            if(rect2.contains(screenX, screenY) && (lastClickX != (screenX-512)/32 || lastClickY != screenY/32)){
                lastClickX = (screenX-512)/32;
                lastClickY = screenY/32;
                tileMap.addTile(null, lastClickX, lastClickY);
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Rectangle rect2 = new Rectangle(512, 0, 25*32, 19*32);
        if(rect2.contains(screenX, screenY) && (lastClickX != (screenX-512)/32 || lastClickY != screenY/32)){
            lastClickX = (screenX-512)/32;
            lastClickY = screenY/32;
            Tile tile = new Tile("Tileset1.png", selectedX, selectedY);
            tileMap.addTile(tile, lastClickX, lastClickY);
        }
        System.out.println(pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
