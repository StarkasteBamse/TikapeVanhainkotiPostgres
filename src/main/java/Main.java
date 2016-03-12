
import java.util.*;
import kayttoliittyma.*;
import sovelluslogiikka.*;
import static spark.Spark.port;

public class Main {

    public static void main(String[] args) {

        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        String jdbcOsoite = System.getenv("DATABASE_URL");      
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        }

        Sovelluslogiikka sl = new Sovelluslogiikka(jdbcOsoite);
        sl.kaynnista();

        Webbikayttoliittyma wkl = new Webbikayttoliittyma(sl);
        wkl.kaynnista();

    }
}
