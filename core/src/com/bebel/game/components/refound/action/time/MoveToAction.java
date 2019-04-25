package com.bebel.game.components.refound.action.time;

import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.utils.Align.bottomLeft;

public class MoveToAction extends TimeAction {
    private Vector2 start = new Vector2();
    private Vector2 end = new Vector2();
    private int align = bottomLeft;

    protected void begin () {
        start.set(target.getX(), target.getY());
        end.set(target.realign(end.x, end.y, align));
    }

    protected void update (float percent) {
        target.setPosition(start.x + (end.x - start.x) * percent, start.y + (end.y - start.y) * percent);
    }

    public void reset () {
        super.reset();
    }

    public void setStartPosition (float x, float y) {
        start.set(x, y);
    }

    public void setPosition (float x, float y) {
        end.set(x, y);
    }

    public void setPosition (float x, float y, int alignment) {
        this.align = alignment;
        setPosition(x, y);
    }

    public float getX () {
        return end.x;
    }

    public void setX (float x) {
        end.x = x;
    }

    public float getY () {
        return end.y;
    }

    public void setY (float y) {
        end.y = y;
    }
}
