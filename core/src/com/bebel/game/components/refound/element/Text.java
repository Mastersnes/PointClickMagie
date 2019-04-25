package com.bebel.game.components.refound.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Pools;
import com.bebel.game.components.interfaces.Refreshable;
import com.bebel.game.components.refound.abstrait.AbstractComponent;

public class Text extends AbstractComponent implements Refreshable {
    private Matrix4 oldMatrix, transform;
    private BitmapFontCache cache;
    private GlyphLayout layout;

    public Text() {
        super();
    }

    public void init(final String key) {
        init(key, new BitmapFont());
    }
    public void init(final String key, final BitmapFont font) {
        init(key, font, Color.WHITE.cpy());
    }
    public void init(final String key, final BitmapFont font, final Color color) {
        setName(key);
        oldMatrix = new Matrix4();
        transform = new Matrix4();
        this.cache = font.newFontCache();
        this.layout = Pools.get(GlyphLayout.class).obtain();
        setColor(color);
        refresh();
    }

    @Override
    public void refresh() {
        layout.setText(cache.getFont(), manager.langue.get(getName()));
        setSize(layout.width, layout.height);
    }

    @Override
    public void draw(final Batch batch, final float alphaModulation) {
        super.draw(batch, alphaModulation);

        oldMatrix.set(batch.getTransformMatrix());
        transform.set(oldMatrix.cpy());
        transform.rotate(0, 0, 1, getRotation());
        transform.trn(getX() + getOriginX(), getY() + getOriginY(), 0);

        batch.setTransformMatrix(transform);

        cache.setText(layout, 0, 0);
        cache.tint(getColor());
        cache.setPosition(-getOriginX(), -getOriginY() + getHeight());
        cache.draw(batch, alphaModulation);

        batch.setTransformMatrix(oldMatrix);
    }

    @Override
    public void scaleChanged() {
        if (cache == null) return;
        cache.getFont().getData().setScale(getScaleX(), getScaleY());
        refresh();
    }

    @Override
    protected void actComponent(float delta) {
    }

    @Override
    public void makeComponentEvents() {
    }

    @Override
    public void resetComponent() {
        Pools.free(layout);
    }
}
