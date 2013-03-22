/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import logiikka.Laiva;
import logiikka.Ruutu;
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
public class RuutuTest {

    private Ruutu ruutu;

    public RuutuTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ruutu = new Ruutu();
    }

    @After
    public void tearDown() {
    }
    @Test
    public void uudenRuudunTilaOnTyhja(){
        assertEquals("tyhja",ruutu.getTila());
    }
    @Test
    public void kunRuutuunAsettaaLaivanTilaMuuttuu(){
        ruutu.setLaiva(new Laiva(2));
        assertEquals("laiva",ruutu.getTila());
    }
    @Test
    public void getLaivaPalauttaaOikeanLaivan(){
        Laiva laiva1=new Laiva(2);
        ruutu.setLaiva(laiva1);
        Laiva laiva2=ruutu.getLaiva();
        assertEquals(laiva1, laiva2);
    }
}
