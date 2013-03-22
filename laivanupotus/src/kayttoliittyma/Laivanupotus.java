/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

/**
 *
 * @author teempitk
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import logiikka.Peli;

public class Laivanupotus extends JFrame {

    private int alkux;
    private int alkuy;
    private int loppux;
    private int loppuy;
    private Nappi[] vastustajan;
    private Nappi[] pelaajan;
    private Peli peli;
    private boolean ammuntavaihe;
    private int edellisenPituus;
    private int asetettavanPituus;
    private boolean alkuAnnettu;
    private JTextField t11 = new JTextField("Sinun laivasi");
    private JTextField t12 = new JTextField("Aseta laivan (5) alkupiste");
    private JTextField t21 = new JTextField("Vihollisen laivat");
    private JTextField t22 = new JTextField("Ohjekenttä");

    public Laivanupotus() {
        peli = new Peli();
        vastustajan = new Nappi[100];
        ammuntavaihe = false;
        asetettavanPituus = 5;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Nappi apunappi;
                apunappi = new Nappi("");
                apunappi.setx(j);
                apunappi.sety(i);
                vastustajan[10 * i + j] = apunappi;
            }
        }
        pelaajan = new Nappi[100];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                pelaajan[10 * i + j] = new Nappi("");
            }
        }

        t11.setEditable(false);
        t12.setEditable(false);
        t21.setEditable(false);
        t22.setEditable(false);

        JPanel p11 = new JPanel(new GridLayout(10, 10));
        for (Nappi nappi : pelaajan) {
            p11.add(nappi);
        }


        JPanel p12 = new JPanel(new GridLayout(10, 10));
        for (Nappi nappi : vastustajan) {
            p12.add(nappi);
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                alkux = j;
                alkuy = i;
                Nappi nappi = pelaajan[10 * i + j];
                nappi.addActionListener(
                        new Kuuntelija(j, i, this));
            }
        }


        JPanel t1 = new JPanel(new GridLayout(2, 1));
        t1.add(t11);
        t1.add(t12);
        t1.add(p11);

        JPanel t2 = new JPanel(new GridLayout(2, 1));
        t2.add(t21);
        t2.add(t22);
        t2.add(p11);

        JPanel p21 = new JPanel(new BorderLayout());
        p21.add("North", t1);
        p21.add(p11);

        JPanel p22 = new JPanel(new BorderLayout());
        p22.add("North", t2);
        p22.add(p12);


        this.setLayout(new GridLayout(1, 2));
        this.add(p21);
        this.add(p22);
    }

    public void toimiiko() {
        System.out.println("JEE!!");
    }

    public void aseta(int x, int y) {
        pelaajan[10 * y + x].setText("X");
        alkuAnnettu = !alkuAnnettu;
        if (alkuAnnettu) {
            alkux = x;
            alkuy = y;
            t12.setText("Aseta laivan (" + asetettavanPituus + ") loppupiste");
        } else {
            loppux = x;
            loppuy = y;
            if (asetettavanPituus == 2 || asetettavanPituus == 4 || asetettavanPituus == 5) {
                asetettavanPituus--;                                                                // JATKA TASTA!!!!
            }                                                                                       // TARKISTETTAVA VOIKO ASETTAA
        }


    }

    public void ammu(int x, int y) {
    }

    public boolean onkoAmmuntavaihe() {
        return ammuntavaihe;
    }

    public void asetaOmaOhje() {
    }

    public void asetaVastustajanOhje() {
    }

    public static void main(String args[]) {
        Laivanupotus ikkuna = new Laivanupotus();
        ikkuna.setTitle("Laivanupotus");
        ikkuna.pack();
        ikkuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ikkuna.setSize(1000, 500);
        ikkuna.setVisible(true);
    }
}