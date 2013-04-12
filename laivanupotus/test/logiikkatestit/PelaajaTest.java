package logiikkatestit;


import logiikka.Laiva;
import logiikka.Pelaaja;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PelaajaTest {

    private Pelaaja pelaaja;

    public PelaajaTest() {
    }

    @Before
    public void setUp() {
        pelaaja = new Pelaaja();
    }

    @Test
    public void uudenPelaajanLaivataulukossaEiLaivoja() {
        Laiva[] malli = {null, null, null, null, null};
        assertEquals(malli, pelaaja.getLaivat());
    }

    @Test
    public void kunOhiTulosMerkattuOnkoAmmuttuPalauttaaTrue() {
        pelaaja.merkkaaTulos(0, 9, 1);
        assertEquals(true, pelaaja.onkoAmmuttu(0, 9));
    }

    @Test
    public void kunOsunutTulosMerkattuOnkoAmmuttuPalauttaaTrue() {
        pelaaja.merkkaaTulos(0, 9, 2);
        assertEquals(true, pelaaja.onkoAmmuttu(0, 9));
    }

    @Test
    public void kunUponnutTulosMerkattuOnkoAmmuttuPalauttaaTrue() {
        pelaaja.merkkaaTulos(0, 9, 3);
        assertEquals(true, pelaaja.onkoAmmuttu(0, 9));
    }

    @Test
    public void tuloksenMerkkausToimiiKaikkiinKulmiin() {
        pelaaja.merkkaaTulos(0, 9, 1);
        pelaaja.merkkaaTulos(9, 9, 1);
        pelaaja.merkkaaTulos(9, 0, 1);
        pelaaja.merkkaaTulos(0, 0, 1);
        assertEquals(true, pelaaja.onkoAmmuttu(0, 9));
        assertEquals(true, pelaaja.onkoAmmuttu(9, 9));
        assertEquals(true, pelaaja.onkoAmmuttu(0, 0));
        assertEquals(true, pelaaja.onkoAmmuttu(9, 0));
    }

    @Test
    public void josYritetaanMerkitaNollaOnkoAmmuttuPalauttaaFalse() {
        pelaaja.merkkaaTulos(0, 9, 0);
        assertEquals(false, pelaaja.onkoAmmuttu(0, 9));
    }

    @Test
    public void laivanVoiAsettaaVasYla() {
        assertEquals(true, pelaaja.asetaLaiva(0, 0, 0, 4));
    }

    @Test
    public void laivanVoiAsettaaVasAla() {
        assertEquals(true, pelaaja.asetaLaiva(0, 9, 4, 9));
    }

    @Test
    public void laivanVoiAsettaaOikYla() {
        assertEquals(true, pelaaja.asetaLaiva(9, 0, 9, 4));
    }

    @Test
    public void laivanVoiAsettaaOikAla() {
        assertEquals(true, pelaaja.asetaLaiva(9, 9, 9, 5));
    }

    @Test
    public void josYritetaanAsettaaYliViisiLaivaaPalauttaaFalse() {
        pelaaja.asetaLaiva(0, 0, 0, 4);
        pelaaja.asetaLaiva(2, 0, 2, 3);
        pelaaja.asetaLaiva(4, 0, 4, 2);
        pelaaja.asetaLaiva(6, 0, 6, 2);
        pelaaja.asetaLaiva(8, 0, 8, 1);
        assertEquals(false, pelaaja.asetaLaiva(9, 8, 9, 9));
    }

    @Test
    public void josLaivojaYrittaaAsettaaVierekkainPalauttaaFalse() {
        pelaaja.asetaLaiva(8, 9, 8, 6);
        assertEquals(false, pelaaja.asetaLaiva(9, 9, 9, 5));
    }

    @Test
    public void josLaivojaYrittaaAsettaaPallekkainPalauttaaFalse() {
        pelaaja.asetaLaiva(9, 9, 9, 6);
        assertEquals(false, pelaaja.asetaLaiva(9, 9, 9, 5));
    }

    @Test
    public void josLaivojaYrittaaAsettaaOsittainPallekkainPalauttaaFalse() {
        pelaaja.asetaLaiva(9, 5, 9, 4);
        assertEquals(false, pelaaja.asetaLaiva(9, 9, 9, 5));
    }

    @Test
    public void josLaivojaYrittaaAsettaaKulmittainPalauttaaTrue() {
        pelaaja.asetaLaiva(8, 4, 8, 3);
        assertEquals(true, pelaaja.asetaLaiva(9, 9, 9, 5));
    }

    @Test
    public void kunRuudussaLaivaGetRuudunTilaPalauttaaLaiva() {
        pelaaja.asetaLaiva(8, 4, 8, 2);
        assertEquals("laiva", pelaaja.getRuudunTila(8, 4));
        assertEquals("laiva", pelaaja.getRuudunTila(8, 3));
        assertEquals("laiva", pelaaja.getRuudunTila(8, 2));
    }

    @Test
    public void kunRuutuTyhjaGetRuudunTilaPalauttaaTyhja() {
        String testi = "tyhja";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(!pelaaja.getLauta().getLauta()[i][j].getTila().equals("tyhja")){
                    testi="laiva";
                }
            }
        }
        assertEquals("laiva", pelaaja.getRuudunTila(8, 3), testi);
    }
    @Test
    public void laivanVieressaGetRuudunTilaPalauttaaTyhja() {
        pelaaja.asetaLaiva(8, 4, 8, 2);
        assertEquals("tyhja", pelaaja.getRuudunTila(9, 4));
        assertEquals("tyhja", pelaaja.getRuudunTila(9, 3));
        assertEquals("tyhja", pelaaja.getRuudunTila(9, 2));
        assertEquals("tyhja", pelaaja.getRuudunTila(7, 4));
        assertEquals("tyhja", pelaaja.getRuudunTila(7, 3));
        assertEquals("tyhja", pelaaja.getRuudunTila(7, 2));
        assertEquals("tyhja", pelaaja.getRuudunTila(8, 5));
        assertEquals("tyhja", pelaaja.getRuudunTila(8, 1));
    }
}
