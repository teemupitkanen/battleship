
package kayttoliittyma;

import javax.swing.JButton;

/**
 * Nappi on JButtonia laajentava luokka. Nappi tuntee oman sijaintinsa pelilaudalla
 * @author teempitk
 */
public class Nappi extends JButton {
    /**
     * Napin x-koordinaatti
     */
    private int xkoord;
    /**
     * Napin y-koordinaatti
     */
    private int ykoord;
    /**
     * ajaa yliluokan konstruktorin
     * @param text 
     */
    public Nappi(String text){
        super();
    }
    
    public int getx(){
        return xkoord;
    }
    public int gety(){
        return ykoord;
    }
    public void setx(int x){
        xkoord=x;
    }
    public void sety(int y){
        ykoord=y;
    }
    
}
