package ep2;

/**
 *
 * @author ziegler
 */


public class Alien extends Sprite{

    private int speedY;

    public Alien(int posx, int posy) {
        super(posx, posy);
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
    
}
