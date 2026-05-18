package com.swingy.model;

import java.util.Random;
import com.swingy.model.artefact.Artefact;
import jakarta.validation.constraints.*;

public class Hero {
	@NotBlank
	@Size(min = 2, max = 20)
	private String heroName;

	@NotBlank
	@Pattern(regexp = "(?i)warrior|mage|rogue|tank", message = "must be Warrior, Mage, Rogue or Tank")
	private String heroClass;

	@Min(1)
	private int level;

	@Min(0)
	private int experience;

	@Min(1)
	private int hitPoints;

	@Min(1)
	private int attack;

	@Min(1)
	private int defense;

	private Equipment equipment;
	private int maxHitPoints;

	private Hero(HeroBuilder builder) {
		this.heroName = builder.heroName;
		this.heroClass = builder.heroClass;
		this.level = builder.level;
		this.experience = builder.experience;
		this.hitPoints = builder.hitPoints;
		this.maxHitPoints = builder.hitPoints;
		this.attack = builder.attack;
		this.defense = builder.defense;
		this.equipment = builder.equipment;
	}

	public static class HeroBuilder {
		private String heroName = "";
		private String heroClass = "";
		private int level = 1;
		private int experience = 0;
		private int hitPoints = -1;
		private int attack = -1;
		private int defense = -1;
		private Equipment equipment = new Equipment();

		public HeroBuilder() {}

		public HeroBuilder setHeroName(String heroName) { this.heroName = heroName; return this; }

		public HeroBuilder setHeroClass(String heroClass) { this.heroClass = heroClass; return this; }

		public HeroBuilder setLevel(int level) { this.level = level; return this; }

		public HeroBuilder setExperience(int experience) { this.experience = experience; return this; }

		public HeroBuilder setHitPoints(int hitPoints) { this.hitPoints = hitPoints; return this; }

		public HeroBuilder setAttack(int attack) { this.attack = attack; return this; }

		public HeroBuilder setDefense(int defense) { this.defense = defense; return this; }

		public Hero build() {
			if (hitPoints == -1) hitPoints = calculateBaseHitPoints(level, heroClass);
			if (attack   == -1) attack    = calculateBaseAttack(level, heroClass);
			if (defense  == -1) defense   = calculateBaseDefense(level, heroClass);
			return new Hero(this);
		}

		private int calculateBaseHitPoints(int level, String heroClass) {
			int baseHP = 80 + (level * 20);
			return switch (heroClass.toLowerCase()) {
				case "tank" -> (int)(baseHP * 1.2);
				case "rogue" -> (int)(baseHP * 0.9);
				default -> baseHP;
			};
		}

		private int calculateBaseAttack(int level, String heroClass) {
			return 8 + (level * 3);
		}

		private int calculateBaseDefense(int level, String heroClass) {
			return 6 + (level * 2);
		}
	}

	public String getHeroName() { return heroName; }

	public String getHeroClass() { return heroClass; }

	public int getLevel() { return level; }

	public void setLevel(int level) { this.level = level; }

	public int getExperience() { return experience; }

	public void setExperience(int experience) { this.experience = experience; }

	public int getHitPoints() { return hitPoints; }

