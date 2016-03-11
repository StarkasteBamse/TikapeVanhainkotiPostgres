
import java.util.*;
import kayttoliittyma.*;
import sovelluslogiikka.*;
import static spark.Spark.port;

public class Main {

    public static void main(String[] args) {
//        Alue alue1 = new Alue(1, "Ohjelmointi");
//        System.out.println(alue1.getId() + ", " + alue1.getNimi());

        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        String jdbcOsoite = "jdbc:sqlite:koe.db";        
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        }

        Sovelluslogiikka sl = new Sovelluslogiikka(jdbcOsoite);
        sl.kaynnista();

//        List<Alue> a = sl.haeAlueet();
//        if (a.isEmpty()) System.out.println("tyhjä");
//        for (Alue alue : a) {
//            System.out.println(alue.getNimi());
//        }
        Webbikayttoliittyma wc = new Webbikayttoliittyma(sl);
        wc.kaynnista();

//        Tekstikayttoliittyma tkl = new Tekstikayttoliittyma(sl);
//        tkl.kaynnista();
    }
}
