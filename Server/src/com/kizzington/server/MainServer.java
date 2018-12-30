package com.kizzington.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.kizzington.packets.*;

public class MainServer {

	public static Server server;

	public static void main(String[] args) throws IOException {
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
