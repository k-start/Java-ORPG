package com.kizzington.server;

import java.util.Collection;
import java.util.HashMap;

public class EntityHandler {

    private HashMap<Integer, EntityPlayer> players;

    public EntityHandler(){
        players = new HashMap<>();
    }

    public void addPlayer(EntityPlayer newPlayer){
        players.put(newPlayer.getID()-1, newPlayer);
    }
    public void addPlayer(int x, int y, String username, ServerConnection connection) {
        players.put(connection.getID()-1, new EntityPlayer(x, y, username, connection));
    }

    public void removePlayer(int id){
        players.remove(id-1);
    }

    public Collection<EntityPlayer> getPlayers(){
        return players.values();
    }
    public EntityPlayer getPlayer(int id) { return players.get(id-1); }

    public EntityPlayer getPlayer(String username){
        for(EntityPlayer player: getPlayers()){
            if(player.getUsername().equals(username)){
                return player;
            }
        }
        return null;
    }

    public void printPlayerIDS(){
        for (EntityPlayer player: getPlayers()) {
            System.out.println(player.getID());
        }
    }
}
