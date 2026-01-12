package com.swingy.Model;

public class GameState {
    private final Hero hero;
    private Position position;
    private int mapSize;

    public GameState(Hero hero, Position position, int mapSize) {
        this.hero = hero;
        this.position = position;
        this.mapSize = mapSize;
    }

    public GameState(Hero hero) {
        this.hero = hero;
        this.mapSize = calculateMapSize(hero.getLevel());
        this.position = new Position(mapSize / 2, mapSize / 2);
    }

    public Hero getHero() { return hero; }

    public Position getPosition() { return position; }

    public int getMapSize() { return mapSize; }

    public void setPosition(Position position) { this.position = position; }

    public void setMapSize(int mapSize) { this.mapSize = mapSize; }

    public static int calculateMapSize(int level) { return (level - 1) * 5 + 10 - (level % 2); }

    public boolean isAtBorder() {
        return position.getX() == 0 || 
               position.getX() == mapSize - 1 || 
               position.getY() == 0 || 
               position.getY() == mapSize - 1;
    }

    public int getDistanceToBorder() {
        return Math.min(
            Math.min(position.getX(), mapSize - position.getX() - 1),
            Math.min(position.getY(), mapSize - position.getY() - 1)
        );
    }

    public boolean isValidMove(int dx, int dy) {
        int newX = position.getX() + dx;
        int newY = position.getY() + dy;
        return isWithinBounds(newX, newY);
    }

	public boolean isWithinBounds(int x, int y) { return x >= 0 && x < mapSize && y >= 0 && y < mapSize;}
}
