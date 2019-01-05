package com.kizzington.server;

import java.io.IOException;
import java.sql.SQLException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.kizzington.packets.*;

public class MainServer {

	public static Server server;
	public static EntityHandler entityHandler;
	public static TileMapHandler mapHandler;
	public static Database database;

	public static void main(String[] args) throws IOException {
        entityHandler = new EntityHandler();
        mapHandler = new TileMapHandler();

        TileMap map = new TileMap(10, 10);
        mapHandler.setMap(0, map);

		database = new Database("serverDB.db");
		try {
			database.connect();
		} catch (SQLException e) {
			System.out.println("Unable to connect to database");
		}

		server = new Server() {
			protected Connection newConnection() {
				return new ServerConnection();
			}
		};
		server.start();
		server.bind(54555, 54777);

		PacketListener packetListener = new PacketListener(server);
		packetListener.registerPackets();

		server.addListener(packetListener);
	}
}
