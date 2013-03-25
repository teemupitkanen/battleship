/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author teempitk
 */
public class Kuuntelija implements ActionListener {

    private int x;
    private int y;
    private Laivanupotus upotus;
    private boolean asettaaVaiAmpuu;  // false=asettaa, true=ampuu

    public Kuuntelija(int x, int y, Laivanupotus upotus, boolean ampuuko) {
        this.x = x;
        this.y = y;
        this.upotus = upotus;
        asettaaVaiAmpuu = ampuuko;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!upotus.onkoAmmuntavaihe()) {
            if (!asettaaVaiAmpuu) {
                upotus.aseta(x, y);
            }
        } else {
            if (asettaaVaiAmpuu) {
                upotus.ammu(x, y);
            }
        }

    }
}