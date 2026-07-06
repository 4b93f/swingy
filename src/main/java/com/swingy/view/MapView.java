package com.swingy.view;

import java.util.List;

import com.swingy.model.Enemy;
import com.swingy.model.GameState;
import com.swingy.model.Hero;
import com.swingy.model.Position;
import com.swingy.model.artefact.Artefact;

public class MapView {
	private static final String ANSI_CLEAR = "\033[H\033[2J";
	// private static final String ANSI_HOME = "\033[H";
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_BOLD = "\u001B[1m";

	private static final int VIEWPORT_SIZE = 11;
	private static final int HALF_VIEW = VIEWPORT_SIZE / 2;

	private final Hero hero;
	private List<Enemy> enemies;
	private Position pos;
	private int mapSize;

	public MapView(Hero hero, List<Enemy> enemies, Position pos, int mapSize) {
		this.hero = hero;
		this.enemies = enemies;
		this.pos = pos;
		this.mapSize = mapSize;
	}

	public void displayMap(GameState state) {
		clearScreen();
		
		this.pos = state.getPosition();
		this.mapSize = state.getMapSize();
		
		header(state, true);
		drawViewport(pos, mapSize);
		footer(false);
	}

	public void displayFullMap(GameState state) {
		clearScreen();
		
		header(state, false);
		drawFullMap();
		footer(true);
	}

	public void displayVictory(GameState state) {
		System.out.println();
		System.out.println(ANSI_GREEN + ANSI_BOLD + "═".repeat(60) + ANSI_RESET);
		System.out.println(ANSI_GREEN + ANSI_BOLD + 
						  "  VICTORY! You reached the border!" + ANSI_RESET);
		System.out.println(ANSI_GREEN + ANSI_BOLD + "═".repeat(60) + ANSI_RESET);
		System.out.println();
	}
	
	public void displayGameOver(GameState state) {
		clearScreen();
		System.out.println();
		System.out.println("═".repeat(60));
		System.out.println("           💀 GAME OVER 💀");
		System.out.println("═".repeat(60));
		System.out.println();
		System.out.println("  You were defeated in battle...");
		System.out.println("  Final Level: " + state.getHero().getLevel());
		System.out.println("  Final XP: " + state.getHero().getExperience());
		System.out.println();
		System.out.println("═".repeat(60));
	}

	public void displayInvalidMove() {
		System.out.println(ANSI_YELLOW + "  Invalid move! You cannot move outside the map." + ANSI_RESET);
	}
	
	public void displayBattleStart(Hero hero, Enemy enemy) {
		System.out.println("\n╔════════════════════════════════════════╗");
		System.out.println("║          BATTLE COMMENCED!             ║");
		System.out.println("╚════════════════════════════════════════╝");
		System.out.println("  You encountered " + ANSI_BOLD + ANSI_YELLOW + enemy.getName() + ANSI_RESET + " (Level " + enemy.getLevel() + ")!");
		System.out.println("  " + ANSI_CYAN + "Your HP:" + ANSI_RESET + " " + hero.getHitPoints());
		System.out.println("  " + ANSI_YELLOW + "Enemy HP:" + ANSI_RESET + " " + enemy.getHitPoints());
	}
	
	public void displayBattleOptions() {
		System.out.println("\n  [F] Fight  [R] Run");
		System.out.print("  > ");
	}
	
	public void displayBattleVictory(Hero hero, Enemy enemy, int xpGained, boolean leveledUp) {
		System.out.println("\n  ⚔️  " + ANSI_GREEN + ANSI_BOLD + "Victory!" + ANSI_RESET + " You defeated " + enemy.getName() + "!");
		System.out.println("  You gained " + ANSI_CYAN + xpGained + " XP" + ANSI_RESET + "!");
		if (leveledUp) {
			System.out.println("  🎉 " + ANSI_GREEN + ANSI_BOLD + "Level Up!" + ANSI_RESET + " You are now level " + hero.getLevel());
		}
	}
	
	public void displayBattleDefeat() {
		System.out.println("\n  💀 " + ANSI_BOLD + "You were defeated..." + ANSI_RESET);
	}
	
	public void displayRunSuccess() {
		System.out.println("\n  🏃 " + ANSI_GREEN + "You successfully fled from battle!" + ANSI_RESET);
	}
	
	public void displayRunFailure() {
		System.out.println("\n  ⚠️  " + ANSI_YELLOW + "Escape failed! The enemy blocks your path!" + ANSI_RESET);
	}
	
	public void displayForcedToFight() {
		System.out.println("  " + ANSI_BOLD + ANSI_YELLOW + "You must fight!" + ANSI_RESET);
	}
	
	public void displayBattleTurn(String attacker, String defender, int damage, boolean isCritical, int defenderHP) {
		String critText = isCritical ? ANSI_BOLD + ANSI_YELLOW + " CRITICAL HIT!" + ANSI_RESET : "";
		System.out.println("  " + ANSI_CYAN + attacker + ANSI_RESET + " attacks " + 
		                   ANSI_YELLOW + defender + ANSI_RESET + " for " + 
		                   ANSI_BOLD + damage + ANSI_RESET + " damage!" + critText);
		System.out.println("  " + ANSI_YELLOW + defender + ANSI_RESET + " HP: " + defenderHP);
	}
	
