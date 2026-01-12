package com.swingy.View;

import com.swingy.Model.GameState;
import com.swingy.Model.Hero;
import com.swingy.Model.Position;

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
	private Position pos;
	private int mapSize;

	public MapView(Hero hero, Position pos, int mapSize) {
		this.hero = hero;
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

	public void displayInvalidMove() {
		System.out.println(ANSI_YELLOW + "  Invalid move! You cannot move outside the map." + ANSI_RESET);
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
		
		return "·";
	}

	private void header(GameState state, boolean showFullDetails) {
		System.out.println("═".repeat(60));
		
		if (showFullDetails) {
			System.out.printf("  " + ANSI_BOLD + ANSI_CYAN + "HERO:" + ANSI_RESET + " %s " + ANSI_GREEN + "(Level %d)" + ANSI_RESET + "%n", 
				hero.getHeroName(), hero.getLevel());
			System.out.printf("  " + ANSI_WHITE + "Position:" + ANSI_RESET + " (%d, %d) / " + ANSI_WHITE + "Map:" + ANSI_RESET + " %dx%d%n",
				pos.getX(), pos.getY(), mapSize, mapSize);
			
			int distanceToBorder = state.getDistanceToBorder();
			System.out.printf("  " + ANSI_YELLOW + "Distance to victory: %d steps" + ANSI_RESET + "%n", distanceToBorder);
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
