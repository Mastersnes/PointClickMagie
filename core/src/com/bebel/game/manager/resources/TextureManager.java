package com.bebel.game.manager.resources;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Manager de texture
 */
public class TextureManager extends AbstractSubManager<TextureAtlas> {
    public TextureManager(final AssetsManager manager) {
        super(manager);
    }

    @Override
    protected String getPath(final String language, final String context) {
        final StringBuilder path = new StringBuilder("textures/");
        path.append(language).append("/");
        if (context != null) path.append(context).append("/");
        return path.toString();
    }

    @Override
    public void load() {
        super.load();
    }

    @Override
    protected void load(final String path) {
        if (path.contains(".atlas")) {
            super.load(path);
        }
    }

    /**
     * Renvoi la texture demandée
     *
     * @param name
     * @return
     */
    public TextureRegion getTexture(final String name) {
        final String atlasPath = name.split(":")[0] + ".atlas";
        final String regionName = name.split(":")[1];
        final TextureAtlas atlas = get(atlasPath);
        return atlas.findRegion(regionName);
    }

    @Override
    protected Class<TextureAtlas> getType() {
        return TextureAtlas.class;
    }

    @Override
    public void dispose() {
    }
}
