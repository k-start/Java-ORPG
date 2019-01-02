package com.kizzington.server;

public class EntityPlayer extends Entity {

    private ServerConnection c;
    private String username;

    public EntityPlayer(int x, int y, String username, ServerConnection connection){
        super(x, y);
        this.username = username;
        c = connection;
        c.setPlayer(this);
    }

    public String getUsername(){ return username; }
    public void setUsername(String username) { this.username = username; }

    public ServerConnection getConnection(){ return c; }
    public void setConnection(ServerConnection c){ this.c = c; }
    public int getID(){ return c.getID(); }

}
