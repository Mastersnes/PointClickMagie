package com.bebel.game.manager.save;

import com.bebel.game.model.lexique.Items;
import com.bebel.game.model.lexique.Sorts;

import java.util.ArrayList;
import java.util.List;

/**
 * Represente les données du joueur
 */
public class Player {
    private List<Items> inventaire = new ArrayList<>();
    private List<Sorts> sorts = new ArrayList<>();


    public List<Items> getInventaire() {
        return inventaire;
    }
    public void setInventaire(List<Items> inventaire) {
        this.inventaire = inventaire;
    }
    public List<Sorts> getSorts() {
        return sorts;
    }
    public void setSorts(List<Sorts> sorts) {
        this.sorts = sorts;
    }
}
