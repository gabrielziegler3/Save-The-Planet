package ep2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Map extends JPanel implements ActionListener {

    private final int SPACESHIP_X = 700;
    private final int SPACESHIP_Y = 750;
    private final int ALIEN_Y = 0;
    private int ALIEN_SIZE = 10;
    Random random = new Random();
    private final Timer timer_map;
    
    private final Image background;
    private final Spaceship spaceship;
    private List <Alien> alienList;
    private List <Bonus> bonusList;
    private final Game game;
    
    private int[][] coordinates = { { 2380, 29 }, { 2600, 59 }, { 1380, 89 },
			{ 780, 109 }, { 580, 139 }, { 880, 239 }, { 790, 259 },
			{ 760, 50 }, { 790, 150 }, { 1980, 209 }, { 560, 45 }, { 510, 70 },
			{ 930, 159 }, { 590, 80 }, { 530, 60 }, { 940, 59 }, { 990, 30 },
			{ 920, 200 }, { 900, 259 }, { 660, 50 }, { 540, 90 }, { 810, 220 },
			{ 860, 20 }, { 740, 180 }, { 820, 128 }, { 490, 170 }, { 700, 30 },
			{ 920, 300 }, { 856, 328 }, { 456, 320 } };

    public Map() {
        
        addKeyListener(new KeyListener());
        
        setFocusable(true);
        setDoubleBuffered(true);
        game = new Game(1);
        ImageIcon space = new ImageIcon("images/space" + game.getStage() + ".jpg");
        initAlien();
        initBonus();
        this.background = space.getImage();
        spaceship = new Spaceship(SPACESHIP_X, SPACESHIP_Y);
        
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
        drawLaserBeam(g);
        drawBonus(g);
        
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawSpaceship(Graphics g) {
        g.drawImage(spaceship.getImage(), spaceship.getPositionX(), spaceship.getPositionY(), this);
    }
    
    private void drawAlien(Graphics g){
        for (int i = 0; i < alienList.size(); i++) {
            Alien enemy = alienList.get(i);
            g.drawImage(enemy.getImage(), enemy.getPositionX(), enemy.getPositionY(), this);
        }
    }
    
    private void drawBonus(Graphics g){
        for (int i = 0; i < bonusList.size(); i++) {
            Bonus bonus = bonusList.get(i);
            g.drawImage(bonus.getImage(), bonus.getPositionX(), bonus.getPositionY(), this);
        }
    }
    
    private void drawLaserBeam(Graphics g){
       List<LaserBeam> laserList = spaceship.getLaser();
       for (int i = 0; i < laserList.size(); i++) {
            LaserBeam laser = (LaserBeam) laserList.get(i);
            g.drawImage(laser.getImage(), laser.getPositionX(), laser.getPositionY(), this);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        updateSpaceship();
        updateAlien();
        updateLaserBeam();
        updateBonus();
        
        checkCollisions();
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
    
    private void initAlien(){
        alienList = new ArrayList<>();
        
        for(int i=0; i<coordinates.length; i++){    
            alienList.add(new Alien(coordinates[i][0], coordinates[i][1], 1));
        }
    }
    
    private void initBonus(){
        bonusList = new ArrayList<Bonus>();
        
        for(int i=0; i<3; i++){
            bonusList.add(new Bonus(200+ 20*i, 0, 1));
        }
    }
    
    private void updateAlien() {
        if(alienList.isEmpty()){
            initAlien();
        }
        for (int i = 0; i < alienList.size(); i++) {
            Alien alien = alienList.get(i);
            if (alien.isVisible()) {
                alien.move();
            } 
            else{
                alienList.remove(i);
            }
        }
    }
    
    private void updateBonus(){
        for(int i=0; i < bonusList.size(); i++){
            Bonus bonus = bonusList.get(i);
            if(bonus.isVisible()){
                bonus.move();
            }
            else{
                bonusList.remove(i);
            }
        }
    }
    
    private void updateSpaceship() {
        spaceship.move();
        System.out.println("Height Y: " + Game.getHeight() + " Width X: " + Game.getWidth());
    }
    
    private void updateLaserBeam() {
        List<LaserBeam> laserList = spaceship.getLaser();
        for(int i=0; i < laserList.size(); i++){
            LaserBeam laser = (LaserBeam) laserList.get(i);
            if(laser.isVisible()){
                laser.move();
            }
            else{
                laserList.remove(i);
            }
        }
    }
    
    private void checkCollisions(){
        Rectangle spaceshipShape = spaceship.getBounds();
        Rectangle laserShape;
        Rectangle alienShape;
        Rectangle bonusShape;
        
        //checks collisions Spaceship - Alien
        for (int i = 0; i < alienList.size(); i++) {
            Alien tempAlien = alienList.get(i);
            alienShape = tempAlien.getBounds();

            if (spaceshipShape.intersects(alienShape)) {
                spaceship.setLife(spaceship.getLife() - 1);
                if(spaceship.getLife() < 1){
                    spaceship.setVisible(false);
                    tempAlien.setVisible(false);
                }
            }
        }
        
        //checks collisions LaserBeam - Alien
        List<LaserBeam> laser = spaceship.getLaser();

        for (int i = 0; i < laser.size(); i++) {

                LaserBeam tempLaser = laser.get(i);
                laserShape = tempLaser.getBounds();

            for (int j = 0; j < alienList.size(); j++) {

                Alien tempAlien = alienList.get(j);
                alienShape = tempAlien.getBounds();

                if (laserShape.intersects(alienShape)) {
                    spaceship.setScore(spaceship.getScore() + 100);
                    tempAlien.setVisible(false);
                    tempLaser.setVisible(false);
                }
            }
        }
        
        //checks collisions Spaceship - Bonus 
        for (int i = 0; i < bonusList.size(); i++) {
            Bonus tempBonus = bonusList.get(i);
            bonusShape = tempBonus.getBounds();
 
            if (spaceshipShape.intersects(bonusShape)) {
                switch (tempBonus.getType()){
                    case 1: //bonus = life
                        spaceship.setLife(spaceship.getLife() + 1);
                        tempBonus.setVisible(false);
                        break;
                    case 2: //bonus = score upgrade
                        spaceship.setScore(spaceship.getScore() + 1000);
                        tempBonus.setVisible(false);
                        break;
                    case 3: //bonus = spaceship upgrade
                        spaceship.setShipLevel(spaceship.getShipLevel()+ 1);
                        tempBonus.setVisible(false);
                        break;
                }
                }
            }
        }
    
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
