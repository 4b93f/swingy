package src.main.java.com.swingy.Model;

public class Hero {
	private String heroName;
	private String heroClass;
	private int level;
	private int experience;
	private int hitPoints;
	private int attack;
	private int defense;

	private Hero(HeroBuilder builder) {
		this.heroName = builder.heroName;
		this.heroClass = builder.heroClass;
		this.level = builder.level;
		this.experience = builder.experience;
		this.hitPoints = builder.hitPoints;
		this.attack = builder.attack;
		this.defense = builder.defense;
	}

	/**
	 * Builder class for creating Hero instances using the builder pattern.
	 */
	public static class HeroBuilder {
		private String heroName = "";
		private String heroClass = "";
		private int level = 1;
		private int experience = 0;
		private int hitPoints = 100;
		private int attack = 10;
		private int defense = 10;

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
			return new Hero(this);
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

	public String toString() {
		return "Hero [heroName=" + heroName + ", heroClass=" + heroClass + ", level=" + level + ", experience=" + experience + ", hitPoints=" + hitPoints + ", attack=" + attack + ", defense=" + defense + "]";
	}
}
