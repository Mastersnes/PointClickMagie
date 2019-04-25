package com.bebel.game.components.refound.runnable;

import com.bebel.game.components.refound.action.Actions;
import com.bebel.game.components.refound.action.ordonnancement.RunnableAction;

/**
 * Runnable avec condition de fin
 * FINISH DOIT ETRE APPELER A LA FIN DE L'OPERATION !
 */
public abstract class FinishRunnable implements Runnable {
    protected boolean finish = false;

    public RunnableAction finish() {
        return finish(null);
    }
    public RunnableAction finish(final Runnable callback) {
        return Actions.run(() -> {
            finish = true;
            if (callback != null) callback.run();
        });
    }

    /**
     * Verifie si l'operation est terminée
     * @return
     */
    public boolean isFinish() {
        return finish;
    }
    public void restart() {finish = false;}
}