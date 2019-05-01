package com.bebel.game.components.refound.event;

import com.bebel.game.components.refound.abstrait.AbstractComponent;
import com.bebel.game.components.refound.event.callbacks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Manager permettant de gerer les evenements
 */
public class EventCatcher {
    private final AbstractComponent target;
    private Map<Events, List<EventCallback>> events = new HashMap<>();

    public EventCatcher(final AbstractComponent target) {
        this.target = target;
    }

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

    public boolean fire(final Events type) {
        if (events.get(type) == null || events.get(type).isEmpty()) return false;
        for (final EventCallback callback : events.get(type)) {
            if (callback instanceof GeneralCallback)
                ((GeneralCallback) callback).run(Mouse.getInstance(), Keyboard.getInstance());
        }
        return true;
    }

    public boolean fireKeyUp(final Events type, final int keycode) {
        if (events.get(type) == null || events.get(type).isEmpty()) return false;
        for (final EventCallback callback : events.get(type)) {
            if (callback instanceof KeyUpCallback)
                ((KeyUpCallback) callback).run(Mouse.getInstance(), Keyboard.getInstance(), keycode);
        }
        return true;
    }

    public boolean fireType(final Events type, final char character) {
        if (events.get(type) == null || events.get(type).isEmpty()) return false;
        for (final EventCallback callback : events.get(type)) {
            if (callback instanceof KeyTypeCallback)
                ((KeyTypeCallback) callback).run(Mouse.getInstance(), Keyboard.getInstance(), character);
        }
        return true;
    }

    public boolean fireTouchUp(final Events type, final int pointer, final int button) {
        if (events.get(type) == null || events.get(type).isEmpty()) return false;
        for (final EventCallback callback : events.get(type)) {
            if (callback instanceof MouseUpCallback)
                ((MouseUpCallback) callback).run(Mouse.getInstance(), Keyboard.getInstance(), pointer, button);
        }
        return true;
    }

    public boolean fireScroll(final Events type, final float amount) {
        if (events.get(type) == null || events.get(type).isEmpty()) return false;
        for (final EventCallback callback : events.get(type)) {
            if (callback instanceof ScrollCallback)
                ((ScrollCallback) callback).run(Mouse.getInstance(), Keyboard.getInstance(), amount);
        }
        return true;
    }
}
