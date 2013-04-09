package logiikka;

public class Ruutu {

    /**
     * String-arvo kertoo onko ruudussa "laiva" vai "tyhja"
     */
    private String tila;
    /**
     * palauttaa ruudussa sijaitsevan laivan
     */
    private Laiva laiva;
    /**
     * kertoo ruudun sijainnin vaakasuunnassa
     */
    private int x;
    /**
     * kertoo ruudun sijainnin pystysuunnassa
     */
    private int y;
    /**
     * palauttaa taulukon ruudun vieressä sijaitsevista ruuduista
     */
    private Ruutu[] naapurit;
    /**
     * kertoo ruudulle lisättyjen naapureiden määrän
     */
    private int lisatytNaapurit;

    /**
     * Alustaa ruudun tuntemaan koordinaattinsa pelilaudalla
     *
     * @param x ruudun x-koordinaatti
     * @param y ruudun y-koordinaatti
     */
    public Ruutu(int x, int y) {
        this.x = x;
        this.y = y;
        tila = "tyhja";
        lisatytNaapurit = 0;
        if ((x == 0 && y == 0) || (x == 0 && y == 9) || (x == 9 && y == 0) || (x == 9 && y == 9)) {
            naapurit = new Ruutu[2];
        } else if (x == 0 || y == 0 || x == 9 || y == 9) {
            naapurit = new Ruutu[3];
        } else {
            naapurit = new Ruutu[4];
        }
    }

    public String getTila() {
        return tila;
    }

    public void setNaapuri(Ruutu ruutu) {
        if (lisatytNaapurit < naapurit.length) {
            naapurit[lisatytNaapurit] = ruutu;
            lisatytNaapurit++;
        }
    }

    /**
     * Metodi palauttaa taulukon ruudun vieressä sijaitsevista ruuduista.
     *
     * @return taulukko Naapuriruuduista
     */
    public Ruutu[] getNaapurit() {
        return naapurit;
    }
    /**
     * lisää ruutuun tiedon siinä sijaitsevasta laivasta
     * @param laiva ruutuun lisättävä laiva
     */
    public void setLaiva(Laiva laiva) {
        this.laiva = laiva;
        tila = "laiva";
        laiva.lisaaRuutu(this);
    }

    /**
     * Jos ruudussa ei ole laivaa, palauttaa null
     *
     * @return Ruudussa sijaitseva laiva
     */
    public Laiva getLaiva() {
        return laiva;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }
}