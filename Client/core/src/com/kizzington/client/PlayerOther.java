package com.kizzington.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerOther {
	int x, y = 0;
	String username;
	int id;
	
	private BitmapFont font = MainClient.font;
	private GlyphLayout layout = new GlyphLayout();
	
	public Sprite playerSprite2;
	
	public PlayerOther() {
		
		playerSprite2 = new Sprite(new Texture(Gdx.files.internal("playerother.png")));
	}
	
	public PlayerOther(PlayerOther po) {
		playerSprite2 = po.playerSprite2;
	}

	public void render(SpriteBatch batch) {
		playerSprite2.setX(x);
		playerSprite2.setY(y);
		playerSprite2.draw(batch);
		
		layout.setText(font, username);
		int textWidth = (int)layout.width;
		int textHeight = (int)layout.height;
		
		font.getData().setScale(1, -1);
		font.setColor(Color.WHITE);
		font.draw(batch, username, x + playerSprite2.getWidth()/2 - textWidth/2, y - 10);
	}
}
