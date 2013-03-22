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

    public Kuuntelija(int x, int y, Laivanupotus upotus) {
        this.x = x;
        this.y = y;
        this.upotus = upotus;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!upotus.onkoAmmuntavaihe()) {
            upotus.aseta(x,y);
        } else {
            upotus.ammu(x,y);
        }

    }
}