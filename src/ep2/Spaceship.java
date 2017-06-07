package ep2;

import java.util.List;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Spaceship extends GameObject {

    private static final int MAX_SPEED_X = 7;
    private static final int MAX_SPEED_Y = 6;
    private int level;
    private int collectedGears;
    private int life;
    private int score;
    private List<LaserBeam> laser;

    public Spaceship(int positionX, int positionY) {
        super(positionX, positionY);
        laser = new ArrayList<>();
        this.level = 1;
        this.life = 3;
        this.speedX = 0;
        this.speedY = 0;
        this.score = 0;
        this.collectedGears = 0;
        loadSpaceShip();
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int shipLevel) {
        this.level = shipLevel;
    }

    public int getCollectedGears() {
        return collectedGears;
    }

    public void setCollectedGears(int collectedGears) {
        this.collectedGears = collectedGears;
    }

    public List<LaserBeam> getLaser() {
        return laser;
    }

    public void loadSpaceShip() {
        if (this.collectedGears < 5) {
            this.level = 1;
            loadImage("images/spaceship1.png");
        } else if (this.collectedGears >= 5 && this.collectedGears < 10) {
            this.level = 2;
            loadImage("images/spaceship2.png");
        } else {
            this.level = 3;
            loadImage("images/spaceship3.png");
        }
    }

    public void move() {
        // Limits the movement of the spaceship to the side edges.
        if ((speedX < 0 && positionX <= 0) || (speedX > 0 && positionX + width >= Game.getWidth())) {
            speedX = 0;
        }

        // Moves the spaceship on the horizontal axis
        positionX += speedX;

        // Limits the movement of the spaceship to the vertical edges.
        if ((speedY < 0 && positionY <= 0) || (speedY > 0 && positionY + height + 50 >= Game.getHeight())) {
            speedY = 0;
        }

        // Moves the spaceship on the verical axis
        positionY += speedY;
    }

    public void shoot() {
        switch (level) {
            case 1:
                this.laser.add(new LaserBeam(this.positionX + width / 2, this.positionY, 8, 1));
                break;
            case 2:
                this.laser.add(new LaserBeam(this.positionX + 20, this.positionY, 8, 2));
                this.laser.add(new LaserBeam(this.positionX + width - 20, this.positionY, 8, 2));
                break;
            case 3:
                this.laser.add(new LaserBeam(this.positionX + 30, this.positionY, 8, 3));
                this.laser.add(new LaserBeam(this.positionX + width - 30, this.positionY, 8, 3));
                this.laser.add(new LaserBeam(this.positionX + width / 2, this.positionY, 8, 3));
                break;
        }
    }

    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                speedX = -1 * MAX_SPEED_X;
                break;

            case KeyEvent.VK_RIGHT:
                speedX = MAX_SPEED_X;
                break;

            case KeyEvent.VK_UP:
                speedY = -1 * MAX_SPEED_Y;
                break;

            case KeyEvent.VK_DOWN:
                speedY = MAX_SPEED_Y;
                break;

            case KeyEvent.VK_SPACE:
                shoot();
                break;

            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                speedX = 0;
                break;
            case KeyEvent.VK_RIGHT:
                speedX = 0;
                break;
            case KeyEvent.VK_UP:
                speedY = 0;
                break;
            case KeyEvent.VK_DOWN:
                speedY = 0;
                break;
            case KeyEvent.VK_SPACE:
                break;
            default:
                break;
        }
    }
}
