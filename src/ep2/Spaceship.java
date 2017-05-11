package ep2;

import java.awt.event.KeyEvent;

public class Spaceship extends Sprite {
    
    private static final int MAX_SPEED_X = 2;
    private static final int MAX_SPEED_Y = 1;
   
    private int speedX;
    private int speedY;

    public Spaceship(int positionX, int positionY) {
        super(positionX, positionY);
        
        initSpaceShip();
    }

    private void initSpaceShip() {
        noThrust();
    }
    
    private void noThrust(){
        loadImage("images/spaceship.png"); 
    }
    
    private void thrust(){
        loadImage("images/spaceship_thrust.png"); 
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

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        
        // Set speed to move to the left
        if (key == KeyEvent.VK_LEFT) { 
            speedX = -1 * MAX_SPEED_X;
        }

        // Set speed to move to the right
        if (key == KeyEvent.VK_RIGHT) {
            speedX = MAX_SPEED_X;
        }
        
        // Set speed to move to up and set thrust effect
        if (key == KeyEvent.VK_UP) {
            speedY = -1 * MAX_SPEED_Y;
            thrust();
        }
        
        // Set speed to move to down
        if (key == KeyEvent.VK_DOWN) {
            speedY = MAX_SPEED_Y;
        }
        
    }
    
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            speedX = 0;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            speedY = 0;
            noThrust();
        }
    }
}
