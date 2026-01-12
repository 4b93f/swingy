package com.swingy.Model;

public class Enemy {
	private String name;
	private int level;
	private int hitPoints;
	private int attack;
	private int defense;
	private int strength;

	private Enemy(EnemyBuilder builder) {
		this.name = builder.name;
		this.level = builder.level;
		this.hitPoints = builder.hitPoints;
		this.attack = builder.attack;
		this.defense = builder.defense;
		this.strength = builder.strength;
	}

	public static class EnemyBuilder {
		private String name;
		private int level;
		private int hitPoints;
		private int attack;
		private int defense;
		private int strength;

		public EnemyBuilder() {}

		public EnemyBuilder(String name) {
			this.name = name;
		}

		public EnemyBuilder generateName() {
			this.name = "Enemy" + (int)(Math.random() * 100);
			return this;
		}

		public EnemyBuilder generateLevel() {
			this.level = (int)(Math.random() * 5) + 1;
			return this;
		}

		public EnemyBuilder generateHitPoints() {
			this.hitPoints = (int)(Math.random() * 10) + 10 * level;
			return this;
		}

		public EnemyBuilder generateAttack() {
			this.attack = (int)(Math.random() * (level * 10)) + level;
			return this;
		}

		public EnemyBuilder generateDefense() {
			this.defense = (int)(Math.random() * (level * 10)) + level;
			return this;
		}

		public EnemyBuilder generateStrength() {
			this.strength = level * 10 + (int)(Math.random() * 10) + level;
			return this;
		}

		public Enemy build() {
			this.generateLevel();
			this.generateStrength();
			this.generateHitPoints();
			this.generateAttack();
			this.generateDefense();
			this.generateName();
			return new Enemy(this);
		}
	}

	public String getName() { return name; }

	public int getHitPoints() { return hitPoints; }

	public int getAttack() { return attack; }

	public int getDefense() { return defense; }

	public int getLevel() { return level; }

	public int getStrength() { return strength; }

	@Override
	public String toString() {
		return "Enemy [name=" + name + ", level=" + level + ", hitPoints=" + hitPoints + ", attack=" + attack
				+ ", defense=" + defense + ", strength=" + strength + "]";
	}
}
