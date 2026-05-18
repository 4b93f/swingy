package com.swingy.model;

import java.io.*;
import java.util.*;
import jakarta.validation.*;

public class HeroPersistence {

    private static final String    FILE      = "heroes.txt";
    private static final Validator VALIDATOR =
        Validation.buildDefaultValidatorFactory().getValidator();

    public static List<Hero> loadAll() {
        List<Hero> heroes = new ArrayList<>();
        File f = new File(FILE);
        if (!f.exists()) return heroes;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    Hero h = fromLine(line);
                    if (h != null) heroes.add(h);
                }
            }
        } catch (IOException ignored) {}
        return heroes;
    }

    public static void save(Hero hero) {
        List<Hero> heroes = loadAll();
        heroes.removeIf(h -> h.getHeroName().equalsIgnoreCase(hero.getHeroName()));
        heroes.add(hero);
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Hero h : heroes) pw.println(toLine(h));
        } catch (IOException ignored) {}
    }

    private static String toLine(Hero h) {
        return String.join(",",
            h.getHeroName(),
            h.getHeroClass(),
            String.valueOf(h.getLevel()),
            String.valueOf(h.getExperience()),
            String.valueOf(h.getHitPoints()),
            String.valueOf(h.getAttack()),
            String.valueOf(h.getDefense())
        );
    }

    private static Hero fromLine(String line) {
        String[] p = line.split(",");
        if (p.length < 7) return null;
        try {
            Hero hero = new Hero.HeroBuilder()
                .setHeroName(p[0].trim())
                .setHeroClass(p[1].trim())
                .setLevel(Integer.parseInt(p[2].trim()))
                .setExperience(Integer.parseInt(p[3].trim()))
                .setHitPoints(Integer.parseInt(p[4].trim()))
                .setAttack(Integer.parseInt(p[5].trim()))
                .setDefense(Integer.parseInt(p[6].trim()))
                .build();
            Set<ConstraintViolation<Hero>> violations = VALIDATOR.validate(hero);
            if (!violations.isEmpty()) {
                System.err.println("Skipping invalid hero \"" + p[0] + "\": "
                    + violations.iterator().next().getMessage());
                return null;
            }
            return hero;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
