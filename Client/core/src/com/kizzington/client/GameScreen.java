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
	}
	
	@Override
	public void show() {
		
		
	}

	@Override
	public void render(float delta) {
		batch.setProjectionMatrix(MainClient.cam.combined);
		batch.begin();
		
		for(EntityPlayer p : MainClient.entityHandler.getPlayers()) {
			p.render(batch, delta);
		}
		
		MainClient.entityHandler.getMyPlayer().render(batch, delta);
		
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