	public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }

	public int getAttack() { return attack; }

	public void setAttack(int attack) { this.attack = attack; }

	public int getDefense() { return defense; }

	public void setDefense(int defense) { this.defense = defense; }

	public Equipment getEquipment() { return equipment; }

	public BattleResult fightEnemyDetailed(Enemy enemy) {
		BattleResult result = new BattleResult();
		int startHP = getTotalHitPoints();
		int heroHP  = startHP;

		while (heroHP > 0 && enemy.getHitPoints() > 0) {
			result = heroAttack(heroName, enemy, result, heroHP);
			if (enemy.getHitPoints() <= 0) break;

			int enemyDamage = enemyAttack(enemy, result, heroHP);
			heroHP -= enemyDamage;
		}

		if (heroHP > 0) {
			int damageTaken = startHP - heroHP;
			setHitPoints(Math.max(1, hitPoints - damageTaken));
		}

		result.setHeroWon(heroHP > 0);
		return result;
	}

	private BattleResult heroAttack(String heroName, Enemy enemy, BattleResult result, int heroHP) {
		int heroAttack = (int)(getTotalAttack() * getClassAttackMultiplier());
		DamageResult heroDamageResult = calculateDamageWithCrit(heroAttack, enemy.getDefense(), getClassCritChance());
		int heroDamage = heroDamageResult.damage;
		enemy.setHitPoints(enemy.getHitPoints() - heroDamage);
		result.addTurn(heroName, enemy.getName(), heroDamage, heroDamageResult.isCritical, heroHP, Math.max(0, enemy.getHitPoints()));
		return result;
	}

	private int enemyAttack(Enemy enemy, BattleResult result, int heroHP) {
		int heroDefense = (int)(getTotalDefense() * getClassDefenseMultiplier());
		DamageResult enemyDamageResult = calculateDamageWithCrit(enemy.getAttack(), heroDefense, 5);
		int enemyDamage = enemyDamageResult.damage;
		result.addTurn(enemy.getName(), this.heroName, enemyDamage, enemyDamageResult.isCritical,
			Math.max(0, enemy.getHitPoints()),
			Math.max(0, heroHP - enemyDamage));
		return enemyDamage;
	}


	public boolean fightEnemy(Enemy enemy) {
		return fightEnemyDetailed(enemy).isHeroWon();
	}

	private double getClassAttackMultiplier() {
		return switch (heroClass.toLowerCase()) {
			case "warrior" -> 1.3;
			case "tank" -> 0.8;
			case "rogue" -> 1.5;
			case "mage" -> 1.4;
			default -> 1.0;
		};
	}

	private double getClassDefenseMultiplier() {
		return switch (heroClass.toLowerCase()) {
			case "warrior" -> 1.1;
			case "tank" -> 1.5;
			case "rogue" -> 0.7;
			case "mage" -> 0.8;
			default -> 1.0;
		};
	}

	private int getClassCritChance() {
		return switch (heroClass.toLowerCase()) {
			case "warrior" -> 15;
			case "tank" -> 5;
			case "rogue" -> 25;
			case "mage" -> 20;
			default -> 10;
		};
	}

	private static class DamageResult {
		int damage;
		boolean isCritical;
		
		DamageResult(int damage, boolean isCritical) {
			this.damage = damage;
			this.isCritical = isCritical;
		}
	}
	
	private DamageResult calculateDamageWithCrit(int attackPower, int defensePower, int critChance) {
		Random random = new Random();
		int baseDamage = Math.max(attackPower - defensePower, 1);
		boolean isCrit = random.nextInt(100) < critChance;
		
		if (isCrit) {
			baseDamage *= 2;
		}

		double variance = 0.9 + random.nextDouble() * 0.2;
		int finalDamage = Math.max((int)(baseDamage * variance), 1);
		return new DamageResult(finalDamage, isCrit);
	}

	public int gainXpFromEnemy(Enemy enemy) {
		int xpGained = enemy.getStrength() * 50;
		this.experience += xpGained;
		return xpGained;
	}

	public boolean shouldLevelUp() { return this.experience >= getXPThreshold(this.level); }

	// XP formula
	private int getXPThreshold(int level) { return level * 1000 + (level - 1) * (level - 1) * 450; }

	public void levelUp() {
		this.experience -= getXPThreshold(this.level);
		this.level++;
		this.hitPoints    += 25;
		this.maxHitPoints += 25;
		this.attack  += 3;
		this.defense += 2;
	}

	public void restoreHp() { this.hitPoints = maxHitPoints; }

	public void equipArtifact(Artefact artifact) {
		String artifactType = artifact.getClass().getSimpleName();
		switch (artifactType) {
			case "Weapon":
				this.equipment.setWeapon(artifact);
				break;
			case "Armor":
				this.equipment.setArmor(artifact);
				break;
			case "Helmet":
				this.equipment.setHelmet(artifact);
				break;
		}
	}

	public int getTotalAttack() {
		int weaponBonus = equipment.getWeapon() != null ? equipment.getWeapon().getBonusAttack() : 0;
		return attack + weaponBonus;
	}

	public int getTotalDefense() {
		int armorBonus = equipment.getArmor() != null ? equipment.getArmor().getBonusDefense() : 0;
		return defense + armorBonus;
	}

	public int getTotalHitPoints() {
		int helmetBonus = equipment.getHelmet() != null ? equipment.getHelmet().getBonusHitPoints() : 0;
		return hitPoints + helmetBonus;
	}

	public String toString() {
		return "Hero [heroName=" + heroName + ", heroClass=" + heroClass + ", level=" + level + ", experience=" + experience + ", hitPoints=" + hitPoints + ", attack=" + attack + ", defense=" + defense + ", equipment=" + equipment + "]";
	}
}
