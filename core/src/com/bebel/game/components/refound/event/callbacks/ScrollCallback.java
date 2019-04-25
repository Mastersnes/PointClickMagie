package com.bebel.game.components.refound.event.callbacks;

import com.bebel.game.components.refound.event.Keyboard;
import com.bebel.game.components.refound.event.Mouse;

/**
 * Represente un callback lié à un evenement de scroll
 */
public interface ScrollCallback extends EventCallback {
    void run(final Mouse mouse, final Keyboard keyboard, final float amount);
}
