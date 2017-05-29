package ep2;

/**
 *
 * @author ziegler
 */
public class Alien extends GameObject {

    private int type;

    public Alien(int posx, int posy, int type, int speedX) {
        super(posx, posy);
        this.speedX = speedX;
        setSpeedY(0);
        this.type = type;
        loadAlien(type);
    }

    public void loadAlien(int type) {
        switch (type) {
            case 1:
                loadImage("images/alien_EASY.png");
                break;
            case 2:
                loadImage("images/alien_MEDIUM.png");
                break;
            case 3:
                loadImage("images/alien_HARD.png");
                break;
            default:
                break;
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
