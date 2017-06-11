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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Map extends JPanel implements ActionListener {

    private final int SPACESHIP_X = 700;
    private final int SPACESHIP_Y = 750;
    private final Timer timerMap;
    private Image background;
    private Spaceship spaceship;
    private Boss boss;
    private List<Alien> alienList = new ArrayList<>();
    private List<Bonus> bonusList = new ArrayList<>();
    private Random random = new Random();
    private MainMenu menu;
    private final Game game;
    private int counter = 0;
    private boolean enable = true;
    //sound files
    private final File deathSound = new File("sounds/deathSound.wav");
    private final File hitmarkerSound = new File("sounds/hitmarkerSound.wav");
    private final File bonusSound = new File("sounds/bonusSound.wav");
    private final File damageSound = new File("sounds/damageSound.wav");
    private final File backgroundSong = new File("sounds/01-Shtrom-05.09.14.wav");
    private AudioStream hitSoundAudio;
    private InputStream hitSoundInput;
    private AudioStream bonusSoundAudio;
    private InputStream bonusSoundInput;
    private AudioStream damageSoundAudio;
    private InputStream damageSoundInput;
    private AudioStream backgroundSongAudio;
    private InputStream backgroundSongInput;

    public Rectangle playButton = new Rectangle(380, 15, 100, 30);

    public static enum STATE {
        MENU,
        GAME,
        HELP,
        GAMEOVER,
        PAUSE,
        PLAY,
        CADASTRO
    };

    public static STATE state = STATE.MENU;

    public Map() {

        addKeyListener(new KeyListener());
        this.addMouseListener(new MouseInput());
        initSounds();
        setFocusable(true);
        setDoubleBuffered(true);
        menu = new MainMenu();
        game = new Game();
        initBackground(game.getStage());
        initSpaceship();
        initBonus();
        initBoss(); //inicia boss invisivel (solucao para NullPointerException)
        initAlien();
//        AudioPlayer.player.start(backgroundSongAudio);

        timerMap = new Timer(Game.getDelay(), this);
        timerMap.start();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(this.background, 0, 0, null);

        if (state == STATE.GAME) {
            drawSpaceship(g);
            drawAlien(g);
            drawBoss(g);
            drawStatus(g, game, spaceship);
            drawLaserBeam(g);
            drawBossLaserBeam(g);
            drawBonus(g);
        } else if (state == STATE.MENU) {
            menu.mainMenu(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    public void drawSpaceship(Graphics g) {
        g.drawImage(spaceship.getImage(), spaceship.getPositionX(), spaceship.getPositionY(), this);
    }

    public void drawBoss(Graphics g) {
        g.drawImage(boss.getImage(), boss.getPositionX(), boss.getPositionY(), this);
    }

    public void drawAlien(Graphics g) {
        for (int i = 0; i < alienList.size(); i++) {
            Alien alien = alienList.get(i);
            g.drawImage(alien.getImage(), alien.getPositionX(), alien.getPositionY(), this);
        }
    }

    public void drawBonus(Graphics g) {
        for (int i = 0; i < bonusList.size(); i++) {
            Bonus bonus = bonusList.get(i);
            g.drawImage(bonus.getImage(), bonus.getPositionX(), bonus.getPositionY(), this);
        }
    }

    public void drawLaserBeam(Graphics g) {
        List<LaserBeam> laserList = spaceship.getLaser();
        for (int i = 0; i < laserList.size(); i++) {
            LaserBeam laser = (LaserBeam) laserList.get(i);
            g.drawImage(laser.getImage(), laser.getPositionX(), laser.getPositionY(), this);
        }
    }

    public void drawBossLaserBeam(Graphics g) {
        List<LaserBeam> bossLaserList = boss.getBossLaser();
        for (int i = 0; i < bossLaserList.size(); i++) {
            LaserBeam bossLaser = (LaserBeam) bossLaserList.get(i);
            g.drawImage(bossLaser.getImage(), bossLaser.getPositionX(), bossLaser.getPositionY(), this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (state == STATE.GAME) {
            updateSpaceship();
            updateAlien();
            updateSpaceShipLaserBeam();
            updateBoss();
            updateBossLaserBeam();
            updateBonus();
            updateGame();
            initAlien();
            initBonus();
            initSounds();
            initBackground(game.getStage());

            checkCollisions();
            repaint();
        }
    }

    public void drawMissionAccomplished(Graphics g) {
        String message = "MISSION ACCOMPLISHED";
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metric = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
    }

    public void drawStatus(Graphics g, Game game, Spaceship spaceship) {
        String stage = "Stage: " + game.getStage();
        String life = "Life: " + spaceship.getLife();
        String score = "Score: " + spaceship.getScore();
        String gear = "Gears Collected: " + spaceship.getCollectedGears();
        String bossLife = "Boss Life: " + boss.getLife();
        String bossRage = "Boss Rage: " + boss.getRage();
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metric = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(stage, (Game.getWidth() - metric.stringWidth(stage)) - 1650, Game.getHeight() - 75);
        g.drawString(life, (Game.getWidth() - metric.stringWidth(stage)) - 1650, Game.getHeight() - 935);
        g.drawString(score, (Game.getWidth() - metric.stringWidth(stage)) - 1650, Game.getHeight() - 920);
        g.drawString(gear, (Game.getWidth() - metric.stringWidth(stage)) - 1650, Game.getHeight() - 905);
        if (boss.isVisible()) {
            g.drawString(bossLife, (Game.getWidth() - metric.stringWidth(stage)) - 1650, Game.getHeight() - 890);
            g.drawString(bossRage, (Game.getWidth() - metric.stringWidth(stage)) - 1650, Game.getHeight() - 875);
        }
    }

    public void drawGameOver(Graphics g) {

        String message = "Game Over";
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metric = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
    }

    public void initSounds() {
        try {
            hitSoundInput = new FileInputStream(hitmarkerSound);
            hitSoundAudio = new AudioStream(hitSoundInput);
            bonusSoundInput = new FileInputStream(bonusSound);
            bonusSoundAudio = new AudioStream(bonusSoundInput);
            damageSoundInput = new FileInputStream(damageSound);
            damageSoundAudio = new AudioStream(damageSoundInput);
            backgroundSongInput = new FileInputStream(backgroundSong);
            backgroundSongAudio = new AudioStream(backgroundSongInput);

        } catch (IOException e) {
            System.out.println("Exception: Couldn't initiate sounds files!");
        }
    }

    public void initBackground(int stage) {
        ImageIcon space = new ImageIcon("images/space" + stage + ".jpg");
        this.background = space.getImage();
    }

    public void initSpaceship() {
        spaceship = new Spaceship(SPACESHIP_X, SPACESHIP_Y);
    }

    public void initAlien() {
        if (!boss.isVisible()) {
            switch (game.getStage()) {
                case 1: //spawns only easy and medium level aliens
                    if (counter % 25 == 0) {
                        alienList.add(new Alien(0, 0, random.nextInt(2 - 1 + 1) + 1, random.nextInt(3 + 3 + 1) - 3, 1));
                    }
                    break;
                case 2: //spawns only easy and medium level aliens
                    if (counter % 20 == 0) {
                        alienList.add(new Alien(0, 0, random.nextInt(2 - 1 + 1) + 1, random.nextInt(3 + 3 + 1) - 3, 1));
                    }
                    break;
                case 3: //spawns only medium and hard level aliens
                    if (counter % 15 == 0) {
                        alienList.add(new Alien(0, 0, random.nextInt(3 - 2 + 1) + 2, random.nextInt(3 + 3 + 1) - 3, 2));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void initBoss() {
        boss = new Boss(700, 0, 4);
    }

    public void initBonus() {
        if (counter % 75 == 0) {
            bonusList.add(new Bonus(0, 0, random.nextInt(3 - 1 + 1) + 1, random.nextInt(3 - 1 + 1) + 1));
        }
    }

    public void updateAlien() {
        if (alienList.isEmpty()) {
            initAlien();
        }
        for (int i = 0; i < alienList.size(); i++) {
            Alien alien = alienList.get(i);
            if (alien.isVisible()) {
                if (alien.getLife() < 1) {
                    alien.setVisible(false);
                }
                alien.move();
            } else {
                alienList.remove(i);
            }
        }
    }

    public void updateBoss() {
        if (enable) {
            if (spaceship.getScore() > 50000) {
                boss.setVisible(true);
                enable = false;
            }
        }
        if (boss.getLife() < 1) {
            boss.setVisible(false);
            //mission over
        }
        if (boss.isVisible()) {
            if (boss.getLife() > 400) {
                boss.setRage(1);
            } else if (boss.getLife() < 401 && boss.getLife() > 250) {
                boss.setRage(2);
                boss.setSpeedX(5);
            } else if (boss.getLife() < 251 && boss.getLife() > 150) {
                boss.setRage(3);
                boss.setSpeedX(7);
            } else if (boss.getLife() < 151) {
                boss.setRage(4);
            }
            boss.loadBoss();
            boss.move();
            switch (boss.getRage()) {
                case 1:
                    if (random.nextInt(25) == 0) {
                        boss.shoot();
                    }
                    break;
                case 2:
                    if (random.nextInt(20) == 0) {
                        boss.shoot();
                    }
                    break;
                case 3:
                    if (random.nextInt(15) == 0) {
                        boss.shoot();
                    }
                    break;
                case 4:
                    if (random.nextInt(10) == 0) {
                        boss.shoot();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void updateBonus() {
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

    public void updateSpaceship() {
//        spaceship.setLife(3 );
        if (spaceship.getLife() < 1) {
            spaceship.setVisible(false);
        }
        if (spaceship.isVisible()) {
            spaceship.initSound();
            spaceship.move();
            spaceship.loadSpaceShip(); //update spaceship image
        } else {
//            drawGameOver(g);
        }
    }

    public void updateGame() {
        if (spaceship.getScore() > 10000 && spaceship.getScore() < 30001) {

            game.setStage(2);
        }
        if (spaceship.getScore() > 30000) {
            game.setStage(3);
        }
        counter++;
    }

    public void updateSpaceShipLaserBeam() {
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

    public void updateBossLaserBeam() {
        List<LaserBeam> bossLaser = boss.getBossLaser();
        for (int i = 0; i < bossLaser.size(); i++) {
            LaserBeam l = (LaserBeam) bossLaser.get(i);
            if (l.isVisible()) {
                l.move();
            } else {
                bossLaser.remove(i);
            }
        }
    }

    public void checkCollisions() {
        //checks collisions Spaceship - Alien
        collisionShipAlien();
        //checks collisions ship Laserbeam - Boss
        collisionShipLaserBoss();
        //checks collisions ship LaserBeam - Alien
        collisionShipLaserAlien();
        //checks collisions Spaceship - Bonus 
        collisionSpaceshipBonus();
        //checks collisions boss Laserbem - Spaceship
        collisionBossLaserShip();

        collisionBossLaserBonus();

        collisionShipBoss();
    }

    public void collisionShipAlien() {
        Rectangle alienShape;
        Rectangle spaceshipShape = spaceship.getBounds();

        for (int i = 0; i < alienList.size(); i++) {
            Alien tempAlien = alienList.get(i);
            alienShape = tempAlien.getBounds();

            if (spaceshipShape.intersects(alienShape)) {
                AudioPlayer.player.start(damageSoundAudio);
                if (spaceship.getLife() < 1) {
                    spaceship.setVisible(false);
                }
                spaceship.setLife(spaceship.getLife() - 1);
                tempAlien.explode();
                tempAlien.setVisible(false);
            }
        }
    }

    public void collisionShipLaserAlien() {
        Rectangle laserShape;
        Rectangle alienShape;
        List<LaserBeam> laser = spaceship.getLaser();

        for (int i = 0; i < laser.size(); i++) {

            LaserBeam tempLaser = laser.get(i);
            laserShape = tempLaser.getBounds();

            for (int j = 0; j < alienList.size(); j++) {

                Alien tempAlien = alienList.get(j);
                alienShape = tempAlien.getBounds();

                if (laserShape.intersects(alienShape)) {
                    AudioPlayer.player.start(hitSoundAudio);
                    switch (tempAlien.getType()) {
                        case 1:
                            tempLaser.setVisible(false);
                            tempAlien.setLife(tempAlien.getLife() - 1);
                            if (tempAlien.getLife() < 1) {
                                spaceship.setScore(spaceship.getScore() + 100);
                                for (int k = 0; k < 5; k++) {
                                    tempAlien.explode();
                                }
                            }
                            break;
                        case 2:
                            tempLaser.setVisible(false);
                            tempAlien.setLife(tempAlien.getLife() - 1);
                            if (tempAlien.getLife() < 1) {
                                spaceship.setScore(spaceship.getScore() + 200);
                                for (int k = 0; k < 5; k++) {
                                    tempAlien.explode();
                                }
                            }
                            break;
                        case 3:
                            tempLaser.setVisible(false);
                            tempAlien.setLife(tempAlien.getLife() - 1);
                            if (tempAlien.getLife() < 1) {
                                spaceship.setScore(spaceship.getScore() + 300);
                                for (int k = 0; k < 5; k++) {
                                    tempAlien.explode();
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void collisionShipLaserBoss() {
        Rectangle laserShape;
        Rectangle bossShape;
        List<LaserBeam> laser = spaceship.getLaser();

        for (int i = 0; i < laser.size(); i++) {

            LaserBeam tempLaser = laser.get(i);
            laserShape = tempLaser.getBounds();

            bossShape = boss.getBounds();

            if (laserShape.intersects(bossShape)) {
                AudioPlayer.player.start(hitSoundAudio);
                tempLaser.setVisible(false);
                boss.setLife(boss.getLife() - 1);
                if (boss.getLife() < 1) {
                    spaceship.setScore(spaceship.getScore() + 1000);
                }
            }
        }
    }

    public void collisionSpaceshipBonus() {
        Rectangle spaceshipShape = spaceship.getBounds();
        Rectangle bonusShape;

        for (int i = 0; i < bonusList.size(); i++) {
            Bonus tempBonus = bonusList.get(i);
            bonusShape = tempBonus.getBounds();

            if (spaceshipShape.intersects(bonusShape)) {
                AudioPlayer.player.start(bonusSoundAudio);
                switch (tempBonus.getType()) {
                    case 1: //bonus = life
                        spaceship.setLife(spaceship.getLife() + 1);
                        tempBonus.setVisible(false);
                        break;
                    case 2: //bonus = score upgrade
                        spaceship.setScore(spaceship.getScore() + 1000);
                        tempBonus.setVisible(false);
                        break;
                    case 3: //bonus = gear
                        tempBonus.setVisible(false);
                        spaceship.setCollectedGears(spaceship.getCollectedGears() + 1);
                        break;
                }
            }
        }
    }

    public void collisionBossLaserShip() {
        Rectangle laserShape;
        Rectangle spaceshipShape;
        List<LaserBeam> laser = boss.getBossLaser();

        for (int i = 0; i < laser.size(); i++) {

            LaserBeam tempLaser = laser.get(i);
            laserShape = tempLaser.getBounds();

            spaceshipShape = spaceship.getBounds();

            if (laserShape.intersects(spaceshipShape)) {
                AudioPlayer.player.start(damageSoundAudio);
                tempLaser.setVisible(false);
                spaceship.setLife(spaceship.getLife() - 1);
                if (spaceship.getLife() < 1) {
                    spaceship.setVisible(false);

                }
            }
        }
    }

    public void collisionBossLaserBonus() {
        Rectangle laserShape;
        Rectangle bonusShape;
        List<LaserBeam> laser = boss.getBossLaser();

        for (int i = 0; i < laser.size(); i++) {

            LaserBeam tempLaser = laser.get(i);
            laserShape = tempLaser.getBounds();

            for (int j = 0; j < bonusList.size(); j++) {

                Bonus tempBonus = bonusList.get(j);
                bonusShape = tempBonus.getBounds();

                if (laserShape.intersects(bonusShape)) {
                    AudioPlayer.player.start(hitSoundAudio);
                    tempLaser.setVisible(false);
                    tempBonus.setVisible(false);
                }
            }
        }
    }

    public void collisionShipBoss() {
        Rectangle bossShape = boss.getBounds();
        Rectangle spaceshipShape = spaceship.getBounds();
        if (spaceshipShape.intersects(bossShape)) {
            AudioPlayer.player.start(damageSoundAudio);
            if (spaceship.getLife() < 1) {
                spaceship.setVisible(false);
            }
            spaceship.setLife(spaceship.getLife() - 1);
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
