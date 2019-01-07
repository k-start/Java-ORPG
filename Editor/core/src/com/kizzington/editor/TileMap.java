package com.kizzington.editor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kizzington.packets.PacketMap;
import com.kizzington.packets.PacketTile;

public class TileMap {

    public Tile[][] map;

    public TileMap(int x, int y) {
        map = new Tile[x][y];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++) {
                map[i][j] = null;
            }
        }
    }

    public void render(SpriteBatch batch, float delta){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] != null) {
                    map[i][j].render(batch, delta, 512/32 + i, j);
                }
            }
        }
    }

    public void addTile(Tile tile, int x, int y){
        map[x][y] = tile;
    }

    public PacketMap createPacket(){
        PacketMap packet = new PacketMap();

        packet.map = new PacketTile[map.length][map[0].length];

        for(int x = 0; x < map.length; x++) {
            for(int y = 0; y < map[0].length; y++) {
                if(map[x][y] != null) {
                    packet.map[x][y] = new PacketTile();
                    packet.map[x][y].location = map[x][y].getLocation();
                    packet.map[x][y].x = map[x][y].getX();
                    packet.map[x][y].y = map[x][y].getY();
                }else{
                    packet.map[x][y] = null;
                }
            }
        }
        return packet;
    }
}
