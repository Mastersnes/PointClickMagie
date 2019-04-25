package com.bebel.game.components.interfaces;

/**
 * Represente une entité pouvant s'animer
 */
public interface Animable {
    /**
     * Programme une action à effectuer à la fin de l'animation
     * @param runnable
     */
    void onFinish(final Runnable runnable);

    /**
     * Pause l'animation en cours
     */
    void pause();

    /**
     * Enleve la pause
     */
    void resume();

    /**
     * Stop l'animation et la remet à 0
     */
    void stop();

    /**
     * redemarre l'animation au debut
     */
    void restart();

    /**
     * Envoi l'animation directement à la fin
     */
    void finish();

    /**
     * Indique si l'animation est finie
     * @return
     */
    boolean isFinish();

    /**
     * Definit la vitesse de l'animation
     * @param speed
     */
    void setSpeed(final float speed);
}