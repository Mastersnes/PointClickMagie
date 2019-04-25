package com.bebel.game.components.refound.runnable;

/**
 * Runnable ne pouvant etre appelé qu'une fois
 */
public class OneTimeRunnable implements Runnable {
    protected boolean alreadyCall = false;
    protected Runnable runnable;

    public static synchronized OneTimeRunnable oneTime(final Runnable runnable) {
        final OneTimeRunnable oneTime = new OneTimeRunnable();
        oneTime.setRunnable(runnable);
        return oneTime;
    }

    @Override
    public void run() {
        if (runnable != null && !alreadyCall) {
            alreadyCall = true;
            runnable.run();
        }
    }

    public void setRunnable(final Runnable runnable) {
        this.runnable = runnable;
        alreadyCall = false;
    }

    public void restart() {
        alreadyCall = false;
    }
}