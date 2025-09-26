package game;

import java.awt.Color;
import java.awt.Graphics;
import game.Polygon;

/**
 * This class represents the Alien enemy
 * Handles its movement
 * And its damage functions as well
 *
 */
public class Alien extends Polygon {

    // Initialization block for damage
    int damage;

    {
        damage = 20;
    }

    /**
     * Sets up Alien object
     * @param points ArrayList for the ships boundaries
     * @param position Ships starting position
     * @param rotation Rotation angle of the object
     * @param type Type of alien
     */
    public Alien(Point[] points, Point position, double rotation, String type) {
        super(points, position, rotation);
    }

    /**
     * Paints the aliens to the screen
     * @param brush Using brush to paint/display
     */
    public void paint(Graphics brush) {
        Point[] points = this.getPoints();

        int[] xCoords = new int[points.length];
        int[] yCoords = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            xCoords[i] = (int) points[i].getX();
            yCoords[i] = (int) points[i].getY();
        }

        brush.setColor(Color.YELLOW);
        brush.fillPolygon(xCoords, yCoords, points.length);

    }

    /**
     * Sets starting position of aliens and
     * determines their speed
     */
    public void move() {
        int moveSpeed = 5;
        position.setY(position.getY() + moveSpeed);

        // Starting positions
        if (position.getY() > 1080) {
            position.setY(-10);
            position.setX(Math.random() * 2000);
        }
    }

    /**
     * Checks for collision
     * @param player The player which the alien has collided with
     * @return true if collision has occurred, otherwise false
     */
    public boolean hasHit(Polygon player) {
        for (Point vertex : player.getPoints()) {
            // For each vertex in the shape, see if this bullet contains it
            if (this.contains(vertex)) {
                System.out.println("We've hit the player.");
                return true;
            }
        }

        return false;
    }

    /**
     * Returns damage value associated with this alien
     * @return integer as damage value
     */
    public int getDamage() {
        return damage;
    }

}
