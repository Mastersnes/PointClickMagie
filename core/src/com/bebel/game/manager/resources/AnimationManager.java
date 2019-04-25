package com.bebel.game.manager.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bebel.game.manager.resources.AssetsManager.GENERAL_PATH;
import static com.bebel.game.utils.Constantes.DEFAULT_LANGUAGE;

/**
 * Manager de texture
 */
public class AnimationManager extends AbstractSubManager<TextureAtlas> {
    public static final String ATLAS_EXTENTION = ".atlas";
    private List<String> loaded = new ArrayList<>();
    private Map<String, Animation<TextureRegion>> animations = new HashMap<>();

    public AnimationManager(final AssetsManager manager) {
        super(manager);
    }

    @Override
    protected String getPath(final String language, final String context) {
        final StringBuilder path = new StringBuilder("animations/");
        path.append(language).append("/");
        if (context != null) path.append(context).append("/");
        return path.toString();
    }


    @Override
    public void load() {
        loaded.clear();
        super.load();
    }

    @Override
    protected void load(final String path) {
        if (path.contains(".atlas")) {
            super.load(path);
            loaded.add(path);
        }
    }

    /**
     * Charge puis Liste les frames d'une animation
     *
     * @param folderName
     * @return
     */
    private TextureRegion[] list(final String folderName) {
        final String language = parent.conf.getLanguage();
        String context = parent.context;
        if (folderName.contains(GENERAL_PATH)) context = null;

        String folderPath = getPath(language, context) + folderName;
        FileHandle folder = Gdx.files.internal(folderPath);
        if (!folder.exists()) {
            folderPath = getPath(DEFAULT_LANGUAGE, context) + folderName;
            folder = Gdx.files.internal(folderPath);
        }

        final Array<TextureRegion> frames = new Array<>();
        for (final FileHandle file : folder.list()) {
            if (file.path().contains(ATLAS_EXTENTION)) {
                final TextureAtlas atlas = parent.get(file.path(), getType());
                frames.addAll(atlas.getRegions());
            }
        }
        return frames.toArray(TextureRegion.class);
    }

    @Override
    protected Class<TextureAtlas> getType() {
        return TextureAtlas.class;
    }

    @Override
    public void dispose() {
        loaded.clear();
    }

    public Animation<TextureRegion> getAnimation(String key, Animation.PlayMode playMode, float fps) {
        Animation<TextureRegion> animation = animations.get(key);

        if (animation == null) {
            final TextureRegion[] frames = list(key);
            animation = new Animation<>(1f / fps, frames);
            animations.put(key, animation);
        }

        animation.setPlayMode(playMode);
        animation.setFrameDuration(1f / fps);
        return animation;
    }
}
