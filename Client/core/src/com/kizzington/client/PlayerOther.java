package com.kizzington.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class PlayerOther {
	public int x, y = 0;
	public String username;
	public int id;

	private BitmapFont font;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontGenerator.FreeTypeFontParameter parameters;
	private GlyphLayout layout = new GlyphLayout();
	
	public Sprite playerSprite;
	
	public PlayerOther() {
		
		playerSprite = new Sprite(new Texture(Gdx.files.internal("playerother.png")));

		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
		parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameters.size = 12;
		parameters.color = Color.WHITE;
		font = generator.generateFont(parameters);
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
}
