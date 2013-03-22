package logiikka;

public class Ruutu {

    private String tila;
    private Laiva laiva;

    public Ruutu() {
        tila = "tyhja";
    }

    public String getTila() {
        return tila;
    }
    
    public void setLaiva(Laiva laiva){
        this.laiva=laiva;
        tila="laiva";
    }
    
    public Laiva getLaiva(){
        return laiva;
    }
    
}