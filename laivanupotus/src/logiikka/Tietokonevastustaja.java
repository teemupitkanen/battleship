package logiikka;

import java.util.ArrayList;

/**
 * Luokka, joka sisältää tietokonevastustajan laivojen asettelun ja vastustajan
 * ampumisen logiikan.
 *
 * @author teempitk
 */
public class Tietokonevastustaja {

    /**
     * Tietokonevastustajan oma pelilauta
     */
    private Pelilauta lauta;
    /**
     * Lista avstustajan laivoista
     */
    private Laiva[] laivat;
    /**
     * Vastuksen kirjanpito tapahtuneesta ammunnasta
     */
    private int[][] kohdelauta;  // 0=ei ammuttu, 1=ohi, 2=osui, 3=uponnut
    /**
     * Lista mahdollisten seuraavaksi ammuttavien pisteiden x-koordinaateista
     */
    private ArrayList<Integer> ammuttavanx;
    /**
     * Lista mahdollisten seuraavaksi ammuttavien pisteiden y-koordinaateista
     */
    private ArrayList<Integer> ammuttavany;
    /**
     * Tieto siitä, onko johonkin laivaan jo osuttu, mutta sitä ei ole upotettu
     */
    private boolean upotusKesken;
    /**
     * Jos laivaan on saatu useampi osuma, ja sen suunta pelilaudalla on siten
     * tiedossa, tämän muuttujan arvo on true
     */
    private boolean suuntaTiedossa;
    /**
     * Jos laiva, jonka upotus on kesken, on vaakasuunnassa, tämä muuttuja on
     * true
     */
    private boolean upotettavaVaaka;
    /**
     * Muuttuja auttaa tehostamaan ampuma-algoritmia. Konstruktori arpoo arvon.
     * Jos 0, algoritmi ampuu rivillä 0 parillisia ruutuja, rivillä 1
     * parittomia... jos 1 niin päin vastoin.
     */
    private int ammutaankoParillisia;
    /**
     * Nyt upotuksessa olevan laivan pienin x-koordinaatti, johon on osuttu
     */
    private int minx;
    /**
     * Nyt upotuksessa olevan laivan suurin x-koordinaatti, johon on osuttu
     */
    private int maxx;
    /**
     * Nyt upotuksessa olevan laivan pienin y-koordinaatti, johon on osuttu
     */
    private int miny;
    /**
     * Nyt upotuksessa olevan laivan suurin y-koordinaatti, johon on osuttu
     */
    private int maxy;

