package com.kizzington.client;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.esotericsoftware.kryonet.Client;
import com.kizzington.packets.*;

public class MainClient extends Game {
	private Game game;
	private SpriteBatch batch;
	
	public static Player player;
	public static ArrayList<PlayerOther> players = new ArrayList<PlayerOther>();
	
	public static Client client;
	public static OrthographicCamera cam;
	
	@Override
	public void create () {
		game = this;
		
		batch = new SpriteBatch();
		
		cam = new OrthographicCamera();
		cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.update();
		
		setScreen(new MainMenu(game));
		
		client = new Client();
	    client.start();

		PacketHandler packetHandler = new PacketHandler(game, client);
		packetHandler.registerClasses();
	    
	    try {
			client.connect(5000, "127.0.0.1", 54555, 54777);
		} catch (IOException e) { e.printStackTrace(); }


		packetHandler.listen();
	    
	    player = new Player();
	    
	}

	@Override
	public void render () {
		update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		super.render();
		
		batch.begin();
		
		batch.end();
	}
	
	public void update() {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	public static void register(String username, String password, String passwordConfirmation) {
		if(password.equals(passwordConfirmation)) {
			if(username.length() >= 3 && password.length() >= 3) {
				PacketRegister reg = new PacketRegister();
				reg.username = username;
				reg.password = password;
				client.sendTCP(reg);
			} else {
				System.out.println("Username/password too short");
			}
		} else {
			System.out.println("Passwords dont match");
		}
	}
	
	public static void login(String username, String password) {
		PacketLogin log = new PacketLogin();
		log.username = username;
		log.password = password;
		client.sendTCP(log);
	}
}
