package com.bebel.game.manager.resources;

import com.badlogic.gdx.graphics.Texture;

/**
 * Manager de texture
 */
public class ImageManager extends AbstractSubManager<Texture> {
    public ImageManager(final AssetsManager manager) {
        super(manager);
    }

    @Override
    protected String getPath(final String language, final String context) {
        final StringBuilder path = new StringBuilder("images/");
        path.append(language).append("/");
        if (context != null) path.append(context).append("/");
        return path.toString();
    }

    @Override
    protected Class<Texture> getType() {
        return Texture.class;
    }

    @Override
    public void dispose() {
    }

}
