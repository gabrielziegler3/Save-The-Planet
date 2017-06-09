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
    public void testLife() {
        ship.setLife(1);
        assertEquals(1, ship.getLife());
    }
    
    @Test
    public void testStationarySpaceship(){
        assertEquals(0, ship.getSpeedY());
        assertEquals(0, ship.getSpeedX());
    }    
}
