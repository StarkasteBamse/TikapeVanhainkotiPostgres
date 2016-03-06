
import java.util.*;
import kayttoliittyma.*;
import sovelluslogiikka.*;


public class Main {
    public static void main(String[] args) {
//        Alue alue1 = new Alue(1, "Ohjelmointi");
//        System.out.println(alue1.getId() + ", " + alue1.getNimi());
        
        Sovelluslogiikka sl = new Sovelluslogiikka("jdbc:sqlite:koe.db");
        sl.kaynnista();        
        
        List<Alue> a = sl.haeAlueet();
        if (a.isEmpty()) System.out.println("tyhjä");
        for (Alue alue : a) {
            System.out.println(alue.getNimi());
        }
        

        Webbikayttoliittyma wc = new Webbikayttoliittyma(sl);
        wc.kaynnista();

//        Tekstikayttoliittyma tkl = new Tekstikayttoliittyma(sl);
//        tkl.kaynnista();
    }
}
