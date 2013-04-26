package kayttoliittyma;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import logiikka.Laiva;
import logiikka.Peli;
import logiikka.Ruutu;

/**
 *
 * @author teempitk Käyttöliittymän keskeisin luokka. Hallinoi myös pelin
 * kulkua, esim. luo uuden pelin.Tällä hetkellä sisältää metodeja, jotka
 * voisivat
 */
public class Laivanupotus extends JFrame {

    /**
     * Väliaikaisen x-koordinaattitiedon tallentamiseen
     */
    private int apux;
    /**
     * Väliaikaisen y-koordinaattitiedon tallentamiseen
     */
    private int apuy;
    /**
     * Taulukko vastustajan pelilaudan napeista
     */
    private Nappi[] vastustajan;
    /**
     * Taulukko pelaajan omista napeista
     */
    private Nappi[] pelaajan;
    /**
     * JButton pelin resetointiin
     */
    private JButton resetoi = new JButton("Aloita alusta");
    /**
     * JButton pelin lopettamiseen
     */
    private JButton lopeta = new JButton("Lopeta");
    /**
     * Käyttöliittymään liittyvä peli
     */
    private Peli peli;
    /**
     * Pelaajan pelilaudan päällä ylimpänä oleva tekstikenttä
     */
    private JTextField t11 = new JTextField("Sinun laivasi");
    /**
     * Pelaajan pelilaudan päällä alempana oleva tekstikenttä
     */
    private JTextField t12 = new JTextField("Aseta laivan (5) alkupiste");
    /**
     * Vastustajan pelilaudan päällä ylempänä oleva tekstikenttä
     */
    private JTextField t21 = new JTextField("Vihollisen laivat");
    /**
     * Vastustajan pelilaudan päällä alempana oleva tekstikenttä
     */
    private JTextField t22 = new JTextField("");
    /**
     * Väliaikaisen tiedon tallennukseen
     */
    private Kuuntelija apukuuntelija;
    /**
     * Taulukko kaikista kuuntelijoista
     */
    private Kuuntelija[] kuuntelijataulu;
    /**
     * Jo lisättyjen kuuntelijoiden määrä
     */
    private int annetutKuuntelijat;

    /**
     * Konstruktori alustaa tietorakenteet, luo pelin ja luo käyttöliittymän
     */
    public Laivanupotus() {
        kuuntelijataulu = new Kuuntelija[200];
        annetutKuuntelijat = 0;
        vastustajan = new Nappi[100];
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
        peli = new Peli(this, vastustajan, pelaajan);

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
                apux = j;
                apuy = i;
                Nappi nappi = pelaajan[10 * i + j];
                apukuuntelija = new Kuuntelija(j, i, peli, false);
                kuuntelijataulu[annetutKuuntelijat] = apukuuntelija;
                annetutKuuntelijat++;
                nappi.addActionListener(
                        apukuuntelija);
            }
        }


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                apux = j;
                apuy = i;
                Nappi nappi = vastustajan[10 * i + j];
                apukuuntelija = new Kuuntelija(j, i, peli, true);
                kuuntelijataulu[annetutKuuntelijat] = apukuuntelija;
                annetutKuuntelijat++;
                nappi.addActionListener(
                        apukuuntelija);
            }
        }

        resetoi.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent tapahtuma) {
                        if (peli.onkoJoAmmuttu()) {
                            peli.uusiVaroitusKeskeyttamisesta(false);
                        } else {
                            aloitaAlusta();
                        }
                    }
                });

        lopeta.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent tapahtuma) {
                        if (peli.onkoJoAmmuttu()) {
                            peli.uusiVaroitusKeskeyttamisesta(true);
                        } else {
                            System.exit(0);
                        }


                    }
                });


        JPanel t1 = new JPanel(new GridLayout(2, 1));
        t1.add(t11);
        t1.add(t12);

        JPanel t2 = new JPanel(new GridLayout(2, 1));
        t2.add(t21);
        t2.add(t22);

        JPanel p21 = new JPanel(new BorderLayout());
        p21.add("North", t1);
        p21.add(p11);
        p21.add("South", resetoi);

        JPanel p22 = new JPanel(new BorderLayout());
        p22.add("North", t2);
        p22.add(p12);
        p22.add("South", lopeta);

        this.setLayout(new GridLayout(1,2,10,10));
        this.add(p21);
        this.add(p22);
        
        this.setTitle("Laivanupotus");
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 500);
        this.setMinimumSize(new Dimension(1000, 500));
//        this.setVisible(true);

    }

    /**
     * Merkkaa upottetun vihollisen laivan #-merkeillä ja poistaa viereiset
     * ruudut näkyvistä
     *
     * @param laiva
     */
    public void merkkaaUponneeksi(Laiva laiva) {
        for (Ruutu ruutu : laiva.getRuudut()) {
            vastustajan[10 * ruutu.gety() + ruutu.getx()].setText("#");
            for (Ruutu naapuri : ruutu.getNaapurit()) {
                if (naapuri.getTila().equals("tyhja")) {
                    vastustajan[10 * naapuri.gety() + naapuri.getx()].setVisible(false);
                }
            }
        }
    }

    /**
     * Tekee käyttöliittymän näkyväksi
     *
     * @param args ei tarvittavia parameterja
     */

    /**
     * asettaa pelaajanpuoleisen alemman tekstikentän
     *
     * @param teksti asetettava teksti
     */
    public void asetaOmaTeksti(String teksti) {
        t12.setText(teksti);

    }

    /**
     * asettaa vastustajan puoleisen alemman tekstikentän
     *
     * @param teksti asetettava teksti
     */
    public void asetaVastuksenTeksti(String teksti) {
        t22.setText(teksti);

    }

    /**
     * Piirtää molemmat pelilaudat laivoineen (testailun apumetodi)
     */
    public void naytaLaivat() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (Peli.getVastus().getRuudunTila(j, i).equals("tyhja")) {
                    vastustajan[10 * i + j].setVisible(false);
                }
            }
        }
    }

    /**
     * Aloittaa pelin alusta, eli kaikki ruudut nollautuvat ja peli alkaa alusta
     * laivojen asettelulla.
     */
    public void aloitaAlusta() {
        t12.setText("Aseta laivan (5) alkupiste");
        t22.setText("");
        this.peli = new Peli(this, vastustajan, pelaajan);
        annetutKuuntelijat = 0;
        for (int i = 0; i < 100; i++) {
            pelaajan[i].setVisible(true);
            pelaajan[i].setText("");
            vastustajan[i].setVisible(true);
            vastustajan[i].setText("");
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                apux = j;
                apuy = i;
                Nappi nappi = pelaajan[10 * i + j];
                nappi.removeActionListener(kuuntelijataulu[10 * i + j]);
                apukuuntelija = new Kuuntelija(j, i, peli, false);
                kuuntelijataulu[annetutKuuntelijat] = apukuuntelija;
                annetutKuuntelijat++;
                nappi.addActionListener(
                        apukuuntelija);
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                apux = j;
                apuy = i;
                Nappi nappi = vastustajan[10 * i + j];
                nappi.removeActionListener(kuuntelijataulu[10 * i + j + 100]);
                apukuuntelija = new Kuuntelija(j, i, peli, true);
                kuuntelijataulu[annetutKuuntelijat] = apukuuntelija;
                annetutKuuntelijat++;
                nappi.addActionListener(
                        apukuuntelija);
            }
        }
    }
}
