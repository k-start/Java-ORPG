package com.kizzington.editor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.kizzington.packets.*;

import java.io.IOException;

public class MainClient extends Game {
	SpriteBatch batch;

	public static OrthographicCamera cam;
	public static Client client;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		cam = new OrthographicCamera();
		cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.update();

		client = new Client();
		client.start();

		try {
			client.connect(5000, "127.0.0.1", 54555, 54777);
		} catch (IOException e) { System.out.println("Unable to connect, retrying in 5 seconds"); }

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

		this.setScreen(new MapEditor());
	}

	@Override
	public void render () {
		cam.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
