/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep2;

import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Sprite {

    protected int positionX;
    protected int positionY;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;

    public Sprite(int posx, int posy) {

        this.positionX = posx;
        this.positionY = posy;
        visible = true;
    }

    protected void loadImage(String imageName) {

        ImageIcon icon = new ImageIcon(imageName);
        image = icon.getImage();
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
