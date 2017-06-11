package ep2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author ziegler
 */
public class MouseInput implements MouseListener {
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        //play button pressed
        if (mouseX >= Game.getWidth() / 2 - 75 && mouseX <= Game.getWidth() / 2 + 75) {
            if (mouseY >= 200 && mouseY <= 300) {
                Map.state = Map.state.GAME;
            }
        }

        //help button pressed
        if (mouseX >= Game.getWidth() / 2 - 75 && mouseX <= Game.getWidth() / 2 + 75) {
            if (mouseY >= 400 && mouseY <= 500) {
                Map.state = Map.state.GAME;
            }
        }

        //quit button pressed
        if (mouseX >= Game.getWidth() / 2 - 75 && mouseX <= Game.getWidth() / 2 + 75) {
            if (mouseY >= 600 && mouseY <= 700) {
                System.exit(1);
            }
        }
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
