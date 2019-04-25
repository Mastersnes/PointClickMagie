package com.bebel.game.components.refound.action.time;

public abstract class RelativeAction extends TimeAction {
    private float lastPercent;

    protected void begin () {
        lastPercent = 0;
    }

    protected void update (float percent) {
        updateRelative(percent - lastPercent);
        lastPercent = percent;
    }

    protected abstract void updateRelative (float percentDelta);
}
