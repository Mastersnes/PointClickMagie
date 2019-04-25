package com.bebel.game.components.refound.action.time;

import com.badlogic.gdx.graphics.Color;

public class ColorAction extends TimeAction {
    private Color start = new Color();
    private final Color end = new Color();

    protected void begin () {
        start.set(target.getColor().cpy());
    }

    protected void update (float percent) {
        float r = start.r + (end.r - start.r) * percent;
        float g = start.g + (end.g - start.g) * percent;
        float b = start.b + (end.b - start.b) * percent;
        float a = start.a + (end.a - start.a) * percent;
        target.setColor(r, g, b, a);
    }

    public void reset () {
        super.reset();
    }

    public Color getEndColor () {
        return end;
    }

    public void setEndColor (Color color) {
        end.set(color);
    }
}
