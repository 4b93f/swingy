package com.swingy.model;

import java.util.List;
import java.util.Random;

public class GameState {
    private final Hero hero;
    private List<Enemy> enemies;
    private Position position;
    private int mapSize;
    private GamePhase currentPhase;

    {
        this.enemies = new java.util.ArrayList<>();
    }

    public GameState(Hero hero, Position position, int mapSize) {
        this.hero = hero;
        this.position = position;
        this.mapSize = mapSize;
        this.currentPhase = GamePhase.EXPLORATION;
    }

    public GameState(Hero hero) {
        this(hero,
            new Position(calculateMapSize(hero.getLevel()) / 2, calculateMapSize(hero.getLevel()) / 2),
            calculateMapSize(hero.getLevel()));
    }

    public Hero getHero() { return hero; }

    public Position getPosition() { return position; }

    public int getMapSize() { return mapSize; }
    
    public GamePhase getCurrentPhase() { return currentPhase; }
    
    public List<Enemy> getEnemies() { return enemies; }

    public void setPosition(Position position) { this.position = position; }

    public void setMapSize(int mapSize) { this.mapSize = mapSize; }
    
    public void setCurrentPhase(GamePhase phase) { this.currentPhase = phase; }

    // Map size formula
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

    public Enemy getEnemyAt(int x, int y) {
        for (Enemy enemy : enemies) {
            if (enemy.getPosition() != null &&
                enemy.getPosition().getX() == x && 
                enemy.getPosition().getY() == y) {
                return enemy;
            }
        }
        return null;
    }
    
    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public void initializeEnemyPositions() {
        Random rand = new Random();
        int inner = mapSize - 2;
        int numberOfEnemies = Math.max(5, (inner * inner) * 3 / 10);
        for (int i = 0; i < numberOfEnemies; i++) {
            int enemyX, enemyY;
            do {
                enemyX = 1 + rand.nextInt(inner);
                enemyY = 1 + rand.nextInt(inner);
            } while ((enemyX == position.getX() && enemyY == position.getY()) || getEnemyAt(enemyX, enemyY) != null);

            addEnemyPosition(enemyX, enemyY);
        }
    }

    public void addEnemyPosition(int x, int y) {
        Enemy enemy = new Enemy.EnemyBuilder()
            .setPosition(new Position(x, y))
            .build(hero.getLevel());
        enemies.add(enemy);
    }

    public boolean isWithinBounds(int x, int y) { 
        return x >= 0 && x < mapSize && y >= 0 && y < mapSize;
    }
}
