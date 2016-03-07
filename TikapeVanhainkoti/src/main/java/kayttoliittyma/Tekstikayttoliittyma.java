package kayttoliittyma;

import java.util.*;
import sovelluslogiikka.*;

public class Tekstikayttoliittyma {

    private Sovelluslogiikka sovelluslogiikka;
    private int viimKetju;
    private Scanner lukija;

    public Tekstikayttoliittyma(Sovelluslogiikka sovelluslogiikka) {
        this.sovelluslogiikka = sovelluslogiikka;
        lukija = new Scanner(System.in);
    }

    public void kaynnista() {

        System.out.print("Anna komento ('H' - tulostaa ohjeen): ");

        while (lukija.hasNext()) {
            System.out.println("Anna komento ('H' - tulostaa ohjeen): ");
            String komento = lukija.nextLine();

            switch (komento) {
                case "H":
                    System.out.println("ohjeet tässä: alueet, alue, ketju, vastaa");
                    break;
                case "alueet":
                    tulostaAlueet();
                    break;
                case "alue":
                    kaytaAlue();
                    break;
                case "ketju":
                    kaytaKetju();
                    break;
                case "vastaa":
                    vastaa();
                    break;
                default:
                    tulostaAlueet();
            }

        }

    }

    public void tulostaAlueet() {
        List<Alue> alueet = sovelluslogiikka.haeAlueet();
        System.out.println("Alueet");
        for (Alue alueet1 : alueet) {
            System.out.println(alueet1.getNimi() + " " + alueet1.getLkm() + " viestiä, viimeisin " + alueet1.getPvm());
        }
    }

    public void kaytaAlue() {
        System.out.println("kirjoita alueen numero tai 'uusi'");
        String p = lukija.nextLine();

        if (p.equals("uusi")) {
            System.out.println("Nimi? ");
            String nimi = lukija.nextLine();
            sovelluslogiikka.luoAlue(nimi);
        } else {
            // ei jaksa käsitellä virheitä
            int n = Integer.parseInt(p);
            List<Ketju> ketjut = sovelluslogiikka.haeKetjut(n);

            // ketjut on tyhjä?
            System.out.println("Ketjut");
            for (Ketju ketjut1 : ketjut) {
                System.out.println(ketjut1.getNimi() + " " + ketjut1.getLkm() + " viestiä, viimeisin " + ketjut1.getPvm());
            }
        }
    }

    public void kaytaKetju() {
        System.out.println("kirjoita ketjun numero tai 'uusi'");
        String p = lukija.nextLine();

        // taas ei jaksa käsitellä virheitä
        if (p.equals("uusi")) {
            System.out.println("Alueen numero? ");
            int alue = Integer.parseInt(lukija.nextLine());

            System.out.println("Ketjun otsikko? ");
            String otsikko = lukija.nextLine();
            System.out.println("nimimerkki? ");
            String nimim = lukija.nextLine();
            System.out.println("Viesti? ");
            String viesti = lukija.nextLine();
            
            sovelluslogiikka.luoKetju(otsikko, alue, nimim, viesti);
        } else {
            int n = Integer.parseInt(p);
            List<Viesti> viestit = sovelluslogiikka.haeViestit(n);
            System.out.println("Ketjun viestit");
            for (Viesti v : viestit) {
                System.out.println(v.getViesti() + " t. " + v.getNimimerkki());
            }
        }
    }

    public void vastaa() {
        System.out.println("kirjoita ketjun numero");
        int p = Integer.parseInt(lukija.nextLine());

        // taas ei jaksa käsitellä virheitä
        System.out.println("nimimerkki? ");
        String nimim = lukija.nextLine();

        System.out.println("Viesti? ");
        String viesti = lukija.nextLine();

        sovelluslogiikka.luoViesti(viesti, nimim, p);
    }

}
