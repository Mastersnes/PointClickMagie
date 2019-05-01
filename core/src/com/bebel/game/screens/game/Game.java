package com.bebel.game.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.bebel.game.LaunchGame;
import com.bebel.game.components.refound.abstrait.AbstractScreen;
import com.bebel.game.screens.game.ui.UI;

import java.util.Arrays;
import java.util.List;

import static com.bebel.game.utils.ElementFactory.*;

public class Game extends AbstractScreen {
    private UI ui;

    public Game(final LaunchGame game) {
        super(game, false);
    }

    @Override
    public Game create() {
        add(ui = group(UI.class));
        return this;
    }

    @Override
    public void makeComponentEvents() {
        onKeyhold((mouse, keyboard) -> {
            if (keyboard.hold(Input.Keys.ESCAPE)) {
                Gdx.app.exit();
                return;
            }
        });

        onKeydown((mouse, keyboard) -> {
            if (keyboard.press(Input.Keys.F1)) {
                manager.conf.setFullscreen(!manager.conf.isFullscreen());
            }
        });
    }

    @Override
    public void resetComponent() {
    }

    @Override
    protected String context() {
        return "game";
    }

    @Override
    protected List<String> nextScreens() {
        return Arrays.asList();
    }
}
