package com.bebel.game.components.refound.action.ordonnancement;

import com.bebel.game.components.refound.action.AbstractAction;
import com.bebel.game.components.refound.runnable.FinishRunnable;

public class RunnableAction extends AbstractAction {
    protected boolean ran;
    protected Runnable runnable;

    @Override
    public boolean act(float delta) {
        if (!ran) {
            ran = true;
            runnable.run();
        }

        if (runnable instanceof FinishRunnable)
            return ((FinishRunnable) runnable).isFinish();
        return ran;
    }

    @Override
    public void stop() {
        ran = true;
    }

    @Override
    public void reset() {
        super.reset();
        ran = false;
        runnable = null;
    }

    @Override
    public void restart() {
        ran = false;
        if (runnable instanceof FinishRunnable)
            ((FinishRunnable) runnable).restart();
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public Runnable getRunnable() {
        return runnable;
    }
}
