package com.bebel.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.bebel.game.LaunchGame;
import com.bebel.game.components.refound.abstrait.AbstractScreen;
import com.bebel.game.components.refound.element.Image;
import com.bebel.game.manager.resources.ScreensManager;

import java.util.Arrays;
import java.util.List;

import static com.bebel.game.utils.ElementFactory.image;

public class Menu extends AbstractScreen {
    private Image img;

    public Menu(final LaunchGame game) {
        super(game, false, Color.WHITE.cpy());
    }

    @Override
    public void create() {
        add(img = image("general/quitter.png"));
        img.center();

        setFocus(true);
    }

    @Override
    public void makeComponentEvents() {
        onKeyhold((mouse, keyboard) -> {
            if (keyboard.hold(Input.Keys.ESCAPE)) {
                Gdx.app.exit();
                return;
            }
        });

        img.onTouchdown((mouse, keyboard) -> {
            if (!mouse.left()) return;
            ScreensManager.getInstance().switchTo(Game.class);
        });
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
