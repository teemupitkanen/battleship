package logiikka;

import javax.swing.JButton;
import kayttoliittyma.IlmoitusPop;
import kayttoliittyma.Laivanupotus;
import kayttoliittyma.Nappi;

/**
 * Peli-luokka on laivanupotuksen loogisen toiminnan ylin luokka. Peli luo
 * pelaajan ja tietokonevastuksen, ja siltä voi kysyä onko toinen pelaajista
 * mahdollisesti voittanut pelin.
 */
public class Peli {

    /**
     * Pelin "ihmispelaajan" toimintoja hallinnoiva olio
     */
    private static Pelaaja pelaaja;
    /**
     * Pelin tietokonevastus
     */
    private static Tietokonevastustaja vastus;
    /**
     * asetuksessa olevan laivan alku-x-koordinaatti
     */
    private int alkux;
    /**
     * asetuksessa olevan laivan alku-y-koordinaatti
     */
    private int alkuy;
    /**
     * asetuksessa olevan laivan loppu-x-koordinaatti
     */
    private int loppux;
    /**
     * asetuksessa olevan laivan loppu-y-koordinaatti
     */
    private int loppuy;
    /**
     * vastustajan pelilaudan napit käyttöliittymässä
     */
    private Nappi[] vastustajan;
    /**
     * pelaajan pelilaudan napit käyttöliittymässä
     */
    private Nappi[] pelaajan;
    /**
     * kertoo onko plissä meneillään ammuntavaihe vai asetteluvaihe
     */
    private boolean ammuntavaihe;
    /**
     * Jos peli kesken muuttujan arvo on true
     */
    private boolean peliKesken;
    /**
     * Jos vastus osui viime ammunnallaan, arvo on true
     */
    private boolean vastusOsui;
    /**
     * Jos pelaaja osui viime ammunnallaan, arvo on true
     */
    private boolean pelaajaOsui;
    /**
     * Viimeksi asetetun laivan pituus
     */
    private int edellisenPituus;
    /**
     * Seuraavaksi asetettavan laivan pituus
     */
    private int asetettavanPituus;
    /**
     * Jos laivalle on jo annettu alkupiste, arvo on true
     */
    private boolean alkuAnnettu;
    /**
     * Pitää kirjaa monesko vuoro pelissä on menossa
     */
    private int moneskoVuoro;
    /**
     * Käyttöliittymä, joka liittyy peliin
     */
    private Laivanupotus upotus;
    /**
     * Linkki viimeksi esitettyyn ilmoitus pop-uppiin
     */
    private IlmoitusPop ilmoitusikkuna;

    /**
     * luo uuden pelaaja- ja tietokonevastustajaolion, alustaa muuttujien arvot
     */
    public Peli(Laivanupotus upotus, Nappi[] vastustajan, Nappi[] pelaajan) {
        pelaaja = new Pelaaja();
        vastus = new Tietokonevastustaja();
        this.upotus = upotus;
        this.vastustajan = vastustajan;
        this.pelaajan = pelaajan;
        //
        ammuntavaihe = false;
        moneskoVuoro = 0;
        peliKesken = true;
        asetettavanPituus = 5;
    }

