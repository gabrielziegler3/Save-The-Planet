package ep2;

import java.util.List;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Spaceship extends GameObject {
    
    private static final int MAX_SPEED_X = 3;
    private static final int MAX_SPEED_Y = 3;
    private int shipLevel;
    private int life;
    private int score;
    private List<LaserBeam> laser;
    
    public Spaceship(int positionX, int positionY) {
        super(positionX, positionY);
        laser = new ArrayList<LaserBeam>();
        setShipLevel(3);
        setLife(3);
        setSpeedX(0);
        setSpeedY(0);
        setScore(0);
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

    public int getShipLevel() {
        return shipLevel;
    }

    public void setShipLevel(int shipLevel) {
        this.shipLevel = shipLevel;
    }

    public List<LaserBeam> getLaser() {
        return laser;
    }

    
    private void loadSpaceShip(){
        switch (getShipLevel()) {
            case 1:
                loadImage("images/spaceship1.gif");
                break;
            case 2:
                loadImage("images/spaceship2.gif");
                break;
            case 3:
                loadImage("images/spaceship3.gif");
                break;
            default:
                break;
        }
    }

    public void move() {
        // Limits the movement of the spaceship to the side edges.
        if((speedX < 0 && positionX <= 0) || (speedX > 0 && positionX + width >= Game.getWidth())){
            speedX = 0;
        }
        
        // Moves the spaceship on the horizontal axis
        positionX += speedX;
        
        // Limits the movement of the spaceship to the vertical edges.
        if((speedY < 0 && positionY <= 0) || (speedY > 0 && positionY + height >= Game.getHeight())){
            speedY = 0;
        }

        // Moves the spaceship on the verical axis
        positionY += speedY;
    }
    
    public void shoot(){
        for(int i=0; i<1; i++){
            this.laser.add(new LaserBeam(this.positionX + width/2, this.positionY));
        }
    }

    public void keyPressed(KeyEvent e) {
        
        switch(e.getKeyCode()){
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

        switch(e.getKeyCode()){

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
