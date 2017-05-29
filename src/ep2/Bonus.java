package ep2;

/**
 *
 * @author ziegler
 */
public class Bonus extends GameObject {

    int type;

    public Bonus(int posx, int posy, int type, int speedY) {
        super(posx, posy);
        this.type = type;
        this.speedY = speedY;
        loadBonus();
    }

    public void loadBonus() {
        switch (type) {
            case 1:
                loadImage("images/life.png");
                break;
            case 2:
                loadImage("images/gems/gem2.png");
                break;
            case 3:
                loadImage("images/gear.png");
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
        // Limits the movement of the bonus to the vertical edges.
        if ((speedY < 0 && positionY <= 0) || (speedY > 0 && positionY >= Game.getHeight())) {
            setVisible(false);
        }
        // Moves the bonus on the verical axis
        positionY += speedY;
    }
}
