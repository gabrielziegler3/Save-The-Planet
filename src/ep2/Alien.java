package ep2;

import java.util.ArrayList;

/**
 *
 * @author ziegler
 */

public class Alien extends GameObject{

    private int speedX = 2;
    private int speedY = 1;
    private int type;
    private ArrayList<Alien> alien = new ArrayList();
    
    public Alien(int posx, int posy, int type) {
        super(posx, posy);
        this.type = type;
        alien.add(this);
        initAlien();
    }
    
    private void initAlien(){
        loadAlien();
    }
    
    public void loadAlien(){
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
        }
        
        // Moves the spaceship on the horizontal axis
        positionX += speedX;
        positionY += speedY;
        // Limits the movement of the spaceship to the vertical edges.
        if((speedY < 0 && positionY <= 0) || (speedY > 0 && positionY + height >= Game.getHeight())){
            speedY = 0;
        }
    }
    
}
