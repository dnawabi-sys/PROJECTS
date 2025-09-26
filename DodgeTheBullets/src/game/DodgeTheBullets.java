package game;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * This is the main class of the game itself which extends the Game class.
 * This class manages the game by handling key presses and display.
 */
class DodgeTheBullets extends Game {
    private int score = 0; // keeps track of score
    private String message = "";
    private long messageTime = 0;

    // Game components
    Ship player;
    ArrayList<Bullet> projectiles;
    ArrayList<Alien> aliens;
    ArrayList<Point> stars;

    static int counter = 0;

    /**
     * UI Manager Inner Class for displaying text utilities
     */
    class UIManager {
        Graphics screen;
        Font hp, lose;

        public UIManager(Graphics screen, Font hp, Font lose) {
            this.screen = screen;
            this.hp = hp;
            this.lose = lose;
        }

        public void displayStrAtPos(String fontType, String s, Color c, Point pos) {
            this.screen.setColor(c);
            if (fontType.toLowerCase().equals("hp")) {
                this.screen.setFont(hp);
            } else {
                this.screen.setFont(lose);
            }
            this.screen.drawString(s, (int) pos.getX(), (int) pos.getY());
        }
    }

    public DodgeTheBullets() {
        super("Dodge The Bullets!", 1920 / 2, 1080 / 2);

        setupGame();

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent k) {
                if (!player.isDead()) {
                    if (k.getKeyCode() == KeyEvent.VK_LEFT) player.left = true;
                    if (k.getKeyCode() == KeyEvent.VK_RIGHT) player.right = true;
                } else {
                    if (k.getKeyCode() == KeyEvent.VK_R) {
                        setupGame();
                        repaint();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent k) {
                if (k.getKeyCode() == KeyEvent.VK_LEFT) player.left = false;
                if (k.getKeyCode() == KeyEvent.VK_RIGHT) player.right = false;
            }

            public void keyTyped(KeyEvent e) {}
        });

        this.setFocusable(true);
        this.requestFocus();
    }

    private void setupGame() {
        score = 0;
        message = "";
        projectiles = new ArrayList<>();
        aliens = new ArrayList<>();

        Point[] points = { new Point(0, 0), new Point(15, 0), new Point(15, 15), new Point(0, 15) };
        Point bottomCenter = new Point(width / 2, (height / 3) * 2.7);
        player = new Ship(points, bottomCenter, 45);

        stars = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            stars.add(new Point(x, y));
        }

        Point[] bulletPoints = { new Point(0, 0), new Point(7, 15), new Point(15, 0) };
        for (int i = 0; i < 15; i++) {
            int randomSpawnX = (int) (Math.random() * (width - 20));
            int randomSpawnY = (int) (Math.random() * (-9));
            Point bulletSpawn = new Point(randomSpawnX, randomSpawnY);

            projectiles.add(new LightBullet(bulletPoints, bulletSpawn, 0));
        }

        Point[] alienPoints = { new Point(0, 0), new Point(20, 0), new Point(20, 20), new Point(0, 20) };
        for (int i = 0; i < 10; i++) {
            int randomSpawnX2 = (int) (Math.random() * (width - 50));
            int randomSpawnY2 = (int) (Math.random() * (-9));
            Point alienSpawn = new Point(randomSpawnX2, randomSpawnY2);
            aliens.add(new Alien(alienPoints, alienSpawn, 2, "default"));
        }
    }

    public void paint(Graphics brush) {
        Font hpFont = new Font("Inter", Font.BOLD, 24);
        Font lossFont = new Font("Inter", Font.BOLD, 48);
        UIManager uim = new UIManager(brush, hpFont, lossFont);

        brush.setColor(Color.black);
        brush.fillRect(0, 0, width, height);

        // draw stars
        brush.setColor(Color.white);
        for (Point star : stars) {
            brush.fillRect((int) star.getX(), (int) star.getY(), 2, 2);
        }

        if (!player.isDead()) {
            brush.setColor(Color.blue);

            projectiles.forEach(b -> {
                b.move();
                b.paint(brush);

                // Check if bullet crossed bottom
                if (((LightBullet) b).util.hasCrossedBoundary()) {
                    score++;
                    message = "Keep Going!";
                    messageTime = System.currentTimeMillis();

                    // Reset bullet
                    Point randomSpawn = ((LightBullet) b).util.getRandomSpawn();
                    ((LightBullet) b).util.setBulletPos(randomSpawn);
                }

                if (b.hasHit(player)) {
                    player.setHP(-b.getDamage());
                }
            });

            brush.setColor(Color.green);
            aliens.forEach(alien -> {
                alien.move();
                alien.paint(brush);

                if (alien.hasHit(player)) {
                    player.setHP(-alien.getDamage());
                }
            });

            // Draw HP
            uim.displayStrAtPos("hp", "Player HP: " + player.getHP(), Color.white, new Point(2, 30));

            // Draw Score
            uim.displayStrAtPos("hp", "Score: " + score, Color.yellow, new Point(2, 60));

            // Draw temporary message
            if (!message.isEmpty() && System.currentTimeMillis() - messageTime < 1000) {
                uim.displayStrAtPos("hp", message, Color.red, new Point(width / 2 - 80, height / 2));
            }

            brush.setColor(Color.red);
            player.move();
            player.paint(brush);
        } else {
            uim.displayStrAtPos("loss", "YOU LOSE", Color.white, new Point(width / 3, height / 2));
            uim.displayStrAtPos("loss", "PRESS R TO RESTART", Color.yellow, new Point(width / 4 - 30, height / 2 + 50));
        }
    }

    public static void main(String[] args) {
        DodgeTheBullets a = new DodgeTheBullets();
        a.repaint();
    }
}