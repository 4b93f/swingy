package com.swingy.Controller;
import com.swingy.Model.Hero;
import com.swingy.View.HeroView;

public class HeroController {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	private final Hero hero;
	private final HeroView heroView;

	public HeroController(Hero hero, HeroView heroView) {
		this.hero = hero;
		this.heroView = heroView;
	}


	public void displayHeroStats() {
		heroView.displayHeroStats(hero);
	}

	public Hero createNewHero(String name, String heroClass) {
		Hero newHero = new Hero.HeroBuilder()
			.setHeroName(name)
			.setHeroClass(heroClass)
			.build();
		heroView.displayHeroCreated(newHero);
		return newHero;
	}

	public void selectHero() {
		heroView.displayHeroSelected(hero);
	}

	public Hero getHero() {
		return hero;
	}
}

