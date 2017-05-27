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
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Map extends JPanel implements ActionListener {

    private final int SPACESHIP_X = 700;
    private final int SPACESHIP_Y = 750;
//    private final int ALIEN_X = 700;
    private final int ALIEN_Y = 0;
    private int ALIEN_SIZE = 10;
    Random random = new Random();
    private final Timer timer_map;
    
    private final Image background;
    private final Spaceship spaceship;
    private final Alien[] alien = new Alien[ALIEN_SIZE];
    private final Game game;
    private final ArrayList<LaserBeam>  laser = new ArrayList<>();

    public Map() {
        
        addKeyListener(new KeyListener());
        
        setFocusable(true);
        setDoubleBuffered(true);
        game = new Game(1);
        ImageIcon space = new ImageIcon("images/space" + game.getStage() + ".jpg");
        
        this.background = space.getImage();
        spaceship = new Spaceship(SPACESHIP_X, SPACESHIP_Y);
        for(int i=0; i<alien.length; i++){    
            alien[i] = new Alien(random.nextInt(1728), ALIEN_Y, 1);
        }
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
        for(int i=0; i<alien.length; i++){    
            g.drawImage(alien[i].getImage(), alien[i].getPositionX(),alien[i].getPositionY(), this);
        }
    }
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
        spaceship.shoot(laser);
        System.out.println("Height Y: " + Game.getHeight() + "Width X: " + Game.getWidth());
    }
  
    private void updateAlien() {
        for(int i=0; i<alien.length; i++){    
            alien[i].move();
        }
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
