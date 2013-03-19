package logiikka;

public class Laiva {

    private int koko;
    private int jaljella;

    public Laiva(int pituus) {
        koko=pituus;
    }

    public boolean onkoUponnut() {
        if (jaljella < 1) {
            return true;

        } else {
            return false;
        }
    }
    public void osuma(){
        jaljella--;
    }
    
}
