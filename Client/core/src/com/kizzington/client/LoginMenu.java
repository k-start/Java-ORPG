package com.kizzington.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoginMenu implements Screen, InputProcessor {
	private Game game;
	private SpriteBatch batch;
	
	private Sprite background, menuBorder, button, buttonHover, buttonClick;
	
	public LoginMenu(Game game) {
		this.game = game;
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		
		background = new Sprite(new Texture(Gdx.files.internal("gui/menu/background.png")));
		menuBorder = new Sprite(new Texture(Gdx.files.internal("gui/menu/uibody.png")));
		button = new Sprite(new Texture(Gdx.files.internal("gui/menu/buttonnormal.png")));
		buttonHover = new Sprite(new Texture(Gdx.files.internal("gui/menu/buttonhover.png")));
		buttonClick = new Sprite(new Texture(Gdx.files.internal("gui/menu/buttonclicked.png")));
		
		background.flip(false, true);
		menuBorder.flip(false, true);
		button.flip(false, true);
		buttonHover.flip(false, true);
		buttonClick.flip(false, true);
		
	}

	@Override
	public void render(float delta) {
		batch.setProjectionMatrix(MainClient.cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0, 800, 600);
		batch.draw(menuBorder, 400 - menuBorder.getWidth()/2, 600 - menuBorder.getHeight());
		
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
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
