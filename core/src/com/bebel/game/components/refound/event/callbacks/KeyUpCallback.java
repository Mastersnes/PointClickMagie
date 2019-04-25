package com.bebel.game.components.refound.event.callbacks;

import com.bebel.game.components.refound.event.Keyboard;
import com.bebel.game.components.refound.event.Mouse;

/**
 * Represente un callback lié à une touche levée du clavier
 */
public interface KeyUpCallback extends EventCallback {
    void run(final Mouse mouse, final Keyboard keyboard, final int keyUp);
}
