package com.bebel.game.components.refound.abstrait;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.SnapshotArray;
import com.bebel.game.components.interfaces.Refreshable;
import com.bebel.game.components.refound.event.Events;

import java.util.Iterator;

import static com.bebel.game.components.refound.event.Events.*;

public abstract class AbstractGroup extends AbstractComponent implements Refreshable {
    private final Affine2 worldTransform = new Affine2();
    private final Matrix4 computedTransform = new Matrix4();
    private final Matrix4 oldTransform = new Matrix4();
    private boolean needTransform = false;
    private Vector2 localMouse = new Vector2();

    protected boolean stopPropagation = false;

    protected final SnapshotArray<AbstractComponent> children = new SnapshotArray(true, 1, AbstractComponent.class);

    /**
     * Procedure de creation, se retourne soit même
     * Appelé automatiquement à l'ajout du groupe
     * @param <COMPONENT>
     * @return
     */
    public abstract <COMPONENT extends AbstractGroup> COMPONENT create();

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
        super.draw(batch, parentAlpha);

        if (needTransform) {
            oldTransform.set(batch.getTransformMatrix());
            batch.setTransformMatrix(computeTransform());
        }
        for (final AbstractComponent child : children.begin()) {
            if (child == null) continue;
            if (!child.visible) continue;

            if (needTransform) {
                child.draw(batch, parentAlpha);
                batch.flush();
            }else { // Si le group n'a pas subit de rotation ou d'agrandissement, on ne le transforme pas
                child.translate(getX(), getY());
                child.draw(batch, parentAlpha);
                child.translate(-getX(), -getY());
            }
        }
        children.end();
        if (needTransform) batch.setTransformMatrix(oldTransform);
    }

    @Override
    public void drawDebug(final ShapeRenderer shapes) {
        super.drawDebug(shapes);

        if (needTransform) {
            oldTransform.set(shapes.getTransformMatrix());
            shapes.setTransformMatrix(computeTransform());
        }
        for (final AbstractComponent child : children.begin()) {
            if (child == null) continue;
            if (!child.visible) continue;

            if (needTransform) {
                child.drawDebug(shapes);
                shapes.flush();
            }else { // Si le group n'a pas subit de rotation ou d'agrandissement, on ne le transforme pas
                child.translate(getX(), getY());
                child.drawDebug(shapes);
                child.translate(-getX(), -getY());
            }
        }
        children.end();
        if (needTransform) shapes.setTransformMatrix(oldTransform);
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

        if (child instanceof AbstractGroup)
            ((AbstractGroup) child).create();
        return child;
    }

    @Override
    public void reset() {
        Pools.freeAll(children);
        children.clear();
    }

    //- Events
    @Override
    protected boolean fireKey(final Events type) {
        if (!isVisible() || !isTouchable() || !isFocus()) return false;
        boolean isScreen = this instanceof AbstractScreen;
        if (isScreen) super.fireKey(type);

        boolean trigger = false;
        stopPropagation = false;
        for (final Iterator<AbstractComponent> iterator = children.iterator(); iterator.hasNext();) {
            final AbstractComponent child = iterator.next();
            if (child.isVisible() && child.isTouchable() && child.isFocus()) {
                trigger = child.fireKey(type) || trigger;
            }
        }
        if (stopPropagation) return trigger;
        else if (!isScreen) return super.fireKey(type) || trigger;
        else return trigger;
    }

    @Override
    protected boolean fireKeyUp(final int keycode) {
        if (!isVisible() || !isTouchable() || !isFocus()) return false;
        boolean isScreen = this instanceof AbstractScreen;
        if (isScreen) super.fireKeyUp(keycode);

        boolean trigger = false;
        for (final Iterator<AbstractComponent> iterator = children.iterator(); iterator.hasNext();) {
            final AbstractComponent child = iterator.next();
            if (child.isVisible() && child.isTouchable() && child.isFocus()) {
                trigger = child.fireKeyUp(keycode) || trigger;
            }
        }
        if (!isScreen) return super.fireKeyUp(keycode) || trigger;
        else return trigger;
    }

    @Override
    protected boolean fireType(final char character) {
        if (!isVisible() || !isTouchable() || !isFocus()) return false;
        boolean isScreen = this instanceof AbstractScreen;
        if (isScreen) super.fireType(character);

        boolean trigger = false;
        for (final Iterator<AbstractComponent> iterator = children.iterator(); iterator.hasNext();) {
            final AbstractComponent child = iterator.next();
            if (child.isVisible() && child.isTouchable() && child.isFocus()) {
                trigger = child.fireType(character) || trigger;
            }
        }
        if (!isScreen) return super.fireType(character) || trigger;
        else return trigger;
    }

    @Override
    protected boolean fireMove(final Events type, final Vector2 mouse) {
        if (!isVisible() || !isTouchable()) return false;
        boolean isScreen = this instanceof AbstractScreen;
        if (isScreen) super.fireMove(type, mouse);

        parentToLocalCoordinates(localMouse.set(mouse.cpy()));

        boolean trigger = false;
        for (final Iterator<AbstractComponent> iterator = children.iterator(); iterator.hasNext();) {
            final AbstractComponent child = iterator.next();
            if (child.hit(localMouse.x, localMouse.y, true)) {
                trigger = child.fireMove(type, localMouse);
                if (!child.isHover()) {
                    child.setHover(true);
                    child.fireTouch(ENTER);
                }
            }else if (child.isHover()) {
                child.setHover(false);
                child.fireTouch(EXIT);
            }

            // A voir si on garde
            if (type == TOUCH_DRAG && trigger) return true;
        }
        if (!isScreen) return super.fireMove(type, localMouse) || trigger;
        else return trigger;
    }

    @Override
    protected boolean fireTouch(final Events type) {
        if (!isVisible() || !isTouchable()) return false;
        boolean isScreen = this instanceof AbstractScreen;
        if (isScreen) super.fireTouch(type);

        for (final Iterator<AbstractComponent> iterator = children.iterator(); iterator.hasNext();) {
            final AbstractComponent child = iterator.next();
            if (child.isHover()) {
                if (child.fireTouch(type)) return true;
            }
        }
        if (!isScreen) return super.fireTouch(type);
        else return false;
    }

    @Override
    protected boolean fireTouchUp(final int pointer, final int button) {
        if (!isVisible() || !isTouchable()) return false;
        boolean isScreen = this instanceof AbstractScreen;
        if (isScreen) super.fireTouchUp(pointer, button);

        for (final Iterator<AbstractComponent> iterator = children.iterator(); iterator.hasNext();) {
            final AbstractComponent child = iterator.next();
            if (child.isHover()) {
                if (child.fireTouchUp(pointer, button)) return true;
            }
        }
        if (!isScreen) return super.fireTouchUp(pointer, button);
        else return false;
    }

    @Override
    protected boolean fireScroll(final float amount) {
        if (!isVisible() || !isTouchable()) return false;
        boolean isScreen = this instanceof AbstractScreen;
        if (isScreen) super.fireScroll(amount);

        for (final Iterator<AbstractComponent> iterator = children.iterator(); iterator.hasNext();) {
            final AbstractComponent child = iterator.next();
            if (child.isHover()) {
                if (child.fireScroll(amount)) return true;
            }
        }
        if (!isScreen) return super.fireScroll(amount);
        else return false;
    }

    /**
     * Recupere les transformations du groupe sous forme d'une matrice
     * @return
     */
    protected Matrix4 computeTransform () {
        final Affine2 worldTransform = this.worldTransform;
        final float originX = getOriginX(), originY = getOriginY();
        worldTransform.setToTrnRotScl(getX() + originX, getY() + originY,
                getRotation(), getScaleX(), getScaleY());
        if (originX != 0 || originY != 0) worldTransform.translate(-originX, -originY);

        // Find the first parent that transforms.
        AbstractGroup parentGroup = parent;
        while (parentGroup != null) {
            if (parentGroup.needTransform) break;
            parentGroup = parentGroup.parent;
        }
        if (parentGroup != null) worldTransform.preMul(parentGroup.worldTransform);

        computedTransform.set(worldTransform);
        return computedTransform;
    }

    @Override
    public void rotationChanged() {
        super.rotationChanged();
        checkNeedTransform();
    }

    @Override
    public void scaleChanged() {
        super.scaleChanged();
        checkNeedTransform();
    }

    private void checkNeedTransform() {
        boolean rotationChanged = getRotation() != 0;
        boolean scaleChanged = (getScaleX() + getScaleY()) != 2;
        needTransform = rotationChanged || scaleChanged;
    }

    /**
     * Stop la propagation des keyhold
     */
    public void stopPropagation() {
        this.stopPropagation = true;
    }
}
