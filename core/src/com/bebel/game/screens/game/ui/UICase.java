package com.bebel.game.screens.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.bebel.game.components.refound.abstrait.AbstractGroup;

public class UICase extends AbstractGroup {
    public static final float TAILLE = 80;

    @Override
    public UICase create() {
        setName("Case d'inventaire");
        setBounds(0, 0, TAILLE, TAILLE);

        debug(Color.RED.cpy());

        return this;
    }

    @Override
    protected void actComponent(float delta) {
    }

    @Override
    public void makeComponentEvents() {
    }

    @Override
    public void resetComponent() {
    }
}
