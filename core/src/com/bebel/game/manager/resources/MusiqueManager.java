package com.bebel.game.manager.resources;

import com.badlogic.gdx.audio.Music;

/**
 * Manager de musiques
 */
public class MusiqueManager extends AbstractSubManager<Music> {
    private Music currentMusic;
    private boolean pause = false;

    public MusiqueManager(final AssetsManager manager) {
        super(manager);
    }

    @Override
    protected String getPath(final String language, final String context) {
        final StringBuilder path = new StringBuilder("musics/");
        if (context != null) path.append(context).append("/");
        return path.toString();
    }

    /**
     * Joue la musique indiquée
     * @param name
     */
    public void play(final String name) {
        play(name, false);
    }
    public void play(final String name, final boolean looping) {
        stop();
        pause = false;
        currentMusic = get(name);
        currentMusic.setVolume(parent.conf.getMusic() / 100f);
        currentMusic.setLooping(looping);
        currentMusic.play();
    }

    /**
     * Met en pause ou resume
     */
    public void togglePause() {
        if (currentMusic == null) return;
        if (pause) currentMusic.play();
        else currentMusic.pause();
        pause = !pause;
    }

    /**
     * Stop la musique courante
     */
    public void stop() {
        if (currentMusic == null) {
            pause = false;
            return;
        }else {
            currentMusic.stop();
            currentMusic = null;
        }
    }

    @Override
    protected Class<Music> getType() {
        return Music.class;
    }


    @Override
    public void dispose() {

    }
}
