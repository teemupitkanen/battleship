package kayttoliittyma;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import logiikka.Laiva;
import logiikka.Peli;
import logiikka.Ruutu;

public class Laivanupotus extends JFrame {

    private int apux;
    private int apuy;
    private int alkux;
    private int alkuy;
    private int loppux;
    private int loppuy;
    private Nappi[] vastustajan;
    private Nappi[] pelaajan;
    private JButton resetoi;
    private Peli peli;
    private boolean ammuntavaihe;
    private boolean peliKesken;
    private boolean vastusOsui;
    private boolean pelaajaOsui;
    private int edellisenPituus;
    private int asetettavanPituus;
    private boolean alkuAnnettu;
    private JTextField t11 = new JTextField("Sinun laivasi");
    private JTextField t12 = new JTextField("Aseta laivan (5) alkupiste");
    private JTextField t21 = new JTextField("Vihollisen laivat");
    private JTextField t22 = new JTextField("");
    private int moneskoVuoro;

    public Laivanupotus() {
        peli = new Peli();
        vastustajan = new Nappi[100];
        ammuntavaihe = false;
        moneskoVuoro=0;
        peliKesken = true;
        asetettavanPituus = 5;
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
                        new Kuuntelija(j, i, this, false));
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                apux = j;
                apuy = i;
                Nappi nappi = vastustajan[10 * i + j];
                nappi.addActionListener(
                        new Kuuntelija(j, i, this, true));
            }
        }

        resetoi.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent tapahtuma) {
                        asetettavanPituus = 5;
                        edellisenPituus = 6;
                        ammuntavaihe = false;
                        alkuAnnettu = false;

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

            int apu;
            if (alkux > loppux) {
                apu = alkux;
                alkux = loppux;
                loppux = apu;
            }
            if (alkuy > loppuy) {
                apu = alkuy;
                alkuy = loppuy;
                loppuy = apu;
            }
            if ((alkuy == loppuy && (loppux - alkux + 1) == asetettavanPituus) || (alkux == loppux && loppuy - alkuy + 1 == asetettavanPituus)) {
                if (peli.getPelaaja().asetaLaiva(alkux, alkuy, loppux, loppuy)) {     // Merkitään ruudussa hyväksytyn laivan pisteet X:llä

                    for (int i = alkux; i <= loppux; i++) {
                        pelaajan[10 * alkuy + i].setText("X");
                    }
                    for (int i = alkuy; i <= loppuy; i++) {
                        pelaajan[10 * i + alkux].setText("X");
                    }

                    if (asetettavanPituus == 5) {
                        asetettavanPituus--;
                        edellisenPituus = 5;
                    } else if (asetettavanPituus == 4) {
                        asetettavanPituus--;
                        edellisenPituus--;
                    } else if (asetettavanPituus == 3) {
                        if (edellisenPituus == 3) {
                            asetettavanPituus--;
                        } else {
                            edellisenPituus--;
                        }
                    } else {
                        asetettavanPituus = 0;
                        ammuntavaihe = true;

                    }
                } else {
                    pelaajan[10 * alkuy + alkux].setText("");
                    pelaajan[10 * loppuy + loppux].setText("");

                }
            } else {
                pelaajan[10 * alkuy + alkux].setText("");
                pelaajan[10 * loppuy + loppux].setText("");

            }
            t12.setText("Aseta laivan (" + asetettavanPituus + ") alkupiste");
            if (asetettavanPituus == 0) {
                t12.setText("Asetteluvaihe on loppunut. Ammu oikeanpuoleiseen ruudukkoon.");
                t22.setText("Klikkaa ruutua ampuaksesi. Osumasta saa uuden vuoron.");
                naytaLaudat();
            }
        }


    }

    public void pelaaKierros(int x, int y) {
        if(!pelaajaOsui && !vastusOsui){
            moneskoVuoro++;
        }
        if (!peli.getPelaaja().onkoAmmuttu(x, y) || vastusOsui == true && peliKesken) {
            if (!vastusOsui) {
                if (ammuPelaaja(x, y)) {
                    pelaajaOsui = true;
                } else {
                    pelaajaOsui = false;
                }
            }
            if (!pelaajaOsui) {
                if (ammuVastus()) {
                    vastusOsui = true;
                    try {
                        Thread.sleep(1000);
                    } catch (Exception c) {
                        System.out.println("ohjelman tauko epaonnistui");
                    }
                    pelaaKierros(x, y);
                } else {
                    vastusOsui = false;
                }
            }
        }
    }

    public boolean ammuPelaaja(int x, int y) {
        String tulos = Peli.getVastus().getRuudunTila(x, y);
        boolean osuiko = false;
        if (tulos.equals("laiva")) {
            osuiko = true;
            Laiva osuttuLaiva = Peli.getVastus().getLauta().getLauta()[y][x].getLaiva();
            vastustajan[10 * y + x].setText("X");
            Peli.getPelaaja().merkkaaTulos(x, y, 2);
            osuttuLaiva.osuma();
            if (osuttuLaiva.onkoUponnut()) {
                Peli.getPelaaja().merkkaaTulos(x, y, 3);
                merkkaaUponneeksi(osuttuLaiva);
            }
        } else if (tulos.equals("tyhja")) {
            vastustajan[10 * y + x].setVisible(false);
            Peli.getPelaaja().merkkaaTulos(x, y, 1);
        }
        if (peli.pvoittaa()) {
            peliKesken = false;
            IlmoitusPop ilmo = new IlmoitusPop("Sinä voitit vuorolla "+moneskoVuoro+"!");
            t22.setText("Sinä voitit pelin!");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (Peli.getVastus().getRuudunTila(j, i).equals("tyhja")) {
                        vastustajan[10 * i + j].setVisible(false);
                    }
                }
            }
        }
        return osuiko;
    }

    public boolean ammuVastus() {
        boolean osuiko = false;
        int[] ammuttavanKoordinaatit = peli.getVastus().ammu();
        int ax = ammuttavanKoordinaatit[0];
        int ay = ammuttavanKoordinaatit[1];
        System.out.println(ax);
        System.out.println(ay);
        String vihollisenTulos = Peli.getPelaaja().getRuudunTila(ax, ay);
        if (vihollisenTulos.equals("laiva")) {
            osuiko = true;
            Peli.getPelaaja().getLauta().getLauta()[ay][ax].getLaiva().osuma();
            pelaajan[10 * ay + ax].setText("#");
            
            if (!Peli.getPelaaja().getLauta().getLauta()[ay][ax].getLaiva().onkoUponnut()) {
                peli.getVastus().asetaTulos(2, ax, ay);
            } else {
                System.out.println("UPPOSI");
                peli.getVastus().asetaTulos(3, ax, ay);
            }
        } else {
            pelaajan[10 * ay + ax].setVisible(false);
            peli.getVastus().asetaTulos(1, ax, ay);
        }
        if (peli.vvoittaa()) {
            peliKesken = false;
            t12.setText("Tietokone voitti!");
            t22.setText("Tietokone voitti!");
            IlmoitusPop ilmo = new IlmoitusPop("Tietokone voitti vuorolla "+moneskoVuoro+"!");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (Peli.getVastus().getRuudunTila(j, i).equals("laiva") && !Peli.getPelaaja().onkoAmmuttu(j, i)) {
                        vastustajan[10 * i + j].setText("o");
                    }
                }
            }
        }
        return osuiko;
    }

    public boolean onkoAmmuntavaihe() {
        return ammuntavaihe;
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
        ikkuna.setMinimumSize(new Dimension(1000,500));
        ikkuna.setVisible(true);
    }

    private static void naytaLaudat() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (Peli.getPelaaja().getRuudunTila(j, i).equals("laiva")) {
                    System.out.print("#");

                } else {
                    System.out.print(".");
                }
            }
            System.out.println("");
        }
        System.out.println("");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (Peli.getVastus().getRuudunTila(j, i).equals("laiva")) {
                    System.out.print("#");

                } else {
                    System.out.print(".");
                }
            }
            System.out.println("");
        }
    }

    public boolean onkoPeliKesken() {
        return peliKesken;
    }
}
