package com.bebel.game.components.refound.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bebel.game.components.interfaces.Animable;
import com.bebel.game.components.refound.runnable.OneTimeRunnable;

import java.util.HashMap;
import java.util.Map;

import static com.bebel.game.components.refound.runnable.OneTimeRunnable.oneTime;


public class Animate extends Image implements Animable {
    protected Map<Integer, OneTimeRunnable> runByFrame = new HashMap<>();
    protected OneTimeRunnable onFinishRun;

    protected float speed, progress;
    protected Animation<TextureRegion> animation;
    protected boolean pause, hideOnFinish;

    public void init(final String key, final Animation.PlayMode playMode, final float fps) {
        setName(key);
        setTouchable(false);
        animation = manager.getAnimation(key, playMode, fps);
        speed = 1f;
        pause();
    }

    @Override
    public void setSpeed(final float speed) {
        this.speed = speed;
    }

    @Override
    public void pause() {
        pause = true;
    }

    @Override
    public void resume() {
        pause = false;
    }

    /**
     * On lance en forcant le flip
     */
    public void play() {
        play(false, false);
    }
    public void play(final boolean flipH, final boolean flipV) {
        start();
        setFlip(flipH, flipV);
    }

    /**
     * On relance sans forcer le flip
     */
    @Override
    public void restart() {
        restart(false, false);
    }
    public void restart(final boolean flipH, final boolean flipV) {
        start();
        flip(flipH, flipV);
    }

    protected void start() {
        progress = 0f;
        pause = false;
        for (final OneTimeRunnable run : runByFrame.values()) run.restart();
        if (onFinishRun != null) onFinishRun.restart();
    }

    @Override
    public void stop() {
        progress = 0f;
        pause = true;
    }

    @Override
    protected void actComponent(float delta) {
        super.actComponent(delta);

        if (isFinish()) return;

        if (!pause) {
            progress += speed * Gdx.graphics.getDeltaTime();
            // On rattrape si on a depasser
            if (isFinish()) finish();

            final int frameIndex = animation.getKeyFrameIndex(progress);
            final TextureRegion frame = animation.getKeyFrame(progress);

            setRegion(frame);

            final OneTimeRunnable run = runByFrame.get(frameIndex);
            if (run != null)run.run();

            if (isFinish() && onFinishRun != null) onFinishRun.run();
        }
    }

    @Override
    public void draw(Batch batch, float alphaModulation) {
        if (isFinish() && hideOnFinish) {
            setTexture(null);
            return;
        }
        super.draw(batch, alphaModulation);
    }

    @Override
    public void makeComponentEvents() {

    }

    @Override
    public void resetComponent() {
        progress = 0f;
        pause = false;
        animation = null;
        setFlip(false, false);
        runByFrame.clear();
        onFinishRun = null;
    }

    @Override
    public void finish() {
        progress = animation.getKeyFrames().length * animation.getFrameDuration();
    }

    @Override
    public boolean isFinish() {
        return progress >= animation.getKeyFrames().length * animation.getFrameDuration();
    }

    public boolean isWorking() {
        return !pause && !isFinish();
    }

    @Override
    public void onFinish(final Runnable run) {
        onFinishRun = oneTime(run);
    }

    public void onFrame(final int frame, final Runnable runnable) {
        runByFrame.put(frame, oneTime(runnable));
    }

    public void hideOnFinish() {
        this.hideOnFinish = true;
    }
}