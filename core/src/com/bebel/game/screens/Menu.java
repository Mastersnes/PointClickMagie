package com.bebel.game.screens;

import com.bebel.game.LaunchGame;
import com.bebel.game.components.refound.abstrait.AbstractScreen;
import com.bebel.game.manager.resources.ScreensManager;
import com.bebel.game.manager.save.SaveManager;
import com.bebel.game.screens.game.Game;

import java.util.Arrays;
import java.util.List;

public class Menu extends AbstractScreen {
    public Menu(final LaunchGame game) {
        super(game, false);
    }

    @Override
    public Menu create() {
        SaveManager.getInstance().loadOrCreate();
        ScreensManager.getInstance().switchTo(Game.class);
        return this;
    }

    @Override
    public void makeComponentEvents() {
    }

    @Override
    public void resetComponent() {
    }

    @Override
    protected String context() {
        return "menu";
    }

    @Override
    protected List<String> nextScreens() {
        return Arrays.asList();
    }
}
