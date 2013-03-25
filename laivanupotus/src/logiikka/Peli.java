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
//    private static void pelaa() {
//        while (true) {
//            while (true) {
//                if (!pelaaja.ammu()) {
//                    break;
//                }
//            }
//            if (pvoittaa()) {                   // LISATTAVA! TARKISTETTAVA VOITTO USEIDEN OSUMIEN VALILLA!
//                System.out.println("Sinä voitit!");
//                break;
//            }
//            while (true) {
//                if (!vastus.pelaa()) {
//                    break;
//                }
//            }
//            if (vvoittaa()) {
//                System.out.println("Tietokone voitti!");
//                break;
//            }
//        }
//    }

}
