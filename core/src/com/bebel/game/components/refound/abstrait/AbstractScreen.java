package com.bebel.game.components.refound.abstrait;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bebel.game.LaunchGame;
import com.bebel.game.components.refound.event.Mouse;
import com.bebel.game.manager.resources.AssetsManager;

import java.util.Iterator;
import java.util.List;

import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.Input.Keys.ANY_KEY;
import static com.bebel.game.components.refound.event.Events.*;
import static com.bebel.game.utils.Constantes.GAME_HEIGHT;
import static com.bebel.game.utils.Constantes.GAME_WIDTH;

public abstract class AbstractScreen extends AbstractGroup implements Screen, InputProcessor {
    protected final LaunchGame game;
    public static Viewport viewport;
    protected final AssetsManager manager;
    protected ShapeRenderer debugShape = new ShapeRenderer();
    protected boolean firstTime = true;

    /**
     * Indique si la scene doit etre reinit à chaque changement d'ecran
     */
    protected boolean renew;
    protected final Color backColor;

    public AbstractScreen(final LaunchGame game) {
        this(game, true);
    }
    public AbstractScreen(final LaunchGame game, final boolean renew) {
        this(game, renew, Color.BLACK.cpy());
    }
    public AbstractScreen(final LaunchGame game, final boolean renew, final Color backColor) {
        this.game = game;
        this.renew = renew;
        this.backColor = backColor;
        final OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT);
        viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, camera);
        manager = AssetsManager.getInstance();
        debugShape.setAutoShapeType(true);

        // Chargement des prochains ecrans
        for (final String next : nextScreens()) {
            manager.loadContext(next);
        }
    }

    @Override
    public void show() {
        setFocus(true);
        Gdx.input.setInputProcessor(this);
        manager.finishLoading(context());
        if (renew || firstTime) {
            firstTime = false;
            create();
            makeGroupEvents();
        }
    }

    @Override
    public void render(final float delta) {
        final Color back = backColor;
        Gdx.gl.glClearColor(back.r, back.g, back.b, back.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.getCamera().update();
        if (visible) {
            game.batch.setProjectionMatrix(viewport.getCamera().combined);
            game.batch.begin();
            draw(game.batch, 1);
            game.batch.end();

            Gdx.gl.glEnable(GL20.GL_BLEND);
            debugShape.setProjectionMatrix(viewport.getCamera().combined);
            debugShape.begin();
            drawDebug(debugShape);
            debugShape.end();

            if (input.isKeyPressed(ANY_KEY)) keyHold();
        }

        actGroup(delta);
    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height, true);
    }

    @Override
    protected void actComponent(float delta) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        setFocus(false);
        hideWithRenew(renew);
    }
    public void hideWithRenew(final boolean renew) {
        Gdx.input.setInputProcessor(null);
        if (renew) {
            manager.unloadContext(context());
            reset();
        }
    }

    @Override
    public void dispose() {
        hideWithRenew(true);
        Gdx.app.log(context(), "DISPOSE");
    }

    @Override
    public void makeEvents() {
        makeComponentEvents();
    }

    @Override
    public boolean keyDown(final int keycode) {
        fireKey(KEY_DOWN);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        fireKeyUp(keycode);
        return true;
    }

    public boolean keyHold() {
        fireKey(KEY_HOLD);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        fireType(character);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!isInsideViewport(screenX, screenY)) return false;
        fireTouch(TOUCH_DOWN);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!isInsideViewport(screenX, screenY)) return false;
        fireTouchUp(pointer, button);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!isInsideViewport(screenX, screenY)) return false;
        final Mouse mouse = Mouse.getInstance();
        final Vector2 oldMouse = mouse.cpy();
        mouse.set(screenX, screenY);
        mouse.set(viewport.unproject(mouse));

        fireMove(TOUCH_DRAG, oldMouse);

        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (!isInsideViewport(screenX, screenY)) return false;
        final Mouse mouse = Mouse.getInstance();
        mouse.set(screenX, screenY);
        mouse.set(viewport.unproject(mouse));

        fireMove(MOVE, mouse.cpy());
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        final Mouse mouse = Mouse.getInstance();
        if (!isInsideViewport(mouse.x, mouse.y)) return false;
        fireScroll(amount);
        return true;
    }

    protected boolean isInsideViewport (final float screenX, float screenY) {
        int x0 = viewport.getScreenX();
        int x1 = x0 + viewport.getScreenWidth();
        int y0 = viewport.getScreenY();
        int y1 = y0 + viewport.getScreenHeight();
        screenY = Gdx.graphics.getHeight() - 1 - screenY;
        return screenX >= x0 && screenX < x1 && screenY >= y0 && screenY < y1;
    }

    protected abstract String context();
    protected abstract List<String> nextScreens();
}
