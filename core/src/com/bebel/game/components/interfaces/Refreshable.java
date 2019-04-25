package com.bebel.game.components.interfaces;

import com.bebel.game.manager.resources.AssetsManager;

/**
 * Represente une entitée necessitant d'etre raffraichit
 */
public interface Refreshable {
    void refresh();
    AssetsManager getManager();
}