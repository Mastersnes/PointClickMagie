package com.bebel.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.bebel.game.manager.resources.AssetsManager;
import com.bebel.game.manager.resources.ScreensManager;
import com.bebel.game.screens.Menu;

/**
 * Main
 */
public class LaunchGame extends Game {
	public SpriteBatch batch;
	public AssetsManager manager;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Logger.DEBUG);
		batch = new SpriteBatch();
		manager = AssetsManager.getInstance();
		ScreensManager.init(this);
		ScreensManager.getInstance().switchTo(Menu.class);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		ScreensManager.getInstance().dispose();
		manager.dispose();
		batch.dispose();
	}
}
