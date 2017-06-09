package ep2;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author ziegler
 */
public class Alien extends GameObject {
    private int type;
    private int life;

    public Alien(int posx, int posy, int type, int speedX, int speedY) {
        super(posx, posy);
        this.speedX = speedX;
        this.speedY = speedY;
        this.positionX = ThreadLocalRandom.current().nextInt(0, 1728 - this.width);
        this.type = type;
        loadAlien(type);
    }

    public void loadAlien(int type) {
        switch (type) {
            case 1:
                setLife(1);
                loadImage("images/alien_EASY.png");
                break;
            case 2:
                setLife(2);
                loadImage("images/alien_MEDIUM.png");
                break;
            case 3:
                setLife(3);
                loadImage("images/alien_HARD.png");
                break;
            default:
                break;
        }
    }
    
    public void explode(){
        loadImage("images/explosion0.png");
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void move() {
        // Limits the movement of the Alien to the side edges.
        if ((speedX < 0 && positionX <= 0) || (speedX > 0 && positionX + width >= Game.getWidth())) {
            speedX *= -1;
        }

        // Moves the Alien on the horizontal axis
        positionX += speedX;
        positionY += speedY;
        // Limits the movement of the Alien to the vertical edges.
        if ((speedY < 0 && positionY <= 0) || (speedY > 0 && positionY >= Game.getHeight())) {
            setVisible(false);
        }
    }
}
