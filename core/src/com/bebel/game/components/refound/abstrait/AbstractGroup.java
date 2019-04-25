package com.bebel.game.components.refound.abstrait;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.SnapshotArray;
import com.bebel.game.components.interfaces.Refreshable;

public abstract class AbstractGroup extends AbstractComponent implements Refreshable {
    final SnapshotArray<AbstractComponent> children = new SnapshotArray(true, 1, AbstractComponent.class);

    public void makeGroupEvents() {
        for (final AbstractComponent child : children.begin()) {
            if (child == null) continue;
            if (child instanceof AbstractGroup) ((AbstractGroup) child).makeGroupEvents();
            else child.makeEvents();
        }
        children.end();
        makeEvents();
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        for (final AbstractComponent child : children.begin()) {
            if (child == null) continue;
            if (!child.visible) continue;
            child.translate(getX(), getY());
            child.draw(batch, parentAlpha);
            child.translate(-getX(), -getY());
        }
        children.end();
    }

    @Override
    public void drawDebug(final ShapeRenderer shapes) {
        for (final AbstractComponent child : children.begin()) {
            if (child == null) continue;
            child.drawDebug(shapes);
        }
        children.end();
    }

    @Override
    public void refresh() {
        for (final AbstractComponent child : children.begin()) {
            if (child == null) continue;
            if (child instanceof Refreshable) ((Refreshable) child).refresh();
        }
        children.end();
    }

    public void actGroup(final float delta) {
        for (final AbstractComponent child : children.begin()) {
            if (child == null) continue;
            if (child instanceof AbstractGroup) ((AbstractGroup) child).actGroup(delta);
            else child.act(delta);
        }
        children.end();
        act(delta);
    }

    public void remove(final AbstractComponent child) {
        Pools.free(child);
        children.removeValue(child, true);
        child.setParent(null);
    }
    public <COMPONENT extends AbstractComponent> COMPONENT add(final COMPONENT child) {
        children.add(child);
        child.setParent(this);
        return child;
    }

    @Override
    public void reset() {
        Pools.freeAll(children);
        children.clear();
    }
}
