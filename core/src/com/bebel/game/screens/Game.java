package com.bebel.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.bebel.game.LaunchGame;
import com.bebel.game.components.refound.abstrait.AbstractScreen;
import com.bebel.game.components.refound.element.Image;
import com.bebel.game.components.refound.element.Text;
import com.bebel.game.manager.resources.ScreensManager;

import java.util.Arrays;
import java.util.List;

import static com.bebel.game.utils.ElementFactory.image;
import static com.bebel.game.utils.ElementFactory.text;

public class Game extends AbstractScreen {
    private Text text;

    public Game(final LaunchGame game) {
        super(game);
    }

    @Override
    public void create() {
        add(text = text("label.delete"));
        text.center();
        text.setColor(Color.RED.cpy());

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

        text.onTouchdown((mouse, keyboard) -> {
            if (!mouse.left()) return;
            ScreensManager.getInstance().switchTo(Menu.class);
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
