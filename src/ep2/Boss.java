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
            loadImage("images/evilboss2.png");
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
        switch (this.rage) {
            case 1:
                this.laserBoss.add(new LaserBeam(this.positionX + width / 2, this.positionY + height - 65, -5, 4));
                break;
            case 2:
                this.laserBoss.add(new LaserBeam(this.positionX + 50, this.positionY + height - 150, -6, 4));
                this.laserBoss.add(new LaserBeam(this.positionX + width - 50, this.positionY + height - 150, -6, 4));
                break;
            case 3:
                this.laserBoss.add(new LaserBeam(this.positionX + 50, this.positionY + height - 150, -8, 4));
                this.laserBoss.add(new LaserBeam(this.positionX + width - 50, this.positionY + height - 150, -8, 4));
                this.laserBoss.add(new LaserBeam(this.positionX + width / 2, this.positionY + height, -8, 4));
                break;
            case 4:
                this.laserBoss.add(new LaserBeam(this.positionX + 50, this.positionY + height - 150, -8, 4));
                this.laserBoss.add(new LaserBeam(this.positionX + width - 50, this.positionY + height - 150, -8, 4));
                this.laserBoss.add(new LaserBeam(this.positionX + width / 2, this.positionY + height, -8, 4));
                break;
        }
    }
}
