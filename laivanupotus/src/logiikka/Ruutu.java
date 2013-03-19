package logiikka;

public class Ruutu {

    private String tila;
    private int x;
    private int y;
    private Laiva laiva;

    public Ruutu() {
        tila = "tyhja";
    }

    public String getTila() {
        return tila;
    }

    public void setTila(String tila) {
        this.tila = tila;
    }
    
    public void setLaiva(Laiva laiva){
        this.laiva=laiva;
    }
    
    public Laiva getLaiva(){
        return laiva;
    }
    
}