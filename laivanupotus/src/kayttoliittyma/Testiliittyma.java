package kayttoliittyma;

import logiikka.Pelaaja;
import logiikka.Peli;
import logiikka.Tietokonevastustaja;

public class Testiliittyma {

    private static Pelaaja pelaaja;
    private static Tietokonevastustaja vastus;

    public static void main(String[] args) {
        Peli peli = new Peli();
        pelaaja = peli.getPelaaja();
        vastus = peli.getVastus();
        pelaaja.asetaLaiva(0, 0, 4, 0);
        pelaaja.asetaLaiva(0, 2, 3, 2);
        pelaaja.asetaLaiva(0, 4, 2, 4);
        pelaaja.asetaLaiva(0, 6, 2, 6);
        pelaaja.asetaLaiva(0, 8, 1, 8);

        naytaLaudat();

        pelaajaAmpuu();
        pelaajaAmpuu();
        pelaajaAmpuu();

    }

    private static void pelaajaAmpuu() {

        int kohdex = (int) (9 * Math.random());
        int kohdey = (int) (9 * Math.random());
        int tulos;

        while (pelaaja.onkoAmmuttu(kohdex, kohdey)) {
            kohdex = (int) (9 * Math.random());
            kohdey = (int) (9 * Math.random());
        }
        if (vastus.getRuudunTila(kohdex, kohdey).equals("tyhja")) {
            tulos = 1;
        } else {
            tulos = 2;
            vastus.getLauta().getLauta()[kohdey][kohdex].getLaiva().osuma();
            if (vastus.getLauta().getLauta()[kohdey][kohdex].getLaiva().onkoUponnut()) {
                tulos = 3;
            }
        }
        System.out.println(kohdex + ", " + kohdey + ", " + tulos);

    }

    private static void naytaLaudat() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (pelaaja.getRuudunTila(j,i).equals("laiva")) {
                    System.out.print("#");

                } else {
                    System.out.print(".");
                }
            }
            System.out.println("");
        }
        System.out.println("");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (vastus.getRuudunTila(j,i).equals("laiva")) {
                    System.out.print("#");

                } else {
                    System.out.print(".");
                }
            }
            System.out.println("");
        }
    }
}
