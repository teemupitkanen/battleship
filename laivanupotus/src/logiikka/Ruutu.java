package logiikka;

public class Ruutu {

    private String tila;
    private Laiva laiva;
    private int x;
    private int y;
    private Ruutu[] naapurit;
    private int lisatytNaapurit;

    public Ruutu(int x, int y) {
        this.x = x;
        this.y = y;
        tila = "tyhja";
        lisatytNaapurit=0;
        if ((x == 0 && y == 0) || (x == 0 && y == 9) || (x == 9 && y == 0) || (x == 9 && y == 9)) {
            naapurit = new Ruutu[2];
        } else if (x == 0 || y == 0 || x == 9 || y == 9) {
            naapurit = new Ruutu[3];
        } else {
            naapurit = new Ruutu[4];
        }
    }

    public String getTila() {
        return tila;
    }

    public void setNaapuri(Ruutu ruutu){
        if(lisatytNaapurit<naapurit.length){
            naapurit[lisatytNaapurit]=ruutu;
            lisatytNaapurit++;
        }
    }
    
    public Ruutu[] getNaapurit(){
        return naapurit;
    }
    
    public void setLaiva(Laiva laiva) {
        this.laiva = laiva;
        tila = "laiva";
        laiva.lisaaRuutu(this);
    }

    public Laiva getLaiva() {
        return laiva;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }
}