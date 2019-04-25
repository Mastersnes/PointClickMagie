package com.bebel.game.components.refound.event;

import com.bebel.game.components.refound.event.callbacks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Manager permettant de gerer les evenements
 */
public class EventCatcher {
    private Map<Events, List<EventCallback>> events = new HashMap<>();

    public void add(final Events type, final EventCallback callback) {
        events.computeIfAbsent(type, c -> new ArrayList<>())
            .add(callback);
    }

    public void clear(final Events type) {
        if (events.get(type) == null) return;
        events.get(type).clear();
    }
    public void clear() {
        events.clear();
    }

    public void fire(final Events type) {
        if (events.get(type) == null) return;
        for (final EventCallback callback : events.get(type)) {
            if (callback instanceof GeneralCallback)
                ((GeneralCallback) callback).run(Mouse.getInstance(), Keyboard.getInstance());
        }
    }

    public void fireKeyUp(final Events type, final int keycode) {
        if (events.get(type) == null) return;
        for (final EventCallback callback : events.get(type)) {
            if (callback instanceof KeyUpCallback)
                ((KeyUpCallback) callback).run(Mouse.getInstance(), Keyboard.getInstance(), keycode);
        }
    }

    public void fireType(final Events type, final char character) {
        if (events.get(type) == null) return;
        for (final EventCallback callback : events.get(type)) {
            if (callback instanceof KeyTypeCallback)
                ((KeyTypeCallback) callback).run(Mouse.getInstance(), Keyboard.getInstance(), character);
        }
    }

    public void fireTouchUp(final Events type, final int pointer, final int button) {
        if (events.get(type) == null) return;
        for (final EventCallback callback : events.get(type)) {
            if (callback instanceof MouseUpCallback)
                ((MouseUpCallback) callback).run(Mouse.getInstance(), Keyboard.getInstance(), pointer, button);
        }
    }

    public void fireScroll(final Events type, final float amount) {
        if (events.get(type) == null) return;
        for (final EventCallback callback : events.get(type)) {
            if (callback instanceof ScrollCallback)
                ((ScrollCallback) callback).run(Mouse.getInstance(), Keyboard.getInstance(), amount);
        }
    }
}
