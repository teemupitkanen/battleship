package logiikka;

public class Pelaaja {

    private Pelilauta lauta;
    private Laiva[] laivat;

    public Pelaaja() {
        lauta = new Pelilauta();
        laivat=new Laiva[5];
        laivat[0]=asetaLaiva(5);
        laivat[1]=asetaLaiva(4);
        laivat[2]=asetaLaiva(3);
        laivat[3]=asetaLaiva(3);
        laivat[4]=asetaLaiva(2);

    }
    public boolean pelaa(){
        return true;
    }
    public Laiva asetaLaiva(int pituus){
        return new Laiva(3);                      /////////KORJAA!
    }
    public Laiva[] getLaivat(){
        return laivat;
    }
}