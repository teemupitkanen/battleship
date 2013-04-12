
package logiikka;
/**
 * Laiva on laivanupotuspelissä yksittäistä laivaa esittävä looginen kokonaisuus.
 * Olennaisesti laiva tuntee oman sijaintinsa, ja tietää onko se uponnut vai ei.
 * @author teempitk
 */
public class Laiva {

    /**
     *Ilmaisee laivan pituuden, eli sen ruutujen määrän.
     */
    private int koko;
    /**
     * Ilmaisee niiden laivan ruutujen määrän, joita ei vielä ole upotettu.
     */
    private int jaljella;
    /*
     * Palauttaa taulukon kaikista ruuduista, joiden alueella laiva sijaitsee.
     */
    private Ruutu[] ruudut;
    /**
     * Kertoo montako laivan ruuduista on jo lisätty lisaaRuutu-metodilla
     */
    private int annetutRuudut;
    /**
     * Alustaa paikalliset muuttujat alivan pituuden pohjalta. 
     */
    public Laiva(int pituus) {
        koko = pituus;
        jaljella = koko;
        ruudut = new Ruutu[koko];
        annetutRuudut = 0;
    }
    /**
     * Jos laiva on uponnut, palauttaa true. Muuten false.
     */
    public boolean onkoUponnut() {
        if (jaljella == 0) {
            return true;

        } else {
            return false;
        }
    }
    /**
     * Osuma vähentää laivan pinnalla olevia ruutuja yhdellä.
     */
    public void osuma() {
        if (jaljella > 0) {
            jaljella--;
        }
    }
    /**
     * Metodi palauttaa taulukon ruuduista, joissa laiva sijaitsee.
     * @return taulukko laivan ruuduista
     */
    public Ruutu[] getRuudut() {
        return ruudut;
    }
    /**
     * Lisää parametrina annetun ruudun laivan ruutuihin, kunhan sillä ei jo ole pituutensa verran ruutuja.
     * @param ruutu lisättävä ruutu
     */
    public void lisaaRuutu(Ruutu ruutu) {
        if (annetutRuudut < koko) {
            ruudut[annetutRuudut] = ruutu;
            annetutRuudut++;
        }
    }
}
