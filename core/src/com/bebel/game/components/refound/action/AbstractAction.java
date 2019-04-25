package com.bebel.game.components.refound.action;

import com.badlogic.gdx.utils.Pool;
import com.bebel.game.components.refound.abstrait.AbstractComponent;

public abstract class AbstractAction implements Pool.Poolable {
    protected AbstractComponent target;

    /**
     * @param delta
     * @return true if finish
     */
    public abstract boolean act(final float delta);
    public abstract void stop();

    /**
     * Utilisé pour le repeat
     */
    public abstract void restart();

    public void reset() {
        target = null;
    }

    public void setTarget(final AbstractComponent target) {
        this.target = target;
    }

    public AbstractComponent getTarget() {
        return target;
    }

}
