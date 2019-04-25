package com.bebel.game.manager.resources;

import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;
import java.util.MissingResourceException;

/**
 * Manager de langue
 */
public class LanguageManager {
    private AssetsManager parent;

    public LanguageManager(final AssetsManager manager) {
        parent = manager;
        load(parent.conf.getLanguage());
    }

    /**
     * Permet de charger le nouveau language
     *
     * @param newLanguage
     */
    public void load(final String newLanguage) {
        parent.conf.setLanguage(newLanguage);

        if (parent.isLoaded("i18n/i18n")) parent.unload("i18n/i18n");
        parent.load("i18n/i18n", I18NBundle.class,
                new I18NBundleLoader.I18NBundleParameter(new Locale(parent.conf.getLanguage()), "UTF-8"));

        parent.finishLoading();

        // On remet le langage finalement trouvé
        parent.conf.setLanguage(getI18n().getLocale().getLanguage());
    }

    /**
     * Permet de recuperer le texte dans la bonne langue
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        try {
            return getI18n().get(key);
        } catch (final MissingResourceException e) {
            return key;
        }
    }
    public I18NBundle getI18n() {
        return parent.get("i18n/i18n", I18NBundle.class);
    }

}
