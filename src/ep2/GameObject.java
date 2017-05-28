package ep2;

import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class GameObject {

    protected int positionX;
    protected int positionY;
    protected int speedX;
    protected int speedY;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;

    public GameObject(int posx, int posy) {

        this.positionX = posx;
        this.positionY = posy;
        visible = true;
    }

    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
        getImageDimensions();
    }
    
    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }    

    public Image getImage() {
        return image;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
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

    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean enable) {
        visible = enable;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(positionX, positionY, width, height);
    }
}
