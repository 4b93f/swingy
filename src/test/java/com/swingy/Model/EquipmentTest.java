package com.swingy.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.swingy.model.artefact.Armor;
import com.swingy.model.artefact.Helmet;
import com.swingy.model.artefact.Weapon;

public class EquipmentTest {

    private Equipment eq;

    @BeforeEach
    void setUp() { eq = new Equipment(); }

    @Test
    @DisplayName("All slots are null on creation")
    void allNullInitially() {
        assertNull(eq.getWeapon());
        assertNull(eq.getArmor());
        assertNull(eq.getHelmet());
    }

    @Test
    @DisplayName("setWeapon / getWeapon roundtrip")
    void weaponSlot() {
        Weapon w = new Weapon("Sword", 10);
        eq.setWeapon(w);
        assertSame(w, eq.getWeapon());
    }

    @Test
    @DisplayName("setArmor / getArmor roundtrip")
    void armorSlot() {
        Armor a = new Armor("Plate", 8);
        eq.setArmor(a);
        assertSame(a, eq.getArmor());
    }

    @Test
    @DisplayName("setHelmet / getHelmet roundtrip")
    void helmetSlot() {
        Helmet h = new Helmet("Iron Helm", 30);
        eq.setHelmet(h);
        assertSame(h, eq.getHelmet());
    }

    @Test
    @DisplayName("toString contains 'Equipment'")
    void toStringTag() {
        assertTrue(eq.toString().contains("Equipment"));
    }

    @Test
    @DisplayName("Weapon bonus attack is correct")
    void weaponBonusAttack() {
        Weapon w = new Weapon("Axe", 15);
        assertEquals(15, w.getBonusAttack());
        assertEquals(0, w.getBonusDefense());
        assertEquals(0, w.getBonusHitPoints());
    }

    @Test
    @DisplayName("Armor bonus defense is correct")
    void armorBonusDefense() {
        Armor a = new Armor("Chainmail", 12);
        assertEquals(12, a.getBonusDefense());
        assertEquals(0, a.getBonusAttack());
        assertEquals(0, a.getBonusHitPoints());
    }

    @Test
    @DisplayName("Helmet bonus HP is correct")
    void helmetBonusHP() {
        Helmet h = new Helmet("Crown", 50);
        assertEquals(50, h.getBonusHitPoints());
        assertEquals(0, h.getBonusAttack());
        assertEquals(0, h.getBonusDefense());
    }
}
