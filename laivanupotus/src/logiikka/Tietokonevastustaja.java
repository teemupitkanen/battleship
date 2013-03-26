package logiikka;

import java.util.ArrayList;

public class Tietokonevastustaja {

    private Pelilauta lauta;
    private Laiva[] laivat;
    private ArrayList<Integer> ammuttujenx;
    private ArrayList<Integer> ammuttujeny;
    private ArrayList<Integer> ammuttujentulos;
    private int[][] kohdelauta;  // 0=ei ammuttu, 1=ohi, 2=osui, 3=uponnut
    private ArrayList<Integer> ammuttavanx;
    private ArrayList<Integer> ammuttavany;
    private boolean upotusKesken;
    private boolean suuntaTiedossa;
    private boolean upotettavaVaaka;
    private int viimeksiOsunutX;
    private int viimeksiOsunutY;

    public Tietokonevastustaja() {
        lauta = new Pelilauta();
        laivat = new Laiva[5];
        laivat[0] = asetaLaiva(5);
        laivat[1] = asetaLaiva(4);
        laivat[2] = asetaLaiva(3);
        laivat[3] = asetaLaiva(3);
        laivat[4] = asetaLaiva(2);
        ammuttujenx = new ArrayList<>();
        ammuttujeny = new ArrayList<>();
        ammuttujentulos = new ArrayList<>();
        kohdelauta = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                kohdelauta[i][j] = 0;
            }
        }
        upotusKesken = false;
        suuntaTiedossa = false;
        upotettavaVaaka = false;

    }

    public int[] ammu() {
        ammuttavanx = new ArrayList();
        ammuttavany = new ArrayList();


        if (upotusKesken) {


            if (suuntaTiedossa) {
                if (upotettavaVaaka) {
                    int oikeax=viimeksiOsunutX;
                    int vasenx=viimeksiOsunutX;
                    
                    while(kohdelauta[viimeksiOsunutY][oikeax]==2 && oikeax<=9){
                        oikeax++;
                    }while(kohdelauta[viimeksiOsunutY][vasenx]==2 && vasenx>=0){
                        vasenx--;
                    }
                    oikeax--;
                    vasenx++;
                    
                    Double kumpiPaa=Math.random();
                    if(oikeax==9 || (kumpiPaa<0.5 && vasenx-1>=0 && kohdelauta[viimeksiOsunutY][vasenx-1]==0)){   // ammutaan vasempaan paahan
                        int[] palautettava={vasenx-1,viimeksiOsunutY};
                        return palautettava;
                    }
                    else if (kumpiPaa>0.5 && oikeax+1<=9 && kohdelauta[viimeksiOsunutY][oikeax+1]==0){               // ammutaan oikeaan paahan
                        int[] palautettava={vasenx-1,viimeksiOsunutY};
                        return palautettava;
                    }
                }
                else{
                    int ylay=viimeksiOsunutY;
                    int alay=viimeksiOsunutY;
                    
                    while(kohdelauta[ylay][viimeksiOsunutX]==2 && ylay<=9){
                        ylay++;
                    }while(kohdelauta[alay][viimeksiOsunutX]==2 && alay>=0){
                        alay--;
                    }
                    ylay--;
                    alay++;
                    
                    Double kumpiPaa=Math.random();
                    if(ylay==9 || (kumpiPaa<0.5 && alay-1>=0 && kohdelauta[alay-1][viimeksiOsunutX]==0)){   // ammutaan vasempaan paahan
                        int[] palautettava={viimeksiOsunutX, alay-1};
                        return palautettava;
                    }
                    else{             // ammutaan oikeaan paahan
                        int[] palautettava={viimeksiOsunutX, ylay+1};
                        return palautettava;
                    }
                    
                }
            } else {
                while (true) {
                    int suunta = (int) (4 * Math.random()); // 0=N, 1=E, 2=S, 3=W
                    if (suunta == 0 && viimeksiOsunutY - 1 >= 0) {
                        if (kohdelauta[viimeksiOsunutY - 1][viimeksiOsunutX] == 0) {
                            int[] palautettava = {viimeksiOsunutX, viimeksiOsunutY - 1};
                            return palautettava;
                        }
                    }
                    if (suunta == 1 && viimeksiOsunutX + 1 <= 9) {
                        if (kohdelauta[viimeksiOsunutY][viimeksiOsunutX + 1] == 0) {
                            int[] palautettava = {viimeksiOsunutX + 1, viimeksiOsunutY};
                            return palautettava;
                        }
                    }
                    if (suunta == 2 && viimeksiOsunutY + 1 <= 9) {
                        if (kohdelauta[viimeksiOsunutY + 1][viimeksiOsunutX] == 0) {
                            int[] palautettava = {viimeksiOsunutX, viimeksiOsunutY + 1};
                            return palautettava;
                        }
                    }
                    if (suunta == 3 && viimeksiOsunutX - 1 >= 0) {
                        if (kohdelauta[viimeksiOsunutY][viimeksiOsunutX - 1] == 0) {
                            int[] palautettava = {viimeksiOsunutX - 1, viimeksiOsunutY};
                            return palautettava;
                        }
                    }
                }
            }

        }



        int x = 0;
        int y = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((i + j) % 2 == 0 && kohdelauta[i][j] == 0) {
                    ammuttavanx.add(j);
                    ammuttavany.add(i);
                }
            }
        }
        int indeksi = (int) (ammuttavanx.size() * Math.random());
      
        x = ammuttavanx.get(indeksi);
        y = ammuttavany.get(indeksi);

        int[] palautettava = {x, y};
        return palautettava;
    }

    public Laiva asetaLaiva(int pituus) {
        int alkux;
        int alkuy;
        while (true) {
            alkux = (int) (9 * Math.random());
            alkuy = (int) (9 * Math.random());
            int suunta = (int) (4 * Math.random()); // suunnat: 0=p 1=i 2=e 3=l

            boolean testimuuttuja;
            int loppuy;
            int loppux;

            if (suunta == 0) {
                loppux = alkux;
                loppuy = alkuy - pituus + 1;
            } else if (suunta == 1) {
                loppux = alkux + pituus - 1;
                loppuy = alkuy;

            } else if (suunta == 2) {
                loppux = alkux;
                loppuy = alkuy + pituus - 1;

            } else {
                loppux = alkux - pituus + 1;
                loppuy = alkuy;

            }
            if (loppux >= 0 && loppuy >= 0 && loppux <= 9 && loppuy <= 9) {
                testimuuttuja = lauta.asetaLaiva(alkux, alkuy, loppux, loppuy);
                if (testimuuttuja) {
                    break;
                }
            }

        }
        return lauta.getLauta()[alkuy][alkux].getLaiva();
    }

    public Laiva[] getLaivat() {
        return laivat;
    }

    public Pelilauta getLauta() {
        return lauta;
    }

    public String getRuudunTila(int x, int y) {
        return lauta.getLauta()[y][x].getTila();
    }

    public void asetaTulos(int tulos, int x, int y) {
        kohdelauta[y][x] = tulos;
        ammuttujenx.add(x);
        ammuttujeny.add(y);
        ammuttujentulos.add(tulos);
        if (upotusKesken && (tulos == 2 || tulos == 3)) {
            suuntaTiedossa = true;
            int koskaOsuiViimeksi = 0;
            for (int i = 1; i < 5; i++) {
                if (ammuttujentulos.size()>=(i+1) && ammuttujentulos.get(ammuttujentulos.size() - i -1) == 2) {
                    koskaOsuiViimeksi = i;
                }
            }
            if (ammuttujenx.get(ammuttujentulos.size() - koskaOsuiViimeksi -1) == viimeksiOsunutX) {
                upotettavaVaaka = false;
            } else {
                upotettavaVaaka = true;
            }


        }
        if (tulos == 2 && upotusKesken == false) {
            upotusKesken = true;
        }
        if (tulos == 2 || tulos == 3) {
            viimeksiOsunutX = x;
            viimeksiOsunutY = y;
        }
        if (tulos == 3) {
            upotusKesken = false;
            suuntaTiedossa = false;
            kohdelauta[y][x]=3;
        }
        if(tulos==2){
            kohdelauta[y][x]=2;
            
        }
        if(tulos==1){
            kohdelauta[y][x]=1;
        }


    }
}