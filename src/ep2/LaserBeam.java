package ep2;

/**
 *
 * @author ziegler
 */
public class LaserBeam extends GameObject {

    private int type;

    public LaserBeam(int posx, int posy, int speedY, int type) {
        super(posx, posy);
        this.type = type;
        this.speedX = 0;
        this.speedY = speedY;
        loadLaser();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void loadLaser() {
        switch (type) {
            case 1:
                loadImage("images/missile.png");
                break;
            case 2:
                loadImage("images/missile.png");
                break;
            case 3:
                loadImage("images/missile.png");
                break;
            case 4: //alien laser
                loadImage("images/missile.png");
                break;
            default:
                break;
        }
    }

    public void move() {
        // Limits the movement of the laser to the vertical edges.
        if ((speedY < 0 && positionY <= 0) || (speedY > 0 && positionY + height >= Game.getHeight())) {
        }

        // Moves the laser on the verical axis
        positionY -= speedY;
    }
}
