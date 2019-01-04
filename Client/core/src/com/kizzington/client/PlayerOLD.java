package com.kizzington.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.esotericsoftware.kryonet.Client;
import com.kizzington.packets.PacketMove;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerOLD implements InputProcessor {
	public float x, y;
	public String username;

	private int screenX,  screenY;
	private float xDiff, yDiff;

	private Client client = MainClient.client;

	private BitmapFont font;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontGenerator.FreeTypeFontParameter parameters;
	private GlyphLayout layout = new GlyphLayout();

	private Texture spriteSheet;
	private Animation<TextureRegion>[] walkAnimation;

	private float stateTime = 0;
	private boolean moving = false;
	private int dir = 0;
	private boolean keyPressed = false;

	private Sprite playerSprite;

	private float timeSince = 1;
	
	public PlayerOLD() {
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
		if(moving) {
			stateTime += delta;
			timeSince += delta;

			y -= yDiff * delta * 2;
			x -= xDiff * delta * 2;
		}else{
			x = Math.round(x);
			y = Math.round(y);
			if(keyPressed){
				stateTime = (float)Math.ceil(stateTime*2)/2.0f;
				keyPressed = false;
			}
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
	
	public void update() {
		screenX = (int)(x * MainClient.tileSize);
		screenY = (int)(y * MainClient.tileSize);

		if(timeSince >= 0.45f) {
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				PacketMove packet = new PacketMove();
				packet.dir = 0;
				client.sendUDP(packet);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				PacketMove packet = new PacketMove();
				packet.dir = 1;
				client.sendUDP(packet);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				PacketMove packet = new PacketMove();
				packet.dir = 2;
				client.sendUDP(packet);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				PacketMove packet = new PacketMove();
				packet.dir = 3;
				client.sendUDP(packet);
			}
		}
	}

	public void moveTo(int newX, int newY, int dir){
		timeSince = 0;
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

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
			case Input.Keys.W:
				keyPressed = true;
				break;
			case Input.Keys.A:
				keyPressed = true;
				break;
			case Input.Keys.S:
				keyPressed = true;
				break;
			case Input.Keys.D:
				keyPressed = true;
				break;
			default: break;
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
