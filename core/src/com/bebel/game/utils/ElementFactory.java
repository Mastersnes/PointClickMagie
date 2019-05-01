package com.bebel.game.utils;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Pools;
import com.bebel.game.components.refound.abstrait.AbstractScreen;
import com.bebel.game.components.refound.element.Animate;
import com.bebel.game.components.refound.element.Image;
import com.bebel.game.components.refound.element.Text;

public class ElementFactory {
    public static Image image(final String key) {
        final Image image = Pools.get(Image.class).obtain();
        image.init(key);
        return image;
    }

    // Text
    public static Text text(final String key) {
        final Text text = Pools.get(Text.class).obtain();
        text.init(key);
        return text;
    }
    public static Text text(final String key, final BitmapFont font) {
        final Text text = Pools.get(Text.class).obtain();
        text.init(key, font);
        return text;
    }
    public static Text text(final String key, final BitmapFont font, final Color color) {
        final Text text = Pools.get(Text.class).obtain();
        text.init(key, font, color);
        return text;
    }

    // Animation
    public static Animate animate(final String key, final Animation.PlayMode playMode, final float fps) {
        final Animate animate = Pools.get(Animate.class).obtain();
        animate.init(key, playMode, fps);
        return animate;
    }
    public static Animate animate(final String key, final float fps) {
        return animate(key, Animation.PlayMode.NORMAL, fps);
    }
    public static Animate animate(final String key) {
        return animate(key, 24);
    }
    public static Animate loop(final String key, final float fps) {
        return animate(key, Animation.PlayMode.LOOP, fps);
    }
    public static Animate loop(final String key) {
        return loop(key, 24);
    }

    public static <T> T group(final Class<T> type) {
        final T group = Pools.get(type).obtain();
        return group;
    }
}
