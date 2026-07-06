package com.swingy.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.swingy.model.artefact.Armor;
import com.swingy.model.artefact.Helmet;
import com.swingy.model.artefact.Weapon;

public class HeroCombatTest {

    private Hero strongHero;
    private Hero weakHero;
    private Enemy weakEnemy;
    private Enemy strongEnemy;

    @BeforeEach
    void setUp() {
        strongHero = new Hero.HeroBuilder()
            .setHeroName("Champion")
            .setHeroClass("Warrior")
            .setLevel(5)
            .setHitPoints(500)
            .setAttack(200)
            .setDefense(100)
            .build();

        weakHero = new Hero.HeroBuilder()
            .setHeroName("Novice")
            .setHeroClass("Warrior")
            .setLevel(1)
            .setHitPoints(1)
            .setAttack(1)
            .setDefense(0)
            .build();

        weakEnemy = new Enemy.EnemyBuilder()
            .setPosition(new Position(1, 1))
            .build(1);

        strongEnemy = new Enemy.EnemyBuilder()
            .setPosition(new Position(1, 1))
            .build(5);
    }

    // ── getTotalX with artifacts ──────────────────────────────────────────────

    @Test
    @DisplayName("getTotalAttack adds weapon bonus")
    void totalAttackWithWeapon() {
        int base = strongHero.getTotalAttack();
        strongHero.equipArtifact(new Weapon("Sword", 25));
        assertEquals(base + 25, strongHero.getTotalAttack());
    }

    @Test
    @DisplayName("getTotalDefense adds armor bonus")
    void totalDefenseWithArmor() {
        int base = strongHero.getTotalDefense();
        strongHero.equipArtifact(new Armor("Plate", 15));
        assertEquals(base + 15, strongHero.getTotalDefense());
    }

    @Test
    @DisplayName("getTotalHitPoints adds helmet bonus")
    void totalHPWithHelmet() {
        int base = strongHero.getTotalHitPoints();
        strongHero.equipArtifact(new Helmet("Crown", 50));
        assertEquals(base + 50, strongHero.getTotalHitPoints());
    }

    @Test
    @DisplayName("equipArtifact replaces existing weapon slot")
    void equipWeaponReplacesOld() {
        strongHero.equipArtifact(new Weapon("Dagger", 5));
        strongHero.equipArtifact(new Weapon("Axe", 30));
        assertEquals(strongHero.getAttack() + 30, strongHero.getTotalAttack());
    }

    @Test
    @DisplayName("No artifact: getTotalAttack equals base attack")
    void totalAttackNoArtifact() {
        Hero h = new Hero.HeroBuilder()
            .setHeroName("Plain")
            .setHeroClass("Warrior")
            .setAttack(15)
            .build();
        assertEquals(15, h.getTotalAttack());
    }

    // ── fightEnemy ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Very strong hero beats very weak enemy")
    void strongHeroWins() {
        assertTrue(strongHero.fightEnemy(weakEnemy));
    }

    @Test
    @DisplayName("fightEnemyDetailed returns non-null result with turns")
    void detailedResultHasTurns() {
        BattleResult result = strongHero.fightEnemyDetailed(weakEnemy);
        assertNotNull(result);
        assertFalse(result.getTurns().isEmpty());
    }

    @Test
    @DisplayName("fightEnemyDetailed: hero wins → isHeroWon true for strong hero")
    void detailedHeroWon() {
        BattleResult result = strongHero.fightEnemyDetailed(weakEnemy);
        assertTrue(result.isHeroWon());
    }

    // ── XP and level-up ───────────────────────────────────────────────────────

    @Test
    @DisplayName("gainXpFromEnemy increases hero experience")
    void gainXpIncreasesExperience() {
        int before = strongHero.getExperience();
        int gained = strongHero.gainXpFromEnemy(weakEnemy);
        assertTrue(gained > 0);
        assertEquals(before + gained, strongHero.getExperience());
    }

    @Test
    @DisplayName("gainXpFromEnemy returns enemy strength × 50")
    void gainXpFormula() {
        int gained = strongHero.gainXpFromEnemy(weakEnemy);
        assertEquals(weakEnemy.getStrength() * 50, gained);
    }

    @Test
    @DisplayName("shouldLevelUp true when XP meets threshold at level 1")
    void shouldLevelUpTrue() {
        Hero h = new Hero.HeroBuilder()
            .setHeroName("Up")
            .setHeroClass("Warrior")
            .setExperience(1000)
            .build();
        assertTrue(h.shouldLevelUp());
    }

    @Test
    @DisplayName("shouldLevelUp false when XP below threshold")
    void shouldLevelUpFalse() {
        Hero h = new Hero.HeroBuilder()
            .setHeroName("Stay")
            .setHeroClass("Warrior")
            .setExperience(999)
            .build();
        assertFalse(h.shouldLevelUp());
    }

    @Test
    @DisplayName("levelUp increases level and stats")
    void levelUpIncreasesStats() {
        Hero h = new Hero.HeroBuilder()
            .setHeroName("Up")
            .setHeroClass("Warrior")
            .setLevel(1)
            .setExperience(1000)
            .setHitPoints(100)
            .setAttack(10)
            .setDefense(8)
            .build();
        h.levelUp();
        assertEquals(2, h.getLevel());
        assertEquals(125, h.getHitPoints());
        assertEquals(13, h.getAttack());
        assertEquals(10, h.getDefense());
    }

    @Test
    @DisplayName("levelUp deducts XP threshold from experience")
    void levelUpDeductsXp() {
        Hero h = new Hero.HeroBuilder()
            .setHeroName("Up")
            .setHeroClass("Warrior")
            .setLevel(1)
            .setExperience(1500)
            .build();
        h.levelUp();
        assertEquals(500, h.getExperience());
    }
}
