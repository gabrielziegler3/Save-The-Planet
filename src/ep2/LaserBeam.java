package ep2;

/**
 *
 * @author ziegler
 */
public class LaserBeam extends GameObject {
    private int type;
    private int speedY = 1;

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
    
    public LaserBeam(int posx, int posy) {
        super(posx, posy);
        setType(1);
        loadLaser();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public void loadLaser(){
        switch (type) {
            case 1:
                loadImage("images/missile.png");
                break;
            case 2:
                loadImage("images/beams.png");
                break;
            case 3:
                loadImage("images/beams.png");
                break;
            default:
                break;
        }
    }
    
    public void move(){
        // Limits the movement of the spaceship to the vertical edges.
        if((speedY < 0 && positionY <= 0) || (speedY > 0 && positionY + height >= Game.getHeight())){
        }

        // Moves the spaceship on the verical axis
        positionY -= speedY;
        
    }
}
