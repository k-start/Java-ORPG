package com.kizzington.packets;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.kizzington.server.EntityPlayer;
import com.kizzington.server.MainServer;
import com.kizzington.server.ServerConnection;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

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
        kryo.register(PacketMap.class);
        kryo.register(PacketTile.class);
        kryo.register(PacketTile[].class);
        kryo.register(PacketTile[][].class);
    }

    public void connected(Connection c) {

    }

    public void received (Connection c, Object object) {
        ServerConnection connection = (ServerConnection)c;

        if(object instanceof PacketRegister) {
            PacketRegister reg = (PacketRegister)object;

            //Check if user exists
            if(MainServer.database.userExists(reg.username)) {
                PacketRegister packet = new PacketRegister();
                packet.response = 2;
                c.sendTCP(packet);
            } else {
                try {
                    //create user and send success
                    System.out.println("Registering user " + reg.username);

                    MainServer.database.addUser(reg.username, reg.password, 0,0 );

                    PacketRegister packet = new PacketRegister();
                    packet.response = 1;
                    c.sendTCP(packet);
                } catch (SQLException e) {
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

            //Check if user is logged in
            if(MainServer.entityHandler.getPlayer(log.username) == null) {
                //Get user from database
                EntityPlayer player = MainServer.database.getPlayer(log.username, log.password, connection);
                if(player != null){
                    //successful login
                    connection.setLoggedIn(true);

                    PacketLogin packet = new PacketLogin();
                    packet.response = 1;
                    c.sendTCP(packet);

                    PacketMap map = MainServer.mapHandler.getPacket(0);
                    c.sendTCP(map);

                    //Send all players to the user
                    for(EntityPlayer ep: MainServer.entityHandler.getPlayers()){
                        PacketPlayer p = new PacketPlayer();
                        p.id = ep.getID();
                        p.x = ep.getX();
                        p.y = ep.getY();
                        p.name = ep.getUsername();
                        c.sendTCP(p);
                    }

                    //Add entity to server
                    MainServer.entityHandler.addPlayer(player);

                    //Send the user to all players
                    PacketPlayer p = new PacketPlayer();
                    p.id = connection.getID();
                    p.x = player.getX();
                    p.y = player.getY();
                    p.name = player.getUsername();
                    server.sendToAllTCP(p);

                } else {
                    //send incorrect user/pass
                    PacketLogin packet = new PacketLogin();
                    packet.response = 0;
                    c.sendTCP(packet);
                }
            } else {
                //send user is logged in already
                PacketLogin packet = new PacketLogin();
                packet.response = 2;
                c.sendTCP(packet);
            }

        }

        if(object instanceof PacketMap){
            PacketMap packet = (PacketMap) object;
            server.sendToAllTCP(packet);
        }

        if(object instanceof PacketMove) {
            if(!connection.getPlayer().isMoving()) {
                connection.getPlayer().setMoving(true);
                PacketMove packet = (PacketMove) object;

                PacketMove newPacket = new PacketMove();

                connection.getPlayer().move(packet.dir);

                newPacket.id = connection.getID();
                newPacket.x = connection.getPlayer().getX();
                newPacket.y = connection.getPlayer().getY();
                newPacket.dir = packet.dir;
                server.sendToAllUDP(newPacket);

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        connection.getPlayer().setMoving(false);
                    }
                }, 500);
            }
        }


    }

    public void disconnected(Connection c) {
        ServerConnection connection = (ServerConnection)c;

        //log user out and save state
        if(connection.isLoggedIn()) {
            try {
                MainServer.database.updateUser(connection.getPlayer());
            } catch(SQLException e){
                e.printStackTrace();
            }
            connection.setLoggedIn(false);

            MainServer.entityHandler.removePlayer(connection.getID());

            //Send logout packet to all players, removing from their game
            PacketLogout logout = new PacketLogout();
            logout.id = c.getID();
            server.sendToAllTCP(logout);
        }
    }
}
