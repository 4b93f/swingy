package com.swingy.model;

public class Enemy {
	private String name;
	private int level;
	private int hitPoints;
	private int attack;
	private int defense;
	private int strength;
	private Position position;

	private Enemy(EnemyBuilder builder) {
		this.name = builder.name;
		this.level = builder.level;
		this.hitPoints = builder.hitPoints;
		this.attack = builder.attack;
		this.defense = builder.defense;
		this.strength = builder.strength;
		this.position = builder.position;
	}

	public static class EnemyBuilder {
		private String name;
		private int level;
		private int hitPoints;
		private int attack;
		private int defense;
		private int strength;
		private Position position;

		public EnemyBuilder() {}

		public EnemyBuilder(String name) { this.name = name; }
		
		public EnemyBuilder generateName() {
			this.name = "Enemy" + (int)(Math.random() * 100);
			return this;
		}

		public EnemyBuilder generateLevel(int heroLevel) {
			double r = Math.random();
			if (r < 0.20 && heroLevel > 1) {
				this.level = heroLevel - 1;
			} else if (r < 0.80) {
				this.level = heroLevel;
			} else {
				this.level = heroLevel + 1;
			}
			return this;
		}

		public EnemyBuilder generateHitPoints() {
			this.hitPoints = 40 * level + (int)(Math.random() * 20 * level);
			return this;
		}

		public EnemyBuilder generateAttack() {
			this.attack = 8 + level * 4 + (int)(Math.random() * level * 3);
			return this;
		}

		public EnemyBuilder generateDefense() {
			this.defense = 2 * level + (int)(Math.random() * 2 * level);
			return this;
		}

		public EnemyBuilder generateStrength() {
			this.strength = level * 10 + (int)(Math.random() * 10) + level;
			return this;
		}

		public EnemyBuilder setPosition(Position position) {
			this.position = position;
			return this;
		}

		public Enemy build(int heroLevel) {
			this.generateLevel(heroLevel);
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

	public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }

	public int getAttack() { return attack; }

	public int getDefense() { return defense; }

	public int getLevel() { return level; }

	public int getStrength() { return strength; }

	public Position getPosition() { return position; }

	@Override
	public String toString() {
		return "Enemy [name=" + name + ", level=" + level + ", hitPoints=" + hitPoints + ", attack=" + attack
				+ ", defense=" + defense + ", strength=" + strength + "]";
	}
}
