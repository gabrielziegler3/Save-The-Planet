package test;

import ep2.Spaceship;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ziegler
 */
public class TestSpaceship {
    
    Spaceship ship;
    
    @Before
    public void setUp() {
        ship = new Spaceship(700, 400);
    }

    @Test
    public void testScore() {
        ship.setScore(100);
        assertEquals(100, ship.getScore());
    }
    
    @Test
    public void testStationarySpaceship(){
        assertEquals(0, ship.getSpeedY());
        assertEquals(0, ship.getSpeedX());
    }    
}
