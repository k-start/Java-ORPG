package com.kizzington.client;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.kizzington.packets.*;

public class MainClient extends Game {
	private Game game;
	private SpriteBatch batch;
	private Player player;
	private ArrayList<PlayerOther> players = new ArrayList<PlayerOther>();
	
	public static Client client;
	public static OrthographicCamera cam;
	public static ShapeRenderer shapeRenderer;
	
	@Override
	public void create () {
		game = this;
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		cam = new OrthographicCamera();
		cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.update();
		
		setScreen(new MainMenu(game));
		
		client = new Client();
	    client.start();
	    
	    Kryo kryo = client.getKryo();
	    kryo.register(PacketMove.class);
	    kryo.register(PacketPlayer.class);
	    kryo.register(PacketLogin.class);
	    kryo.register(PacketLogout.class);
	    
	    try {
			client.connect(5000, "127.0.0.1", 54555, 54777);
		} catch (IOException e) { e.printStackTrace(); }
	    
	    client.addListener(new Listener() {
	    	public void received (Connection c, Object object) {
	    		
	    		if(object instanceof PacketPlayer) {
	    			PacketPlayer packet = (PacketPlayer) object;
	    			
	    			if(packet.id != client.getID()) {
	    				PlayerOther other = new PlayerOther();
	    				other.x = packet.x;
	    				other.y = packet.y;
	    				other.id = packet.id;
	    				players.add(other);
	    			} else {
	    				player.x = packet.x;
	    				player.y = packet.y;
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
	    
	    //client.sendTCP(new PacketLogin());
	    
	}

	@Override
	public void render () {
		update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		super.render();
		
		batch.begin();
		
		/*for(PlayerOther p : players) {
			p.render(batch);
		}*/
		
		
		//player.render(batch);
		
		batch.end();
	}
	
	public void update() {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		
		
		//player.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
