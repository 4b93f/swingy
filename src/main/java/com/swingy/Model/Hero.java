package com.swingy.Model;

import java.util.Random;

public class Hero {
	private String heroName;
	private String heroClass;
	private int level;
	private int experience;
	private int hitPoints;
	private int attack;
	private int defense;
	private Equipement equipement;

	private Hero(HeroBuilder builder) {
		this.heroName = builder.heroName;
		this.heroClass = builder.heroClass;
		this.level = builder.level;
		this.experience = builder.experience;
		this.hitPoints = builder.hitPoints;
		this.attack = builder.attack;
		this.defense = builder.defense;
		this.equipement = builder.equipement;
	}

	public static class HeroBuilder {
		private String heroName = "";
		private String heroClass = "";
		private int level = 1;
		private int experience = 0;
		private int hitPoints = 100;
		private int attack = 10;
		private int defense = 10;
		private Equipement equipement = new Equipement();

		public HeroBuilder() {}

		public HeroBuilder(String heroName) {
			this.heroName = heroName;
		}

		public HeroBuilder setHeroName(String heroName) {
			this.heroName = heroName;
			return this;
		}

		public HeroBuilder setHeroClass(String heroClass) {
			this.heroClass = heroClass;
			return this;
		}

		public HeroBuilder setLevel(int level) {
			this.level = level;
			return this;
		}

		public HeroBuilder setExperience(int experience) {
			this.experience = experience;
			return this;
		}

		public HeroBuilder setHitPoints(int hitPoints) {
			this.hitPoints = hitPoints;
			return this;
		}

		public HeroBuilder setAttack(int attack) {
			this.attack = attack;
			return this;
		}

		public HeroBuilder setDefense(int defense) {
			this.defense = defense;
			return this;
		}

		public Hero build() {
			// Calculate stats based on level if not explicitly set
			if (hitPoints == 100) {
				hitPoints = calculateBaseHitPoints(level, heroClass);
			}
			if (attack == 10) {
				attack = calculateBaseAttack(level, heroClass);
			}
			if (defense == 10) {
				defense = calculateBaseDefense(level, heroClass);
			}
			return new Hero(this);
		}

		private int calculateBaseHitPoints(int level, String heroClass) {
			int baseHP = 80 + (level * 20); // 100 at level 1, 120 at level 2, etc.
			return switch (heroClass.toLowerCase()) {
				case "tank" -> (int)(baseHP * 1.2);     // Tanks get more base HP
				case "rogue" -> (int)(baseHP * 0.9);    // Rogues get less base HP
				default -> baseHP;
			};
		}

		private int calculateBaseAttack(int level, String heroClass) {
			int baseAtk = 8 + (level * 3); // 11 at level 1, 14 at level 2, etc.
			return switch (heroClass.toLowerCase()) {
				case "warrior" -> (int)(baseAtk * 1.1);
				case "rogue" -> (int)(baseAtk * 1.2);
				case "mage" -> (int)(baseAtk * 1.15);
				case "tank" -> (int)(baseAtk * 0.9);
				default -> baseAtk;
			};
		}

		private int calculateBaseDefense(int level, String heroClass) {
			int baseDef = 6 + (level * 2); // 8 at level 1, 10 at level 2, etc.
			return switch (heroClass.toLowerCase()) {
				case "tank" -> (int)(baseDef * 1.3);
				case "warrior" -> (int)(baseDef * 1.1);
				case "rogue" -> (int)(baseDef * 0.8);
				case "mage" -> (int)(baseDef * 0.9);
				default -> baseDef;
			};
		}
	}

	public String getHeroName() {
		return heroName;
	}

	public String getHeroClass() {
		return heroClass;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public Equipement getEquipement() {
		return equipement;
	}

	public boolean fightEnemy(Enemy enemy) {
		int heroHP = getTotalHitPoints();
		int enemyHP = enemy.getHitPoints();
		
		heroHP = (int)(heroHP * getClassHPMultiplier());
		
		while (heroHP > 0 && enemyHP > 0) {
			int heroAttack = (int)(getTotalAttack() * getClassAttackMultiplier());
			int heroDefense = (int)(getTotalDefense() * getClassDefenseMultiplier());
			
			int heroDamage = calculateDamage(heroAttack, enemy.getDefense(), getClassCritChance());
			enemyHP -= heroDamage;
			
			if (enemyHP <= 0) {
				return true;
			}
			
			int enemyDamage = calculateDamage(enemy.getAttack(), heroDefense, 10);
			heroHP -= enemyDamage;
		}
		
		return heroHP > 0;
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

	private double getClassHPMultiplier() {
		return switch (heroClass.toLowerCase()) {
			case "tank" -> 1.3;
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

	private int calculateDamage(int attackPower, int defensePower, int critChance) {
		Random random = new Random();
		int baseDamage = Math.max(attackPower - defensePower, 1);
		
		if (random.nextInt(100) < critChance) {
			baseDamage *= 2;
		}

		double variance = 0.9 + random.nextDouble() * 0.2;
		return (int)(baseDamage * variance);
	}

	public int gainXpFromEnemy(Enemy enemy) {
		int xpGained = enemy.getStrength() * 50;
		this.experience += xpGained;
		return xpGained;
	}

	public boolean shouldLevelUp() {
		int xpThreshold = getXPThreshold(this.level);
		return this.experience >= xpThreshold;
	}

	private int getXPThreshold(int level) {
		return level * 1000 + (level - 1) * (level - 1) * 450;
	}

	public void levelUp() {
		this.experience -= getXPThreshold(this.level);
		this.level++;
		
		this.hitPoints += 25;
		this.attack += 3;
		this.defense += 2;
	}

	public void equipArtifact(com.swingy.Model.Artefact.Artefact artifact) {
		String artifactType = artifact.getClass().getSimpleName();
		switch (artifactType) {
			case "Weapon":
				this.equipement.setWeapon(artifact);
				break;
			case "Armor":
				this.equipement.setArmor(artifact);
				break;
			case "Helmet":
				this.equipement.setHelmet(artifact);
				break;
		}
	}

	public int getTotalAttack() {
		int weaponBonus = equipement.getWeapon() != null ? equipement.getWeapon().getBonusAttack() : 0;
		return attack + weaponBonus;
	}

	public int getTotalDefense() {
		int armorBonus = equipement.getArmor() != null ? equipement.getArmor().getBonusDefense() : 0;
		return defense + armorBonus;
	}

	public int getTotalHitPoints() {
		int helmetBonus = equipement.getHelmet() != null ? equipement.getHelmet().getBonusHitPoints() : 0;
		return hitPoints + helmetBonus;
	}

	public String toString() {
		return "Hero [heroName=" + heroName + ", heroClass=" + heroClass + ", level=" + level + ", experience=" + experience + ", hitPoints=" + hitPoints + ", attack=" + attack + ", defense=" + defense + ", equipement=" + equipement + "]";
	}
}
