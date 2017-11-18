package com.bad;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
	private SpriteBatch batch;
	private World world;
	private String avatarImage;

	public Main(String avatarImage){
		this.avatarImage = avatarImage;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		world = new World(avatarImage);
	}

	@Override
	public void render () {
		world.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		world.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
