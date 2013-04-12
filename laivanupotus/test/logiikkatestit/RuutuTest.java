package logiikkatestit;


import logiikka.Laiva;
import logiikka.Ruutu;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RuutuTest {

    private Ruutu ruutu;

    public RuutuTest() {
    }
    @Before
    public void setUp() {
        ruutu = new Ruutu(0,1);
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
    @Test
    public void setNaapuriJaGetNaapuriToimivatOikein(){
        Ruutu naapuri=new Ruutu(1,1);
        ruutu.setNaapuri(naapuri);
        assertEquals(naapuri,ruutu.getNaapurit()[0]);
    }
    @Test
    public void josNaapurejaEiLisattyGetPalauttaaNull(){
        assertEquals(null,ruutu.getNaapurit()[0]);
    }
    @Test
    public void getxPalauttaaOikeanArvon(){
        assertEquals(0,ruutu.getx());
    }
    @Test
    public void getyPalauttaaOikeanArvon(){
        assertEquals(1,ruutu.gety());
    }
}
