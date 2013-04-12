package logiikka;

/**
 * Pelaaja-luokka kuvaa laivanupotuspelin ihmispelaajaa. Pelaajan toiminta
 * tapahtuu pääosin graafisessa käyttöliittymässä, pelaaja-luokan keskeisin
 * tehtävä onkin hallinnoida pelaajan omaa pelilautaa.
 *
 * @author teempitk
 */
public class Pelaaja {

    /**
     * Pelaajan oma pelilauta, jolle hän asettaa laivansa ja jota vastus ampuu
     */
    private Pelilauta lauta;
    /**
     * Lista kaikista pelaajan omista laivoista.
     */
    private Laiva[] laivat;
    /**
     * Kohdelauta pitää kirjaa pelaajan ampumatoiminnasta vihollisen laudalle.
     * Tämän avulla saadaan esim. varmistettua, ettei samaa ruutua ammuta
     * useasti.
     */
    private int[][] kohdelauta;  // 0=ei ammuttu, 1=ohi, 2=osui, 3=uponnut
    /**
     * asetetutLaivat pitää kirjaa asetteluvaiheessa jo asetettujen laivojen
     * määrästä.
     */
    private int asetetutLaivat;

    /**
     * Konstruktori luo pelaajalle uuden tyhjän pelilaudan, uuden tyhjän
     * taulukon laivoja varten, alustaa kohdelaudan ja asetetutLaivat-muuttujan
     * nolliksi.
     */
    public Pelaaja() {
        lauta = new Pelilauta();
        laivat = new Laiva[5];
        kohdelauta = new int[10][10];
        asetetutLaivat = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                kohdelauta[i][j] = 0;
            }
        }
    }
    /**
     * Merkkaa kohdelautaan ampumisen tuloksen, jotta se voidaan ottaa huomioon tulevilla ampumakierroksilla.
     * @param x ammutun ruudun x-koordinaatti
     * @param y ammutun ruudun y-koordinaatti
     * @param tulos ammunnan tulos, eli ohi(1), osui(2) tai osui ja upposi(3)
     */
    public void merkkaaTulos(int x, int y, int tulos) {
        kohdelauta[y][x] = tulos;
    }
    /**
     * Terkistaa, onko pelaaja jo ampunut tiettyyn ruutuun
     * @param x kysytyn ruudun x-koordinaatti (vaaka)
     * @param y kysytyn ruudun y-koordinaatti (pysty)
     * @return Jos ruutuun on ammuttu, palauttaa true. Muuten false.
     */
    public boolean onkoAmmuttu(int x, int y) {
        if (kohdelauta[y][x] == 0) {
            return false;

        } else {
            return true;
        }
    }
/**
 * Asettaa laivan pelaajan pelilaudalle.Tarkistaa samalla, onko asettelu sallittu (ei esim mene toisen laivan päälle)
 * @param alkux asetettavan laivan alkupään x-koordinaatti
 * @param alkuy asetettavan laivan alkupään y-koordinaatti
 * @param loppux asetettavan laivan loppupään x-koordinaatti
 * @param loppuy asetettavan laivan loppupään y-koordinaatti
 * @return Jos laivan asetus onnistui, palauttaa true. Muuten false.
 */
    public boolean asetaLaiva(int alkux, int alkuy, int loppux, int loppuy) {
        if (asetetutLaivat < 5 && lauta.asetaLaiva(alkux, alkuy, loppux, loppuy)) {
            laivat[asetetutLaivat] = lauta.getLauta()[alkuy][alkux].getLaiva();
            asetetutLaivat++;
            return true;
        } else {
            return false;
        }
    }

    public Laiva[] getLaivat() {
        return laivat;
    }

    public Pelilauta getLauta() {
        return lauta;
    }
/**
 * Palauttaa pelaajan oman pelilaudan jonkun ruudun tilan, eli onko ruudussa laiva vai onko se tyhjä.
 * @param x kysyttävän ruudun x-koordinaatti
 * @param y kysyttävän ruudun y-koordinaatti
 * @return Jos ruudussa on laiva, palauttaa "laiva". Muuten "tyhja".
 */
    public String getRuudunTila(int x, int y) {
        return lauta.getLauta()[y][x].getTila();
    }
}