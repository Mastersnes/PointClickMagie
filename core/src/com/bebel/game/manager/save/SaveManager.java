package com.bebel.game.manager.save;

import com.badlogic.gdx.Gdx;

/**
 * Manager de sauvegarde
 */
public class SaveManager {
    private static SaveManager instance;

    private SaveManager() {
        loadAll();
    }

    public static synchronized SaveManager getInstance() {
        if (instance == null) {
            instance = new SaveManager();
        }
        return instance;
    }

    public void loadAll()  {
    }

    public String loadOrCreate(final String type) {
            Gdx.app.log("SaveManager", "Chargement de la sauvegarde " + type);


            return null;
    }

    public void save(final String saveInstance) {

    }
}
