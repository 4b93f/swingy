package com.swingy.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class EnemyTest {

    private Enemy buildEnemy(int heroLevel) {
        return new Enemy.EnemyBuilder()
            .setPosition(new Position(1, 1))
            .build(heroLevel);
    }

    @Test
    @DisplayName("Enemy has a non-null name after build")
    void hasName() {
        Enemy e = buildEnemy(1);
        assertNotNull(e.getName());
        assertFalse(e.getName().isBlank());
    }

    @Test
    @DisplayName("Enemy stats are positive after build")
    void statsPositive() {
        Enemy e = buildEnemy(1);
        assertTrue(e.getHitPoints() > 0);
        assertTrue(e.getAttack() > 0);
        assertTrue(e.getDefense() > 0);
        assertTrue(e.getStrength() > 0);
    }

    @Test
    @DisplayName("Enemy HP scales with enemy level: [40*level, 60*level)")
    void hpRange() {
        for (int heroLevel = 1; heroLevel <= 5; heroLevel++) {
            for (int i = 0; i < 30; i++) {
                Enemy e = buildEnemy(heroLevel);
                int lv = e.getLevel();
                int hp = e.getHitPoints();
                assertTrue(hp >= 40 * lv && hp < 60 * lv,
                    "HP " + hp + " out of range for enemy level " + lv);
            }
        }
    }

    @Test
    @DisplayName("Enemy ATK scales with enemy level: [8+4*level, 8+7*level)")
    void atkRange() {
        for (int heroLevel = 1; heroLevel <= 5; heroLevel++) {
            for (int i = 0; i < 30; i++) {
                Enemy e = buildEnemy(heroLevel);
                int lv = e.getLevel();
                int atk = e.getAttack();
                assertTrue(atk >= 8 + 4 * lv && atk < 8 + 7 * lv,
                    "ATK " + atk + " out of range for enemy level " + lv);
            }
        }
    }

    @Test
    @DisplayName("Enemy DEF scales with enemy level: [2*level, 4*level)")
    void defRange() {
        for (int heroLevel = 1; heroLevel <= 5; heroLevel++) {
            for (int i = 0; i < 30; i++) {
                Enemy e = buildEnemy(heroLevel);
                int lv = e.getLevel();
                int def = e.getDefense();
                assertTrue(def >= 2 * lv && def < 4 * lv,
                    "DEF " + def + " out of range for enemy level " + lv);
            }
        }
    }

    @RepeatedTest(20)
    @DisplayName("A hero with minimal stats loses to a level 3 enemy")
    void weakHeroCanDie() {
        Hero fragile = new Hero.HeroBuilder()
            .setHeroName("Glass")
            .setHeroClass("Warrior")
            .setLevel(1)
            .setHitPoints(10)
            .setAttack(1)
            .setDefense(1)
            .build();
        Enemy e = buildEnemy(3);
        assertFalse(fragile.fightEnemy(e));
    }

    @Test
    @DisplayName("Enemy position is set correctly")
    void positionSet() {
        Enemy e = buildEnemy(2);
        assertEquals(1, e.getPosition().getX());
        assertEquals(1, e.getPosition().getY());
    }

    @Test
    @DisplayName("Enemy level is within 1 when hero is level 1")
    void levelClampedAtMin() {
        for (int i = 0; i < 50; i++) {
            Enemy e = buildEnemy(1);
            assertTrue(e.getLevel() >= 1 && e.getLevel() <= 2,
                "level " + e.getLevel() + " out of range for hero lv1");
        }
    }

    @Test
    @DisplayName("Enemy level is within 4–6 when hero is level 5")
    void levelScalesAtHeroLevel5() {
        for (int i = 0; i < 50; i++) {
            Enemy e = buildEnemy(5);
            assertTrue(e.getLevel() >= 4 && e.getLevel() <= 6,
                "level " + e.getLevel() + " out of range for hero lv5");
        }
    }

    @RepeatedTest(30)
    @DisplayName("Enemy level is always within [hero-1, hero+1], min 1")
    void levelScalesWithHero() {
        int heroLevel = 3;
        Enemy e = buildEnemy(heroLevel);
        assertTrue(e.getLevel() >= heroLevel - 1 && e.getLevel() <= heroLevel + 1);
        assertTrue(e.getLevel() >= 1);
    }

    @Test
    @DisplayName("toString contains name and level")
    void toStringContainsFields() {
        Enemy e = buildEnemy(2);
        String s = e.toString();
        assertTrue(s.contains("name="));
        assertTrue(s.contains("level="));
    }
}
