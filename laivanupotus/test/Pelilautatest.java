

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class pelilautatest {

    public pelilautatest() {
    }
    Pelilauta lauta;

    @Before
    public void setUp() {
        lauta = new Pelilauta();
    }

    @Test
    public void testaaEttaUudenLaudanRuudutTyhjia() {
        int a = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (lauta.getLauta()[i][j].getTila().equals("laiva")) {
                    a = 1;
                }
            }
        }
        assertEquals(a, 0);
    }

    @Test
    public void josIndeksiEiMahduLaudallePalauttaaFalse() {
        int a = 0;
        lauta.asetaLaiva(1,1,11,1);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (lauta.getLauta()[i][j].getTila().equals("laiva")) {
                    a = 1;
                }
            }
        }
        assertEquals(a, 0);
    }
    
    @Test
    public void ensimmainenLaivaAsettuuOikein() {
        lauta.asetaLaiva(1, 3, 1, 6);
        String[][] malli = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                malli[i][j] = "tyhja";
            }
        }
        malli[3][1] = "laiva";
        malli[4][1] = "laiva";
        malli[5][1] = "laiva";
        malli[6][1] = "laiva";

        int testimuuttuja = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(!malli[i][j].equals(lauta.getLauta()[i][j].getTila())){
                    testimuuttuja=0;
                }
            }
        }
        assertEquals(testimuuttuja, 1);

    }
    @Test
    public void laivaAsettuuOikeinJosKoordinaatitVarinPain() {
        lauta.asetaLaiva(1, 6, 1, 3);
        String[][] malli = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                malli[i][j] = "tyhja";
            }
        }
        malli[3][1] = "laiva";
        malli[4][1] = "laiva";
        malli[5][1] = "laiva";
        malli[6][1] = "laiva";

        int testimuuttuja = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(!malli[i][j].equals(lauta.getLauta()[i][j].getTila())){
                    testimuuttuja=0;
                }
            }
        }
        assertEquals(testimuuttuja, 1);

    }
    
    @Test
    public void laivaKulmassaAsettuuOikein() {
        lauta.asetaLaiva(7, 9, 9, 9);
        String[][] malli = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                malli[i][j] = "tyhja";
            }
        }
        malli[9][7] = "laiva";
        malli[9][8] = "laiva";
        malli[9][9] = "laiva";

        int testimuuttuja = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(!malli[i][j].equals(lauta.getLauta()[i][j].getTila())){
                    testimuuttuja=0;
                }
            }
        }
        assertEquals(testimuuttuja, 1);

    }
    
    @Test
    public void laivatEivatAsetuPaallekkainRistiin() {
        lauta.asetaLaiva(1, 3, 1, 6);
        lauta.asetaLaiva(0, 4, 3, 4);
        String[][] malli = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                malli[i][j] = "tyhja";
            }
        }
        malli[3][1] = "laiva";
        malli[4][1] = "laiva";
        malli[5][1] = "laiva";
        malli[6][1] = "laiva";

        int testimuuttuja = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(!malli[i][j].equals(lauta.getLauta()[i][j].getTila())){
                    testimuuttuja=0;
                }
            }
        }
        assertEquals(testimuuttuja, 1);
        
    }
    @Test
    public void laivatEivatAsetuPaallekkainsamansuuntainen() {
        lauta.asetaLaiva(1, 3, 1, 6);
        lauta.asetaLaiva(1, 3, 1, 7);
        String[][] malli = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                malli[i][j] = "tyhja";
            }
        }
        malli[3][1] = "laiva";
        malli[4][1] = "laiva";
        malli[5][1] = "laiva";
        malli[6][1] = "laiva";

        int testimuuttuja = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(!malli[i][j].equals(lauta.getLauta()[i][j].getTila())){
                    testimuuttuja=0;
                }
            }
        }
        assertEquals(testimuuttuja, 1);
        
    }
    @Test
    public void laivatEivatAsetuVierekkain() {
        lauta.asetaLaiva(1, 3, 1, 6);
        lauta.asetaLaiva(2, 3, 2, 6);
        String[][] malli = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                malli[i][j] = "tyhja";
            }
        }
        malli[3][1] = "laiva";
        malli[4][1] = "laiva";
        malli[5][1] = "laiva";
        malli[6][1] = "laiva";

        int testimuuttuja = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(!malli[i][j].equals(lauta.getLauta()[i][j].getTila())){
                    testimuuttuja=0;
                }
            }
        }
        assertEquals(testimuuttuja, 1);
        
    }
    @Test
    public void laivatEivatAsetuPerakkain() {
        lauta.asetaLaiva(1, 3, 1, 6);
        lauta.asetaLaiva(1, 7, 1, 8);
        String[][] malli = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                malli[i][j] = "tyhja";
            }
        }
        malli[3][1] = "laiva";
        malli[4][1] = "laiva";
        malli[5][1] = "laiva";
        malli[6][1] = "laiva";

        int testimuuttuja = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(!malli[i][j].equals(lauta.getLauta()[i][j].getTila())){
                    testimuuttuja=0;
                }
            }
        }
        assertEquals(testimuuttuja, 1);
        
    }
    @Test
    public void laivatEivatAsetuKokonaanPaallekkain() {
        lauta.asetaLaiva(1, 3, 1, 6);
        assertEquals(false, lauta.asetaLaiva(1, 3, 1, 6));
        
    }
    @Test
    public void laivatAsettuvatOikeinJosVierekkainJaYksiRiviValissa(){
        lauta.asetaLaiva(1, 3, 1, 6);
        lauta.asetaLaiva(3, 3, 3, 6);
        String[][] malli = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                malli[i][j] = "tyhja";
            }
        }
        malli[3][1] = "laiva";
        malli[4][1] = "laiva";
        malli[5][1] = "laiva";
        malli[6][1] = "laiva";

        malli[3][3] = "laiva";
        malli[4][3] = "laiva";
        malli[5][3] = "laiva";
        malli[6][3] = "laiva";
        
        int testimuuttuja = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(!malli[i][j].equals(lauta.getLauta()[i][j].getTila())){
                    testimuuttuja=0;
                }
            }
        }
        assertEquals(testimuuttuja, 1);
        
    }
    @Test
    public void laivatAsettuvatOikeinJosPerakkainJaYksiRiviValissa(){
        lauta.asetaLaiva(1, 3, 1, 6);
        lauta.asetaLaiva(1, 8, 1, 9);
        String[][] malli = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                malli[i][j] = "tyhja";
            }
        }
        malli[3][1] = "laiva";
        malli[4][1] = "laiva";
        malli[5][1] = "laiva";
        malli[6][1] = "laiva";

        malli[8][1] = "laiva";
        malli[9][1] = "laiva";
        
        int testimuuttuja = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(!malli[i][j].equals(lauta.getLauta()[i][j].getTila())){
                    testimuuttuja=0;
                }
            }
        }
        assertEquals(testimuuttuja, 1);
        
    }
}