package logiikkatestit;

import kayttoliittyma.Laivanupotus;
import kayttoliittyma.Nappi;
import logiikka.Laiva;
import logiikka.Peli;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PeliTest {

    private Peli peli;

    public PeliTest() {
    }

    @Before
    public void setUp() {
        Nappi[] nappitaulukko1 = new Nappi[100];
        Nappi[] nappitaulukko2 = new Nappi[100];
        for (int i = 0; i < 100; i++) {
            nappitaulukko1[i] = new Nappi("");
            nappitaulukko2[i] = new Nappi("");
        }
        peli = new Peli(new Laivanupotus(), nappitaulukko1, nappitaulukko2);
        peli.getPelaaja().asetaLaiva(0, 0, 4, 0);
        peli.getPelaaja().asetaLaiva(0, 2, 3, 2);
        peli.getPelaaja().asetaLaiva(0, 4, 2, 4);
        peli.getPelaaja().asetaLaiva(0, 6, 2, 6);
        peli.getPelaaja().asetaLaiva(0, 8, 1, 8);
    }

    @Test
    public void uudessaPelissaVvoittaaPalauttaaFalse() {
        assertEquals(false, peli.vvoittaa());
    }

    @Test
    public void uudessaPelissaPvoittaaPalauttaaFalse() {
        assertEquals(false, peli.pvoittaa());
    }

    @Test
    public void kunYksiLaivaUpotettuVvoittaaPalauttaaFalse() {
        Laiva laiva = peli.getPelaaja().getLaivat()[0];
        int pituus = laiva.getRuudut().length;
        for (int i = 0; i < pituus; i++) {
            laiva.osuma();
        }
        assertEquals(false, peli.vvoittaa());
    }

    @Test
    public void kunYksiLaivaUpotettuPvoittaaPalauttaaFalse() {
        Laiva laiva = peli.getVastus().getLaivat()[0];
        int pituus = laiva.getRuudut().length;
        for (int i = 0; i < pituus; i++) {
            laiva.osuma();
        }
        assertEquals(false, peli.pvoittaa());
    }

    @Test
    public void kunKaikkiPelaajanLaivatUponnutVvoittaaTrue() {
        for (Laiva laiva : peli.getPelaaja().getLaivat()) {
            int pituus = laiva.getRuudut().length;
            for (int i = 0; i < pituus; i++) {
                laiva.osuma();
            }
        }
        assertEquals(true, peli.vvoittaa());
    }

    @Test
    public void kunKaikkiPelaajanLaivatUponnutPvoittaaFalse() {
        for (Laiva laiva : peli.getPelaaja().getLaivat()) {
            int pituus = laiva.getRuudut().length;
            for (int i = 0; i < pituus; i++) {
                laiva.osuma();
            }
        }
        assertEquals(false, peli.pvoittaa());
    }

    @Test
    public void kunKaikkiVastuksenLaivatUponnutPvoittaaTrue() {
        for (Laiva laiva : peli.getVastus().getLaivat()) {
            int pituus = laiva.getRuudut().length;
            for (int i = 0; i < pituus; i++) {
                laiva.osuma();
            }
        }
        assertEquals(true, peli.pvoittaa());
    }

    @Test
    public void kunKaikkiVastuksenLaivatUponnutVvoittaaFalse() {
        for (Laiva laiva : peli.getVastus().getLaivat()) {
            int pituus = laiva.getRuudut().length;
            for (int i = 0; i < pituus; i++) {
                laiva.osuma();
            }
        }
        assertEquals(false, peli.vvoittaa());
    }

    @Test
    public void getPelaajaToimii() {
        peli.getPelaaja();
    }

    @Test
    public void getVastusToimii() {
        peli.getVastus();
    }

    @Test
    public void viidenRuudunLaivanAsetusOnnistuuUuteen() {
        Nappi[] nappitaulukko1 = new Nappi[100];
        Nappi[] nappitaulukko2 = new Nappi[100];
        for (int i = 0; i < 100; i++) {
            nappitaulukko1[i] = new Nappi("");
            nappitaulukko2[i] = new Nappi("");
        }
        peli = new Peli(new Laivanupotus(), nappitaulukko1, nappitaulukko2);
        peli.aseta(1, 1);
        peli.aseta(5, 1);
        assertEquals("laiva", peli.getPelaaja().getRuudunTila(5, 1));
        assertEquals("laiva", peli.getPelaaja().getRuudunTila(4, 1));
        assertEquals("laiva", peli.getPelaaja().getRuudunTila(3, 1));
        assertEquals("laiva", peli.getPelaaja().getRuudunTila(2, 1));
        assertEquals("laiva", peli.getPelaaja().getRuudunTila(1, 1));
    }
}
