package com.kizzington.client;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.esotericsoftware.kryonet.Client;
import com.kizzington.packets.*;

public class MainClient extends Game {
	private Game game;
	
	public static Player player;
	public static ArrayList<PlayerOther> players = new ArrayList<PlayerOther>();
	public static Client client;
	public static OrthographicCamera cam;
	public static int height, width;

	public static int tileSize = 32;

	private float connectionTimer = 5;
	
	@Override
	public void create () {
		game = this;
		game.setScreen(new MainMenu(game));

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		cam = new OrthographicCamera();
		cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.update();
		
		client = new Client();
	    client.start();

		PacketListener packetListener = new PacketListener(game, client);
		packetListener.registerPackets();
	    
	    try {
			client.connect(5000, "127.0.0.1", 54555, 54777);
		} catch (IOException e) { System.out.println("Unable to connect, retrying in 5 seconds"); }

		client.addListener(packetListener);
	    
	    player = new Player();
	}

	@Override
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();
		update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		super.render();
	}
	
	public void update(float delta) {
		if(!client.isConnected()){
			connectionTimer -= delta;
			if(connectionTimer <= 0){
				try {
					client.connect(5000, "127.0.0.1", 54555, 54777);
				} catch (IOException e) {
					System.out.println("Unable to connect, retrying in 5 seconds");
					connectionTimer = 5;
				}
			}
		}

		cam.update();
	}

	@Override
	public void resize(int width, int height){
		this.width = width;
		this.height = height;
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

	@Override
	public void dispose () {

	}
}
