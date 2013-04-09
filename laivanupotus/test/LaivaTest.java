import logiikka.Ruutu;
import logiikka.Laiva;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LaivaTest {
    private Laiva laiva;
    public LaivaTest() {
    }
    
    @Before
    public void setUp() {
        laiva=new Laiva(3);
    }
    
    @Test
    public void laivaUppoaaKunKoonVerranOsumia(){
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
    @Test
    public void lisaaRuutuToimii(){
        Ruutu ruutu=new Ruutu(0,0);
        laiva.lisaaRuutu(ruutu);
        assertEquals(ruutu,laiva.getRuudut()[0]);
    }
    @Test
    public void getRuudutPalauttaaOikeinJosLaivanPituudenVerranRuutuja(){
        Ruutu ruutu1=new Ruutu(0,0);
        Ruutu ruutu2=new Ruutu(0,1);
        Ruutu ruutu3=new Ruutu(0,2);
        laiva.lisaaRuutu(ruutu1);
        laiva.lisaaRuutu(ruutu2);
        laiva.lisaaRuutu(ruutu3);
        Ruutu[] malli = {ruutu1,ruutu2,ruutu3};
        assertEquals(malli,laiva.getRuudut());
    }
    @Test
    public void lisaaRuutuEiLisaaJosLaivallaKoonVerranRuutuja(){
        Ruutu ruutu1=new Ruutu(0,0);
        Ruutu ruutu2=new Ruutu(0,1);
        Ruutu ruutu3=new Ruutu(0,2);
        Ruutu ruutu4=new Ruutu(0,3);
        laiva.lisaaRuutu(ruutu1);
        laiva.lisaaRuutu(ruutu2);
        laiva.lisaaRuutu(ruutu3);
        laiva.lisaaRuutu(ruutu4);
        Ruutu[] malli = {ruutu1,ruutu2,ruutu3};
        assertEquals(malli,laiva.getRuudut());
    }
}
