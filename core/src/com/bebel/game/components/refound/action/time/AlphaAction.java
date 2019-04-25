package com.bebel.game.components.refound.action.time;

public class AlphaAction extends TimeAction {
    private float start, end;
    protected void begin () {
        start = target.getColor().a;
    }

    protected void update (float percent) {
        target.setAlpha(start + (end - start) * percent);
    }

    public void reset () {
        super.reset();
    }

    public float getAlpha () {
        return end;
    }

    public void setAlpha (float alpha) {
        this.end = alpha;
    }
}
