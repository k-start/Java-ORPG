package com.kizzington.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerOther {
	public int x, y = 0;
	public String username;
	public int id;

	private float screenX,  screenY;
	private float xDiff, yDiff;

	private Texture spriteSheet;
	private Animation<TextureRegion>[] walkAnimation;

	private float stateTime = 0;
	private boolean moving = false;
	private int dir = 0;

	private BitmapFont font;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontGenerator.FreeTypeFontParameter parameters;
	private GlyphLayout layout = new GlyphLayout();
	
	public Sprite playerSprite;
	
	public PlayerOther() {
		spriteSheet = new Texture(Gdx.files.internal("entities/1.png"));
		TextureRegion[][] split = TextureRegion.split(spriteSheet, 32, 48);

		walkAnimation = new Animation[4];

		for(int i = 0; i < walkAnimation.length; i++) {
			walkAnimation[i] = new Animation<>(0.25f, split[i]);
		}

		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
		parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameters.size = 12;
		parameters.color = Color.WHITE;
		font = generator.generateFont(parameters);
	}

	public void render(SpriteBatch batch, float delta) {
		screenX = x * MainClient.tileSize;
		screenY = y * MainClient.tileSize;

		if(moving) {
			stateTime += delta;

			y -= yDiff * delta * 2;
			x -= xDiff * delta * 2;
		}else{
			x = Math.round(x);
			y = Math.round(y);
		}

		playerSprite = new Sprite(walkAnimation[dir].getKeyFrame(stateTime, true));

		playerSprite.flip(false, true);
		playerSprite.setX(screenX);
		playerSprite.setY(screenY - 16);

		playerSprite.draw(batch);

		layout.setText(font, username);
		int textWidth = (int)layout.width;
		int textHeight = (int)layout.height;

		font.getData().setScale(1, -1);
		font.setColor(Color.WHITE);
		font.draw(batch, username, screenX + 32/2 - textWidth/2, screenY - 26);
	}

	public void moveTo(int newX, int newY, int dir){
		moving = true;
		if(dir == 0){
			this.dir = 1;
		}else if(dir == 1){
			this.dir = 2;
		}else if(dir == 2){
			this.dir = 3;
		}else if(dir == 3){
			this.dir = 0;
		}

		this.xDiff = x - newX;
		this.yDiff = y - newY;

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				moving = false;
			}
		}, 500);
	}
}
