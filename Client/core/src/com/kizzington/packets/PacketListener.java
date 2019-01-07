package com.kizzington.packets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.kizzington.client.GameScreen;
import com.kizzington.client.MainMenu;

import com.kizzington.client.*;
import com.sun.tools.javac.Main;

public class PacketListener extends Listener{

    private Game game;
    private Client client;

    public PacketListener(Game game, Client client) {
        this.game = game;
        this.client = client;
    }

    public void registerPackets(){
        Kryo kryo = client.getKryo();
        kryo.register(PacketMove.class);
        kryo.register(PacketPlayer.class);
        kryo.register(PacketLogin.class);
        kryo.register(PacketLogout.class);
        kryo.register(PacketRegister.class);
        kryo.register(PacketMap.class);
        kryo.register(PacketTile.class);
        kryo.register(PacketTile[].class);
        kryo.register(PacketTile[][].class);
    }

    public void received (Connection c, Object object) {

        if (object instanceof PacketRegister) {
            PacketRegister reg = (PacketRegister) object;
            if (reg.response == 2) {
                System.out.println("User already exists");
            } else {
                //successful account creation, log in
                Gdx.app.postRunnable(() -> game.setScreen(new MainMenu(game)));
            }
        }

        if (object instanceof PacketLogin) {
            PacketLogin log = (PacketLogin) object;
            if (log.response == 1) {
                //successful login
                Gdx.app.postRunnable(() -> game.setScreen(new GameScreen(game)));

            } else if (log.response == 2) {
                System.out.println("User logged in already");
            } else {
                System.out.println("Incorrect username or password");
            }
        }

        Gdx.app.postRunnable(() -> {
            if (object instanceof PacketPlayer) {
                PacketPlayer packet = (PacketPlayer) object;

                if (packet.id != client.getID()) {
                    MainClient.entityHandler.addPlayer(packet.x, packet.y, packet.name, packet.id);
                } else {
                    MainClient.entityHandler.setMyPlayer(packet.x, packet.y, packet.name, packet.id);
                }
            }
        });


        if (object instanceof PacketMove) {
            PacketMove packet = (PacketMove) object;
            if (packet.id == client.getID()) {
                MainClient.entityHandler.getMyPlayer().move(packet.x, packet.y, packet.dir);
            } else {
                for (EntityPlayer p : MainClient.entityHandler.getPlayers()) {
                    if (p.getID() == packet.id) {
                        p.move(packet.x, packet.y, packet.dir);
                        break;
                    }
                }
            }
        }
        Gdx.app.postRunnable(() -> {
            if (object instanceof PacketMap) {
                PacketMap packet = (PacketMap) object;

                TileMap tileMap = new TileMap();
                tileMap.map = new Tile[packet.map.length][packet.map[0].length];

                for (int x = 0; x < packet.map.length; x++) {
                    for (int y = 0; y < packet.map[x].length; y++) {
                        PacketTile tile = packet.map[x][y];
                        if(tile != null) {
                            tileMap.map[x][y] = new Tile(tile.location, tile.x, tile.y);
                        }else{
                            tileMap.map[x][y] = null;
                        }
                    }
                }
                MainClient.tileMap = tileMap;

            }
        });

        if (object instanceof PacketLogout) {
            PacketLogout packet = (PacketLogout) object;
            for (EntityPlayer p : MainClient.entityHandler.getPlayers()) {
                if (p.getID() == packet.id) {
                    MainClient.entityHandler.removePlayer(p.getID());
                    break;
                }
            }
        }
    }
}
