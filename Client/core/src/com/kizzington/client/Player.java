package com.kizzington.client;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kizzington.packets.PacketMove;

public class Player extends EntityPlayer implements InputProcessor {

    public Player(float x, float y){
        super(x, y);
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        super.render(batch, delta);
    }

    public void sendMovement(int direction) {
        PacketMove packet = new PacketMove();
        packet.dir = direction;
        MainClient.client.sendUDP(packet);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                sendMovement(3);
                break;
            case Input.Keys.A:
                sendMovement(1);
                break;
            case Input.Keys.S:
                sendMovement(0);
                break;
            case Input.Keys.D:
                sendMovement(2);
                break;
            default: break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
