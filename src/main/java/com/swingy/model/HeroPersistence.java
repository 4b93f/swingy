package com.swingy.model;

import java.io.*;
import java.util.*;
import jakarta.validation.*;
import com.swingy.model.artefact.Armor;
import com.swingy.model.artefact.Artefact;
import com.swingy.model.artefact.Helmet;
import com.swingy.model.artefact.Weapon;

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
            String.valueOf(h.getDefense()),
            artefactToField(h.getEquipment().getWeapon()),
            artefactToField(h.getEquipment().getArmor()),
            artefactToField(h.getEquipment().getHelmet())
        );
    }

    private static String artefactToField(Artefact a) {
        if (a == null) return "";
        int bonus = a.getBonusAttack() != 0 ? a.getBonusAttack()
                  : a.getBonusDefense() != 0 ? a.getBonusDefense()
                  : a.getBonusHitPoints();
        return a.getArtefactName() + ":" + bonus;
    }

    private static Hero fromLine(String line) {
        String[] p = line.split(",", -1);
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
            if (p.length > 7 && !p[7].trim().isEmpty())
                parseArtefact(p[7].trim(), "weapon").ifPresent(hero::equipArtifact);
            if (p.length > 8 && !p[8].trim().isEmpty())
                parseArtefact(p[8].trim(), "armor").ifPresent(hero::equipArtifact);
            if (p.length > 9 && !p[9].trim().isEmpty())
                parseArtefact(p[9].trim(), "helmet").ifPresent(hero::equipArtifact);
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

    private static java.util.Optional<Artefact> parseArtefact(String field, String slot) {
        int colon = field.lastIndexOf(':');
        if (colon < 1) return java.util.Optional.empty();
        try {
            String name  = field.substring(0, colon);
            int    bonus = Integer.parseInt(field.substring(colon + 1));
            Artefact a = switch (slot) {
                case "weapon" -> new Weapon(name, bonus);
                case "armor"  -> new Armor(name, bonus);
                default       -> new Helmet(name, bonus);
            };
            return java.util.Optional.of(a);
        } catch (NumberFormatException e) {
            return java.util.Optional.empty();
        }
    }
}
