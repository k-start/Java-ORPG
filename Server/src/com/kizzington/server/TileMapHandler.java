package com.kizzington.server;

import com.kizzington.packets.PacketMap;
import com.kizzington.packets.PacketTile;

import java.util.ArrayList;

public class TileMapHandler {

    private ArrayList<TileMap> maps;

    public TileMapHandler(){
        maps = new ArrayList<>();
    }

    public ArrayList<TileMap> getMaps() {
        return maps;
    }

    public TileMap getMap(int i){
        return maps.get(i);
    }

    public void setMap(int i , TileMap map){
        maps.add(i, map);
    }

    public PacketMap getPacket(int i){
        TileMap map = maps.get(i);

        PacketMap packet = new PacketMap();

        packet.map = new PacketTile[map.width][map.height];

        for(int x = 0; x < map.width; x++) {
            for(int y = 0; y < map.width; y++) {
                packet.map[x][y] = new PacketTile();
                packet.map[x][y].location = map.map.get(x).get(y).getLocation();
                packet.map[x][y].x = map.map.get(x).get(y).getX();
                packet.map[x][y].y = map.map.get(x).get(y).getY();
            }
        }
        return packet;
    }
}
