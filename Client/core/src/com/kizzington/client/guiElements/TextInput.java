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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class TextInput implements InputProcessor {
	public int x, y, width, height;
	public Sprite sprite;
	public String text;
	public Stage stage;
	
	private BitmapFont font;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameters;
	private GlyphLayout layout = new GlyphLayout();
	
	private TextField field;
	
	private Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
	
	public TextInput(Sprite sprite, int x, int y, String text, Stage stage, boolean password) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.width = (int)sprite.getWidth();
		this.height = (int)sprite.getHeight();
		this.text = text;
		this.stage = stage;
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
		parameters = new FreeTypeFontParameter();
		parameters.size = 19;
		parameters.color = Color.BLACK;
		font = generator.generateFont(parameters);
		
		font.getData().setScale(1, -1);
	    
	    field = new TextField("", skin);
	    
	    field.setX(x + sprite.getWidth()/3 + 35);
	    field.setY(600 - (y + sprite.getHeight() - 15));
	    
	    field.setPasswordMode(password);
	    field.setPasswordCharacter('*');
	    
	    field.setWidth(250);
	    
	    this.stage.addActor(field);  
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(sprite, x, y, width, height);
				
		layout.setText(font, text);
		int textWidth = (int)layout.width;
		int textHeight = (int)layout.height;		
		
		font.setColor(Color.BLACK);
		font.draw(batch, text, x + sprite.getWidth()/5 - textWidth/2, y + sprite.getHeight()/2 + textHeight/2);
	}
	
	public String getText() {
		return field.getText();
	}
	
	public void giveFocus() {
		stage.setKeyboardFocus(field);
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
		Rectangle rect = new Rectangle(x, y, width, height);
		if(rect.contains(screenX, screenY)) {
			stage.setKeyboardFocus(field);
		}
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
		// TODO Auto-generated method stub
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
