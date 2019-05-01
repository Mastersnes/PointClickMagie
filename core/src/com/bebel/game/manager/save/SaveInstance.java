package com.bebel.game.manager.save;

/**
 * Represente les données de jeu
 */
public class SaveInstance {
    private Player player = new Player();


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
