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
    List<LaserBeam> laserBoss;

    public Boss(int posx, int posy, int speedX) {
        super(posx, posy);
        laserBoss = new ArrayList<>();
        this.speedX = speedX;
        this.positionX = ThreadLocalRandom.current().nextInt(0, 1728);
        this.positionY = posy;
        this.life = 200;
        this.visible = false;
        loadBoss();
    }

    public void loadBoss() {
        if (this.visible) {
            loadImage("images/boss.gif");
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
        if (this.life > 150) {
            this.laserBoss.add(new LaserBeam(this.positionX + width / 2, this.positionY + height - 65, -5, 4));
        } else if (this.life < 150 && this.life > 75) {
            this.laserBoss.add(new LaserBeam(this.positionX + 20, this.positionY + height, -6, 4));
            this.laserBoss.add(new LaserBeam(this.positionX + width - 20, this.positionY + height, -6, 4));
        } else {
            this.laserBoss.add(new LaserBeam(this.positionX + 30, this.positionY + height, -8, 4));
            this.laserBoss.add(new LaserBeam(this.positionX + width - 30, this.positionY + height, -8, 4));
            this.laserBoss.add(new LaserBeam(this.positionX + width / 2, this.positionY + height, -8, 4));
        }
    }
}
