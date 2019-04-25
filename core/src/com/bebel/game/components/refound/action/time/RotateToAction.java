package com.bebel.game.components.refound.action.time;

import com.badlogic.gdx.math.MathUtils;

public class RotateToAction extends TimeAction {
    private float start, end;
    private boolean useShortestDirection = false;

    public RotateToAction () {
    }

    /** @param useShortestDirection Set to true to move directly to the closest angle */
    public RotateToAction (boolean useShortestDirection) {
        this.useShortestDirection = useShortestDirection;
    }

    @Override
    protected void begin () {
        start = target.getRotation();
    }

    @Override
    protected void update (float percent) {
        if (useShortestDirection)
            target.setRotation(MathUtils.lerpAngleDeg(this.start, this.end, percent));
        else
            target.setRotation(start + (end - start) * percent);
    }

    public float getRotation () {
        return end;
    }

    public void setRotation (float rotation) {
        this.end = rotation;
    }

    public boolean isUseShortestDirection () {
        return useShortestDirection;
    }

    public void setUseShortestDirection (boolean useShortestDirection) {
        this.useShortestDirection = useShortestDirection;
    }
}
