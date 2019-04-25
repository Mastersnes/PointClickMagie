package com.bebel.game.components.refound.action.ordonnancement;

import com.bebel.game.components.refound.action.AbstractAction;

public class SequenceAction extends ParallelAction {
    protected int index;

    @Override
    public boolean act(float delta) {
        if (finish) return true;
        final AbstractAction action = actions.get(index);
        if (action.act(delta)) index++;

        finish = false;
        if (index >= actions.size()) finish = true;
        return finish;
    }

    @Override
    public void reset() {
        super.reset();
        index = 0;
    }

    @Override
    public void restart() {
        super.restart();
        index = 0;
    }
}
