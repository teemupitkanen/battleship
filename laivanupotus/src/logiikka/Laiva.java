package logiikka;

public class Laiva {

    private int koko;
    private int jaljella;
    private Ruutu[] ruudut;
    private int annetutRuudut;

    public Laiva(int pituus) {
        koko = pituus;
        jaljella = koko;
        ruudut = new Ruutu[koko];
        annetutRuudut = 0;
    }

    public boolean onkoUponnut() {
        if (jaljella == 0) {
            return true;

        } else {
            return false;
        }
    }

    public void osuma() {
        if (jaljella > 0) {
            jaljella--;
        }
    }
    
    public Ruutu[] getRuudut(){
        return ruudut;
    }

    public void lisaaRuutu(Ruutu ruutu) {
        if (annetutRuudut < koko) {
            ruudut[annetutRuudut] = ruutu;
            annetutRuudut++;
        }
    }

}
