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
            int aid = 0;
            int sivu = 0;
            try {
                aid = Integer.parseInt(req.params("id"));
                sivu = Integer.parseInt(req.params("sivu"));
            } catch (Exception e) {
                map.put("alueet", sovelluslogiikka.haeAlueet());
                map.put("varoitus", "Yritit virheelliseen osoitteeseen");
                return new ModelAndView(map, "index");
            }

            ArrayList<Integer> sivunumerot = new ArrayList<>();
            int sivumaara = sovelluslogiikka.haeKetjujenLkm(aid) / 10 + 1;

            for (int i = 1; i <= sivumaara; i++) {
                sivunumerot.add(i);
            }

            map.put("ketjut", sovelluslogiikka.haeKetjut(aid));
            map.put("alue", sovelluslogiikka.haeAlue(aid));
            map.put("sivumaara", (sivumaara));
            map.put("sivu", sivu);
            map.put("sivunumerot", sivunumerot);

            return new ModelAndView(map, "alue");

        }, new ThymeleafTemplateEngine());

        get("/alue/:id", (req, res) -> {
            HashMap map = new HashMap<>();

            int aid = 0;
            try {
                aid = Integer.parseInt(req.params("id"));
            } catch (Exception e) {
                map.put("alueet", sovelluslogiikka.haeAlueet());
                map.put("varoitus", "Yritit virheelliseen osoitteeseen");
                return new ModelAndView(map, "index");
            }

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
            HashMap map = new HashMap<>();
            String nimimerkki = req.queryParams("nimimerkki");
            String otsikko = req.queryParams("otsikko");
            String viesti = req.queryParams("viesti");

            int alueid = 0;
            try {
                alueid = Integer.parseInt(req.queryParams("alueid"));
            } catch (Exception e) {
                map.put("alueet", sovelluslogiikka.haeAlueet());
                map.put("varoitus", "Yritit virheelliseen osoitteeseen");
                return new ModelAndView(map, "index");
            }

            if (nimimerkki.isEmpty()) {
                map.put("varoitus", "Yritit aloittaa keskustelun ilman nimimerkkiä");
            } else if (viesti.isEmpty()) {
                map.put("varoitus", "Yritit aloittaa keskustelun tyhjällä viestillä");
            } else if (otsikko.isEmpty()) {
                map.put("varoitus", "Yritit aloittaa keskustelun ilman otsikkoa");
            } else {
                sovelluslogiikka.luoKetju(otsikko, alueid, nimimerkki, viesti);
            }

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

            int kid = 0;
            int sivu = 0;
            try {
                kid = Integer.parseInt(req.params("id"));
                sivu = Integer.parseInt(req.params("sivu"));
            } catch (Exception e) {
                map.put("alueet", sovelluslogiikka.haeAlueet());
                map.put("varoitus", "Yritit virheelliseen osoitteeseen");
                return new ModelAndView(map, "index");
            }

            ArrayList<Integer> sivunumerot = new ArrayList<>();
            int sivumaara = sovelluslogiikka.haeViestienLkm(kid) / 10 + 1;

            for (int i = 1; i <= sivumaara; i++) {
                sivunumerot.add(i);
            }

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

            int kid = 0;
            int sivu = 0;
            try {
                kid = Integer.parseInt(req.params("id"));
                sivu = Integer.parseInt(req.params("sivu"));
            } catch (Exception e) {
                map.put("alueet", sovelluslogiikka.haeAlueet());
                map.put("varoitus", "Yritit virheelliseen osoitteeseen");
                return new ModelAndView(map, "index");
            }

            String nimimerkki = req.queryParams("nimimerkki");
            String viesti = req.queryParams("viesti");
            if (nimimerkki.isEmpty()) {
                map.put("varoitus", "Yritit vastata ilman nimimerkkiä");
            } else if (viesti.isEmpty()) {
                map.put("varoitus", "Yritit vastata tyhjällä viestillä");
            } else {
                sovelluslogiikka.luoViesti(viesti, nimimerkki, kid);
            }

            ArrayList<Integer> sivunumerot = new ArrayList<>();
            int sivumaara = sovelluslogiikka.haeViestienLkm(kid) / 10 + 1;

            for (int i = 1; i <= sivumaara; i++) {
                sivunumerot.add(i);
            }

            map.put("viestit", sovelluslogiikka.haeViestit(kid));
            map.put("ketju", sovelluslogiikka.haeKetju(kid));
            map.put("sivumaara", (sivumaara));
            map.put("sivu", sivu);
            map.put("sivunumerot", sivunumerot);

            return new ModelAndView(map, "ketju");
        }, new ThymeleafTemplateEngine());

        post("/ketju/:id", (req, res) -> {
            HashMap map = new HashMap<>();

            int kid = 0;
            try {
                kid = Integer.parseInt(req.params("id"));
            } catch (Exception e) {
                map.put("alueet", sovelluslogiikka.haeAlueet());
                map.put("varoitus", "Yritit virheelliseen osoitteeseen");
                return new ModelAndView(map, "index");
            }

            int sivu = 0;

            String nimimerkki = req.queryParams("nimimerkki");
            String viesti = req.queryParams("viesti");

            if (nimimerkki.isEmpty()) {
                map.put("varoitus", "Yritit vastata ilman nimimerkkiä");
            } else if (viesti.isEmpty()) {
                map.put("varoitus", "Yritit vastata tyhjällä viestillä");
            } else {
                sovelluslogiikka.luoViesti(viesti, nimimerkki, kid);
            }

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

            int kid = 0;
            try {
                kid = Integer.parseInt(req.params("id"));
            } catch (Exception e) {
                map.put("alueet", sovelluslogiikka.haeAlueet());
                map.put("varoitus", "Yritit virheelliseen osoitteeseen");
                return new ModelAndView(map, "index");
            }

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

        get("*", (req, res) -> {
            HashMap map = new HashMap<>();

            map.put("alueet", sovelluslogiikka.haeAlueet());
            map.put("varoitus", "Yritit virheelliseen osoitteeseen");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        post("*", (req, res) -> {
            HashMap map = new HashMap<>();

            map.put("alueet", sovelluslogiikka.haeAlueet());
            map.put("varoitus", "Yritit virheelliseen osoitteeseen");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

    }
}
