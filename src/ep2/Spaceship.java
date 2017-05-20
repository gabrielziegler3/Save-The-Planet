package ep2;

import java.awt.event.KeyEvent;

public class Spaceship extends GameObject {
    
    private static final int MAX_SPEED_X = 3;
    private static final int MAX_SPEED_Y = 3;
   
    private int speedX;
    private int speedY;
    private int shipLevel;
    private int life;
    private int score;
    LaserBeam laser;
    
    public Spaceship(int positionX, int positionY) {
        super(positionX, positionY);
        setShipLevel(3);
        setLife(3);
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
    

    public int getSpeedX() {
        return speedX;  
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public int getShipLevel() {
        return shipLevel;
    }

    public void setShipLevel(int shipLevel) {
        this.shipLevel = shipLevel;
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
    
    public void shoot(LaserBeam laser){
        laser.move();
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
                laser = new LaserBeam(positionX, positionY);
                shoot(laser);
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
