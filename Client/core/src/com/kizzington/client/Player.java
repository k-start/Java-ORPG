package com.kizzington.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.esotericsoftware.kryonet.Client;
import com.kizzington.packets.PacketMove;

public class Player {
	int x, y = 0;
	
	private Client client = MainClient.client;
	private ShapeRenderer shapeRenderer = MainClient.shapeRenderer;
	
	public void render(SpriteBatch batch) {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(x, y, 32, 32);
		shapeRenderer.end();
	}
	
	public void update() {
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			PacketMove packet = new PacketMove();
			packet.dir = 0;
			client.sendTCP(packet);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			PacketMove packet = new PacketMove();
			packet.dir = 1;
			client.sendTCP(packet);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			PacketMove packet = new PacketMove();
			packet.dir = 2;
			client.sendTCP(packet);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			PacketMove packet = new PacketMove();
			packet.dir = 3;
			client.sendTCP(packet);
		}
	}
}
