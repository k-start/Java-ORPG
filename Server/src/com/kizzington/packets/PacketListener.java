package com.kizzington.packets;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.kizzington.server.ServerConnection;
import com.kizzington.server.UserFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PacketListener extends Listener {

    private Server server;

    public PacketListener(Server server) {
        this.server = server;
    }

    public void registerPackets(){
        Kryo kryo = server.getKryo();
        kryo.register(PacketMove.class);
        kryo.register(PacketPlayer.class);
        kryo.register(PacketLogin.class);
        kryo.register(PacketLogout.class);
        kryo.register(PacketRegister.class);
    }

    public void connected(Connection c) {

    }

    public void received (Connection c, Object object) {
        ServerConnection connection = (ServerConnection)c;

        if(object instanceof PacketRegister) {
            PacketRegister reg = (PacketRegister)object;

            //Check if user file exists
            File f = new File("data/users/" + reg.username);
            if(f.exists() && !f.isDirectory()) {
                System.out.println("User Exists");

                PacketRegister packet = new PacketRegister();
                packet.response = 2;
                c.sendTCP(packet);
            } else {
                System.out.println("Registering user " + reg.username);
                UserFile newUser = new UserFile();
                newUser.username = reg.username;
                newUser.password = reg.password;
                newUser.x = 0;
                newUser.y = 0;
                try {
                    //create user and send success
                    newUser.saveUser();

                    PacketRegister packet = new PacketRegister();
                    packet.response = 1;
                    c.sendTCP(packet);
                } catch (IOException e) {
                    //unable to create user, return error
                    e.printStackTrace();

                    PacketRegister packet = new PacketRegister();
                    packet.response = 0;
                    c.sendTCP(packet);
                }

            }
        }

        if(object instanceof PacketLogin) {
            PacketLogin log = (PacketLogin)object;

            UserFile user;

            File f = new File("data/users/" + log.username);
            if(f.exists() && !f.isDirectory()) {
                FileInputStream fi;
                try {
                    fi = new FileInputStream(f);
                    ObjectInputStream oi = new ObjectInputStream(fi);

                    user = (UserFile)oi.readObject();

                    if(user.password.equals(log.password)) {
                        boolean loggedIn = false;
                        for(int i = 0; i < server.getConnections().length; i++) {
                            ServerConnection player = (ServerConnection) server.getConnections()[i];
                            if(player.loggedIn && player.name.equals(log.username)) {
                                loggedIn = true;
                                break;
                            }
                        }

                        if(!loggedIn) {
                            //successful login
                            connection.loggedIn = true;
                            connection.name = log.username;
                            connection.x = user.x;
                            connection.y = user.y;
                            connection.user = user;

                            PacketLogin packet = new PacketLogin();
                            packet.response = 1;
                            c.sendTCP(packet);

                            //Send online player data/current user data
                            for(int i = 0; i < server.getConnections().length; i++) {
                                ServerConnection player = (ServerConnection) server.getConnections()[i];
                                if(player.loggedIn) {
                                    PacketPlayer p = new PacketPlayer();
                                    p.id = player.getID();
                                    p.x = player.x;
                                    p.y = player.y;
                                    p.name = player.name;
                                    c.sendTCP(p);
                                }
                            }

                            PacketPlayer p = new PacketPlayer();
                            p.id = connection.getID();
                            p.x = connection.x;
                            p.y = connection.y;
                            p.name = connection.name;
                            server.sendToAllTCP(p);
                        } else {
                            PacketLogin packet = new PacketLogin();
                            packet.response = 2;
                            c.sendTCP(packet);
                        }
                    } else {
                        PacketLogin packet = new PacketLogin();
                        packet.response = 0;
                        c.sendTCP(packet);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    PacketLogin packet = new PacketLogin();
                    packet.response = 0;
                    c.sendTCP(packet);
                }
            } else {
                PacketLogin packet = new PacketLogin();
                packet.response = 0;
                c.sendTCP(packet);
            }

        }

        if(object instanceof PacketMove) {
            PacketMove packet = (PacketMove) object;

            PacketMove newPacket = new PacketMove();

            if(packet.dir == 0) {
                connection.x--;
            } else if(packet.dir == 1) {
                connection.x++;
            } else if(packet.dir == 2) {
                connection.y--;
            } else if(packet.dir == 3) {
                connection.y++;
            }

            newPacket.id = connection.getID();
            newPacket.x = connection.x;
            newPacket.y = connection.y;
            server.sendToAllTCP(newPacket);
        }
    }

    public void disconnected(Connection c) {
        ServerConnection connection = (ServerConnection)c;

        //log user out and save state
        if(connection.loggedIn) {
            connection.user.x = connection.x;
            connection.user.y = connection.y;
            try {
                connection.user.saveUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.loggedIn = false;

            //Send logout packet to all players, removing from their game
            PacketLogout logout = new PacketLogout();
            logout.id = c.getID();
            server.sendToAllTCP(logout);
        }
    }
}
