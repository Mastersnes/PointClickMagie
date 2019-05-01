package com.bebel.game.components.refound.abstrait;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.bebel.game.components.refound.action.AbstractAction;
import com.bebel.game.components.refound.event.EventCatcher;
import com.bebel.game.components.refound.event.Events;
import com.bebel.game.components.refound.event.callbacks.*;
import com.bebel.game.components.refound.hitbox.IHitbox;
import com.bebel.game.components.refound.hitbox.PolygonHitbox;
import com.bebel.game.manager.resources.AssetsManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.badlogic.gdx.Input.Keys.CONTROL_LEFT;
import static com.badlogic.gdx.Input.Keys.SHIFT_LEFT;
import static com.badlogic.gdx.utils.Align.*;
import static com.bebel.game.components.refound.action.Actions.*;
import static com.bebel.game.components.refound.event.Events.*;
import static com.bebel.game.utils.Constantes.GAME_HEIGHT;
import static com.bebel.game.utils.Constantes.GAME_WIDTH;

public abstract class AbstractComponent extends Sprite implements Pool.Poolable {
    private Vector2 tmp = new Vector2();
    protected EventCatcher events = new EventCatcher(this);
    protected String name;
    protected AbstractGroup parent;
    protected AssetsManager manager;
    protected IHitbox hitbox;
    protected Color debugColor;
    protected List<AbstractAction> actions = new ArrayList<>();

    protected boolean touchable, visible, focus, hover;
    protected boolean debug;

    public AbstractComponent() {
        super();
        manager = AssetsManager.getInstance();
        initComponent();
    }

    public void initComponent() {
        hitbox = new PolygonHitbox();
        setName("");
        setBounds(0, 0, GAME_WIDTH, GAME_HEIGHT);
        touchable = visible = true;
        debug = false;
        focus = false;
        events.clear();
        debugColor = Color.GREEN.cpy();
        setColor(Color.WHITE.cpy());
        parent = null;
        setOriginCenter();
    }

    public void act(final float delta) {
        for (final Iterator<AbstractAction> iterator = actions.iterator(); iterator.hasNext();) {
            final AbstractAction action = iterator.next();
            if (action.act(delta)) {
                Pools.free(action);
                iterator.remove();
            }
        }
        actComponent(delta);
    }

    protected abstract void actComponent(float delta);

    public void makeEvents() {
        onTouchdown((mouse, keyboard) -> {
            if (mouse.right() && keyboard.hold(CONTROL_LEFT)) {
                debug = !debug;
                if (debug) setFocus(isHover());
            }
        });

        onEnter((mouse, keyboard)-> {
            if (debug) setFocus(true);
        });
        onExit((mouse, keyboard)-> {
            if (debug) setFocus(false);
        });

        onKeyhold((mouse, keyboard)-> {
            if (!debug  || !focus) return;
            if (keyboard.left()) {
                if (keyboard.hold(CONTROL_LEFT)) rotate(-0.5f);
                else translateX(-0.5f);
            }
            else if (keyboard.right()) {
                if (keyboard.hold(CONTROL_LEFT)) rotate(0.5f);
                else translateX(0.5f);
            }
            if (keyboard.up()) {
                if (keyboard.hold(CONTROL_LEFT)) scale(0.1f);
                else translateY(0.5f);
            }
            else if (keyboard.down()) {
                if (keyboard.hold(CONTROL_LEFT)) scale(-0.1f);
                else translateY(-0.5f);
            }
            parent.stopPropagation();
        });
        onScroll((mouse, keyboard, amount)-> {
            if (!debug || !focus) return;
            if (amount > 0) scale(0.05f);
            else scale(-0.05f);
        });
        onDrag((mouse, keyboard) -> {
            if (!debug || !focus) return;
            if (mouse.left()) {
                translate(mouse.deltaX(), -mouse.deltaY());
            }
        });
        onKeydown((mouse, keyboard) -> {
            if (keyboard.press(SHIFT_LEFT)) {
                Gdx.app.debug(getName(), "Rotation: " + getRotation());
                Gdx.app.debug(getName(), "Scale: " + getScaleX() + ", " + getScaleY());
                Gdx.app.debug(getName(), "Position: " + getX() + ", " + getY());
            }
            parent.stopPropagation();
        });

        makeComponentEvents();
    }

    public abstract void makeComponentEvents();
    public abstract void resetComponent();

    public boolean hit(final float x, final float y, final boolean checkTouchable) {
        if (checkTouchable && !this.touchable) return false;
        if (!visible) return false;
        return hitbox.contains(x, y);
    }

