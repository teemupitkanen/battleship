package logiikka;


import logiikka.Ruutu;

public class Pelilauta {

    private Ruutu[][] lauta;

    public Pelilauta() {
        lauta = new Ruutu[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                lauta[i][j] = new Ruutu();
            }
        }
    }

    public boolean asetaLaiva(int alkux, int alkuy, int loppux, int loppuy) {
        if (voikoAsettaa(alkux, alkuy, loppux, loppuy) == false) {
            return false;
        }
        
        int[] jarjestys = jarjestaKoordinaatit(alkux,alkuy,loppux,loppuy);
        alkux=jarjestys[0];
        alkuy=jarjestys[1];
        loppux=jarjestys[2];
        loppuy=jarjestys[3];
        
        Laiva laiva=new Laiva((loppuy-alkuy)+(loppux-alkux)+1);

        for (int i = alkuy; i <= loppuy; i++) {         // Jos sijainti sopii, asetetaan laiva.
            lauta[i][alkux].setLaiva(laiva);
        }
        for (int j = alkux; j <= loppux; j++) {
            lauta[alkuy][j].setLaiva(laiva);
        }
        
        return true;
    }
    
    public Ruutu[][] getLauta() {
        return lauta;
    }
    
    public static int[] jarjestaKoordinaatit(int alkux, int alkuy, int loppux, int loppuy) {
        
        int[] jarjestyksessa = new int[4];
        int apux;
        int apuy;
        if (alkux > loppux) {           // asetetaan siten, että alku < loppu
            apux = loppux;
            loppux = alkux;
            alkux = apux;
        }
        if (alkuy > loppuy) {
            apuy = loppuy;
            loppuy = alkuy;
            alkuy = apuy;
        }
        jarjestyksessa[0]=alkux;
        jarjestyksessa[1]=alkuy;
        jarjestyksessa[2]=loppux;
        jarjestyksessa[3]=loppuy;
        return jarjestyksessa;
    }
        
    public boolean voikoAsettaa(int alkux, int alkuy, int loppux, int loppuy) {
        if (alkux < 0 || alkuy < 0 || loppux < 0 || loppuy < 0 || alkux > 9 || alkuy > 9 || loppux > 9 || loppuy > 9) {
            return false;       // jos indeksit eivät osu laudalle
        }
        
        int[] jarjestys = jarjestaKoordinaatit(alkux,alkuy,loppux,loppuy);
        alkux=jarjestys[0];
        alkuy=jarjestys[1];
        loppux=jarjestys[2];
        loppuy=jarjestys[3];

        for (int i = alkuy; i <= loppuy; i++) {                          // tarkastetaan, ettei "sivuilla" tai "alla" laivaa
            if (alkux - 1 > -1 && lauta[i][alkux - 1].getTila().equals("laiva")) {
                return false;
            }
            if (alkux + 1 < 10 && lauta[i][alkux + 1].getTila().equals("laiva")) {
                return false;
            }
            if (lauta[i][alkux].getTila().equals("laiva")) {
                return false;
            }
        }
        for (int j = alkux; j <= loppux; j++) {
            if (alkuy - 1 > -1 && lauta[alkuy - 1][j].getTila().equals("laiva")) {
                return false;
            }
            if (alkuy + 1 < 10 && lauta[alkuy + 1][j].getTila().equals("laiva")) {
                return false;
            }
            if (lauta[alkuy][j].getTila().equals("laiva")) {
                return false;
            }
        }

        if (alkuy - 1 > -1 && lauta[alkuy - 1][alkux].getTila().equals("laiva")) {            // tarkistetaan vielä laivan "päät"
            return false;
        }
        if (loppuy + 1 < 10 && lauta[loppuy + 1][alkux].getTila().equals("laiva")) {
            return false;
        }
        if (alkux - 1 > -1 && lauta[alkuy][alkux - 1].getTila().equals("laiva")) {
            return false;
        }
        if (loppux + 1 < 10 && lauta[alkuy][loppux + 1].getTila().equals("laiva")) {
            return false;
        }
        return true;
    }

    
}

