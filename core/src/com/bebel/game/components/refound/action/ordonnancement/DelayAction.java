package com.bebel.game.components.refound.action.ordonnancement;

public class DelayAction extends DelegatedAction {
    private float duration, time;

    public DelayAction() {
    }

    public DelayAction(float duration) {
        this.duration = duration;
    }

    public boolean act(float delta) {
        if (time < duration) {
            time += delta;
            if (time < duration) return false;
            delta = time - duration;
        }
        if (action == null) return true;
        return action.act(delta);
    }

    @Override
    public void stop() {
        time = duration;
    }

    @Override
    public void restart() {
        time = 0;
    }

    /**
     * Gets the time spent waiting for the delay.
     */
    public float getTime() {
        return time;
    }

    /**
     * Sets the time spent waiting for the delay.
     */
    public void setTime(float time) {
        this.time = time;
    }

    public float getDuration() {
        return duration;
    }

    /**
     * Sets the length of the delay in seconds.
     */
    public void setDuration(float duration) {
        this.duration = duration;
    }
}
