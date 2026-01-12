package com.swingy.View;

public class FightView {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public void displayHeroEscaped() { System.out.println(ANSI_YELLOW + "Hero escaped!" + ANSI_RESET); }

	public void displayHeroCaught() { System.out.println(ANSI_RED + "Hero failed to escape!" + ANSI_RESET); }

	public void displayHeroGainedXp(int xpGained) { System.out.println(ANSI_GREEN + "Hero gained " + xpGained + " XP!" + ANSI_RESET); }

	public void displayHeroFoundArtifact(Object artifact) { System.out.println(ANSI_GREEN + "Hero found an artifact! : " + artifact + ANSI_RESET); }

	public void displayNoArtifactFound() { System.out.println("No artifact found."); }

	public void displayHeroLevelUp(int newLevel) { System.out.println(ANSI_GREEN + "Hero leveled up to level " + newLevel + "!" + ANSI_RESET); }

}
