package com.bebel.game.components.refound.hitbox;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Shape2D;

public interface IHitbox extends Shape2D {
    void draw(final ShapeRenderer shapes, final Color color);

    void set(final float x, final float y, final float w, final float h);

    void setPosition(final float x, final float y);
    void setX(final float x);
    void setY(final float y);

    void setSize(final float w, final float h);
    void setW(final float w);
    void setH(final float h);

    void setOrigin(final float x, final float y);
    void setRotation(final float r);
    void setScale(final float x, final float y);
    void setScaleX(final float x);
    void setScaleY(final float y);
}
