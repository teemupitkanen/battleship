/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import logiikka.Tietokonevastustaja;
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
public class VastusTest {
    Tietokonevastustaja vastus;
    public VastusTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        vastus=new Tietokonevastustaja();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void konstruktoriLuoLaudanJossaOikeaMaaraLaivoja(){
        int apu=0;
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                if(vastus.getRuudunTila(i, j).equals("laiva")){
                    apu++;
                }
            }
        }
        assertEquals(17,apu);
    }
}
