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

        get("/alue/:id/:sivu", (req, res) -> {
            HashMap map = new HashMap<>();

            //try catch
            int aid = Integer.parseInt(req.params("id"));
            int sivu = Integer.parseInt(req.params("sivu"));

            ArrayList<Integer> sivunumerot = new ArrayList<>();
            int sivumaara = sovelluslogiikka.haeKetjujenLkm(aid) / 10 + 1;

            for (int i = 1; i <= sivumaara; i++) {
                sivunumerot.add(i);
            }

            //hae ketjut sivun perusteella, sivunro mukaan mappiin
            map.put("ketjut", sovelluslogiikka.haeKetjut(aid));
            map.put("alue", sovelluslogiikka.haeAlue(aid));
            map.put("sivumaara", (sivumaara));
            map.put("sivu", sivu);
            map.put("sivunumerot", sivunumerot);

            return new ModelAndView(map, "alue");

            // jonkinlainen redirect juureen jos virhe?
            // res.redirect("/index.html"); //tms
        }, new ThymeleafTemplateEngine());

        get("/alue/:id", (req, res) -> {
            HashMap map = new HashMap<>();

            //try catch
            int aid = Integer.parseInt(req.params("id"));

            ArrayList<Integer> sivunumerot = new ArrayList<>();
            int sivumaara = sovelluslogiikka.haeKetjujenLkm(aid) / 10 + 1;

            for (int i = 1; i <= sivumaara; i++) {
                sivunumerot.add(i);
            }

            map.put("ketjut", sovelluslogiikka.haeKetjut(aid));
            map.put("alue", sovelluslogiikka.haeAlue(aid));
            map.put("sivumaara", (sivumaara));
            map.put("sivu", 1);
            map.put("sivunumerot", sivunumerot);

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        post("/alue/uusi", (req, res) -> {
            HashMap map = new HashMap<>();
            String nimi = req.queryParams("nimi");

            sovelluslogiikka.luoAlue(nimi);

            map.put("alueet", sovelluslogiikka.haeAlueet());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        post("/ketju/uusi", (req, res) -> {
            HashMap map = new HashMap<>();
            String nimimerkki = req.queryParams("nimimerkki");
            String otsikko = req.queryParams("otsikko");
            String viesti = req.queryParams("viesti");

            // try catch
            int alueid = Integer.parseInt(req.queryParams("alueid"));

            sovelluslogiikka.luoKetju(otsikko, alueid, nimimerkki, viesti);

            ArrayList<Integer> sivunumerot = new ArrayList<>();
            int sivumaara = sovelluslogiikka.haeKetjujenLkm(alueid) / 10 + 1;

            for (int i = 1; i <= sivumaara; i++) {
                sivunumerot.add(i);
            }

            map.put("ketjut", sovelluslogiikka.haeKetjut(alueid));
            map.put("alue", sovelluslogiikka.haeAlue(alueid));
            map.put("sivumaara", (sivumaara));
            map.put("sivu", 1);
            map.put("sivunumerot", sivunumerot);

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        get("/ketju/:id/:sivu", (req, res) -> {
            HashMap map = new HashMap<>();

            //try catch
            int kid = Integer.parseInt(req.params("id"));
            int sivu = Integer.parseInt(req.params("sivu"));

            String varoitus = "Apuvva!!!";

            ArrayList<Integer> sivunumerot = new ArrayList<>();
            int sivumaara = sovelluslogiikka.haeViestienLkm(kid) / 10 + 1;

            for (int i = 1; i <= sivumaara; i++) {
                sivunumerot.add(i);
            }

            //hae viestit sivun perusteella, sivunro mukaan mappiin
            map.put("viestit", sovelluslogiikka.haeSivuViesteja(kid, sivu));
            map.put("ketju", sovelluslogiikka.haeKetju(kid));
            map.put("sivumaara", (sivumaara));
            map.put("sivu", sivu);
            map.put("sivunumerot", sivunumerot);
//            map.put("varoitus", varoitus);

            return new ModelAndView(map, "ketju");
        }, new ThymeleafTemplateEngine());

        post("/ketju/:id/:sivu", (req, res) -> {
            HashMap map = new HashMap<>();

            //try catch
            int kid = Integer.parseInt(req.params("id"));
            int sivu = Integer.parseInt(req.params("sivu"));

            String nimimerkki = req.queryParams("nimimerkki");
            String viesti = req.queryParams("viesti");

            sovelluslogiikka.luoViesti(viesti, nimimerkki, kid);

            ArrayList<Integer> sivunumerot = new ArrayList<>();
            int sivumaara = sovelluslogiikka.haeViestienLkm(kid) / 10 + 1;

            for (int i = 1; i <= sivumaara; i++) {
                sivunumerot.add(i);
            }

            //hae viestit sivun perusteella, sivunro mukaan mappiin
            map.put("viestit", sovelluslogiikka.haeViestit(kid));
            map.put("ketju", sovelluslogiikka.haeKetju(kid));
            map.put("sivumaara", (sivumaara));
            map.put("sivu", sivu);
            map.put("sivunumerot", sivunumerot);

            return new ModelAndView(map, "ketju");
        }, new ThymeleafTemplateEngine());

        post("/ketju/:id", (req, res) -> {
            HashMap map = new HashMap<>();

            //try catch
            int kid = Integer.parseInt(req.params("id"));
            int sivu = 0;

            String nimimerkki = req.queryParams("nimimerkki");
            String viesti = req.queryParams("viesti");

            sovelluslogiikka.luoViesti(viesti, nimimerkki, kid);

            ArrayList<Integer> sivunumerot = new ArrayList<>();
            int sivumaara = sovelluslogiikka.haeViestienLkm(kid) / 10 + 1;

            for (int i = 1; i <= sivumaara; i++) {
                sivunumerot.add(i);
            }

            //hae viestit sivun perusteella, sivunro mukaan mappiin
            map.put("viestit", sovelluslogiikka.haeViestit(kid));
            map.put("ketju", sovelluslogiikka.haeKetju(kid));
            map.put("sivumaara", (sivumaara));
            map.put("sivu", sivu);
            map.put("sivunumerot", sivunumerot);

            return new ModelAndView(map, "ketju");
        }, new ThymeleafTemplateEngine());

        get("/ketju/:id", (req, res) -> {
            HashMap map = new HashMap<>();

            //try catch
            int kid = Integer.parseInt(req.params("id"));

            ArrayList<Integer> sivunumerot = new ArrayList<>();
            int sivumaara = sovelluslogiikka.haeViestienLkm(kid) / 10 + 1;

            for (int i = 1; i <= sivumaara; i++) {
                sivunumerot.add(i);
            }

            map.put("viestit", sovelluslogiikka.haeViestit(kid));
            map.put("ketju", sovelluslogiikka.haeKetju(kid));
            map.put("sivumaara", (sivumaara));
            map.put("sivu", 1);
            map.put("sivunumerot", sivunumerot);

            return new ModelAndView(map, "ketju");
        }, new ThymeleafTemplateEngine());

    }
}
