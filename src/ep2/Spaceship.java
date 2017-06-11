package ep2;

import java.util.List;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Spaceship extends GameObject {

    private static final int MAX_SPEED_X = 7;
    private static final int MAX_SPEED_Y = 6;
    private int level;
    private int collectedGears;
    private int collectedGems;
    private int life;
    private Long score = new Long(0);
    private boolean enable = true;
    private List<LaserBeam> laser;
    private File beamSound = new File("sounds/alienBeam.wav");
    private File levelUpSound = new File("sounds/levelUpSound.wav");
    private AudioStream levelUpSoundAudio;
    private InputStream levelUpSoundInput;
    private AudioStream beamSoundAudio;
    private InputStream beamSoundInput;

    public Spaceship(int positionX, int positionY) {
        super(positionX, positionY);
        laser = new ArrayList<>();
        this.level = 1;
        this.life = 3;
        this.speedX = 0;
        this.speedY = 0;
        this.collectedGears = 0;
        this.collectedGems = 0;
        loadSpaceShip();
    }

    public void initSound() {
        try {
            levelUpSoundInput = new FileInputStream(levelUpSound);
            levelUpSoundAudio = new AudioStream(levelUpSoundInput);
        } catch (IOException e) {
            System.out.println("Exception: Can't instantiate sound file object!");
        }
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
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

    public int getCollectedGems() {
        return collectedGems;
    }

    public void setCollectedGems(int collectedGems) {
        this.collectedGems = collectedGems;
    }
    
    public List<LaserBeam> getLaser() {
        return laser;
    }

    public void loadSpaceShip() {
        if (this.collectedGears < 5) {
            this.level = 1;
            loadImage("images/spaceship1.png");
        } 
        else if (this.collectedGears >= 5 && this.collectedGears < 10) {
            if (enable) {
                AudioPlayer.player.start(levelUpSoundAudio);
                enable = false;
            }
            this.level = 2;
            loadImage("images/spaceship2.png");
        } 
        else {
            if (!enable) {
                AudioPlayer.player.start(levelUpSoundAudio);
                enable = true;
            }
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
                this.laser.add(new LaserBeam(this.positionX + width / 2 - 5, this.positionY -15, 8, 1));
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
