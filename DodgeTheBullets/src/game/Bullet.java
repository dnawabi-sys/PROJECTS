package game;

import java.awt.Graphics;

/**
 * This interface defines common methods for all enemies in the game
 * It takes care of movement, display, collisions, and damage taken.
 */
public interface Bullet {
    // Not sure if these are needed
    // But were useful common methods I found I needed to call
    // In the game loop
    public void move();

    /**
     * Paints the Bullet to the screen
     * @param brush The graphics object which handles the rendering
     */
    public void paint(Graphics brush);

    /**
     * Checks collision between Bullet and Player
     * @param player The player it wants to collide with
     * @return True if bullet vertices are contained in player
     */
    public boolean hasHit(Polygon player);

    /**
     * Returns damage associated with bullet
     * @return int Damage value of the bullet
     */
    public int getDamage();
}
