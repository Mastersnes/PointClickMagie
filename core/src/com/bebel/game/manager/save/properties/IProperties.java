package com.bebel.game.manager.save.properties;

import com.bebel.game.utils.SmartProperties;

/**
 * interface d'ecriture et lecture de properties
 */
public interface IProperties<SAVE> {
    SAVE loadData(final SmartProperties prop);
    void saveData(final SmartProperties prop, final SAVE save);
}
