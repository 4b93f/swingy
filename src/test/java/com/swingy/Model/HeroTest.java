package com.swingy.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HeroTest {

    private Hero.HeroBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new Hero.HeroBuilder();
    }

    @Test
    @DisplayName("Should create hero with default values")
    void testHeroCreationWithDefaults() {
        Hero hero = builder.build();

        assertEquals("", hero.getHeroName());
        assertEquals("", hero.getHeroClass());
        assertEquals(1, hero.getLevel());
        assertEquals(0, hero.getExperience());
        // Stats are now calculated based on level (1) and class (default/"")
        assertEquals(100, hero.getHitPoints()); // 80 + (1 * 20) = 100
        assertEquals(11, hero.getAttack());     // 8 + (1 * 3) = 11
        assertEquals(8, hero.getDefense());     // 6 + (1 * 2) = 8
        assertNotNull(hero.getEquipement());
    }

    @Test
    @DisplayName("Should create hero with custom name and class")
    void testHeroCreationWithNameAndClass() {
        Hero hero = builder
                .setHeroName("Aragorn")
                .setHeroClass("Warrior")
                .build();

        assertEquals("Aragorn", hero.getHeroName());
        assertEquals("Warrior", hero.getHeroClass());
    }

    @Test
    @DisplayName("Should create hero with all custom attributes")
    void testHeroCreationWithAllAttributes() {
        Hero hero = builder
                .setHeroName("Gandalf")
                .setHeroClass("Wizard")
                .setLevel(5)
                .setExperience(10000)
                .setHitPoints(150)
                .setAttack(25)
                .setDefense(20)
                .build();

        assertEquals("Gandalf", hero.getHeroName());
        assertEquals("Wizard", hero.getHeroClass());
        assertEquals(5, hero.getLevel());
        assertEquals(10000, hero.getExperience());
        assertEquals(150, hero.getHitPoints());
        assertEquals(25, hero.getAttack());
        assertEquals(20, hero.getDefense());
    }

    @Test
    @DisplayName("Should update hero level")
    void testSetLevel() {
        Hero hero = builder.build();
        hero.setLevel(3);

        assertEquals(3, hero.getLevel());
    }

    @Test
    @DisplayName("Should update hero experience")
    void testSetExperience() {
        Hero hero = builder.build();
        hero.setExperience(5000);

        assertEquals(5000, hero.getExperience());
    }

    @Test
    @DisplayName("Should update hero hit points")
    void testSetHitPoints() {
        Hero hero = builder.build();
        hero.setHitPoints(200);

        assertEquals(200, hero.getHitPoints());
    }

    @Test
    @DisplayName("Should update hero attack")
    void testSetAttack() {
        Hero hero = builder.build();
        hero.setAttack(50);

        assertEquals(50, hero.getAttack());
    }

    @Test
    @DisplayName("Should update hero defense")
    void testSetDefense() {
        Hero hero = builder.build();
        hero.setDefense(30);

        assertEquals(30, hero.getDefense());
    }

    @Test
    @DisplayName("Should return correct toString representation")
    void testToString() {
        Hero hero = builder
                .setHeroName("TestHero")
                .setHeroClass("TestClass")
                .setLevel(2)
                .setExperience(1500)
                .setHitPoints(120)
                .setAttack(15)
                .setDefense(12)
                .build();

        String result = hero.toString();
        assertNotNull(result);
        assertEquals(true, result.contains("heroName=TestHero"));
        assertEquals(true, result.contains("heroClass=TestClass"));
        assertEquals(true, result.contains("level=2"));
        assertEquals(true, result.contains("experience=1500"));
        assertEquals(true, result.contains("hitPoints=120"));
        assertEquals(true, result.contains("attack=15"));
        assertEquals(true, result.contains("defense=12"));
    }

    @Test
    @DisplayName("Builder should support method chaining")
    void testBuilderChaining() {
        Hero hero = new Hero.HeroBuilder()
                .setHeroName("Legolas")
                .setHeroClass("Archer")
                .setLevel(3)
                .setExperience(3000)
                .setHitPoints(110)
                .setAttack(30)
                .setDefense(15)
                .build();

        assertNotNull(hero);
        assertEquals("Legolas", hero.getHeroName());
        assertEquals("Archer", hero.getHeroClass());
    }

    @Test
    @DisplayName("Should create multiple independent heroes")
    void testMultipleHeroCreation() {
        Hero hero1 = new Hero.HeroBuilder()
                .setHeroName("Hero1")
                .setLevel(1)
                .build();

        Hero hero2 = new Hero.HeroBuilder()
                .setHeroName("Hero2")
                .setLevel(2)
                .build();

        assertNotEquals(hero1.getHeroName(), hero2.getHeroName());
        assertNotEquals(hero1.getLevel(), hero2.getLevel());
    }
}