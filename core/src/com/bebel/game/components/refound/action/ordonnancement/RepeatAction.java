package com.bebel.game.components.refound.action.ordonnancement;

public class RepeatAction extends DelegatedAction {
    static public final int FOREVER = -1;

    private int repeatCount, executedCount;
    private boolean finished;

    public boolean act (float delta) {
        if (executedCount == repeatCount) return true;
        if (action.act(delta)) {
            if (finished) return true;
            if (repeatCount > 0) executedCount++;
            if (executedCount == repeatCount) return true;
            if (action != null) action.restart();
        }
        return false;
    }

    /** Causes the action to not repeat again. */
    @Override
    public void stop () {
        finished = true;
    }

    @Override
    public void restart () {
        executedCount = 0;
        finished = false;
    }

    /** Sets the number of times to repeat. Can be set to {@link #FOREVER}. */
    public void setCount (int count) {
        this.repeatCount = count;
    }

    public int getCount () {
        return repeatCount;
    }
}
