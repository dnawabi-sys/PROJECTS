package game;

import game.Polygon;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import game.Point;

/**
 * This class represents the player in the game
 * Its movement functions and health management
 */
public class Ship extends Polygon {

    boolean right, left;
    boolean isDead;

    {
        right = false;
        left = false;
        isDead = false;
    }

    // Initialization block for hp
    int hp;

    {
        hp = 250;
    }

    /**
     * Constructor which calls the super constructor
     * And sets up the ship's polygon
     * @param points ArrayList for the ships boundaries
     * @param position Ships starting position
     * @param rotation rotation Rotation angle of the object
     */
    public Ship(Point[] points, Point position, double rotation) {
        super(points, position, rotation);
    }

    /**
     * Paints the ship to the screen
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

        int n = xCoords.length + yCoords.length;

        brush.fillPolygon(xCoords, yCoords, points.length);
    }

    public void keyTyped(KeyEvent e) {
    } // Dummy for interface

    /**
     * Moves the ship across the screen
     * Based on booleans modified by keyboard events
     * And also makes it rotate a certain amount by default
     */
    public void move() {
        int moveSpeed = 2;
        int degreesPerFrame = 2;

        rotate(degreesPerFrame);

        if (right) {
            position.setX(position.getX() + moveSpeed);
        } else if (left) {
            position.setX(position.getX() - moveSpeed);
        }

    }

    /**
     * Gets HP value default for player
     * @return The current HP as an integer
     */
    public int getHP() {
        return hp;
    }

    /**
     * Method to modify the current HP of the player
     * @param val Negative integer for damage and positive for healing
     */
    public void setHP(int val) {
        // If damage is dealt, val is negative
        // If healing, val is positive
        hp += val;
    }

    /**
     * Checks whether the player's HP is depleted
     * @return Returns true if less than or equal to 0
     */
    public boolean isDead() {
        if (hp <= 0)
            return true;

        return false;
    }
}
