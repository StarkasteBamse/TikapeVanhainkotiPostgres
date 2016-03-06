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

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();

            map.put("alueet", sovelluslogiikka.haeAlueet());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/alue/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            
            int aid = Integer.parseInt(req.params("id"));
            map.put("ketjut", sovelluslogiikka.haeKetjut(aid));
            map.put("alue", sovelluslogiikka.haeAlue(aid));

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        get("/ketju/:id", (req, res) -> {
            HashMap map = new HashMap<>();

            int kid = Integer.parseInt(req.params("id"));
            map.put("viestit", sovelluslogiikka.haeViestit(kid));
            map.put("ketju", sovelluslogiikka.haeKetju(kid));

            return new ModelAndView(map, "ketju");
        }, new ThymeleafTemplateEngine());

    }
}
