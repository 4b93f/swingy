package com.swingy.model;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HeroPersistenceTest {

    private static final String FILE = "heroes.txt";

    private Hero warrior() {
        return new Hero.HeroBuilder()
            .setHeroName("Arthur")
            .setHeroClass("Warrior")
            .setLevel(2)
            .setExperience(500)
            .setHitPoints(120)
            .setAttack(18)
            .setDefense(14)
            .build();
    }

    private Hero mage() {
        return new Hero.HeroBuilder()
            .setHeroName("Merlin")
            .setHeroClass("Mage")
            .setLevel(3)
            .setExperience(1000)
            .setHitPoints(100)
            .setAttack(22)
            .setDefense(10)
            .build();
    }

    @BeforeEach
    @AfterEach
    void cleanFile() {
        new File(FILE).delete();
    }

    // ── Basic save / load ─────────────────────────────────────────────────────

    @Test
    @DisplayName("Load from missing file returns empty list")
    void loadMissingFile() {
        List<Hero> heroes = HeroPersistence.loadAll();
        assertTrue(heroes.isEmpty());
    }

    @Test
    @DisplayName("Save and load roundtrip preserves all fields")
    void saveAndLoad() {
        Hero h = warrior();
        HeroPersistence.save(h);

        List<Hero> loaded = HeroPersistence.loadAll();
        assertEquals(1, loaded.size());

        Hero l = loaded.get(0);
        assertEquals("Arthur", l.getHeroName());
        assertEquals("Warrior", l.getHeroClass());
        assertEquals(2, l.getLevel());
        assertEquals(500, l.getExperience());
        assertEquals(120, l.getHitPoints());
        assertEquals(18, l.getAttack());
        assertEquals(14, l.getDefense());
    }

    @Test
    @DisplayName("Multiple heroes saved and loaded correctly")
    void saveMultipleHeroes() {
        HeroPersistence.save(warrior());
        HeroPersistence.save(mage());

        List<Hero> loaded = HeroPersistence.loadAll();
        assertEquals(2, loaded.size());

        boolean hasArthur = loaded.stream().anyMatch(h -> h.getHeroName().equals("Arthur"));
        boolean hasMerlin = loaded.stream().anyMatch(h -> h.getHeroName().equals("Merlin"));
        assertTrue(hasArthur);
        assertTrue(hasMerlin);
    }

    // ── Upsert behaviour ──────────────────────────────────────────────────────

    @Test
    @DisplayName("Saving hero with same name replaces old entry (upsert)")
    void upsertSameName() {
        HeroPersistence.save(warrior());

        Hero updated = new Hero.HeroBuilder()
            .setHeroName("Arthur")
            .setHeroClass("Warrior")
            .setLevel(3)
            .setExperience(200)
            .setHitPoints(130)
            .setAttack(20)
            .setDefense(15)
            .build();
        HeroPersistence.save(updated);

        List<Hero> loaded = HeroPersistence.loadAll();
        assertEquals(1, loaded.size());
        assertEquals(3, loaded.get(0).getLevel());
    }

    @Test
    @DisplayName("Upsert is case-insensitive on name")
    void upsertCaseInsensitive() {
        HeroPersistence.save(warrior());

        Hero dup = new Hero.HeroBuilder()
            .setHeroName("ARTHUR")
            .setHeroClass("Warrior")
            .setLevel(4)
            .setExperience(0)
            .setHitPoints(140)
            .setAttack(20)
            .setDefense(15)
            .build();
        HeroPersistence.save(dup);

        List<Hero> loaded = HeroPersistence.loadAll();
        assertEquals(1, loaded.size());
    }

    // ── Corrupt / invalid lines ───────────────────────────────────────────────

    @Test
    @DisplayName("Corrupt line (too few fields) is silently skipped")
    void corruptLineSkipped() throws Exception {
        try (PrintWriter pw = new PrintWriter(FILE)) {
            pw.println("BadLine,NoEnoughFields");
            pw.println("Arthur,Warrior,2,500,120,18,14");
        }

        List<Hero> loaded = HeroPersistence.loadAll();
        assertEquals(1, loaded.size());
        assertEquals("Arthur", loaded.get(0).getHeroName());
    }

    @Test
    @DisplayName("Line with invalid class fails validation and is skipped")
    void invalidClassSkipped() throws Exception {
        try (PrintWriter pw = new PrintWriter(FILE)) {
            pw.println("Ghost,Dragon,1,0,100,10,8");
            pw.println("Merlin,Mage,3,1000,100,22,10");
        }

        List<Hero> loaded = HeroPersistence.loadAll();
        assertEquals(1, loaded.size());
        assertEquals("Merlin", loaded.get(0).getHeroName());
    }

    @Test
    @DisplayName("Line with non-numeric stats is silently skipped")
    void nonNumericSkipped() throws Exception {
        try (PrintWriter pw = new PrintWriter(FILE)) {
            pw.println("Hero,Warrior,ONE,0,100,10,8");
        }

        List<Hero> loaded = HeroPersistence.loadAll();
        assertTrue(loaded.isEmpty());
    }
}
