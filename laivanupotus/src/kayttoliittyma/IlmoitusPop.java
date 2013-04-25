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
    private JTextField prosenttikentta;
    private JTextField omavuorokentta;
    private JTextField vastvuorokentta;
    private JTextField kierroskentta;
    private JButton uusiPeli;
    private JButton lopeta;
    private JButton peruuta;
    private Peli peli;

    public IlmoitusPop(final Peli peli, String teksti, double voittoprosentti, double omavuoro, double vastvuoro, int kierrokset) {
        tekstikentta = new JTextField(teksti);
        prosenttikentta = new JTextField("Voittoprosenttisi: " + voittoprosentti);
        omavuorokentta = new JTextField("Sinä olet voittanut keskimäärin vuorolla: " + omavuoro);
        vastvuorokentta = new JTextField("Tietokone on voittanut keskimäärin vuorolla: " + vastvuoro);
        kierroskentta = new JTextField("Pelattuja kierroksia: " + kierrokset);
        uusiPeli = new JButton("Uusi peli");
        lopeta = new JButton("Lopeta");
        JPanel nappulat = new JPanel(new GridLayout(1, 2));
        JPanel tekstit = new JPanel(new GridLayout(5, 1));
        nappulat.add(uusiPeli);
        nappulat.add(lopeta);
        tekstit.add(tekstikentta);
        tekstit.add(kierroskentta);
        tekstit.add(prosenttikentta);
        tekstit.add(omavuorokentta);
        tekstit.add(vastvuorokentta);
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
        prosenttikentta.setEditable(false);
        omavuorokentta.setEditable(false);
        vastvuorokentta.setEditable(false);
        kierroskentta.setEditable(false);
        this.setLayout(new BorderLayout());
        this.add("Center", tekstit);
        this.add("South", nappulat);
        this.setTitle("Tulosikkuna");
        this.pack();
        this.setSize(500, 200);
        this.setMinimumSize(new Dimension(500, 200));
        this.setLocationRelativeTo(peli.getUpotus());
        this.setVisible(true);
    }

    public IlmoitusPop(final Peli peli, String teksti, boolean lopettava) {
        tekstikentta = new JTextField(teksti);
        lopeta = new JButton("Lopeta");
        uusiPeli = new JButton("Uusi peli");
        peruuta=new JButton("Peruuta");
        this.peli = peli;
        lopeta.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent tapahtuma) {
                        peli.paivitaKeskeytettyTilastoihin();
                        System.exit(0);

                    }
                });
        uusiPeli.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent tapahtuma) {
                        peli.paivitaKeskeytettyTilastoihin();
                        peli.aloitaAlusta();

                    }
                });
        peruuta.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent tapahtuma) {
                        suljeIkkuna();

                    }
                });
        JPanel napit=new JPanel(new GridLayout(1,2));
        tekstikentta.setEditable(false);
        this.setLayout(new BorderLayout());
        this.add("Center", tekstikentta);
        if (lopettava) {
            napit.add(lopeta);
        } else {
            napit.add(uusiPeli);
        }
        napit.add(peruuta);
        this.add("South",napit);
        this.setTitle("Laivanupotus");
        this.pack();
        this.setSize(500, 200);
        this.setMinimumSize(new Dimension(500, 200));
        this.setLocationRelativeTo(peli.getUpotus());
        this.setVisible(true);

    }
    /**
     * Poistaa ikkunan näkyvistä
     */
    public void suljeIkkuna(){
        this.setVisible(false);
    }
}