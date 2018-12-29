package com.kizzington.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kizzington.client.guiElements.TextButton;
import com.kizzington.client.guiElements.TextInput;

public class RegisterMenu implements Screen, InputProcessor {
	private Game game;
	private SpriteBatch batch;
	
	private Sprite background, menuBorder, button, buttonHover, buttonClick, inputField;
	
	private TextInput usernameField, passwordField, passwordConfField;
	private TextButton registerButton, exitButton;
	
	private Stage stage = new Stage();
	
	public RegisterMenu(Game game) {
		this.game = game;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		
		background = new Sprite(new Texture(Gdx.files.internal("gui/menu/background.png")));
		menuBorder = new Sprite(new Texture(Gdx.files.internal("gui/menu/uibody.png")));
		button = new Sprite(new Texture(Gdx.files.internal("gui/menu/buttonnormal.png")));
		buttonHover = new Sprite(new Texture(Gdx.files.internal("gui/menu/buttonhover.png")));
		buttonClick = new Sprite(new Texture(Gdx.files.internal("gui/menu/buttonclicked.png")));
		inputField = new Sprite(new Texture(Gdx.files.internal("gui/menu/inputfield.png")));
		
		background.flip(false, true);
		menuBorder.flip(false, true);
		button.flip(false, true);
		buttonHover.flip(false, true);
		buttonClick.flip(false, true);
		inputField.flip(false, true);
		
		usernameField = new TextInput(inputField, 400 - (int)inputField.getWidth()/2, 600 - (int)menuBorder.getHeight() + 45, "Username:", stage, false);
		usernameField.giveFocus();
		passwordField = new TextInput(inputField, usernameField.x, usernameField.y + usernameField.height + 10, "Password:", stage, true);
		passwordConfField = new TextInput(inputField, usernameField.x, passwordField.y + passwordField.height + 10, "Confirm Password:", stage, true);
		
		registerButton = new TextButton(button, 400 - (int)button.getWidth() - 10, 600 - (int)button.getHeight() - 50);
		registerButton.text = "Register"; 
		registerButton.clickTexture = buttonClick;
		registerButton.hoverTexture = buttonHover;
		registerButton.nonHoverTexture = button;
		
		exitButton = new TextButton(button, 410, 600 - (int)button.getHeight() - 50);
		exitButton.text = "Exit";
		exitButton.clickTexture = buttonClick;
		exitButton.hoverTexture = buttonHover;
		exitButton.nonHoverTexture = button;
		
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(usernameField);
		inputMultiplexer.addProcessor(passwordField);
		inputMultiplexer.addProcessor(passwordConfField);
		inputMultiplexer.addProcessor(registerButton);
		inputMultiplexer.addProcessor(exitButton);
		Gdx.input.setInputProcessor(inputMultiplexer);
		
	}

	@Override
	public void render(float delta) {
		batch.setProjectionMatrix(MainClient.cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0, 800, 600);
		batch.draw(menuBorder, 400 - menuBorder.getWidth()/2, 600 - menuBorder.getHeight());
		
		usernameField.render(batch);
		passwordField.render(batch);
		passwordConfField.render(batch);
		registerButton.render(batch);
		exitButton.render(batch);

		batch.end();
		
		stage.draw();
		stage.act();
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
		stage.dispose();
		batch.dispose();
		registerButton.dispose();
		exitButton.dispose();
		usernameField.dispose();
		passwordField.dispose();
		passwordConfField.dispose();
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(registerButton.checkClick(screenX, screenY)) {
			MainClient.register(usernameField.getText(), passwordField.getText(), passwordConfField.getText());
		} else if(exitButton.checkClick(screenX, screenY)) {
			game.setScreen(new MainMenu(game));
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}


}
