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

    private int apux;
    private int apuy;
    private Nappi[] vastustajan;
    private Nappi[] pelaajan;
    private JButton resetoi;
    private Peli peli;
    private JTextField t11 = new JTextField("Sinun laivasi");
    private JTextField t12 = new JTextField("Aseta laivan (5) alkupiste");
    private JTextField t21 = new JTextField("Vihollisen laivat");
    private JTextField t22 = new JTextField("");

    public Laivanupotus() {
        vastustajan = new Nappi[100];
        resetoi = new JButton();
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
                nappi.addActionListener(
                        new Kuuntelija(j, i, peli, false));
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                apux = j;
                apuy = i;
                Nappi nappi = vastustajan[10 * i + j];
                nappi.addActionListener(
                        new Kuuntelija(j, i, peli, true));
            }
        }

        resetoi.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent tapahtuma) {
//                        asetettavanPituus = 5;
//                        edellisenPituus = 6;
//                        ammuntavaihe = false;
//                        alkuAnnettu = false;

                    }
                });

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

    public static void main(String args[]) {
        Laivanupotus ikkuna = new Laivanupotus();
        ikkuna.setTitle("Laivanupotus");
        ikkuna.pack();
        ikkuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ikkuna.setSize(1000, 500);
        ikkuna.setMinimumSize(new Dimension(1000, 500));
        ikkuna.setVisible(true);
    }

    public void asetaOmaTeksti(String teksti) {
        t12.setText(teksti);

    }

    public void asetaVastuksenTeksti(String teksti) {
        t22.setText(teksti);

    }
    public void naytaLaivat() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (Peli.getVastus().getRuudunTila(j, i).equals("tyhja")) {
                    vastustajan[10 * i + j].setVisible(false);
                }
            }
        }
    }
}
