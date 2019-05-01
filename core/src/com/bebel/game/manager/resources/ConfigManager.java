package com.bebel.game.manager.resources;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.files.FileHandle;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;
import java.util.Properties;

import static com.bebel.game.utils.Constantes.GAME_HEIGHT;
import static com.bebel.game.utils.Constantes.GAME_WIDTH;

/**
 * Manager de configuration
 */
public class ConfigManager {
    private final Preferences conf;
    private AssetsManager parent;

    private String language;
    private float sound;
    private float music;
    private boolean fullscreen;

    public ConfigManager(final AssetsManager manager) {
        this.parent = manager;
        conf = Gdx.app.getPreferences("magix");
        language = conf.getString("language", Locale.getDefault().getLanguage());
        sound = conf.getFloat("sound", 50);
        music = conf.getFloat("music", 50);
        setFullscreen(conf.getBoolean("fullscreen", true));
        save();
    }

    /**
     * Sauvegarde les configurations
     */
    public void save() {
        conf.putString("language", language);
        conf.putFloat("sound", sound);
        conf.putFloat("music", music);
        conf.putBoolean("fullscreen", fullscreen);
        conf.flush();
    }

    public float getMusic() {
        return music;
    }
    public void setMusic(float music) {
        this.music = music;
    }

    public float getSound() {
        return sound;
    }
    public void setSound(float sound) {
        this.sound = sound;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }
    public void setFullscreen(final boolean fullscreen) {
        this.fullscreen = fullscreen;
        final Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        if (fullscreen) Gdx.graphics.setFullscreenMode(displayMode);
        else {
            Gdx.graphics.setWindowedMode(GAME_WIDTH, GAME_HEIGHT);
            if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                final Lwjgl3Graphics lwg = (Lwjgl3Graphics) Gdx.graphics;
                lwg.getWindow().setPosition(displayMode.width/2 - GAME_WIDTH/2, displayMode.height/2 - GAME_HEIGHT/2);
            }
        }
    }
}
