/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kayttoliittyma;

import javax.swing.JButton;


public class Nappi extends JButton {
    private int xkoord;
    private int ykoord;
    private JButton nappula;
    
    public Nappi(String text){
        nappula=new JButton(text);
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
