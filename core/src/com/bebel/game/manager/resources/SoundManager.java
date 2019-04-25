package com.bebel.game.manager.resources;

import com.badlogic.gdx.audio.Sound;

/**
 * Manager de son
 */
public class SoundManager extends AbstractSubManager<Sound> {
    public SoundManager(final AssetsManager manager) {
        super(manager);
    }

    @Override
    protected String getPath(final String language, final String context) {
        final StringBuilder path = new StringBuilder("sounds/");
        if (context != null) path.append(context).append("/");
        return path.toString();
    }

    /**
     * Joue le son indiqué
     * @param name
     * @return
     */
    public long play(final String name) {
        final Sound sound = get(name);
        return sound.play(parent.conf.getSound() / 100f);
    }

    @Override
    protected Class<Sound> getType() {
        return Sound.class;
    }


    @Override
    public void dispose() {

    }
}
