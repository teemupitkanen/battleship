package logiikka;

public class Peli {
    private static Pelaaja pelaaja;

    public static void main(String[] args) {
        pelaaja = new Pelaaja();
        Tietokonevastustaja vastus = new Tietokonevastustaja();

        while (true) {
            while (true) {
                if (!pelaaja.pelaa()) {
                    break;
                }
            }
            if (pvoittaa()) {                   // LISATTAVA! TARKISTETTAVA VOITTO USEIDEN OSUMIEN VALILLA!
                System.out.println("Sinä voitit!");

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
        return true;
    }
}
