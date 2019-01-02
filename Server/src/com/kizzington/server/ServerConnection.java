package com.kizzington.server;

import com.esotericsoftware.kryonet.Connection;

public class ServerConnection extends Connection {

	private boolean loggedIn = false;
	private EntityPlayer player;

	public EntityPlayer getPlayer(){ return player; }
	public void setPlayer(EntityPlayer player){ this.player = player; }

	public boolean isLoggedIn() { return loggedIn; }
	public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }
}
