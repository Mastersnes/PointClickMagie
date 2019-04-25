package com.bebel.game.components.refound.action.ordonnancement;

import com.bebel.game.components.refound.abstrait.AbstractComponent;
import com.bebel.game.components.refound.action.AbstractAction;

public abstract class DelegatedAction extends AbstractAction {
    protected AbstractAction action;

    @Override
    public void reset() {
        super.reset();
        action = null;
    }

    public void setAction(final AbstractAction action) {
        this.action = action;
    }

    public AbstractAction getAction() {
        return action;
    }

    @Override
    public void setTarget(AbstractComponent target) {
        super.setTarget(target);
        if (action != null) action.setTarget(target);
    }
}
