/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import logiikka.Peli;

/**
 *
 * @author teempitk
 */
public class Kuuntelija implements ActionListener {

    private int x;
    private int y;
    private boolean asettaaVaiAmpuu;  // false=asettaa, true=ampuu
    private Peli peli;

    public Kuuntelija(int x, int y, Peli peli, boolean ampuuko) {
        this.x = x;
        this.y = y;
        asettaaVaiAmpuu = ampuuko;
        this.peli=peli;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!peli.onkoAmmuntavaihe()) {
            if (!asettaaVaiAmpuu) {
                peli.aseta(x, y);
                peli.naytaLaudat();
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