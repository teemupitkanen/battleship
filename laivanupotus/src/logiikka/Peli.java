package logiikka;

public class Peli {
    private static Pelaaja pelaaja;
    private static Tietokonevastustaja vastus;

    public static void main(String[] args) {
        pelaaja = new Pelaaja();
        vastus = new Tietokonevastustaja();

        while (true) {
            while (true) {
                if (!pelaaja.pelaa()) {
                    break;
                }
            }
            if (pvoittaa()) {                   // LISATTAVA! TARKISTETTAVA VOITTO USEIDEN OSUMIEN VALILLA!
                System.out.println("Sinä voitit!");
                break;
            }
            while (true) {
                if (!vastus.pelaa()) {
                    break;
                }
            }
            if (vvoittaa()) {
                System.out.println("Tietokone voitti!");
                break;
            }
        }
    }

    private static boolean vvoittaa() {
        Laiva[] laivat=pelaaja.getLaivat();
        boolean testi=true;
        for(Laiva laiva:laivat){
            if(!laiva.onkoUponnut()){
                testi=false;
            }
        }
        return testi;
    }

    
    private static boolean pvoittaa() {
        Laiva[] laivat=vastus.getLaivat();
        boolean testi=true;
        for(Laiva laiva:laivat){
            if(!laiva.onkoUponnut()){
                testi=false;
            }
        }
        return testi;
    }
}
