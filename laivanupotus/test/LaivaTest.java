/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import logiikka.Laiva;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author teempitk
 */
public class LaivaTest {
    private Laiva laiva;
    public LaivaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        laiva=new Laiva(3);
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void uudenLaivanKokoOnOikein(){
        laiva.osuma();
        laiva.osuma();
        laiva.osuma();
        assertEquals(true,laiva.onkoUponnut());
    }
    @Test
    public void laivaEiUppoaJosLiianVahanOsumia(){
        laiva.osuma();
        laiva.osuma();
        assertEquals(false,laiva.onkoUponnut());
    }
    @Test
    public void uusiLaivaOnPinnalla(){
        assertEquals(false,laiva.onkoUponnut());
    }
}
