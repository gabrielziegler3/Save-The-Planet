package test;

import ep2.Bonus;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ziegler
 */
public class TestBonus {    
    Bonus bonus;
    
    @Before
    public void setUp() {
        bonus = new Bonus(400, 400, 1, 1);
    }
    
    @Test
    public void testStationaryBonus(){
        bonus.setSpeedX(0);
        bonus.setSpeedY(0);
        
        assertEquals(0, bonus.getSpeedY());
        assertEquals(0, bonus.getSpeedX());
    }
    
    @Test 
    public void testBonusMovement() {
        bonus.move();
        
        assertEquals(400, bonus.getPositionX());
        assertEquals(401, bonus.getPositionY());
    }
}
