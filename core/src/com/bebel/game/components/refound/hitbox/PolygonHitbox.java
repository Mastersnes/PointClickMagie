package com.bebel.game.components.refound.hitbox;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;

public class PolygonHitbox extends Polygon implements IHitbox {
    private float w, h;

    public PolygonHitbox() {
    }
    public PolygonHitbox(final float x, final float y, final float w, final float h) {
        set(x, y, w, h);
    }

    @Override
    public void draw(final ShapeRenderer shapes, final Color color) {
        shapes.setColor(color);
        shapes.polygon(getTransformedVertices());
    }

    @Override
    public void set(float x, float y, float w, float h) {
        this.w = w; this.h = h;
        setVertices(new float[] {0,0, w,0, w,h, 0,h, 0,0});
        setPosition(x, y);
    }

    @Override
    public void setX(float x) {
        setPosition(x, getY());
    }

    @Override
    public void setY(float y) {
        setPosition(getX(), y);
    }

    @Override
    public void setSize(float w, float h) {
        set(getX(), getY(), w, h);
    }

    @Override
    public void setW(float w) {
        setSize(w, h);
    }

    @Override
    public void setH(float h) {
        setSize(w, h);
    }

    @Override
    public void setScaleX(float x) {
        setScale(x, getScaleY());
    }

    @Override
    public void setScaleY(float y) {
        setScale(getX(), y);
    }
}
