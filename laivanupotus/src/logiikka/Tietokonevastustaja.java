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
     * kertoo mikä on lyhin vielä upottamaton laiva ampuma-algoritmin
     * tehostamiseksi
     */
    private int lyhinUpottamaton;
    /**
     * true jos 2 ruudun laiva uponnut
     */
    private boolean laiva2uponnut;
    /**
     * true jos 1. 3 ruudun laiva uponnut
     */
    private boolean laiva3auponnut;
    /**
     * true jos 2. 3 ruudun laiva uponnut
     */
    private boolean laiva3buponnut;
    /**
     * true jos 4 ruudun laiva uponnut
     */
    private boolean laiva4uponnut;
    /**
     * true jos 5 ruudun laiva uponnut
     */
    private boolean laiva5uponnut;

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
        lyhinUpottamaton = 2;
        laiva2uponnut = false;
        laiva3auponnut = false;
        laiva3buponnut = false;
        laiva4uponnut = false;
        laiva5uponnut = false;

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
                if ((i + j + ammutaankoParillisia) % 2 == 0 && kohdelauta[i][j] == 0 && mahtuukoLyhin(j, i)) {
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
        while (true) {
            int suunta = (int) (4 * Math.random()); // 0=N, 1=E, 2=S, 3=W
            if (suunta == 0 && miny - 1 >= 0) {
                if (kohdelauta[miny - 1][minx] == 0) {
                    int[] palautettava = {minx, miny - 1};
                    return palautettava;
                }
            }
            if (suunta == 1 && minx + 1 <= 9) {
                if (kohdelauta[miny][minx + 1] == 0) {
                    int[] palautettava = {minx + 1, miny};
                    return palautettava;
                }
            }
            if (suunta == 2 && miny + 1 <= 9) {
                if (kohdelauta[miny + 1][minx] == 0) {
                    int[] palautettava = {minx, miny + 1};
                    return palautettava;
                }
            }
            if (suunta == 3 && minx - 1 >= 0) {
                if (kohdelauta[miny][minx - 1] == 0) {
                    int[] palautettava = {minx - 1, miny};
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
            int upotetunPituus = (maxx - minx + maxy - miny + 2); 
            lisaaUponneisiin(upotetunPituus);
            minx = 9;
            maxx = 0;
            miny = 9;
            maxy = 0;
        }

    }

    /**
     * Lisaa laivan uppoamisen listaan uponneista
     *
     * @param pituus uponneen laivan pituus
     */
    private void lisaaUponneisiin(int pituus) {
        switch (pituus) {
            case (2):
                laiva2uponnut = true;
                break;
            case (3):
                if (laiva3auponnut) {
                    laiva3buponnut = true;
                } else {
                    laiva3auponnut = true;
                }
                break;
            case (4):
                laiva4uponnut = true;
                break;
            case (5):
                laiva5uponnut = true;
                break;
        }
        if (laiva2uponnut) {
            lyhinUpottamaton = 3;
            if (laiva3auponnut && laiva3buponnut) {
                lyhinUpottamaton = 4;
                if (laiva4uponnut) {
                    lyhinUpottamaton = 5;
                }
            }
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

    /**
     * Tarkistaa mahtuuko lyhin löytämätön laiva ruutuun
     *
     * @param x kysytty x
     * @param y kysytty y
     * @return true jos mahtuu, muuten false
     */
    private boolean mahtuukoLyhin(int x, int y) {
        if (tilaaPysty(x, y) >= lyhinUpottamaton || tilaaVaaka(x, y) >= lyhinUpottamaton) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tarkistaa paljonko kohdelaudassa on tilaa pystysuunnassa annetusta
     * ruudusta
     *
     * @param x kysytty x
     * @param y kysytty y
     * @return paljonko tilaa
     */
    private int tilaaPysty(int x, int y) {
        int tilaa = 1;
        for (int j = y - 1; j >= 0; j--) {
            if (kohdelauta[j][x] != 0) {
                break;
            }
            tilaa++;
        }
        for (int j = y + 1; j <= 9; j++) {
            if (kohdelauta[j][x] != 0) {
                break;
            }
            tilaa++;
        }
        return tilaa;
    }

    /**
     * Tarkistaa paljonko kohdelaudassa on tilaa vaakasuunnassa annetusta
     * ruudusta
     *
     * @param x kysytty x
     * @param y kysytty y
     * @return paljonko tilaa
     */
    private int tilaaVaaka(int x, int y) {
        int tilaa = 1;
        for (int i = x - 1; i >= 0; i--) {
            if (kohdelauta[y][i] != 0) {
                break;
            }
            tilaa++;
        }
        for (int i = x + 1; i <= 9; i++) {
            if (kohdelauta[y][i] != 0) {
                break;
            }
            tilaa++;
        }
        return tilaa;
    }
}