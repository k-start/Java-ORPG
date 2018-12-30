package com.kizzington.server;

import com.esotericsoftware.kryonet.Connection;

public class EntityPlayer extends Entity {

    private ServerConnection c;
    private String username;

    public EntityPlayer(int x, int y, String username){
        super(x, y);
        this.username = username;
    }

    public String getUsername(){ return username; }
    public void setUsername(String username) { this.username = username; }

}
