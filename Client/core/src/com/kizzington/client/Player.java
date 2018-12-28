package com.kizzington.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import com.kizzington.packets.PacketMove;

public class Player {
	int x, y;
	String username;
	
	private Client client = MainClient.client;
	
	private BitmapFont font = MainClient.font;
	private GlyphLayout layout = new GlyphLayout();
	
	private Sprite playerSprite;
	
	public Player() {		
		playerSprite = new Sprite(new Texture(Gdx.files.internal("player.png")));
	}
	
	public void render(SpriteBatch batch) {
		playerSprite.setX(x);
		playerSprite.setY(y);
		playerSprite.draw(batch);
		
		layout.setText(font, username);
		int textWidth = (int)layout.width;
		int textHeight = (int)layout.height;
		
		font.getData().setScale(1, -1);
		font.setColor(Color.WHITE);
		font.draw(batch, username, x + playerSprite.getWidth()/2 - textWidth/2, y - 10);
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
