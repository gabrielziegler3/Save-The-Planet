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
    private final Timer timerMap;
    private final Image background;
    private final Spaceship spaceship;
    private Boss boss;
    private List<Alien> alienList = new ArrayList<>();
    private List<Bonus> bonusList = new ArrayList<>();
    Random random = new Random();
    private final Game game;
    private int counter = 0;

    public Map() {

        addKeyListener(new KeyListener());

        setFocusable(true);
        setDoubleBuffered(true);
        game = new Game(1);
        ImageIcon space = new ImageIcon("images/space1.jpg");
        this.background = space.getImage();
        spaceship = new Spaceship(SPACESHIP_X, SPACESHIP_Y);
        initAlien();
        initBonus();
//        initBoss();

        timerMap = new Timer(Game.getDelay(), this);
        timerMap.start();
    
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(this.background, 0, 0, null);

        drawSpaceship(g);
        drawAlien(g);
//        drawBoss(g);
        drawStatus(g, game, spaceship);
        drawLaserBeam(g);
        drawBonus(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawSpaceship(Graphics g) {
        g.drawImage(spaceship.getImage(), spaceship.getPositionX(), spaceship.getPositionY(), this);
    }
    
    private void drawBoss (Graphics g){
        g.drawImage(boss.getImage(), boss.getPositionX(), boss.getPositionY(), this);
    }

    private void drawAlien(Graphics g) {
        for (int i = 0; i < alienList.size(); i++) {
            Alien alien = alienList.get(i);
            g.drawImage(alien.getImage(), alien.getPositionX(), alien.getPositionY(), this);
        }
    }

    private void drawBonus(Graphics g) {
        for (int i = 0; i < bonusList.size(); i++) {
            Bonus bonus = bonusList.get(i);
            g.drawImage(bonus.getImage(), bonus.getPositionX(), bonus.getPositionY(), this);
        }
    }

    private void drawLaserBeam(Graphics g) {
        List<LaserBeam> laserList = spaceship.getLaser();
        for (int i = 0; i < laserList.size(); i++) {
            LaserBeam laser = (LaserBeam) laserList.get(i);
            g.drawImage(laser.getImage(), laser.getPositionX(), laser.getPositionY(), this);
        }
    }
    
//    private void drawBossLaserBeam(Graphics g) {
//        List<LaserBeam> laserList = spaceship.getLaser();
//        for (int i = 0; i < laserList.size(); i++) {
//            LaserBeam laser = (LaserBeam) laserList.get(i);
//            g.drawImage(laser.getImage(), laser.getPositionX(), laser.getPositionY(), this);
//        }
//    }


    @Override
    public void actionPerformed(ActionEvent e) {
        updateSpaceship();
        updateAlien();
        initAlien();
        initBonus();
        updateSpaceShipLaserBeam();
//        updateBoss();
//        updateAlienLaserBeam();
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

    private void drawStatus(Graphics g, Game game, Spaceship spaceship) {
        String stage = "Stage: " + game.getStage();
        String life = "Life: " + spaceship.getLife();
        String score = "Score: " + spaceship.getScore();
        String shipLevel = "Ship Level: " + spaceship.getShipLevel();
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metric = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(stage, (Game.getWidth() - metric.stringWidth(stage)) - 1650, Game.getHeight() - 950);
        g.drawString(life, (Game.getWidth() - metric.stringWidth(stage)) - 1650, Game.getHeight() - 935);
        g.drawString(score, (Game.getWidth() - metric.stringWidth(stage)) - 1650, Game.getHeight() - 920);
        g.drawString(shipLevel, (Game.getWidth() - metric.stringWidth(stage)) - 1650, Game.getHeight() - 905);
    }

    private void drawGameOver(Graphics g) {

        String message = "Game Over";
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metric = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
    }

    private void initAlien() {
        if(counter % 7 == 0)
            alienList.add(new Alien(0, 0, random.nextInt(3 - 1 + 1) + 1, random.nextInt(3 + 3 + 1 ) - 3, 2));
        counter++;
    }
    
    private void initBoss() {
        boss = new Boss(700, 0, 4);
    }

    private void initBonus() {
        if(counter % 100 == 0)
            bonusList.add(new Bonus(0, 0, random.nextInt(3 - 1 + 1) + 1, random.nextInt(3 - 1 + 1) + 1));
        counter++;
    }

    private void updateAlien() {
        if(alienList.isEmpty())
            initAlien();
        for (int i = 0; i < alienList.size(); i++) {
            Alien alien = alienList.get(i);
            if (alien.isVisible()) {
                if(alien.getLife() < 1){
                    alien.setVisible(false);
                }
                alien.move();
            } else {
                alienList.remove(i);
            }
        }
    }
    
    private void updateBoss() {
        if(spaceship.getScore() > 50000){
            initBoss();
        }
        if (boss.isVisible()){
            boss.move();
//            boss.shoot();
        }
    }

    private void updateBonus() {
        if (bonusList.isEmpty()) {
            initBonus();
        }

        for (int i = 0; i < bonusList.size(); i++) {
            Bonus bonus = bonusList.get(i);
            if (bonus.isVisible()) {
                bonus.move();
            } else {
                bonusList.remove(i);
            }
        }
    }

    private void updateSpaceship() {
        if (spaceship.isVisible()) {
            spaceship.move();
            spaceship.loadSpaceShip(); //update spaceship image
            System.out.println("Height Y: " + Game.getHeight() + " Width X: " + Game.getWidth());
            System.out.println("Spaceship X: " + spaceship.getPositionX() + " Spaceship Y: " + spaceship.getPositionY());
            if (spaceship.getScore() > 5000 && spaceship.getScore() < 10000) {
                game.setStage(2);
            }
            if (spaceship.getScore() > 10000) {
                game.setStage(3);
            }
        } else {
//            drawGameOver(g);
        }
    }

    private void updateSpaceShipLaserBeam() {
        List<LaserBeam> laserList = spaceship.getLaser();
        for (int i = 0; i < laserList.size(); i++) {
            LaserBeam laser = (LaserBeam) laserList.get(i);
            if (laser.isVisible()) {
                laser.move();
            } else {
                laserList.remove(i);
            }
        }
    }
    
    private void updateBossLaserBeam(){
        List<LaserBeam> alienLaser = boss.getBossLaser();
        for (int i = 0; i < alienLaser.size(); i++) {
            LaserBeam laser = (LaserBeam) alienLaser.get(i);
            if (laser.isVisible()) {
                laser.move();
            } else {
                alienLaser.remove(i);
            }
        }
    }

    private void checkCollisions() {
        Rectangle spaceshipShape = spaceship.getBounds();
        Rectangle laserShape;
        Rectangle alienShape;
        Rectangle bonusShape;
        Rectangle bossShape;

        //checks collisions Spaceship - Alien
        for (int i = 0; i < alienList.size(); i++) {
            Alien tempAlien = alienList.get(i);
            alienShape = tempAlien.getBounds();

            if (spaceshipShape.intersects(alienShape)) {
                if (spaceship.getLife() < 1) {
                    spaceship.setVisible(false);
                }
                spaceship.setLife(spaceship.getLife() - 1);
                tempAlien.setVisible(false);
            }
        }
        
        //checks collisions ship Laserbeam - Boss
//        tempLaser.setVisible(false);
//        tempAlien.setLife(tempAlien.getLife() - 1);
//        if (tempAlien.getLife() < 1) {
//            spaceship.setScore(spaceship.getScore() + 5000);
//        }

        //checks collisions ship LaserBeam - Alien
        List<LaserBeam> laser = spaceship.getLaser();

        for (int i = 0; i < laser.size(); i++) {

            LaserBeam tempLaser = laser.get(i);
            laserShape = tempLaser.getBounds();

            for (int j = 0; j < alienList.size(); j++) {

                Alien tempAlien = alienList.get(j);
                alienShape = tempAlien.getBounds();

                if (laserShape.intersects(alienShape)) {
                    switch (tempAlien.getType()) {
                        case 1:
                            tempLaser.setVisible(false);
                            tempAlien.setLife(tempAlien.getLife() - 1);
                            if (tempAlien.getLife() < 1) {
                                spaceship.setScore(spaceship.getScore() + 100);
                            }
                            break;
                        case 2:
                            tempLaser.setVisible(false);
                            tempAlien.setLife(tempAlien.getLife() - 1);
                            if (tempAlien.getLife() < 1) {
                                spaceship.setScore(spaceship.getScore() + 200);
                            }
                            break;
                        case 3:
                            tempLaser.setVisible(false);
                            tempAlien.setLife(tempAlien.getLife() - 1);
                            if (tempAlien.getLife() < 1) {
                                spaceship.setScore(spaceship.getScore() + 300);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        //checks collisions Spaceship - Bonus 
        for (int i = 0; i < bonusList.size(); i++) {
            Bonus tempBonus = bonusList.get(i);
            bonusShape = tempBonus.getBounds();

            if (spaceshipShape.intersects(bonusShape)) {
                switch (tempBonus.getType()) {
                    case 1: //bonus = life
                        spaceship.setLife(spaceship.getLife() + 1);
                        tempBonus.setVisible(false);
                        break;
                    case 2: //bonus = score upgrade
                        spaceship.setScore(spaceship.getScore() + 1000);
                        tempBonus.setVisible(false);
                        break;
                    case 3: //bonus = spaceship upgrade
                        if (spaceship.getShipLevel() == 3) {
                            tempBonus.setVisible(false);
                            break;
                        } else {
                            spaceship.setShipLevel(spaceship.getShipLevel() + 1);
                            tempBonus.setVisible(false);
                            break;
                        }
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
