package com.bebel.game.manager.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.bebel.game.model.lexique.Items;
import com.bebel.game.model.lexique.Sorts;
import com.bebel.game.utils.SecurityUtils;

/**
 * Manager de sauvegarde
 */
public class SaveManager {
    private static final String BASE_PATH = "magix/save/magix";
    public static final String EXT = ".save";
    private static SaveManager instance;

    private Json json = new Json();
    private SaveInstance current;

    private SaveManager() {
    }

    public static synchronized SaveManager getInstance() {
        if (instance == null) {
            instance = new SaveManager();
        }
        return instance;
    }

    public SaveInstance getCurrent() {
        if (current == null) loadOrCreate();
        return current;
    }

    /**
     * Charge la partie indiquée en memoire. Si elle n'existe pas on la creer
     */
    public SaveInstance loadOrCreate() {
        Gdx.app.log("SaveManager", "Chargement de la sauvegarde ");

        final FileHandle file = Gdx.files.external(BASE_PATH + EXT);
        if (file.exists()) {
            final String content = SecurityUtils.decrypt(file.readString(), "");
            current = json.fromJson(SaveInstance.class, content);
        }
        else {
            current = create();
            save(current);
        }

        return current;
    }

    public void save(final SaveInstance save) {
        final FileHandle file = Gdx.files.external(BASE_PATH + EXT);
        final String content = SecurityUtils.encrypt(json.toJson(save));
        file.writeString(content, false);
    }

    /**
     * Initialisation d'une nouvelle partie
     * @return
     */
    private SaveInstance create() {
        final SaveInstance instance = new SaveInstance();
        final Player player = instance.getPlayer();
        player.getInventaire().add(Items.CARTE);
        player.getInventaire().add(Items.ORNA);
        player.getSorts().add(Sorts.PERSUASION);
        return instance;
    }
}
