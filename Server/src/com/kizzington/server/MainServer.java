package com.kizzington.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.kizzington.packets.*;

public class MainServer {
	
	Server server;
	
	public MainServer() throws IOException {
		server = new Server() { 
			protected Connection newConnection() {
				return new ServerConnection();
			}
		};
	    server.start();
	    server.bind(54555, 54777);
	    
	    Kryo kryo = server.getKryo();
	    kryo.register(PacketMove.class);
	    kryo.register(PacketPlayer.class);
	    kryo.register(PacketLogin.class);
	    kryo.register(PacketLogout.class);
	    kryo.register(PacketRegister.class);
	    
	    server.addListener(new Listener() {
	    	public void connected(Connection c) {
	    		
	    	}
	    	
	    	public void received (Connection c, Object object) {
	    		ServerConnection connection = (ServerConnection)c;
	    		
	    		if(object instanceof PacketRegister) {
	    			PacketRegister reg = (PacketRegister)object;
	    			
	    			File f = new File("data/users/" + reg.username);
	    			if(f.exists() && !f.isDirectory()) { 
	    			    System.out.println("User Exists");
	    			    
	    			    PacketRegister packet = new PacketRegister();
						packet.response = 2;
						c.sendTCP(packet);
	    			} else {
	    				System.out.println("Registering user " + reg.username);
	    				UserFile newUser = new UserFile();
	    				newUser.username = reg.username;
	    				newUser.password = reg.password;
	    				try {
							newUser.saveUser();
							
							PacketRegister packet = new PacketRegister();
							packet.response = 1;
							c.sendTCP(packet);
							
						} catch (IOException e) {
							e.printStackTrace();
						}

	    			}
	    		}
	    		
	    		if(object instanceof PacketLogin) {
		    		for(int i = 0; i < server.getConnections().length; i++) {
		    			ServerConnection player = (ServerConnection) server.getConnections()[i];
		    			
		    			PacketPlayer p = new PacketPlayer();
		    			p.id = player.getID();
		    			p.x = player.x;
		    			p.y = player.y;
		    			p.name = player.name;
		    			c.sendTCP(p);
		    		}
		    		
		    		PacketPlayer p = new PacketPlayer();
	    			p.id = connection.getID();
	    			p.x = connection.x;
	    			p.y = connection.y;
	    			p.name = connection.name;
	    			server.sendToAllTCP(p);
	    		}
	    		
	    		if(object instanceof PacketMove) {
	    			PacketMove packet = (PacketMove) object;
	    			
	    			PacketMove newPacket = new PacketMove();
	    			
	    			if(packet.dir == 0) {
	    				connection.x--;
	    			} else if(packet.dir == 1) {
	    				connection.x++;
	    			} else if(packet.dir == 2) {
	    				connection.y--;
	    			} else if(packet.dir == 3) {
	    				connection.y++;
	    			}
	    			
	    			newPacket.id = connection.getID();
	    			newPacket.x = connection.x;
	    			newPacket.y = connection.y;
		    		server.sendToAllTCP(newPacket);
	    		}
	    	}
	    	
	    	public void disconnected(Connection connection) {
	    		PacketLogout logout = new PacketLogout();
	    		logout.id = connection.getID();
	    		server.sendToAllTCP(logout);
	    	}
	    });
	}
	

	public static void main(String[] args) throws IOException {
		new MainServer();
	}

}
