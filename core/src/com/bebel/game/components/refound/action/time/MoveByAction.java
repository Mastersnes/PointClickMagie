package com.bebel.game.components.refound.action.time;

import com.badlogic.gdx.utils.Align;

public class MoveByAction extends RelativeAction {
    private float amountX, amountY;
    private int alignment = Align.bottomLeft;

    protected void updateRelative (float percentDelta) {
        target.translate(amountX * percentDelta, amountY * percentDelta, alignment);
    }

    public void setAmount (float x, float y) {
        amountX = x;
        amountY = y;
    }

    public float getAmountX () {
        return amountX;
    }

    public void setAmountX (float x) {
        amountX = x;
    }

    public float getAmountY () {
        return amountY;
    }

    public void setAmountY (float y) {
        amountY = y;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public int getAlignment() {
        return alignment;
    }
}
