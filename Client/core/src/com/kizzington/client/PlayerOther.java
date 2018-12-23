package com.kizzington.client;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PlayerOther {
	int x, y = 0;
	int id;
	
	private ShapeRenderer shapeRenderer = MainClient.shapeRenderer;
	
	public void render(SpriteBatch batch) {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLUE);
		shapeRenderer.rect(x, y, 32, 32);
		shapeRenderer.end();
	}
}
