package ep2;

/**
 *
 * @author ziegler
 */

public class Alien extends GameObject{

    private int speedX = 3;
    private int speedY = 1;
    private int type;

    public Alien(int posx, int posy, int type) {
        super(posx, posy);
        this.type = type;
        initAlien();
    }
    
    private void initAlien(){
        loadAlien();
    }
    
    public void loadAlien(){
        switch (type) {
            case 1:
                loadImage("images/crab-moving-transparent.gif");
                break;
            case 2:
                loadImage("images/crab-moving-transparent.gif");
                break;
            case 3:
                loadImage("images/crab-moving-transparent.gif");
                break;
            default:
                break;
        }
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
      public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }
    
     public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    
    public void move(){
    // Limits the movement of the spaceship to the side edges.
        if((speedX < 0 && positionX <= 0) || (speedX > 0 && positionX + width >= Game.getWidth())){
            speedX *= -1;
            speedY += 50;
            positionY += speedY;
        }
        
        // Moves the spaceship on the horizontal axis
        positionX += speedX;
        
        // Limits the movement of the spaceship to the vertical edges.
        if((speedY < 0 && positionY <= 0) || (speedY > 0 && positionY + height >= Game.getHeight())){
            speedY = 0;
        }
    }
    
}
