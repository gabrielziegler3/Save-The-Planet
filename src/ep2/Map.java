package ep2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Map extends JPanel implements ActionListener {

    private final int SPACESHIP_X = 700;
    private final int SPACESHIP_Y = 750;
    private final int ALIEN_X = 700;
    private final int ALIEN_Y = 100;
//    private final int LASER_X = ;
//    private final int LASER_Y =   
    private final Timer timer_map;
    
    private final Image background;
    private final Spaceship spaceship;
    private final Alien alien;
    private final Game game;
//    private final LaserBeam laser;

    public Map() {
        
        addKeyListener(new KeyListener());
        
        setFocusable(true);
        setDoubleBuffered(true);
        game = new Game(1);
        ImageIcon space = new ImageIcon("images/space" + game.getStage() + ".jpg");
        
        this.background = space.getImage();
        spaceship = new Spaceship(SPACESHIP_X, SPACESHIP_Y);
        alien  = new Alien(ALIEN_X ,ALIEN_Y, 1);

        timer_map = new Timer(Game.getDelay(), this);
        timer_map.start();
         
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage(this.background, 0, 0, null);
       
        drawSpaceship(g);
        drawAlien(g);
        drawStatus(g, game, spaceship);
//        drawLaser(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawSpaceship(Graphics g) {
        // Draw spaceship
        g.drawImage(spaceship.getImage(), spaceship.getPositionX(), spaceship.getPositionY(), this);
    }
    private void drawAlien(Graphics g){
        g.drawImage(alien.getImage(), alien.getPositionX(),alien.getPositionY(), this);
    }
//    private void drawLaser(Graphics g){
//        g.drawImage(laser.getImage(), laser.getPositionX(), laser.getPositionY(), this);
//    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       
        updateSpaceship();
        updateAlien();
//        updateLaserBeam();
       
        repaint();
    }
    
    private void drawMissionAccomplished(Graphics g) {

        String message = "MISSION ACCOMPLISHED";
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metric = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
    }
    
    private void drawStatus(Graphics g, Game game, Spaceship spaceship){
        String stage = "Stage: " + game.getStage();
        String life = "Life: " + spaceship.getLife();
        String score = "Score: " + spaceship.getScore();
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metric = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(stage, (Game.getWidth() - metric.stringWidth(stage)) - 1650, Game.getHeight() - 950);
        g.drawString(life, (Game.getWidth() - metric.stringWidth(stage)) - 1650, Game.getHeight() - 935);
        g.drawString(score, (Game.getWidth() - metric.stringWidth(stage)) -1650, Game.getHeight() - 920);
    }
    
    private void drawGameOver(Graphics g) {

        String message = "Game Over";
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metric = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
    }
    
    private void updateSpaceship() {
        spaceship.move();
    }
  
    private void updateAlien() {
        alien.move();
//        System.out.println("Laserbeam X = " + laser.getPositionX() + "Laserbeam Y" +laser.getPositionY());
    }
    
//    private void updateLaserBeam() {
//        laser.move();
//    }
//    

    private class KeyListener extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {
            spaceship.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            spaceship.keyReleased(e);
        }

        
    }
}
