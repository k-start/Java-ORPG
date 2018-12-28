package com.kizzington.client;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.kizzington.packets.*;

public class MainClient extends Game {
	private Game game;
	private SpriteBatch batch;
	
	public static Player player;
	public static ArrayList<PlayerOther> players = new ArrayList<PlayerOther>();
	
	public static Client client;
	public static OrthographicCamera cam;
	public static ShapeRenderer shapeRenderer;
	
	public boolean setGameScreen = false;
	
	
	public static BitmapFont font;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameters;
	
	@Override
	public void create () {
		game = this;
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		cam = new OrthographicCamera();
		cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.update();
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
		parameters = new FreeTypeFontParameter();
		parameters.size = 12;
		parameters.color = Color.WHITE;
		font = generator.generateFont(parameters);
		
		final PlayerOther po = new PlayerOther();
		
		setScreen(new MainMenu(game));
		
		client = new Client();
	    client.start();
	    
	    Kryo kryo = client.getKryo();
	    kryo.register(PacketMove.class);
	    kryo.register(PacketPlayer.class);
	    kryo.register(PacketLogin.class);
	    kryo.register(PacketLogout.class);
	    kryo.register(PacketRegister.class);
	    
	    try {
			client.connect(5000, "127.0.0.1", 54555, 54777);
		} catch (IOException e) { e.printStackTrace(); }
	    
	    client.addListener(new Listener() {
	    	public void received (Connection c, Object object) {
	    		
	    		if(object instanceof PacketRegister) {
	    			PacketRegister reg = (PacketRegister) object;
	    			if(reg.response == 2) {
	    				System.out.println("User already exists");
	    			} else {
	    				//successful account creation, log in
	    				game.setScreen(new MainMenu(game));
	    			}
	    		}
	    		
	    		if(object instanceof PacketLogin) {
	    			PacketLogin log = (PacketLogin) object;
	    			if(log.response == 1) {
	    				//successful login
	    				//setScreen(new GameScreen(game));
	    				setGameScreen = true;
	    				
	    			} else if(log.response == 2){
	    				System.out.println("User logged in already");
	    			} else {
	    				System.out.println("Incorrect username or password");
	    			}
	    		}
	    		
	    		if(object instanceof PacketPlayer) {
	    			PacketPlayer packet = (PacketPlayer) object;
	    			
	    			if(packet.id != client.getID()) {
	    				PlayerOther other = new PlayerOther(po);
	    				other.x = packet.x;
	    				other.y = packet.y;
	    				other.id = packet.id;
	    				other.username = packet.name;
	    				players.add(other);
	    			} else {
	    				player.x = packet.x;
	    				player.y = packet.y;
	    				player.username = packet.name;
	    			}
	    		}
	    		
	    		
	    		if(object instanceof PacketMove) {
	    			PacketMove packet = (PacketMove) object;
	    			if(packet.id == client.getID()) {
	    				player.x = packet.x;
	    				player.y = packet.y;
	    			} else {
	    				for(PlayerOther p : players) {
	    					if(p.id == packet.id) {
	    						p.x = packet.x;
	    						p.y = packet.y;
	    						break;
	    					}
	    				}
	    			}
	    		}
	    		
	    		if(object instanceof PacketLogout) {
	    			PacketLogout packet = (PacketLogout) object;
	    			for(PlayerOther p : players) {
	    				if(p.id == packet.id) {
	    					players.remove(p);
	    					break;
	    				}
	    			}
	    		}
	    	}
	    });
	    
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
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		
		if(setGameScreen == true) {
			setGameScreen = false;
			setScreen(new GameScreen(game));
		}
		//player.update();
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
