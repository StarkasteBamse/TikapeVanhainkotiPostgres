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

        staticFileLocation("/public");

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();

            map.put("alueet", sovelluslogiikka.haeAlueet());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/alue/:id/:sivu", (req, res) -> {

            int alueid = 1;
            int sivu = 1;
            try {
                alueid = Integer.parseInt(req.params("id"));
                sivu = Integer.parseInt(req.params("sivu"));
            } catch (Exception e) {
                return new ModelAndView(virheOsoite(), "index");
            }

            if (alueid < 1 || sivu < 1) {
                return new ModelAndView(virheOsoite(), "index");
            }

            HashMap map = haeAlue(alueid, sivu);

            return new ModelAndView(map, "alue");

        }, new ThymeleafTemplateEngine());

        get("/alue/:id", (req, res) -> {

            int alueid = 1;
            int sivu = 1;
            try {
                alueid = Integer.parseInt(req.params("id"));
            } catch (Exception e) {
                return new ModelAndView(virheOsoite(), "index");
            }

            if (alueid < 1 || sivu < 1) {
                return new ModelAndView(virheOsoite(), "index");
            }

            HashMap map = haeAlue(alueid, sivu);

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        post("/alue/uusi", (req, res) -> {
            HashMap map = new HashMap<>();
            String nimi = req.queryParams("nimi");

            if (nimi.isEmpty()) {
                map.put("alueet", sovelluslogiikka.haeAlueet());
                map.put("varoitus", "Yritit luoda alueen ilman nimeä");
                return new ModelAndView(map, "index");
            }

            sovelluslogiikka.luoAlue(nimi);

            map.put("alueet", sovelluslogiikka.haeAlueet());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        post("/ketju/uusi", (req, res) -> {

            String nimimerkki = req.queryParams("nimimerkki");
            String otsikko = req.queryParams("otsikko");
            String viesti = req.queryParams("viesti");

            int alueid = 1;
            int sivu = 1;
            try {
                alueid = Integer.parseInt(req.queryParams("alueid"));
            } catch (Exception e) {
                return new ModelAndView(virheOsoite(), "index");
            }

            String varoitus = "";
            if (nimimerkki.isEmpty()) {
                varoitus = "Yritit aloittaa keskustelun ilman nimimerkkiä";
            } else if (viesti.isEmpty()) {
                varoitus = "Yritit aloittaa keskustelun tyhjällä viestillä";
            } else if (otsikko.isEmpty()) {
                varoitus = "Yritit aloittaa keskustelun ilman otsikkoa";
            } else {
                sovelluslogiikka.luoKetju(otsikko, alueid, nimimerkki, viesti);
            }

            HashMap map = haeAlue(alueid, sivu);
            if (!varoitus.isEmpty()) {
                map.put("varoitus", varoitus);
            }

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        get("/ketju/:id/:sivu", (req, res) -> {

            int ketjuid = 1;
            int sivu = 1;
            try {
                ketjuid = Integer.parseInt(req.params("id"));
                sivu = Integer.parseInt(req.params("sivu"));
            } catch (Exception e) {
                return new ModelAndView(virheOsoite(), "index");
            }

            if (ketjuid < 1 || sivu < 1) {
                return new ModelAndView(virheOsoite(), "index");
            }

            HashMap map = haeKetju(ketjuid, sivu);

            return new ModelAndView(map, "ketju");
        }, new ThymeleafTemplateEngine());

        post("/ketju/:id/:sivu", (req, res) -> {

            int ketjuid = 1;
            int sivu = 1;
            try {
                ketjuid = Integer.parseInt(req.params("id"));
                sivu = Integer.parseInt(req.params("sivu"));
            } catch (Exception e) {
                return new ModelAndView(virheOsoite(), "index");
            }

            if (ketjuid < 1 || sivu < 1) {
                return new ModelAndView(virheOsoite(), "index");
            }

            String nimimerkki = req.queryParams("nimimerkki");
            String viesti = req.queryParams("viesti");

            String varoitus = "";

            if (nimimerkki.isEmpty()) {
                varoitus = "Yritit vastata ilman nimimerkkiä";
            } else if (viesti.isEmpty()) {
                varoitus = "Yritit vastata tyhjällä viestillä";
            } else {
                sovelluslogiikka.luoViesti(viesti, nimimerkki, ketjuid);
            }

            HashMap map = haeKetju(ketjuid, sivu);
            if (!varoitus.isEmpty()) {
                map.put("varoitus", varoitus);
            }

            return new ModelAndView(map, "ketju");
        }, new ThymeleafTemplateEngine());

        post("/ketju/:id", (req, res) -> {

            int ketjuid = 1;
            int sivu = 1;

            try {
                ketjuid = Integer.parseInt(req.params("id"));
            } catch (Exception e) {
                return new ModelAndView(virheOsoite(), "index");
            }

            if (ketjuid < 1 || sivu < 1) {
                return new ModelAndView(virheOsoite(), "index");
            }

            String nimimerkki = req.queryParams("nimimerkki");
            String viesti = req.queryParams("viesti");

            String varoitus = "";

            if (nimimerkki.isEmpty()) {
                varoitus = "Yritit vastata ilman nimimerkkiä";
            } else if (viesti.isEmpty()) {
                varoitus = "Yritit vastata tyhjällä viestillä";
            } else {
                sovelluslogiikka.luoViesti(viesti, nimimerkki, ketjuid);
            }

            HashMap map = haeKetju(ketjuid, sivu);
            if (!varoitus.isEmpty()) {
                map.put("varoitus", varoitus);
            }

            return new ModelAndView(map, "ketju");
        }, new ThymeleafTemplateEngine());

        get("/ketju/:id", (req, res) -> {

            int ketjuid = 1;
            int sivu = 1;

            try {
                ketjuid = Integer.parseInt(req.params("id"));
            } catch (Exception e) {
                return new ModelAndView(virheOsoite(), "index");
            }

            if (ketjuid < 1 || sivu < 1) {
                return new ModelAndView(virheOsoite(), "index");
            }

            HashMap map = haeKetju(ketjuid, sivu);

            return new ModelAndView(map, "ketju");
        }, new ThymeleafTemplateEngine());

        get("*", (req, res) -> {
            return new ModelAndView(virheOsoite(), "index");
        }, new ThymeleafTemplateEngine());

        post("*", (req, res) -> {
            return new ModelAndView(virheOsoite(), "index");
        }, new ThymeleafTemplateEngine());

    }

    private HashMap virheOsoite() {
        HashMap map = new HashMap<>();

        map.put("alueet", sovelluslogiikka.haeAlueet());
        map.put("varoitus", "Yritit virheelliseen osoitteeseen");

        return map;

    }

    private HashMap haeAlue(int alueid, int sivu) {
        HashMap map = new HashMap<>();

        int sivumaara = ((sovelluslogiikka.haeKetjujenLkm(alueid) - 1) / 10 + 1);
        ArrayList<Integer> sivunumerot = laskeSivunumerot(sivumaara);

        map.put("ketjut", sovelluslogiikka.haeSivuKetjuja(alueid, sivu, 10));
        map.put("alue", sovelluslogiikka.haeAlue(alueid));

        map.put("sivumaara", (sivumaara));
        map.put("sivu", sivu);
        map.put("sivunumerot", sivunumerot);

        return map;

    }

    private HashMap haeKetju(int ketjuid, int sivu) {
        HashMap map = new HashMap<>();

        int sivumaara = ((sovelluslogiikka.haeViestienLkm(ketjuid) - 1) / 10 + 1);
        ArrayList<Integer> sivunumerot = laskeSivunumerot(sivumaara);

        map.put("viestit", sovelluslogiikka.haeSivuViesteja(ketjuid, sivu, 10));
        map.put("ketju", sovelluslogiikka.haeKetju(ketjuid));
        map.put("sivumaara", (sivumaara));
        map.put("sivu", sivu);
        map.put("sivunumerot", sivunumerot);

        return map;

    }

    private ArrayList<Integer> laskeSivunumerot(int sivumaara) {
        ArrayList<Integer> sivunumerot = new ArrayList<>();

        for (int i = 1; i <= sivumaara; i++) {
            sivunumerot.add(i);
        }

        return sivunumerot;

    }

}
