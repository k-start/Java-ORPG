package com.kizzington.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.Pack200.Packer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.kizzington.packets.*;

public class MainClient extends ApplicationAdapter {
	SpriteBatch batch;
	static Client client;
	static ShapeRenderer shapeRenderer;
	Player player;
	
	ArrayList<PlayerOther> players = new ArrayList<PlayerOther>();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
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
	    
	    shapeRenderer = new ShapeRenderer();
	    player = new Player();
	    
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
	    
	    client.sendTCP(new PacketLogin());
	    
	    
	}

	@Override
	public void render () {
		update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		for(PlayerOther p : players) {
			p.render(batch);
		}
		
		
		player.render(batch);
		
		batch.end();
	}
	
	public void update() {
		player.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
