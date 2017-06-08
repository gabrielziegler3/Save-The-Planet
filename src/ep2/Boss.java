package ep2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author ziegler
 */
class Boss extends GameObject {

    private int life;
    private int rage;
    List<LaserBeam> laserBoss;

    public Boss(int posx, int posy, int speedX) {
        super(posx, posy);
        laserBoss = new ArrayList<>();
        this.speedX = speedX;
        this.positionX = ThreadLocalRandom.current().nextInt(0, 1728);
        this.positionY = posy;
        this.life = 500;
        this.visible = false;
        this.rage = 1;
        loadBoss();
    }

    public void loadBoss() {
        if (this.visible) {
            loadImage("images/boss.gif");
        }
    }
    
    public void updateRage(){
        if (this.life < 400){
            this.rage = 1;
        }
        else if (this.life < 300){
            this.rage = 2;
        }
        else if (this.life < 200){
            this.rage = 3;
        }
        else if (this.life < 100){
            this.rage = 4;
        }
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public List<LaserBeam> getBossLaser() {
        return laserBoss;
    }

    public int getRage() {
        return rage;
    }

    public void setRage(int rage) {
        this.rage = rage;
    }
    
    public void move() {
        if ((speedX < 0 && positionX <= 0) || (speedX > 0 && positionX + width >= Game.getWidth())) {
            positionX = 0;
        }
        positionX += speedX;

        if ((speedY < 0 && positionY <= 0) || (speedY > 0 && positionY >= Game.getHeight())) {
            setVisible(false);
        }
    }

    public void shoot() {
        if (this.life > 350) {
            this.laserBoss.add(new LaserBeam(this.positionX + width / 2, this.positionY + height - 65, -5, 4));
        } else if (this.life < 351 && this.life > 100) {
            this.laserBoss.add(new LaserBeam(this.positionX, this.positionY + height, -6, 4));
            this.laserBoss.add(new LaserBeam(this.positionX + width, this.positionY + height, -6, 4));
        } else {
            this.laserBoss.add(new LaserBeam(this.positionX, this.positionY + height, -8, 4));
            this.laserBoss.add(new LaserBeam(this.positionX + width, this.positionY + height, -8, 4));
            this.laserBoss.add(new LaserBeam(this.positionX + width / 2, this.positionY + height, -8, 4));
        }
    }
}
