package logiikka;

import logiikka.Ruutu;

/**
 * Pelialuta-luokassa ovat tärkeimmät pelaajan tai vastustajan omaan pelilautaan
 * liittyvät tiedot ja metodit.
 *
 * @author teempitk
 */
public class Pelilauta {

    /**
     * Pitää muistissa taulukon kaikista pelilautaan kuuluvista ruuduista.
     */
    private Ruutu[][] lauta;

    /**
     * Luo uuden 10*10 Ruutu-taulukon, ja asettaa ruuduille niiden naapurit.
     */
    public Pelilauta() {
        lauta = new Ruutu[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                lauta[i][j] = new Ruutu(j, i);
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((i - 1) >= 0) {
                    lauta[i][j].setNaapuri(lauta[i - 1][j]);
                }
                if ((i + 1) <= 9) {
                    lauta[i][j].setNaapuri(lauta[i + 1][j]);
                }
                if ((j - 1) >= 0) {
                    lauta[i][j].setNaapuri(lauta[i][j - 1]);
                }
                if ((j + 1) <= 9) {
                    lauta[i][j].setNaapuri(lauta[i][j + 1]);
                }

            }
        }
    }

    /**
     * Asettaa laivan pelilaudalle. Jos asettelu ei ole sallittu, asetusta ei
     * tapahdu ja metodi palauttaa false. Asettelu ei ole sallittu, jos laivat
     * koskevat toisiinsa (kulmittain koskeminen on sallittu) tai laivat ovat
     * päällekkäin.
     *
     * @param alkux asetettavan laivan alkupään x-koordinaatti
     * @param alkuy asetettavan laivan alkupään y-koordinaatti
     * @param loppux asetettavan laivan loppupään x-koordinaatti
     * @param loppuy asetettavan laivan loppupään y-koordinaatti
     * @return Jos asettelu onnistuu palauttaa true, muuten false.
     */
    public boolean asetaLaiva(int alkux, int alkuy, int loppux, int loppuy) {
        if (voikoAsettaa(alkux, alkuy, loppux, loppuy) == false) {
            return false;
        }

        int apu;
        if (loppux < alkux) {
            apu = loppux;
            loppux = alkux;
            alkux = apu;
        }
        if (loppuy < alkuy) {
            apu = loppuy;
            loppuy = alkuy;
            alkuy = apu;
        }

        Laiva laiva = new Laiva((loppuy - alkuy) + (loppux - alkux) + 1);

        if (alkux == loppux) {
            for (int i = alkuy; i <= loppuy; i++) {         // Jos sijainti sopii, asetetaan laiva.
                lauta[i][alkux].setLaiva(laiva);
            }
        }
        if (alkuy == loppuy) {
            for (int j = alkux; j <= loppux; j++) {
                lauta[alkuy][j].setLaiva(laiva);
            }
        }

        return true;
    }

    public Ruutu[][] getLauta() {
        return lauta;
    }

    /**
     * Tarkistaa, saako koordinaatteihin asettaa laivan
     *
     * @param alkux asetettavan laivan alkupään x-koordinaatti
     * @param alkuy asetettavan laivan alkupään y-koordinaatti
     * @param loppux asetettavan laivan loppupään x-koordinaatti
     * @param loppuy asetettavan laivan loppupään y-koordinaatti
     * @return jos asettelu sallittu, palauttaa true. Muuten false.
     */
    public boolean voikoAsettaa(int alkux, int alkuy, int loppux, int loppuy) {
        if (alkux < 0 || alkuy < 0 || loppux < 0 || loppuy < 0 || alkux > 9 || alkuy > 9 || loppux > 9 || loppuy > 9) {
            return false;       // jos indeksit eivät osu laudalle
        }

        int apu;
        if (loppux < alkux) {
            apu = loppux;
            loppux = alkux;
            alkux = apu;
        }
        if (loppuy < alkuy) {
            apu = loppuy;
            loppuy = alkuy;
            alkuy = apu;
        }

        for (int i = alkuy; i <= loppuy; i++) {                          // tarkastetaan, ettei "sivuilla" tai "alla" laivaa
            if (alkux - 1 > -1 && lauta[i][alkux - 1].getTila().equals("laiva")) {
                return false;
            }
            if (alkux + 1 < 10 && lauta[i][alkux + 1].getTila().equals("laiva")) {
                return false;
            }
            if (lauta[i][alkux].getTila().equals("laiva")) {
                return false;
            }
        }
        for (int j = alkux; j <= loppux; j++) {
            if (alkuy - 1 > -1 && lauta[alkuy - 1][j].getTila().equals("laiva")) {
                return false;
            }
            if (alkuy + 1 < 10 && lauta[alkuy + 1][j].getTila().equals("laiva")) {
                return false;
            }
            if (lauta[alkuy][j].getTila().equals("laiva")) {
                return false;
            }
        }

        if (alkuy - 1 > -1 && lauta[alkuy - 1][alkux].getTila().equals("laiva")) {            // tarkistetaan vielä laivan "päät"
            return false;
        }
        if (loppuy + 1 < 10 && lauta[loppuy + 1][alkux].getTila().equals("laiva")) {
            return false;
        }
        if (alkux - 1 > -1 && lauta[alkuy][alkux - 1].getTila().equals("laiva")) {
            return false;
        }
        if (loppux + 1 < 10 && lauta[alkuy][loppux + 1].getTila().equals("laiva")) {
            return false;
        }
        return true;
    }
}