    /**
     * Transforme les coordonnées indiqué dans le referentiel de l'element
     * Utilisé pour situer la souris au sein d'un element
     * @param parentCoords
     * @return
     */
    public Vector2 parentToLocalCoordinates (final Vector2 parentCoords) {
        final float rotation = this.getRotation();
        final float scaleX = this.getScaleX();
        final float scaleY = this.getScaleY();
        final float childX = getX();
        final float childY = getY();
        if (rotation == 0) {
            if (scaleX == 1 && scaleY == 1) {
                parentCoords.x -= childX;
                parentCoords.y -= childY;
            } else {
                final float originX = this.getOriginX();
                final float originY = this.getOriginY();
                parentCoords.x = (parentCoords.x - childX - originX) / scaleX + originX;
                parentCoords.y = (parentCoords.y - childY - originY) / scaleY + originY;
            }
        } else {
            final float cos = (float)Math.cos(rotation * MathUtils.degreesToRadians);
            final float sin = (float)Math.sin(rotation * MathUtils.degreesToRadians);
            final float originX = this.getOriginX();
            final float originY = this.getOriginY();
            final float tox = parentCoords.x - childX - originX;
            final float toy = parentCoords.y - childY - originY;
            parentCoords.x = (tox * cos + toy * sin) / scaleX + originX;
            parentCoords.y = (tox * -sin + toy * cos) / scaleY + originY;
        }
        return parentCoords;
    }

    public void remove() {
        Pools.free(this);
        if (parent != null) {
            parent.remove(this);
            parent = null;
        }
    }

    @Override
    public void draw(final Batch batch, final float alphaModulation) {
        if (getTexture() != null) super.draw(batch, alphaModulation);
    }

    protected void drawDebug(final ShapeRenderer shapes) {
        if (this.debug) {
            hitbox.draw(shapes, debugColor);
        }
    }

    //--- Getter/Setter
    protected void setParent(final AbstractGroup parent) {
        this.parent = parent;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
        if (focus && parent != null) parent.setFocus(true);
    }

