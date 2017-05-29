package ep2;

import java.awt.Dimension;
import java.awt.Toolkit;

public final class Game {

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WIDTH = ((int) screenSize.getWidth()) * 90 / 100;
    private static final int HEIGHT = ((int) screenSize.getHeight()) * 90 / 100;
    private static final int DELAY = 10;
    private int stage;

    public Game(int stage) {
        setStage(1);
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int getDelay() {
        return DELAY;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
}
