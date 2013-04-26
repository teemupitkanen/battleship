package kayttoliittyma;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logiikka.Peli;

/**
 * Kuuntelija on ActionListener-luokkaa implementoiva luokka, joka kuuntelee
 * pelikenttien nappeja. Kuuntelija tuntee nappinsa sijainnin pelilaudalla, ja
 * kutsuu pelaajan aseta/ammu-metodia.
 *
 * @author teempitk
 */
public class Kuuntelija implements ActionListener {

    /**
     * Liittyvän ruudun x-koordinaatti
     */
    private int x;
    /**
     * Liittyvän ruudun y-koordinaatti
     */
    private int y;
    /**
     * Nappi on toiminnoltaa joko asettava tai ampuva ja kuuntelija alustetaan
     * sen mukaan
     */
    private boolean asettaaVaiAmpuu;  // false=asettaa, true=ampuu
    /**
     * Peli, johon nappi ja kuuntelija liittyvät
     */
    private Peli peli;

    /**
     * Alustaa kuuntelijan rakenteet
     */
    public Kuuntelija(int x, int y, Peli peli, boolean ampuuko) {
        this.x = x;
        this.y = y;
        asettaaVaiAmpuu = ampuuko;
        this.peli = peli;
    }

    /**
     * Kutsuu pelaajan ammu- tai aseta-metodia riippuen siitä onko nappi ampuva
     * vai asettava. Jos pelitilanne ei ole oikea, ei tee mitään (esim asettavan
     * nappulan klikkaaminen pelin ampumavaiheessa)
     *
     * @param e nappulan klikkaus
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!peli.onkoAmmuntavaihe()) {
            if (!asettaaVaiAmpuu) {
                peli.aseta(x, y);
            }
        } else {
            if (asettaaVaiAmpuu) {
                if (peli.onkoPeliKesken()) {
                    peli.pelaaKierros(x, y);
                }
            }
        }

    }
}