package logiikka;

public class Pelaaja {

    private Pelilauta lauta;
    private Laiva[] laivat;
    private int[][] kohdelauta;  // 0=ei ammuttu, 1=ohi, 2=osui, 3=uponnut

    public Pelaaja() {
        lauta = new Pelilauta();
        laivat = new Laiva[5];
        kohdelauta = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                kohdelauta[i][j] = 0;
            }
        }
    }

    public void ammu(int x, int y, int tulos) {
        kohdelauta[y][x] = tulos;
    }

    public boolean onkoAmmuttu(int x, int y) {
        if (kohdelauta[y][x] == 0) {
            return false;
                    
        } else {
            return true;
        }
    }

    public boolean asetaLaiva(int alkux, int alkuy, int loppux, int loppuy) {
        return(lauta.asetaLaiva(alkux, alkuy, loppux, loppuy));
    }

    public Laiva[] getLaivat() {
        return laivat;
    }

    public Pelilauta getLauta() {
        return lauta;
    }
    public String getRuudunTila(int x, int y){
        return lauta.getLauta()[y][x].getTila();
    }
}