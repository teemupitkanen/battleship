package logiikka;

import javax.swing.JButton;
import kayttoliittyma.IlmoitusPop;
import kayttoliittyma.Laivanupotus;
import kayttoliittyma.Nappi;
import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

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
     * Kertoo onko pelissä tapahtunut jo ammuntaa, eli voiko uuden aloittaa
     * luovuttamatta
     */
    private boolean saakoKeskeyttaa;

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
        saakoKeskeyttaa = false;
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
                apu = alkuy;
                alkuy = loppuy;
                loppuy = apu;
            }
            if (alkuy > loppuy) {
                apu = alkuy;
                alkuy = loppuy;
                loppuy = apu;
                apu = alkux;
                alkux = loppux;
                loppux = apu;
            }
            if ((alkuy == loppuy && (loppux - alkux + 1) == asetettavanPituus) || (alkux == loppux && loppuy - alkuy + 1 == asetettavanPituus)) {
                if (pelaaja.asetaLaiva(alkux, alkuy, loppux, loppuy)) {
                    paivitaRakenteisiinAsetuksenOnnistuminen();
                } else {
                    if (pelaaja.getRuudunTila(alkux, alkuy).equals("tyhja")) {
                        pelaajan[10 * alkuy + alkux].setText("");
                    }
                    if (pelaaja.getRuudunTila(loppux, loppuy).equals("tyhja")) {
                        pelaajan[10 * loppuy + loppux].setText("");
                    }

                }
            } else {
                if (pelaaja.getRuudunTila(alkux, alkuy).equals("tyhja")) {
                    pelaajan[10 * alkuy + alkux].setText("");
                }
                if (pelaaja.getRuudunTila(loppux, loppuy).equals("tyhja")) {
                    pelaajan[10 * loppuy + loppux].setText("");
                }

            }
            upotus.asetaOmaTeksti("Aseta laivan (" + asetettavanPituus + ") alkupiste");
            if (asetettavanPituus == 0) {
                upotus.asetaOmaTeksti("Asetteluvaihe on loppunut. Ammu oikeanpuoleiseen ruudukkoon.");
                upotus.asetaVastuksenTeksti("Klikkaa ruutua ampuaksesi. Osumasta saa uuden vuoron.");
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
        saakoKeskeyttaa = true;
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
            saakoKeskeyttaa = false;
            upotus.asetaOmaTeksti("Sinä voitit!");
            upotus.asetaVastuksenTeksti("Sinä voitit!");
            double[] tilastot = haeJaPaivitaStatistiikat("voitto", moneskoVuoro);
            double voittoprosentti = tilastot[0];
            double omakeskimvuoro = tilastot[1];
            double vastkeskimvuoro = tilastot[2];
            int kierrokset = (int) tilastot[3];
            ilmoitusikkuna = new IlmoitusPop(this, "Sinä voitit vuorolla " + moneskoVuoro + "!", voittoprosentti, omakeskimvuoro, vastkeskimvuoro, kierrokset);
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
        String vihollisenTulos = Peli.pelaaja.getRuudunTila(ax, ay);
        if (vihollisenTulos.equals("laiva")) {
            osuiko = true;
            Peli.pelaaja.getLauta().getLauta()[ay][ax].getLaiva().osuma();
            pelaajan[10 * ay + ax].setText("#");
            upotus.repaint();
            try {
                Thread.sleep(500);
            } catch (Exception c) {
                System.out.println("ohjelman pysayttaminen hetkeksi epaonnistui");
            }

            if (!Peli.pelaaja.getLauta().getLauta()[ay][ax].getLaiva().onkoUponnut()) {
                vastus.asetaTulos(2, ax, ay);
            } else {
                vastus.asetaTulos(3, ax, ay);
            }
        } else {
            pelaajan[10 * ay + ax].setVisible(false);
            vastus.asetaTulos(1, ax, ay);
        }
        if (vvoittaa()) {
            peliKesken = false;
            saakoKeskeyttaa = false;
            upotus.asetaOmaTeksti("Tietokone voitti!");
            upotus.asetaVastuksenTeksti("Tietokone voitti!");
            double[] tilastot = haeJaPaivitaStatistiikat("tappio", moneskoVuoro);
            double voittoprosentti = tilastot[0];
            double omakeskimvuoro = tilastot[1];
            double vastkeskimvuoro = tilastot[2];
            int kierrokset = (int) tilastot[3];
            ilmoitusikkuna = new IlmoitusPop(this, "Tietokone voitti vuorolla " + moneskoVuoro + "!", voittoprosentti, omakeskimvuoro, vastkeskimvuoro, kierrokset);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (Peli.vastus.getRuudunTila(j, i).equals("laiva") && !Peli.pelaaja.onkoAmmuttu(j, i)) {
                        vastustajan[10 * i + j].setText("o");
                    }
                }
            }
            osuiko = false; // Jos peli loppui jo, ei anneta enää uutta vuoroa
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

    /**
     * Testailua helpottamaan kirjoitettu metodi, joka tulostaa molemmat
     * pelilaudat laivoineen
     */
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

    /**
     * Merkkaa käyttöliittymässä laivan pisteet x-merkillä. Pienentää
     * seuraavaksi asetettavan laivan kokoa.
     */
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

    /**
     * Aloittaa pelin alusta
     */
    public void aloitaAlusta() {
        upotus.aloitaAlusta();
        ilmoitusikkuna.setVisible(false);
    }

    /**
     * Palauttaa peliin liittyvän laivanupotus-käyttöliittymän
     *
     * @return pelin käyttöliittymä
     */
    public Laivanupotus getUpotus() {
        return upotus;
    }

    /**
     * tallentaa pelin loppumisvuoron ja tuloksen tiedostoon, lukee tiedostosta
     * aiemmat tulokset ja palauttaa voittoprosentin ja keskimääräisen
     * loppumisvuoron.
     *
     * @param tulos tämän pelin tulos, "voitto"/"tappio"
     * @param vuoro tämän pelin päättymisvuoro
     * @return voittoprosentti, keskimääräinen loppunmisvuoro
     */
    public double[] haeJaPaivitaStatistiikat(String tulos, int vuoro){
        File file= new File("savefile.txt");
        ArrayList<Integer> tulokset = new ArrayList();
        ArrayList<Integer> vuorot = new ArrayList();
        Scanner skanneri = null;
        int voittojenmaara = 0;
        double voittoprosentti;
        int omavuorosumma = 0;
        int vastvuorosumma = 0;
        double omakeskimvuoro;
        double vastkeskimvuoro;
        int pelatutKierrokset;
        try {
            skanneri = new Scanner(file);
        } catch (Exception e) {
            System.out.println("tiedostoa ei loytynyt");
        }
        while (skanneri.hasNext()) {
            tulokset.add(Integer.parseInt(skanneri.next()));
            vuorot.add(Integer.parseInt(skanneri.next()));
        }
        if (tulos.equals("tappio")) {
            tulokset.add(0);
        } else {
            tulokset.add(1);
        }
        vuorot.add(vuoro);



        PrintWriter kirj = null;
        try {
            kirj = new PrintWriter(file);
        } catch (Exception c) {
            System.out.println("tiedostoa ei löytynyt");
        }
        for (int i = 0; i < tulokset.size(); i++) {
            if (tulokset.get(i) == 1) {
                voittojenmaara++;
                omavuorosumma = omavuorosumma + vuorot.get(i);
            } else {
                vastvuorosumma = vastvuorosumma + vuorot.get(i);
            }
            kirj.print(tulokset.get(i) + " ");
            kirj.println(vuorot.get(i));
        }
        kirj.close();
        voittoprosentti = 100 * (1.0 * voittojenmaara / tulokset.size());
        omakeskimvuoro = 1.0 * omavuorosumma / voittojenmaara;
        vastkeskimvuoro = 1.0 * vastvuorosumma / (vuorot.size() - voittojenmaara);
        pelatutKierrokset = tulokset.size();
        double[] palautettava = {voittoprosentti, omakeskimvuoro, vastkeskimvuoro, pelatutKierrokset};
        return palautettava;
    }

    /**
     * Avaa varoitus-pop-upin pelin lopettamisesta tai resetoinnista, joka
     * aiheuttaisi häviön.
     *
     * @param lopetetaan kertoo yritetäänkö resetoida vai lopettaa
     */
    public void uusiVaroitusKeskeyttamisesta(boolean lopetetaan) {
        if (lopetetaan) {
            ilmoitusikkuna = new IlmoitusPop(this, "Haluatko varmasti lopettaa? Häviät pelin.", true);
        } else {
            ilmoitusikkuna = new IlmoitusPop(this, "Haluatko varmasti luovuttaa? Häviät pelin.", false);
        }
    }

    /**
     * Kertoo onko pelissä jo ammuttu (eli voidaanko keskeyttää häviämättä)
     *
     * @return true jos on ammuttu
     */
    public boolean onkoJoAmmuttu() {
        return saakoKeskeyttaa;
    }

    /**
     * Paivittää tilastoihin tappion siinä tapauksessa, että peli lopetetaan
     * kesken.
     */
    public void paivitaKeskeytettyTilastoihin() {
        haeJaPaivitaStatistiikat("tappio", moneskoVuoro);
    }
}
