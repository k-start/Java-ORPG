package com.kizzington.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kizzington.client.guiElements.TextButton;

public class MainMenu implements Screen, InputProcessor {
	private Game game;
	private SpriteBatch batch;
	
	private Sprite background, menuBorder;
	
	private TextButton loginButton, registerButton;

	
	public MainMenu(Game game) {
		this.game = game;
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		
		background = new Sprite(new Texture(Gdx.files.internal("gui/menu/background.png")));
		menuBorder = new Sprite(new Texture(Gdx.files.internal("gui/menu/uibody.png")));
		Sprite button = new Sprite(new Texture(Gdx.files.internal("gui/menu/buttonnormal.png")));
		Sprite buttonHover = new Sprite(new Texture(Gdx.files.internal("gui/menu/buttonhover.png")));
		Sprite buttonClick = new Sprite(new Texture(Gdx.files.internal("gui/menu/buttonclicked.png")));
		
		background.flip(false, true);
		menuBorder.flip(false, true);
		button.flip(false, true);
		buttonHover.flip(false, true);
		buttonClick.flip(false, true);
		
		loginButton = new TextButton(button, 400 - (int)button.getWidth()/2, 600 - (int)menuBorder.getHeight() + (int)button.getHeight());
		loginButton.text = "Login";
		loginButton.clickTexture = buttonClick;
		loginButton.hoverTexture = buttonHover;
		loginButton.nonHoverTexture = button;
		
		registerButton = new TextButton(button, loginButton.x, loginButton.y + (int)button.getHeight() + 10);
		registerButton.text = "Register";
		registerButton.clickTexture = buttonClick;
		registerButton.hoverTexture = buttonHover;
		registerButton.nonHoverTexture = button;
		
	}

	@Override
	public void render(float delta) {
		batch.setProjectionMatrix(MainClient.cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0, 800, 600);
		batch.draw(menuBorder, 400 - menuBorder.getWidth()/2, 600 - menuBorder.getHeight());
		
		loginButton.render(batch);
		registerButton.render(batch);
		
		//batch.draw(button, 400 - button.getWidth()/2, menuBorder.getHeight() - button.getHeight() - 60);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		loginButton.dispose();
		registerButton.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		loginButton.clickDown(screenX, screenY);
		registerButton.clickDown(screenX, screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(loginButton.checkClick(screenX, screenY)) {
			game.setScreen(new LoginMenu(game));
		}
		if(registerButton.checkClick(screenX, screenY)) {
			game.setScreen(new RegisterMenu(game));
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		loginButton.hover(screenX, screenY);
		registerButton.hover(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
