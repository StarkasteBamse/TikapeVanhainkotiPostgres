package kayttoliittyma;

import java.util.*;
import sovelluslogiikka.Sovelluslogiikka;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Webbikayttoliittyma {
    
    private Sovelluslogiikka sovelluslogiikka;

    public Webbikayttoliittyma(Sovelluslogiikka sovelluslogiikka) {
        this.sovelluslogiikka = sovelluslogiikka;
    }
    
    public void kaynnista() {
        
        
        get("/alue", (req, res) -> {
            HashMap map = new HashMap<>();
            
            map.put("alueet", sovelluslogiikka.haeAlueet());

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());
        
        get("/ketju", (req, res) -> {
            HashMap map = new HashMap<>();
            
            map.put("ketju", sovelluslogiikka.haeKetjut(1));

            return new ModelAndView(map, "ketju");
        }, new ThymeleafTemplateEngine());
        
    }
}
