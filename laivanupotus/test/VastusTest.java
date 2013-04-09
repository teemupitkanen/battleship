
import logiikka.Peli;
import logiikka.Tietokonevastustaja;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VastusTest {
    Tietokonevastustaja vastus;
    Peli peli;
    
    public VastusTest() {
    }
    @Before
    public void setUp() {
        peli=new Peli();
        vastus=peli.getVastus();
        peli.getPelaaja().asetaLaiva(0, 0, 4, 0);
        peli.getPelaaja().asetaLaiva(0, 2, 3, 2);
        peli.getPelaaja().asetaLaiva(0, 4, 2, 4);
        peli.getPelaaja().asetaLaiva(0, 6, 2, 6);
        peli.getPelaaja().asetaLaiva(0, 8, 1, 8);
    }
    
    @Test
    public void konstruktoriLuoLaudanJossaOikeaMaaraLaivoja(){
        int apu=0;
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                if(vastus.getRuudunTila(i, j).equals("laiva")){
                    apu++;
                }
            }
        }
        assertEquals(17,apu);
    }
    @Test
    public void getLaivatPalauttaaViisiLaivaa(){
        assertEquals(5,vastus.getLaivat().length);
    }
    @Test
    public void ammuPalauttaaKoordinaatitValillaNollaYhdeksan(){
        boolean testi=true;
        int[] ammuttava=vastus.ammu();
        if(ammuttava[0]>9)testi=false;
        if(ammuttava[1]>9)testi=false;
        if(ammuttava[0]<0)testi=false;
        if(ammuttava[1]<0)testi=false;
        assertEquals(true,testi);
    }
    @Test
    public void josOsuuSeuraavanKerranAmmuPalauttaaViereisen(){
        vastus.asetaTulos(2,1,2);
        boolean testi=false;
        int[] ammuttava=vastus.ammu();
        if(ammuttava[0]==0 && ammuttava[1]==2)testi=true;
        if(ammuttava[0]==2 && ammuttava[1]==2)testi=true;
        if(ammuttava[0]==1 && ammuttava[1]==1)testi=true;
        if(ammuttava[0]==1 && ammuttava[1]==3)testi=true;
        assertEquals(true,testi);
    }
    @Test
    public void josOsuuKulmaanSeuraavanKerranAmmuPalauttaaViereisen(){
        vastus.asetaTulos(2,0,0);
        boolean testi=false;
        int[] ammuttava=vastus.ammu();
        if(ammuttava[0]==0 && ammuttava[1]==1)testi=true;
        if(ammuttava[0]==1 && ammuttava[1]==0)testi=true;
        assertEquals(true,testi);
    }
    @Test
    public void josKaksiOsumaaAmpuuToiseenPaahanVaaka(){
        vastus.asetaTulos(2,1,2);
        vastus.asetaTulos(2,2,2);
        boolean testi=false;
        int[] ammuttava=vastus.ammu();
        if(ammuttava[0]==0 && ammuttava[1]==2)testi=true;
        if(ammuttava[0]==3 && ammuttava[1]==2)testi=true;
        assertEquals(true,testi);
    }
    @Test
    public void josKaksiOsumaaJaToisessaPaassaHutiAmpuuToiseenPaahanVaaka(){
        vastus.asetaTulos(2,1,2);
        vastus.asetaTulos(2,2,2);
        vastus.asetaTulos(1,3,2);
        boolean testi=false;
        int[] ammuttava=vastus.ammu();
        if(ammuttava[0]==0 && ammuttava[1]==2)testi=true;
        assertEquals(true,testi);
    }
    @Test
    public void josKaksiOsumaaAmpuuToiseenPaahanPysty(){
        vastus.asetaTulos(2,2,1);
        vastus.asetaTulos(2,2,2);
        boolean testi=false;
        int[] ammuttava=vastus.ammu();
        if(ammuttava[0]==2 && ammuttava[1]==0)testi=true;
        if(ammuttava[0]==2 && ammuttava[1]==3)testi=true;
        assertEquals(true,testi);
    }
    @Test
    public void josKaksiOsumaaJaToisessaPaassaHutiAmpuuToiseenPaahanPysty(){
        vastus.asetaTulos(2,2,1);
        vastus.asetaTulos(2,2,2);
        vastus.asetaTulos(1,2,3);
        boolean testi=false;
        int[] ammuttava=vastus.ammu();
        if(ammuttava[0]==2 && ammuttava[1]==0)testi=true;
        assertEquals(true,testi);
    }
    @Test
    public void kunLaivaUponnutEiAmmuSenViereen(){
        vastus.asetaTulos(2,2,1);
        vastus.asetaTulos(3,2,2);
        boolean testi=true;
        int[] ammuttava=vastus.ammu();
        if(ammuttava[0]==2 && ammuttava[1]==0)testi=false;
        if(ammuttava[0]==2 && ammuttava[1]==3)testi=false;
        if(ammuttava[0]==1 && ammuttava[1]==1)testi=false;
        if(ammuttava[0]==1 && ammuttava[1]==2)testi=false;
        if(ammuttava[0]==3 && ammuttava[1]==1)testi=false;
        if(ammuttava[0]==3 && ammuttava[1]==2)testi=false;
        assertEquals(true,testi);
    }
    
    @Test
    public void getLautaToimii(){
        vastus.getLauta();
    }
    @Test
    public void getRuudunTilaPalauttaaOikeinJosLaiva(){
        assertEquals("laiva",vastus.getRuudunTila(vastus.getLaivat()[0].getRuudut()[0].getx(),vastus.getLaivat()[0].getRuudut()[0].gety()));
    }
}