    public boolean isTouchable() {
        return touchable;
    }

    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }

    public void debug(final Color color) {
        this.debugColor.set(color);
        debug = true;
    }
    public void debug() {
        debug(Color.GREEN.cpy());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void front() {
        setZ(1000000);
    }

    public void back() {
        setZ(0);
    }

    public int getZ() {
        return parent != null ? parent.children.indexOf(this, true) : 0;
    }

    public void setZ(final int _z) {
        if (parent == null) return;
        int z = _z;
        z = z < 0 ? 0 : z;
        z = z >= parent.children.size ? parent.children.size - 1 : z;

        parent.children.insert(z, this);
    }

    public void center() {
        if (parent == null) return;
        setCenter(parent.getWidth() / 2, parent.getHeight() / 2);
    }

    @Override
    public void setOriginCenter() {
        super.setOriginCenter();
        originChanged();
    }

    //---- Changement d'etat
    public void sizeChanged() {
        hitbox.setSize(getWidth(), getHeight());
        setOriginCenter();
    }

    public void positionChanged() {
        hitbox.setPosition(getX(), getY());
    }

    public void scaleChanged() {
        hitbox.setScale(getScaleX(), getScaleY());
    }

    public void rotationChanged() {
        hitbox.setRotation(getRotation());
    }

    public void originChanged() {
        hitbox.setOrigin(getOriginX(), getOriginY());
    }

    @Override
    public void reset() {
        resetComponent();
        initComponent();
    }

    //---- Events
    public void onEnter(final GeneralCallback event) {
        events.add(ENTER, event);
    }
    public void onExit(final GeneralCallback event) {
        events.add(EXIT, event);
    }

    public void onKeyup(final KeyUpCallback event) {
        events.add(KEY_UP, event);
    }
    public void onKeytype(final KeyTypeCallback event) {
        events.add(KEY_TYPE, event);
    }
    public void onKeydown(final GeneralCallback event) {
        events.add(KEY_DOWN, event);
    }
    public void onKeyhold(final GeneralCallback event) {
        events.add(KEY_HOLD, event);
    }

    public void onTouchdown(final GeneralCallback event) {
        events.add(TOUCH_DOWN, event);
    }
    public void onDrag(final GeneralCallback event) {
        events.add(TOUCH_DRAG, event);
    }
    public void onMouseMove(final GeneralCallback event) {
        events.add(MOVE, event);
    }
    public void onTouchup(final MouseUpCallback event) {
        events.add(TOUCH_UP, event);
    }
    public void onScroll(final ScrollCallback event) {
        events.add(SCROLL, event);
    }

    //- Event Fire
    protected boolean fireKey(final Events type) {
        return events.fire(type);
    }
    protected boolean fireKeyUp(final int keycode) {
        return events.fireKeyUp(KEY_UP, keycode);
    }
    protected boolean fireType(final char character) {
        return events.fireType(KEY_TYPE, character);
    }

    protected boolean fireMove(final Events type, final Vector2 oldMouse) {
        return events.fire(type);
    }
    protected boolean fireTouch(final Events type) {
        return events.fire(type);
    }
    protected boolean fireTouchUp(final int pointer, final int button) {
        return events.fireTouchUp(TOUCH_UP, pointer, button);
    }
    protected boolean fireScroll(final float amount) {
        return events.fireScroll(SCROLL, amount);
    }

    public Vector2 realign(final float xAmount, final float yAmount, final int align) {
        float x = xAmount;
        float y = yAmount;
        float pW = GAME_WIDTH, pH = GAME_HEIGHT;
        if (parent != null) {
            pW = parent.getWidth() * parent.getScaleX();
            pH = parent.getHeight() * parent.getScaleY();
        }
        float aW = getWidth() * getScaleX();
        float aH = getHeight() * getScaleY();

        if ((align & right) != 0) x = pW - aW - xAmount;
        if ((align & top) != 0) y = pH - aH - yAmount;
        return tmp.set(x, y);
    }

    // --- Gestion align
    public void setPosition(final float xAmount, final float yAmount, final int align) {
        realign(xAmount, yAmount, align);
        super.setPosition(tmp.x, tmp.y);
        positionChanged();
    }

    public void translate(float xAmount, float yAmount, int align) {
        tmp.set(xAmount, yAmount);
        if ((align & right) != 0) tmp.x = -tmp.x;
        if ((align & bottom) != 0) tmp.y = -tmp.y;
        super.translate(tmp.x, tmp.y);
        positionChanged();
    }

    // --- Override
    public AssetsManager getManager() {
        return manager;
    }

    @Override
    public void translateX(float xAmount) {
        super.translateX(xAmount);
        positionChanged();
    }

    @Override
    public void translateY(float yAmount) {
        super.translateY(yAmount);
        positionChanged();
    }

    @Override
    public void translate(float xAmount, float yAmount) {
        super.translate(xAmount, yAmount);
        positionChanged();
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        setBounds(x, y, width, height, bottomLeft);
    }
    public void setBounds(float x, float y, float width, float height, int align) {
        setSize(width, height);
        setPosition(x, y, align);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        sizeChanged();
    }

    public void rotateBy(final float degrees) {
        setRotation(getRotation() + degrees);
    }

    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        rotationChanged();
    }

    @Override
    public void rotate(float degrees) {
        super.rotate(degrees);
        rotationChanged();
    }

    @Override
    public void rotate90(boolean clockwise) {
        super.rotate90(clockwise);
        rotationChanged();
    }

    @Override
    public void setScale(float scaleXY) {
        super.setScale(scaleXY);
        scaleChanged();
    }

    public void addScale(float x, float y) {
        setScale(getScaleX() + x, getScaleY() + y);
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
        super.setScale(scaleX, scaleY);
        scaleChanged();
    }

    @Override
    public void scale(float amount) {
        super.scale(amount);
        scaleChanged();
    }

    @Override
    public void setOrigin(float originX, float originY) {
        super.setOrigin(originX, originY);
        originChanged();
    }

    @Override
    public void setOriginBasedPosition(float x, float y) {
        super.setOriginBasedPosition(x, y);
        originChanged();
    }

    //- Actions
    public List<AbstractAction> getActions() {
        return actions;
    }

    public void addSequence(final AbstractAction... actions) {
        addAction(sequence(actions));
    }
    public void addParallel(final AbstractAction... actions) {
        addAction(parallel(actions));
    }
    public void addAction(final AbstractAction action) {
        action.setTarget(this);
        this.actions.add(action);
    }
    public void runAction(final Runnable r) {
        addAction(run(r));
    }

    //- Logs
    public void error(final String message, final Throwable e) {
        Gdx.app.error(getName(), message, e);
    }
    public void debug(final String message) {
        Gdx.app.debug(getName(), message);
    }
    public void debug(final String message, final Throwable e) {
        Gdx.app.debug(getName(), message, e);
    }
    public void log(final String message) {
        Gdx.app.log(getName(), message);
    }
}
