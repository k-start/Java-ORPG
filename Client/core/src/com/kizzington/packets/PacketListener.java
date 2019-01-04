package com.kizzington.packets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.kizzington.client.GameScreen;
import com.kizzington.client.MainMenu;
import com.kizzington.client.PlayerOther;

import com.kizzington.client.*;

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
                    EntityPlayer other = new EntityPlayer(packet.x, packet.y, packet.name, packet.id);
                    MainClient.players.add(other);
                } else {
                    MainClient.player.setX((float)packet.x);
                    MainClient.player.setY((float)packet.y);
                    MainClient.player.setUsername(packet.name);
                }
            }
        });


        if (object instanceof PacketMove) {
            PacketMove packet = (PacketMove) object;
            if (packet.id == client.getID()) {
                MainClient.player.move(packet.x, packet.y, packet.dir);
            } else {
                for (EntityPlayer p : MainClient.players) {
                    if (p.getID() == packet.id) {
                        p.move(packet.x, packet.y, packet.dir);
                        break;
                    }
                }
            }
        }

        if (object instanceof PacketLogout) {
            PacketLogout packet = (PacketLogout) object;
            for (EntityPlayer p : MainClient.players) {
                if (p.getID() == packet.id) {
                    MainClient.players.remove(p);
                    break;
                }
            }
        }
    }
}
