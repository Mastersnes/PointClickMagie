package com.bebel.game.manager.resources;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.bebel.game.components.refound.FontParameter;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager de police
 */
public class FontsManager extends AbstractSubManager<FreeTypeFontGenerator> {
    private final Map<String, BitmapFont> fonts = new HashMap<>();

    public FontsManager(final AssetsManager manager) {
        super(manager);
    }

    @Override
    protected String getPath(final String language, final String context) {
        final StringBuilder path = new StringBuilder("fonts/");
        if (context != null) path.append(context).append("/");
        return path.toString();
    }

    /**
     * Renvoi une police parametrée en cache
     * @param name
     * @param parameter
     * @return
     */
    public BitmapFont get(final String name, final FontParameter parameter) {
        final String key = name + parameter.getCode();
        BitmapFont font = fonts.get(key);
        if (font == null) {
            font = get(name).generateFont(parameter);
            fonts.put(key, font);
        }
        return font;
    }

    @Override
    protected Class<FreeTypeFontGenerator> getType() {
        return FreeTypeFontGenerator.class;
    }

    @Override
    public void dispose() {
        for (final BitmapFont font : fonts.values()) {
            font.dispose();
        }
    }

}
