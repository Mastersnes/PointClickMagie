package com.bebel.game.components.refound.event;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bebel.game.components.refound.abstrait.AbstractComponent;

import static com.badlogic.gdx.Input.Buttons.*;

/**
 * Permet de classer les differents outils utiles à la souris
 */
public class Mouse extends Vector2 {
    private static Mouse instance;

    private Mouse(){}

    public static Mouse getInstance() {
        if (instance == null) {
            instance = new Mouse();
        }
        return instance;
    }

    public float deltaX() {return Gdx.input.getDeltaX();}
    public float deltaY() {return Gdx.input.getDeltaY();}
    public float pressure() {return Gdx.input.getPressure();}
    public boolean touch() {return Gdx.input.isButtonPressed(LEFT) || Gdx.input.isTouched();}


    public boolean left() {return button(LEFT);}
    public boolean middle() {return button(MIDDLE);}
    public boolean right() {return button(RIGHT);}
    public boolean button(final int button) {return Gdx.input.isButtonPressed(button);}

    public boolean touch(final int pointer) {return Gdx.input.isTouched(pointer);}
    public boolean justTouch() {return Gdx.input.justTouched();}
}
