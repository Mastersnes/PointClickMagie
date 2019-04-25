package com.bebel.game.components.refound.event;

import com.badlogic.gdx.Gdx;

import static com.badlogic.gdx.Input.Keys.*;

/**
 * Permet de classer les differents outils utiles au clavier
 */
public class Keyboard {
    private static Keyboard instance;

    private Keyboard(){}

    public static Keyboard getInstance() {
        if (instance == null)
            instance = new Keyboard();
        return instance;
    }

    public boolean z() {return hold(Z);}
    public boolean q() {return hold(Q);}
    public boolean s() {return hold(S);}
    public boolean d() {return hold(D);}
    public boolean up() {return hold(UP);}
    public boolean down() {return hold(DOWN);}
    public boolean left() {return hold(LEFT);}
    public boolean right() {return hold(RIGHT);}

    public boolean hold(final int... keys) {
        for (final int key : keys) {
            if (!Gdx.input.isKeyPressed(key)) return false;
        }
        return true;
    }
    public boolean press(final int key) {return Gdx.input.isKeyJustPressed(key);}
}
