package com.bebel.game.components.refound.action.time;

public class ScaleByAction extends RelativeAction {
    private float amountX, amountY;

    protected void updateRelative (float percentDelta) {
        target.addScale(amountX * percentDelta, amountY * percentDelta);
    }

    public void setAmount (float x, float y) {
        amountX = x;
        amountY = y;
    }

    public void setAmount (float scale) {
        amountX = scale;
        amountY = scale;
    }

    public float getAmountX () {
        return amountX;
    }

    public void setAmountX (float x) {
        this.amountX = x;
    }

    public float getAmountY () {
        return amountY;
    }

    public void setAmountY (float y) {
        this.amountY = y;
    }
}
