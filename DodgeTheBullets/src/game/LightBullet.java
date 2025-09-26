package game;

import java.awt.Graphics;

// This class represents a specific type of bullet
// It inherits Bullet’s methods
// It also extends Polygon
public class LightBullet extends Polygon implements Bullet {

    /**
     * Inner class to simplify code for bullets
     */
    class BulletUtilities {
        /**
         * Method to randomize spawn point for a LightBullet
         * @return Point
         */
        public Point getRandomSpawn() {
            // Randomize spawn point across screen width
            int randomSpawnX = (int) (Math.random() * (1920 / 2 + 1));
            int randomSpawnY = (int) (Math.random() * (-9));

            return new Point(randomSpawnX, randomSpawnY);
        }

        /**
         * Sets bullet position based on input Point
         * @param p Point where we want Bullet to move
         */
        public void setBulletPos(Point p) {
            position.setX(p.getX());
            position.setY(p.getY());
        }

        /**
         * Checks if bullet has crossed screen from the bottom
         * @return boolean
         */
        public boolean hasCrossedBoundary() {
            return position.getY() >= 1080 / 2;
        }
    }

    int damage;
    public BulletUtilities util;

    // Initialization block for damage
    {
        damage = 10;
    }

    /**
     * Constructor to setup the bullet utilities and shape of the bullet
     * @param points Shape of the bullet
     * @param position Where it should be
     * @param rotation How much it should be rotated
     */
    public LightBullet(Point[] points, Point position, double rotation) {
        super(points, position, rotation);
        util = new BulletUtilities();
    }

    /**
     * Paints the bullets to the screen
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

        brush.fillPolygon(xCoords, yCoords, points.length);
    }

    /**
     * Sets starting position of bullet and
     * determines their speed
     */
    public void move() {
        int moveSpeed = 3;
        position.setY(position.getY() + moveSpeed);
    }

    /**
     * Checks for collision
     * @param player The player which the bullet has collided with
     * @return true if collision has occurred, otherwise false
     */
    public boolean hasHit(Polygon player) {
        for (Point vertex : player.getPoints()) {
            if (this.contains(vertex)) {
                System.out.println("We've hit the player.");
                return true;
            }
        }
        return false;
    }

    /**
     * Returns damage value associated with this bullet
     * @return integer as damage value
     */
    public int getDamage() {
        return damage;
    }
}