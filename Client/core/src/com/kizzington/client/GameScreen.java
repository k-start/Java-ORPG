package com.kizzington.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

	private Game game;
	private SpriteBatch batch;
	
	public GameScreen(Game game) {
		this.game = game;
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(MainClient.player);
	}
	
	@Override
	public void show() {
		
		
	}

	@Override
	public void render(float delta) {
		batch.setProjectionMatrix(MainClient.cam.combined);
		batch.begin();
		
		for(EntityPlayer p : MainClient.players) {
			p.render(batch, delta);
		}
		
		MainClient.player.render(batch, delta);
		
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

}
