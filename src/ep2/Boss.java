package ep2;

import java.util.List;

/**
 *
 * @author ziegler
 */
class Boss extends GameObject {

    private int life;
    List<LaserBeam> laserBoss;

    public Boss(int posx, int posy, int speedX) {
        super(posx, posy);
        this.speedX = speedX;
        this.positionX = posx;
        this.positionY = posy;
        this.life = 200;
        loadBoss();
    }

    public void loadBoss() {
        loadImage("images/crab-moving-transparent.png");
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
            speedX *= -1;
        }
        positionX += speedX;
        
        if(this.life < 150){
            this.speedX += 2;
        }
        if(this.life < 100){
            this.speedX += 2;
        }
        
        if ((speedY < 0 && positionY <= 0) || (speedY > 0 && positionY >= Game.getHeight())) {
            setVisible(false);
        }
    }

    public void shoot() {
        if (this.life > 150) {
            this.laserBoss.add(new LaserBeam(this.positionX + width / 2, this.positionY, -8, 1));
        } else if (this.life < 150 && this.life > 75) {
            this.laserBoss.add(new LaserBeam(this.positionX + 20, this.positionY, -8, 2));
            this.laserBoss.add(new LaserBeam(this.positionX + width - 20, this.positionY, -8, 2));
        } else {
            this.laserBoss.add(new LaserBeam(this.positionX + 30, this.positionY, -8, 3));
            this.laserBoss.add(new LaserBeam(this.positionX + width - 30, this.positionY, -8, 3));
            this.laserBoss.add(new LaserBeam(this.positionX + width / 2, this.positionY, -8, 3));
        }
    }
}
