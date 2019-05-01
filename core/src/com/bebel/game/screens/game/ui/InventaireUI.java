package com.bebel.game.screens.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.bebel.game.components.refound.abstrait.AbstractGroup;

import java.util.ArrayList;
import java.util.List;

import static com.bebel.game.utils.ElementFactory.group;

public class InventaireUI extends AbstractGroup {
    private final float NB_CASES = 10;
    private final float MARGE = 10;
    private final List<UICase> cases = new ArrayList<>();

    @Override
    public InventaireUI create() {
        setName("Barre d'inventaire");
        setPosition(MARGE, MARGE);
        setSize(NB_CASES * (UICase.TAILLE + MARGE-1), parent.getHeight()-2*MARGE);

        createCases();

        debug(Color.BLUE.cpy());

        return this;
    }

    /**
     * Creer les cases de l'inventaire
     */
    private void createCases() {
        for (int i=0; i<=NB_CASES-1; i++) {
            final UICase uiCase = add(group(UICase.class));
            float marge = MARGE;
            if (i==0) marge = 0;
            uiCase.setPosition(i*(UICase.TAILLE + marge), 0);
            cases.add(uiCase);
        }
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