    /**
     * Luo vastustajalle pelilaudan, luo ja asettaa laivat ja alustaa muuttujien
     * arvot.
     */
    public Tietokonevastustaja() {

        lauta = new Pelilauta();
        laivat = new Laiva[5];
        laivat[0] = asetaLaiva(5);
        laivat[1] = asetaLaiva(4);
        laivat[2] = asetaLaiva(3);
        laivat[3] = asetaLaiva(3);
        laivat[4] = asetaLaiva(2);

        kohdelauta = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                kohdelauta[i][j] = 0;
            }
        }
        ammutaankoParillisia = (int) (2 * Math.random());
        minx = 9;
        maxx = 0;
        miny = 9;
        maxy = 0;
        upotusKesken = false;
        suuntaTiedossa = false;
        upotettavaVaaka = false;

    }

    /**
     * Päättää mitä ruutua ammutaan. Tarkistaa onko upotus kesken, ja jos on
     * ampuu loogisesti. Muuten ampuu satunnaista.
     *
     * @return Palauttaa ammuttavan ruudun koordinaatit {x,y}
     */
    public int[] ammu() {
        ammuttavanx = new ArrayList();
        ammuttavany = new ArrayList();


        if (upotusKesken) {
            if (suuntaTiedossa) {
                if (upotettavaVaaka) {
                    return upotaVaakasuunnassaOleva();
                } else {
                    return upotaPystysuunnassaOleva();
                }
            } else {
                return osumanJalkeenArvaaSuunta();
            }
        }

        int x = 0;
        int y = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((i + j + ammutaankoParillisia) % 2 == 0 && kohdelauta[i][j] == 0) {
                    ammuttavanx.add(j);
                    ammuttavany.add(i);
                }
            }
        }
        int indeksi = (int) (ammuttavanx.size() * Math.random());

        x = ammuttavanx.get(indeksi);
        y = ammuttavany.get(indeksi);

        int[] palautettava = {x, y};
        return palautettava;
    }

    /**
     * Metodi palauttaa ruudun upotettavan laivan toisesta päästä. Jos metodia
     * kutsutaan, pitää laivan suunnan olla tiedossa.
     *
     * @return ammuttavan pisteen koordinaatit
     */
    public int[] upotaVaakasuunnassaOleva() {
        Double kumpiPaa = Math.random();
        if (maxx == 9 || kohdelauta[miny][maxx + 1] == 1 || (kumpiPaa < 0.5 && minx - 1 >= 0 && kohdelauta[miny][minx - 1] == 0)) {
            int[] palautettava = {minx - 1, miny};
            return palautettava;
        } else {
            int[] palautettava = {maxx + 1, miny};
            return palautettava;
        }

    }

    /**
     * Metodi palauttaa ruudun upotettavan laivan toisesta päästä. Jos metodia
     * kutsutaan, pitää laivan suunnan olla tiedossa.
     *
     * @return ammuttavan pisteen koordinaatit
     */
    public int[] upotaPystysuunnassaOleva() {
        Double kumpiPaa = Math.random();
        if (maxy == 9 || kohdelauta[maxy + 1][minx] == 1 || (kumpiPaa < 0.5 && miny - 1 >= 0 && kohdelauta[miny - 1][minx] == 0)) {
            int[] palautettava = {minx, miny - 1};
            return palautettava;
        } else {
            int[] palautettava = {minx, maxy + 1};
            return palautettava;
        }
    }

    /**
     * Metodia kutsutaan seuraavalla ampumisvuorolla siitä, kun uuteen
     * vastustajan laivaan on saatu ensimmäinen osuma. Metodi arpoo jonkun
     * ruudun osumaruudun vierestä, johon ei vielä ole ammuttu.
     *
     * @return ammuttavan pisteen koordinaatit
     */
    public int[] osumanJalkeenArvaaSuunta() {
        System.out.println("arvataan suuntaa");
        while (true) {
            int suunta = (int) (4 * Math.random()); // 0=N, 1=E, 2=S, 3=W
            if (suunta == 0 && miny - 1 >= 0) {
                if (kohdelauta[miny - 1][minx] == 0) {
                    int[] palautettava = {minx, miny - 1};
                    System.out.println("Suunta: " + suunta);
                    System.out.println("palauttaa:" + minx + "," + (miny - 1));
                    return palautettava;
                }
            }
            if (suunta == 1 && minx + 1 <= 9) {
                if (kohdelauta[miny][minx + 1] == 0) {
                    int[] palautettava = {minx + 1, miny};
                    System.out.println("Suunta: " + suunta);
                    System.out.println("palauttaa:" + (minx + 1) + "," + miny);
                    return palautettava;
                }
            }
            if (suunta == 2 && miny + 1 <= 9) {
                if (kohdelauta[miny + 1][minx] == 0) {
                    int[] palautettava = {minx, miny + 1};
                    System.out.println("Suunta: " + suunta);
                    System.out.println("palauttaa:" + minx + "," + (miny + 1));
                    return palautettava;
                }
            }
            if (suunta == 3 && minx - 1 >= 0) {
                if (kohdelauta[miny][minx - 1] == 0) {
                    int[] palautettava = {minx - 1, miny};
                    System.out.println("Suunta: " + suunta);
                    System.out.println("palauttaa:" + (minx - 1) + "," + miny);
                    return palautettava;
                }
            }
        }
    }

    /**
     * Metodi asettaa laivan satunnaiseen paikkaan vastustajan pelilaudalle
     *
     * @param pituus asetettavan laivan pituus
     * @return asetettu laiva
     */
    public Laiva asetaLaiva(int pituus) {
        int alkux;
        int alkuy;
        while (true) {
            alkux = (int) (9 * Math.random());
            alkuy = (int) (9 * Math.random());
            int suunta = (int) (4 * Math.random()); // suunnat: 0=p 1=i 2=e 3=l

            int loppuy;
            int loppux;

            if (suunta == 0) {
                loppux = alkux;
                loppuy = alkuy - pituus + 1;
            } else if (suunta == 1) {
                loppux = alkux + pituus - 1;
                loppuy = alkuy;

            } else if (suunta == 2) {
                loppux = alkux;
                loppuy = alkuy + pituus - 1;

            } else {
                loppux = alkux - pituus + 1;
                loppuy = alkuy;

            }
            if (loppux >= 0 && loppuy >= 0 && loppux <= 9 && loppuy <= 9) {
                if (lauta.asetaLaiva(alkux, alkuy, loppux, loppuy)) {
                    break;
                }
            }

        }
        return lauta.getLauta()[alkuy][alkux].getLaiva();
    }

    public Laiva[] getLaivat() {
        return laivat;
    }

    public Pelilauta getLauta() {
        return lauta;
    }

    public String getRuudunTila(int x, int y) {
        return lauta.getLauta()[y][x].getTila();
    }

    /**
     * Asettaa ampumisen tuloksen kohdelautaan, jotta tulos voidaan ottaa
     * huomioon seuraavan kierroksen ammuttavan ruudun valinnassa. Jos osuu
     * laivaan, päivittää myös tarvittavat muuttujat, esim upotusKesken jne.
     *
     * @param tulos suoritetun ammunnan tulos
     * @param x ammutun ruudun x-koordinaatti
     * @param y ammutun ruudun y-koordinaatti
     */
    public void asetaTulos(int tulos, int x, int y) {
        kohdelauta[y][x] = tulos;

        if (tulos == 2) {

            if (x < minx) {
                minx = x;
            }
            if (x > maxx) {
                maxx = x;
            }
            if (y < miny) {
                miny = y;
            }
            if (y > maxy) {
                maxy = y;
            }


            if (!suuntaTiedossa && (minx != maxx || miny != maxy)) {
                System.out.println("suunta tiedossa");
                suuntaTiedossa = true;

                if (minx < maxx) {
                    upotettavaVaaka = true;
                } else {
                    upotettavaVaaka = false;
                }
            }
            upotusKesken = true;
        }

        if (tulos == 3) {
            upotusKesken = false;
            suuntaTiedossa = false;
            merkkaaUppoaminenKohdelautaan();
            minx = 9;
            maxx = 0;
            miny = 9;
            maxy = 0;
        }

    }

    /**
     * Kun ampuminen upottaa laivan, merkataan sen viereiset ruudut tyhjiksi
     * kohdelautaan, koska niissä ei voi sijaita laivaa. Samalla laivan kaikki
     * sijaintikoordinaatit merkitään uponneeksi.
     */
    public void merkkaaUppoaminenKohdelautaan() {
        for (int i = minx; i <= maxx; i++) {
            kohdelauta[miny][i] = 3;
            if (miny - 1 >= 0) {
                kohdelauta[miny - 1][i] = 1;
            }
            if (maxy + 1 <= 9) {
                kohdelauta[maxy + 1][i] = 1;
            }
        }
        for (int i = miny; i <= maxy; i++) {
            kohdelauta[i][minx] = 3;
            if (minx - 1 >= 0) {
                kohdelauta[i][minx - 1] = 1;
            }
            if (maxx + 1 <= 9) {
                kohdelauta[i][maxx + 1] = 1;
            }
        }
    }
}