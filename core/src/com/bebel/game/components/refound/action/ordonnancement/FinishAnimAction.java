package com.bebel.game.components.refound.action.ordonnancement;

import com.bebel.game.components.refound.action.AbstractAction;
import com.bebel.game.components.refound.element.Animate;

/**
 * Action lancant une animation et attendant sa fin
 */
public class FinishAnimAction extends AbstractAction {
    private Animate animation;
    private boolean flipH;
    private boolean flipV;
    boolean ran;

    public boolean act (final float delta) {
        if (animation == null) return true;
        if (!ran) {
            ran = true;
            animation.play(flipH, flipV);
        }
        return animation.isFinish();
    }

    @Override
    public void stop() {
        ran = true;
        animation.pause();
}

    public void restart () {
        ran = false;
        flipH = false; flipV = false;
        if (animation != null) animation.stop();
    }

    public void reset () {
        super.reset();
        ran = false;
        flipH = false; flipV = false;
        animation = null;
    }

    public void init (final Animate animation, final boolean flipH, final boolean flipV) {
        this.animation = animation;
        this.flipH = flipH;
        this.flipV = flipV;
    }
}