    /**
     * Asettaa laivan alku- tai loppupääksi annetut koordinaatit (jos sallittua)
     *
     * @param x laivan päätypisteen x-koordinaatti
     * @param y laivan päätypisteen y-koordinaatti
     */
    public void aseta(int x, int y) {
        pelaajan[10 * y + x].setText("X");
        alkuAnnettu = !alkuAnnettu;
        if (alkuAnnettu) {
            alkux = x;
            alkuy = y;
            upotus.asetaOmaTeksti("Aseta laivan (" + asetettavanPituus + ") loppupiste");
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
                if (pelaaja.asetaLaiva(alkux, alkuy, loppux, loppuy)) {
                    paivitaRakenteisiinAsetuksenOnnistuminen();
                } else {
                    pelaajan[10 * alkuy + alkux].setText("");
                    pelaajan[10 * loppuy + loppux].setText("");

                }
            } else {
                pelaajan[10 * alkuy + alkux].setText("");
                pelaajan[10 * loppuy + loppux].setText("");

            }
            upotus.asetaOmaTeksti("Aseta laivan (" + asetettavanPituus + ") alkupiste");
            if (asetettavanPituus == 0) {
                upotus.asetaOmaTeksti("Asetteluvaihe on loppunut. Ammu oikeanpuoleiseen ruudukkoon.");
                upotus.asetaVastuksenTeksti("Klikkaa ruutua ampuaksesi. Osumasta saa uuden vuoron.");
                naytaLaudat();
            }
        }


    }

    /**
     * Kertoo onko pelissä meneillään ampuma- vai asetteluvaihe
     *
     * @return true jos ampumavaihe, muuten false
     */
    public boolean onkoAmmuntavaihe() {
        return ammuntavaihe;
    }

    /**
     * Pelaa yhden kierroksen peliä (molemmat pelaajat ampuvat kerran). Jos
     * Vastus osuu, metodi kutsuu itseään rekursiivisesti. Jos pelaaja on osunut
     * viimeksi, vastus ei ammu
     *
     * @param x pelaajan ampuman pisteen x-koordinaatti
     * @param y pelaajan ampuman pisteen y-koordinaatti
     */
    public void pelaaKierros(int x, int y) {
        if (!pelaaja.onkoAmmuttu(x, y) || vastusOsui == true && peliKesken) {
            if (!pelaajaOsui && !vastusOsui) {
                moneskoVuoro++;
            }
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
                    pelaaKierros(x, y);
                } else {
                    vastusOsui = false;
                }
            }
        }
    }

    /**
     * Suorittaa pelaajan amunnan pisteeseen ja merkitsee tuloksen rakenteisiin
     *
     * @param x ammuttavan x-koordinaatti
     * @param y ammuttavan y-koordinaatti
     * @return ammunnan tulos (true=osui)
     */
    private boolean ammuPelaaja(int x, int y) {
        String tulos = Peli.vastus.getRuudunTila(x, y);
        boolean osuiko = false;
        if (tulos.equals("laiva")) {
            osuiko = true;
            Laiva osuttuLaiva = Peli.vastus.getLauta().getLauta()[y][x].getLaiva();
            vastustajan[10 * y + x].setText("X");
            Peli.pelaaja.merkkaaTulos(x, y, 2);
            osuttuLaiva.osuma();
            if (osuttuLaiva.onkoUponnut()) {
                Peli.pelaaja.merkkaaTulos(x, y, 3);
                upotus.merkkaaUponneeksi(osuttuLaiva);
            }
        } else if (tulos.equals("tyhja")) {
            vastustajan[10 * y + x].setVisible(false);
            Peli.pelaaja.merkkaaTulos(x, y, 1);
        }
        if (pvoittaa()) {
            peliKesken = false;
            ilmoitusikkuna = new IlmoitusPop(this, "Sinä voitit vuorolla " + moneskoVuoro + "!");
            upotus.naytaLaivat();
        }
        return osuiko;
    }

    /**
     * Suorittaa vastuksen ammunnan ja merkkaa tuloksen rakenteisiin.
     *
     * @return ammunnan tulos (true=osui)
     */
    private boolean ammuVastus() {
        boolean osuiko = false;
        int[] ammuttavanKoordinaatit = vastus.ammu();
        int ax = ammuttavanKoordinaatit[0];
        int ay = ammuttavanKoordinaatit[1];
        System.out.println(ax);
        System.out.println(ay);
        String vihollisenTulos = Peli.pelaaja.getRuudunTila(ax, ay);
        if (vihollisenTulos.equals("laiva")) {
            osuiko = true;
            Peli.pelaaja.getLauta().getLauta()[ay][ax].getLaiva().osuma();
            pelaajan[10 * ay + ax].setText("#");
            upotus.repaint();
            try {
                Thread.sleep(500);
            } catch (Exception c) {
                System.out.println("ohjelman tauko epaonnistui");
            }

            if (!Peli.pelaaja.getLauta().getLauta()[ay][ax].getLaiva().onkoUponnut()) {
                vastus.asetaTulos(2, ax, ay);
            } else {
                System.out.println("UPPOSI");
                vastus.asetaTulos(3, ax, ay);
            }
        } else {
            pelaajan[10 * ay + ax].setVisible(false);
            vastus.asetaTulos(1, ax, ay);
        }
        if (vvoittaa()) {
            peliKesken = false;
            upotus.asetaOmaTeksti("Tietokone voitti!");
            upotus.asetaVastuksenTeksti("Tietokone voitti!");
            ilmoitusikkuna = new IlmoitusPop(this, "Tietokone voitti vuorolla " + moneskoVuoro + "!");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (Peli.vastus.getRuudunTila(j, i).equals("laiva") && !Peli.pelaaja.onkoAmmuttu(j, i)) {
                        vastustajan[10 * i + j].setText("o");
                    }
                }
            }
        }
        return osuiko;
    }

    /**
     * Jos ihmispelaajan kaikki laivat ovat uponneet, palauttaa true
     *
     * @return boolean-arvo kertoo voittaako tietokonevastus, jos true niin
     * voittaa
     */
    public boolean vvoittaa() {
        Laiva[] laivat = pelaaja.getLaivat();
        boolean testi = true;
        for (Laiva laiva : laivat) {
            if (!laiva.onkoUponnut()) {
                testi = false;
            }
        }
        return testi;
    }

    /**
     * Jos tietokoneen kaikki laivat ovat uponneet, palauttaa true
     *
     * @return boolean-arvo kertoo voittaako pelaaja
     */
    public boolean pvoittaa() {
        Laiva[] laivat = vastus.getLaivat();
        boolean testi = true;
        for (Laiva laiva : laivat) {
            if (!laiva.onkoUponnut()) {
                testi = false;
            }
        }
        return testi;
    }

    public static Pelaaja getPelaaja() {
        return pelaaja;
    }

    public static Tietokonevastustaja getVastus() {
        return vastus;
    }

    public static void naytaLaudat() {
        System.out.println("");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (Peli.pelaaja.getRuudunTila(j, i).equals("laiva")) {
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
                if (Peli.vastus.getRuudunTila(j, i).equals("laiva")) {
                    System.out.print("#");

                } else {
                    System.out.print(".");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    /**
     * Kertoo, onko peli kesken
     *
     * @return Jos peli kesken palauttaa true, muuten false
     */
    public boolean onkoPeliKesken() {
        return peliKesken;
    }

    private void paivitaRakenteisiinAsetuksenOnnistuminen() {

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
    }

    public void aloitaAlusta() {
        upotus.aloitaAlusta();
        ilmoitusikkuna.setVisible(false);
    }
    public Laivanupotus getUpotus(){
        return upotus;
    }
}
