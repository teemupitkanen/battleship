package logiikka;
/**
 * Peli-luokka on laivanupotuksen loogisen toiminnan ylin luokka. Peli luo pelaajan ja tietokonevastuksen,
 * ja siltä voi kysyä onko toinen pelaajista mahdollisesti voittanut pelin.
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
 * luo uuden pelaaja- ja tietokonevastustajaolion
 */
    public Peli() {
        pelaaja = new Pelaaja();
        vastus = new Tietokonevastustaja();
    }

/**
 * Jos ihmispelaajan kaikki laivat ovat uponneet, palauttaa true
 * @return boolean-arvo kertoo voittaako tietokonevastus, jos true niin voittaa
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
    
    public static Pelaaja getPelaaja(){
        return pelaaja;
    }
    
    public static Tietokonevastustaja getVastus(){
        return vastus;
    }

}
