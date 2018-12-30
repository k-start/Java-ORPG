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

public class PacketHandler extends Listener{

    private Game game;
    private Client client;

    public PacketHandler(Game game, Client client) {
        this.game = game;
        this.client = client;
    }

    public void registerClasses(){
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
                    PlayerOther other = new PlayerOther();
                    other.x = packet.x;
                    other.y = packet.y;
                    other.y = packet.y;
                    other.id = packet.id;
                    other.username = packet.name;
                    MainClient.players.add(other);
                } else {
                    MainClient.player.x = packet.x;
                    MainClient.player.y = packet.y;
                    MainClient.player.username = packet.name;
                }
            }
        });


        if (object instanceof PacketMove) {
            PacketMove packet = (PacketMove) object;
            if (packet.id == client.getID()) {
                MainClient.player.x = packet.x;
                MainClient.player.y = packet.y;
            } else {
                for (PlayerOther p : MainClient.players) {
                    if (p.id == packet.id) {
                        p.x = packet.x;
                        p.y = packet.y;
                        break;
                    }
                }
            }
        }

        if (object instanceof PacketLogout) {
            PacketLogout packet = (PacketLogout) object;
            for (PlayerOther p : MainClient.players) {
                if (p.id == packet.id) {
                    MainClient.players.remove(p);
                    break;
                }
            }
        }
    }
}
