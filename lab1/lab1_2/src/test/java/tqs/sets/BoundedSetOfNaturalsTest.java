/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;
    private BoundedSetOfNaturals setD;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
        setD = new BoundedSetOfNaturals(4);
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        assertThrows(IllegalArgumentException.class, () -> setB.add(1));
        assertFalse(setB.contains(11), "add: added element not found in set.");
        assertEquals(6, setB.size(), "add: elements count not as expected.");
    }

    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }

    @Test
    public void addDuplicateException() {
        setD.add(10);
        assertThrows(IllegalArgumentException.class, () -> setD.add(10));
    }

    @Test 
    public void addNegativeException(){
        assertThrows(IllegalArgumentException.class, () -> setA.add(-1));
    }

    @Test 
    public void checkIntersect() {
        setD.add(1);
        setD.add(20);
        setD.add(30);
        assertTrue(setD.intersects(setB));
        assertFalse(setD.intersects(setC));
    }

}
