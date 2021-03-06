package ep2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {

    public Rectangle playButton = new Rectangle(Game.getWidth() / 2 - 75, 200, 150, 100);
    public Rectangle quitButton = new Rectangle(Game.getWidth() / 2 - 75, 400, 150, 100);

    public final void mainMenu(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        String title = "Save the Planet!";
        Font font1 = new Font("Helvetica", Font.BOLD, 50);
        FontMetrics metric1 = getFontMetrics(font1);

        g.setColor(Color.white);
        g.setFont(font1);
        g.drawString(title, (Game.getWidth() - metric1.stringWidth(title)) / 2, 100);

        g2d.draw(playButton);

        String play = "PLAY";
        Font font2 = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics metric2 = getFontMetrics(font2);

        g.setColor(Color.white);
        g.setFont(font2);
        g.drawString(play, (Game.getWidth() - metric2.stringWidth(play)) / 2, 250);

        g2d.draw(quitButton);

        String quit = "QUIT";
        Font font4 = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics metric4 = getFontMetrics(font4);

        g.setColor(Color.white);
        g.setFont(font4);
        g.drawString(quit, (Game.getWidth() - metric4.stringWidth(quit)) / 2, 450);
    }

    public void gameOver(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        String gameover = "GAME OVER!";
        Font font1 = new Font("Helvetica", Font.BOLD, 50);
        FontMetrics metric1 = getFontMetrics(font1);

        g.setColor(Color.white);
        g.setFont(font1);
        g.drawString(gameover, (Game.getWidth() - metric1.stringWidth(gameover)) / 2, 100);
        
        g2d.draw(quitButton);

        String quit = "QUIT";
        Font font4 = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics metric4 = getFontMetrics(font4);

        g.setColor(Color.white);
        g.setFont(font4);
        g.drawString(quit, (Game.getWidth() - metric4.stringWidth(quit)) / 2, 450);
    }

    public void missionAccomplished(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        String success = "MISSION ACCOMPLISHED, COMMANDER!!!";
        Font font1 = new Font("Helvetica", Font.BOLD, 50);
        FontMetrics metric1 = getFontMetrics(font1);

        g.setColor(Color.white);
        g.setFont(font1);
        g.drawString(success, (Game.getWidth() - metric1.stringWidth(success)) / 2, 100);
        
        g2d.draw(quitButton);

        String quit = "QUIT";
        Font font4 = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics metric4 = getFontMetrics(font4);

        g.setColor(Color.white);
        g.setFont(font4);
        g.drawString(quit, (Game.getWidth() - metric4.stringWidth(quit)) / 2, 450);
    }
}
