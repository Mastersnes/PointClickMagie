package com.bebel.game.components.refound.action.ordonnancement;

import com.bebel.game.components.refound.abstrait.AbstractComponent;
import com.bebel.game.components.refound.action.AbstractAction;

import java.util.ArrayList;
import java.util.List;

public class ParallelAction extends AbstractAction {
    protected boolean finish;
    protected final List<AbstractAction> actions = new ArrayList<>();

    @Override
    public boolean act(float delta) {
        if (finish) return finish;
        finish = true;
        for (final AbstractAction action : actions ) {
            if (!action.act(delta)) finish = false;
        }

        return finish;
    }

    @Override
    public void stop() {
        finish = true;
    }

    @Override
    public void restart() {
        finish = false;
        for (final AbstractAction action : actions ) {
            action.restart();
        }
    }

    @Override
    public void reset() {
        super.reset();
        finish = false;
        actions.clear();
    }

    @Override
    public void setTarget(AbstractComponent target) {
        super.setTarget(target);
        for (final AbstractAction action : actions) {
            action.setTarget(target);
        }
    }

    public void addActions(final AbstractAction... actions) {
        for (final AbstractAction action : actions) {
            action.setTarget(target);
            this.actions.add(action);
        }
    }
}
