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

        get("/alue", (req, res) -> {
            HashMap map = new HashMap<>();

            System.out.println(req);
//            int aid = Integer.parseInt(req.params("id"));
            int aid = 1;
            map.put("ketjut", sovelluslogiikka.haeKetjut(aid));
            map.put("alue", sovelluslogiikka.haeAlue(aid));

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        get("/ketju/", (req, res) -> {
            HashMap map = new HashMap<>();

            int kid = 1;
            map.put("viestit", sovelluslogiikka.haeViestit(kid));
            map.put("ketju", sovelluslogiikka.haeKetju(kid));

            return new ModelAndView(map, "ketju");
        }, new ThymeleafTemplateEngine());

    }
}
