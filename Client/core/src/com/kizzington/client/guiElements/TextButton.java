package com.kizzington.client.guiElements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;

public class TextButton implements InputProcessor {
	public int x, y, width, height;
	public Sprite sprite, hoverTexture, nonHoverTexture, clickTexture;
	public String text;
	
	private BitmapFont font;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameters;
	private GlyphLayout layout = new GlyphLayout();
	
	public TextButton(Sprite sprite, int x, int y) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.width = (int)sprite.getWidth();
		this.height = (int)sprite.getHeight();
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
		parameters = new FreeTypeFontParameter();
		parameters.size = 25;
		parameters.color = Color.BLACK;
		font = generator.generateFont(parameters);
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(sprite, x, y, width, height);
		
		layout.setText(font, text);
		int textWidth = (int)layout.width;
		int textHeight = (int)layout.height;
		
		font.getData().setScale(1, -1);
		
		font.draw(batch, text, x + sprite.getWidth()/2 - textWidth/2, y + sprite.getHeight()/2 + textHeight/2);
	}
	
	public boolean checkClick(int screenX, int screenY) {
		Rectangle rect = new Rectangle(x, y, width, height);
		if(rect.contains(screenX, screenY)) {
			return(true);
		}
		return(false);
	}
	
	public void hover(int screenX, int screenY) {
		Rectangle rect = new Rectangle(x, y, width, height);
		if(rect.contains(screenX, screenY)) {
			if(sprite != hoverTexture) {
				sprite = hoverTexture;
			}
		} else {
			if(sprite != nonHoverTexture) {
				sprite = nonHoverTexture;
			}
		}
	}
	
	public void clickDown(int screenX, int screenY) {
		Rectangle rect = new Rectangle(x, y, width, height);
		if(rect.contains(screenX, screenY)) {
			if(sprite != clickTexture) {
				sprite = clickTexture;
			}
		} else {
			if(sprite != nonHoverTexture) {
				sprite = nonHoverTexture;
			}
		}
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
		clickDown(screenX, screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		hover(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

    public void dispose() {
		font.dispose();
		generator.dispose();
    }
}
