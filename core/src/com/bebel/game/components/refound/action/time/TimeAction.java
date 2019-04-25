package com.bebel.game.components.refound.action.time;

import com.badlogic.gdx.math.Interpolation;
import com.bebel.game.components.refound.action.AbstractAction;

public abstract class TimeAction extends AbstractAction {
    private float duration, time;
    private Interpolation interpolation;
    private boolean reverse, began, complete;

    public TimeAction() {
    }

    public TimeAction(float duration) {
        this.duration = duration;
    }

    public TimeAction(float duration, Interpolation interpolation) {
        this.duration = duration;
        this.interpolation = interpolation;
    }

    @Override
    public boolean act(float delta) {
        if (complete) return true;
            if (!began) {
                begin();
                began = true;
            }
            time += delta;
            complete = time >= duration;
            float percent = 1;
            if (!complete) {
                percent = time / duration;
                if (interpolation != null) percent = interpolation.apply(percent);
            }
            update(reverse ? 1 - percent : percent);
            if (complete) end();
            return complete;
    }

    protected void begin () {
    }

    protected void end () {
    }

    protected abstract void update (final float percent);

    @Override
    public void stop() {
        time = duration;
    }

    @Override
    public void reset() {
        super.reset();
        reverse = false;
        interpolation = null;
    }

    @Override
    public void restart() {
        time = 0;
        began = false;
        complete = false;
    }

    public void setTime(float time) {
        this.time = time;
    }
    public float getTime() {
        return time;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public float getDuration() {
        return duration;
    }
    public Interpolation getInterpolation () {
        return interpolation;
    }

    public void setInterpolation (Interpolation interpolation) {
        this.interpolation = interpolation;
    }

    public boolean isReverse () {
        return reverse;
    }

    public void setReverse (boolean reverse) {
        this.reverse = reverse;
    }
}
