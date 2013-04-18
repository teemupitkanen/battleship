package kayttoliittyma;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import logiikka.Peli;

public class IlmoitusPop extends JFrame {

    private JTextField tekstikentta;
    private JButton uusiPeli;
    private JButton lopeta;
    private Peli peli;

    public IlmoitusPop(final Peli peli, String teksti) {
        tekstikentta = new JTextField(teksti);
        uusiPeli = new JButton("Uusi peli");
        lopeta = new JButton("Lopeta");
        JPanel nappulat = new JPanel(new GridLayout(1, 2));
        nappulat.add(uusiPeli);
        nappulat.add(lopeta);
        this.peli = peli;

        uusiPeli.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent tapahtuma) {
                        peli.aloitaAlusta();

                    }
                });
        lopeta.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent tapahtuma) {
                        System.exit(0);

                    }
                });
        tekstikentta.setEditable(false);
        this.setLayout(new BorderLayout());
        this.add("Center", tekstikentta);
        this.add("South", nappulat);
        this.setTitle("Tulosikkuna");
        this.pack();
        this.setSize(500, 200);
        this.setMinimumSize(new Dimension(500, 200));
        this.setVisible(true);
    }
}