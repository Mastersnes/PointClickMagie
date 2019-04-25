package com.bebel.game.components.refound.event.callbacks;

import com.bebel.game.components.refound.event.Keyboard;
import com.bebel.game.components.refound.event.Mouse;

/**
 * Represente un callback d'evenement
 */
public interface GeneralCallback extends EventCallback {
    void run(final Mouse mouse, final Keyboard keyboard);
}
