package com.bebel.game.components.refound.action.time;

public class RotateByAction extends RelativeAction {
    private float amount;

    protected void updateRelative(float percentDelta) {
        target.rotateBy(amount * percentDelta);
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float rotationAmount) {
        amount = rotationAmount;
    }
}
