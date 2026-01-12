package com.swingy.View;

public class HeroView {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public void displayHeroLevelUp(int newLevel) { System.out.println(ANSI_GREEN + "Hero leveled up to level " + newLevel + "!" + ANSI_RESET); }

	public void displayHeroGainedXp(int xpGained) { System.out.println(ANSI_GREEN + "Hero gained " + xpGained + " XP!" + ANSI_RESET); }

	public void displayHeroStats(com.swingy.Model.Hero hero) {
		System.out.println(ANSI_CYAN + "========== Hero Stats ==========" + ANSI_RESET);
		System.out.println(ANSI_WHITE + "Name: " + ANSI_YELLOW + hero.getHeroName() + ANSI_RESET);
		System.out.println(ANSI_WHITE + "Class: " + ANSI_YELLOW + hero.getHeroClass() + ANSI_RESET);
		System.out.println(ANSI_WHITE + "Level: " + ANSI_GREEN + hero.getLevel() + ANSI_RESET);
		System.out.println(ANSI_WHITE + "Experience: " + ANSI_GREEN + hero.getExperience() + ANSI_RESET);
		System.out.println(ANSI_WHITE + "HP: " + ANSI_RED + hero.getHitPoints() + ANSI_RESET);
		System.out.println(ANSI_WHITE + "Attack: " + ANSI_RED + hero.getAttack() + ANSI_RESET);
		System.out.println(ANSI_WHITE + "Defense: " + ANSI_BLUE + hero.getDefense() + ANSI_RESET);
		System.out.println(ANSI_CYAN + "===============================" + ANSI_RESET);
	}

	public void displayHeroCreated(com.swingy.Model.Hero hero) {
		System.out.println(ANSI_GREEN + "Hero created successfully!" + ANSI_RESET);
		System.out.println(ANSI_YELLOW + "Name: " + hero.getHeroName() + ANSI_RESET);
		System.out.println(ANSI_YELLOW + "Class: " + hero.getHeroClass() + ANSI_RESET);
	}

	public void displayHeroSelected(com.swingy.Model.Hero hero) { System.out.println(ANSI_GREEN + "Selected hero: " + hero.getHeroName() + " (" + hero.getHeroClass() + ")" + ANSI_RESET); }

}