package com.bebel.game.screens.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.bebel.game.components.refound.abstrait.AbstractGroup;
import com.bebel.game.components.refound.element.Image;

import static com.badlogic.gdx.utils.Align.bottomLeft;
import static com.badlogic.gdx.utils.Align.bottomRight;
import static com.bebel.game.utils.Constantes.GAME_WIDTH;
import static com.bebel.game.utils.ElementFactory.group;
import static com.bebel.game.utils.ElementFactory.image;

public class UI extends AbstractGroup {
    private InventaireUI inventaire;
//    final SortsUI sorts;

    private Image switcher;

    @Override
    public UI create() {
        setName("UI");
        setBounds(0, 0, GAME_WIDTH, 100, bottomLeft);
        setRegion(manager.getTexture("ui/barre.jpg"));
        add(inventaire = group(InventaireUI.class));

        add(switcher = image("general/quitter.png"));
        switcher.setSize(50, 50);
        switcher.setPosition(10, 30, bottomRight);

        inventaire.setCenter((getWidth()-switcher.getWidth())/2, getHeight()/2);

        debug(Color.GREEN.cpy());

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
