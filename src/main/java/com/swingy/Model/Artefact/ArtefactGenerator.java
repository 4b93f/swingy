// Model/ArtefactGenerator.java
package com.swingy.Model.Artefact;

import java.util.Random;

import com.swingy.Model.Enemy;

public class ArtefactGenerator {
    private static final Random random = new Random();
    
    private static final String[] WEAPON_NAMES = {"Sword", "Axe", "Mace", "Spear", "Dagger"};
    private static final String[] ARMOR_NAMES = {"Chainmail", "Plate Armor", "Leather Armor", "Scale Mail", "Dragon Armor"};
    private static final String[] HELMET_NAMES = {"Iron Helmet", "Steel Helm", "Crown", "Battle Helm", "Wizard Hat"};
    
    public static Artefact generate(Enemy enemy) {
		int enemyStrength = enemy.getStrength();
        int baseStatBonus = enemyStrength * 2;
        int bonusAttack = baseStatBonus + random.nextInt(baseStatBonus / 2 + 1);
        int bonusDefense = baseStatBonus + random.nextInt(baseStatBonus / 2 + 1);
        int bonusHitPoints = baseStatBonus * 5 + random.nextInt(baseStatBonus * 3 + 1);
        
        int artifactType = random.nextInt(3);
        
        switch (artifactType) {
            case 0:
                return new Weapon(
                    WEAPON_NAMES[random.nextInt(WEAPON_NAMES.length)], 
                    bonusAttack + enemyStrength
                );
            case 1:
                return new Armor(
                    ARMOR_NAMES[random.nextInt(ARMOR_NAMES.length)], 
                    bonusDefense + enemyStrength
                );
            case 2:
                return new Helmet(
                    HELMET_NAMES[random.nextInt(HELMET_NAMES.length)], 
                    bonusHitPoints
                );
            default:
                return null;
        }
    }
}