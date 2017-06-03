package test;

import ep2.Alien;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ziegler
 */
public class TestAlien {    
    Alien alien;
    
    @Before
    public void setUp() {
        alien = new Alien(400, 400, 1, 1, 0);
    }
    
    @Test
    public void testStationaryAlien(){
        alien.setSpeedX(0);
        alien.setSpeedY(0);
        
        assertEquals(0, alien.getSpeedY());
        assertEquals(0, alien.getSpeedX());
    }

    @Test 
    public void testAlienMovement() {
        alien.move();
        
        assertEquals(401, alien.getPositionX());
        assertEquals(400, alien.getPositionY());
    }
}
