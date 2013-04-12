package kayttoliittyma;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class IlmoitusPop extends JFrame {

    private JTextField tekstikentta;

    public IlmoitusPop(String teksti) {
        tekstikentta=new JTextField(teksti);
        tekstikentta.setEditable(false);
        this.setLayout(new BorderLayout());
        this.add("Center",tekstikentta);
        this.setTitle("Tulosikkuna");
        this.pack();
        this.setSize(500, 200);
        this.setMinimumSize(new Dimension(500,200));
        this.setVisible(true);
    }
}