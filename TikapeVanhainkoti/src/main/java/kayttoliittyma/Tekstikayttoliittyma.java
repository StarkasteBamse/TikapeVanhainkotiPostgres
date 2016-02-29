package kayttoliittyma;

import java.util.*;
import sovelluslogiikka.Sovelluslogiikka;

public class Tekstikayttoliittyma {

    private Sovelluslogiikka sovelluslogiikka;

    public Tekstikayttoliittyma(Sovelluslogiikka sovelluslogiikka) {
        this.sovelluslogiikka = sovelluslogiikka;
    }

    public void kaynnista() {

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            System.out.print("Anna komento ('H' - tulostaa ohjeen): ");
            String komento = scanner.nextLine();
            
            
            
        }

    }

}