	public void displayArtifactFound(Artefact artifact) {
		System.out.println("\n  💎 " + ANSI_GREEN + "Artifact dropped: " + ANSI_RESET +
			artifact.getClass().getSimpleName() +
			" (ATK+" + artifact.getBonusAttack() +
			" DEF+" + artifact.getBonusDefense() +
			" HP+" + artifact.getBonusHitPoints() + ")");
		System.out.print("  Keep it? [K] Keep  [L] Leave  > ");
	}

	public void displayBattleError(String errorMessage) {
		System.err.println("Error in battle: " + errorMessage);
	}
	
	public void displayTerminalInitError(String errorMessage) {
		System.err.println("Failed to initialize terminal: " + errorMessage);
	}
	
	public void displayInputError(String errorMessage) {
		System.err.println("Error reading input: " + errorMessage);
	}
	
	public void displayPressKeyPrompt() {
		System.out.print("  Press any key to continue...");
	}

	public void displayQuickStats(Hero hero) {
		System.out.printf("  " + ANSI_CYAN + "HP:" + ANSI_RESET + " %d | " + 
						 ANSI_CYAN + "ATK:" + ANSI_RESET + " %d | " + 
						 ANSI_CYAN + "DEF:" + ANSI_RESET + " %d | " + 
						 ANSI_CYAN + "XP:" + ANSI_RESET + " %d%n",
						 hero.getHitPoints(), hero.getAttack(), hero.getDefense(), hero.getExperience());
	}

	private void drawViewport(Position heroPos, int mapSize) {
		int startX = heroPos.getX() - HALF_VIEW;
		int startY = heroPos.getY() - HALF_VIEW;
		int endX = heroPos.getX() + HALF_VIEW;
		int endY = heroPos.getY() + HALF_VIEW;
		
		for (int y = startY; y <= endY; y++) {
			System.out.print("        ");
			
			for (int x = startX; x <= endX; x++) {
				String tile = getTile(x, y, heroPos, mapSize);
				System.out.print(tile + " ");
			}
			System.out.println();
		}
	}

	private void drawFullMap() {
		for (int y = 0; y < mapSize; y++) {
			System.out.print("  ");
			for (int x = 0; x < mapSize; x++) {
				String tile = getTile(x, y, pos, mapSize);
				System.out.print(tile);
			}
			System.out.println();
		}
	}

	private String getTile(int x, int y, Position heroPos, int mapSize) {
		if (x == heroPos.getX() && y == heroPos.getY()) {
			return ANSI_BOLD + ANSI_GREEN + "H" + ANSI_RESET;
		}
		
		if (x < 0 || x >= mapSize || y < 0 || y >= mapSize) {
			return "█";
		}
		
		if (x == 0 || x == mapSize - 1 || y == 0 || y == mapSize - 1) {
			return "▓";
		}

		for (Enemy enemy : enemies) {
			Position enemyPos = enemy.getPosition();
			if (x == enemyPos.getX() && y == enemyPos.getY()) {
				return ANSI_BOLD + ANSI_YELLOW + "E" + ANSI_RESET;
			}
		}
		
		return "·";
	}

	private void header(GameState state, boolean showFullDetails) {
		System.out.println("═".repeat(60));

		if (showFullDetails) {
			System.out.printf("  " + ANSI_BOLD + ANSI_CYAN + "%s" + ANSI_RESET + "  " + ANSI_GREEN + "Lv.%d  %s" + ANSI_RESET + "%n",
				hero.getHeroName(), hero.getLevel(), hero.getHeroClass());
			System.out.printf("  " + ANSI_CYAN + "HP:" + ANSI_RESET + " %-5d " +
							  ANSI_CYAN + "ATK:" + ANSI_RESET + " %-5d " +
							  ANSI_CYAN + "DEF:" + ANSI_RESET + " %-5d " +
							  ANSI_CYAN + "XP:" + ANSI_RESET + " %d%n",
				hero.getTotalHitPoints(), hero.getTotalAttack(), hero.getTotalDefense(), hero.getExperience());
			System.out.printf("  " + ANSI_WHITE + "Pos:" + ANSI_RESET + " (%d,%d)  " +
							  ANSI_YELLOW + "Border in %d steps" + ANSI_RESET + "%n",
				pos.getX(), pos.getY(), state.getDistanceToBorder());
		} else {
			System.out.printf("  " + ANSI_BOLD + ANSI_CYAN + "FULL MAP" + ANSI_RESET + " - %s at (%d, %d)%n",
				hero.getHeroName(), pos.getX(), pos.getY());
		}

		System.out.println("═".repeat(60));
		System.out.println();
	}

	private void footer(boolean isFullMap) {
		System.out.println();
		System.out.println("═".repeat(60));
		
		if (isFullMap) {
			System.out.println("  " + ANSI_CYAN + "[Enter]" + ANSI_RESET + " Return to game");
			System.out.print("  ");
		} else {
			System.out.println("  " + ANSI_CYAN + "[W]" + ANSI_RESET + " North | " + 
				ANSI_CYAN + "[S]" + ANSI_RESET + " South | " + 
				ANSI_CYAN + "[A]" + ANSI_RESET + " West | " + 
				ANSI_CYAN + "[D]" + ANSI_RESET + " East");
			System.out.println("  " + ANSI_CYAN + "[M]" + ANSI_RESET + " Show full map | " + 
				ANSI_CYAN + "[Q]" + ANSI_RESET + " Quit");
			System.out.print("  " + ANSI_BOLD + "Enter move: " + ANSI_RESET);
		}
	}

	private void clearScreen() {
		System.out.print(ANSI_CLEAR);
		System.out.flush();
	}
}
