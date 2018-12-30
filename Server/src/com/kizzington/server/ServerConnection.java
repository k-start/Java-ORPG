package com.kizzington.server;

import com.esotericsoftware.kryonet.Connection;

public class ServerConnection extends Connection{

	public boolean loggedIn = false;

	public String name;

	public int x = 0;
	public int y = 0;

	public UserFile user;
}
