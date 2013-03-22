package logiikka;

public class Laiva {

    private int koko;
    private int jaljella;

    public Laiva(int pituus) {
        koko=pituus;
        jaljella=koko;
    }

    public boolean onkoUponnut() {
        if (jaljella==0) {
            return true;

        } else {
            return false;
        }
    }
    public void osuma(){
        jaljella--;
    }
    
}
