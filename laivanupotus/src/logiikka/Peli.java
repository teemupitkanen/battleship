package logiikka;

public class Peli {

    private static Pelaaja pelaaja;
    private static Tietokonevastustaja vastus;

    public Peli() {
        pelaaja = new Pelaaja();
        vastus = new Tietokonevastustaja();
    }


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
