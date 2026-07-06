package com.swingy.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameStateTest {

    private Hero hero;

    @BeforeEach
    void setUp() {
        hero = new Hero.HeroBuilder()
            .setHeroName("Tester")
            .setHeroClass("Warrior")
            .build();
    }

    // ── Map size formula ──────────────────────────────────────────────────────

    @Test
    @DisplayName("Map size formula: level 1 → 9")
    void mapSizeLevel1() { assertEquals(9, GameState.calculateMapSize(1)); }

    @Test
    @DisplayName("Map size formula: level 2 → 15")
    void mapSizeLevel2() { assertEquals(15, GameState.calculateMapSize(2)); }

    @Test
    @DisplayName("Map size formula: level 3 → 19")
    void mapSizeLevel3() { assertEquals(19, GameState.calculateMapSize(3)); }

    @Test
    @DisplayName("Map size formula: level 4 → 25")
    void mapSizeLevel4() { assertEquals(25, GameState.calculateMapSize(4)); }

    @Test
    @DisplayName("Map size formula: level 5 → 29")
    void mapSizeLevel5() { assertEquals(29, GameState.calculateMapSize(5)); }

    // ── Hero spawn position ───────────────────────────────────────────────────

    @Test
    @DisplayName("Hero spawns at center of map")
    void heroSpawnsAtCenter() {
        GameState state = new GameState(hero);
        int mapSize = state.getMapSize();
        int expected = mapSize / 2;
        assertEquals(expected, state.getPosition().getX());
        assertEquals(expected, state.getPosition().getY());
    }

    // ── Border detection ──────────────────────────────────────────────────────

    @Test
    @DisplayName("isAtBorder: top edge")
    void atBorderTop() {
        GameState state = new GameState(hero, new Position(4, 0), 9);
        assertTrue(state.isAtBorder());
    }

    @Test
    @DisplayName("isAtBorder: bottom edge")
    void atBorderBottom() {
        GameState state = new GameState(hero, new Position(4, 8), 9);
        assertTrue(state.isAtBorder());
    }

    @Test
    @DisplayName("isAtBorder: left edge")
    void atBorderLeft() {
        GameState state = new GameState(hero, new Position(0, 4), 9);
        assertTrue(state.isAtBorder());
    }

    @Test
    @DisplayName("isAtBorder: right edge")
    void atBorderRight() {
        GameState state = new GameState(hero, new Position(8, 4), 9);
        assertTrue(state.isAtBorder());
    }

    @Test
    @DisplayName("isAtBorder: center is not border")
    void notAtBorderCenter() {
        GameState state = new GameState(hero, new Position(4, 4), 9);
        assertFalse(state.isAtBorder());
    }

    // ── Distance to border ────────────────────────────────────────────────────

    @Test
    @DisplayName("distanceToBorder from center of 9×9 map is 4")
    void distanceToBorderFromCenter() {
        GameState state = new GameState(hero, new Position(4, 4), 9);
        assertEquals(4, state.getDistanceToBorder());
    }

    @Test
    @DisplayName("distanceToBorder from (1,1) in 9×9 map is 1")
    void distanceToBorderNearCorner() {
        GameState state = new GameState(hero, new Position(1, 1), 9);
        assertEquals(1, state.getDistanceToBorder());
    }

    // ── Move validation ───────────────────────────────────────────────────────

    @Test
    @DisplayName("isValidMove: move within bounds allowed")
    void validMoveWithinBounds() {
        GameState state = new GameState(hero, new Position(4, 4), 9);
        assertTrue(state.isValidMove(1, 0));
        assertTrue(state.isValidMove(-1, 0));
        assertTrue(state.isValidMove(0, 1));
        assertTrue(state.isValidMove(0, -1));
    }

    @Test
    @DisplayName("isValidMove: move past left boundary rejected")
    void invalidMoveOutOfBoundsLeft() {
        GameState state = new GameState(hero, new Position(0, 4), 9);
        assertFalse(state.isValidMove(-1, 0));
    }

    @Test
    @DisplayName("isValidMove: move past top boundary rejected")
    void invalidMoveOutOfBoundsTop() {
        GameState state = new GameState(hero, new Position(4, 0), 9);
        assertFalse(state.isValidMove(0, -1));
    }

    // ── Enemy management ──────────────────────────────────────────────────────

    @Test
    @DisplayName("getEnemyAt: returns null when no enemy")
    void getEnemyAtEmpty() {
        GameState state = new GameState(hero, new Position(4, 4), 9);
        assertNull(state.getEnemyAt(2, 2));
    }

    @Test
    @DisplayName("getEnemyAt: returns enemy at correct position")
    void getEnemyAtFound() {
        GameState state = new GameState(hero, new Position(4, 4), 9);
        state.addEnemyPosition(2, 2);
        assertNotNull(state.getEnemyAt(2, 2));
    }

    @Test
    @DisplayName("removeEnemy: enemy no longer found after removal")
    void removeEnemyWorks() {
        GameState state = new GameState(hero, new Position(4, 4), 9);
        state.addEnemyPosition(2, 2);
        Enemy e = state.getEnemyAt(2, 2);
        assertNotNull(e);
        state.removeEnemy(e);
        assertNull(state.getEnemyAt(2, 2));
    }

    @Test
    @DisplayName("initializeEnemyPositions: no enemy spawns on hero tile")
    void enemiesNotOnHeroTile() {
        GameState state = new GameState(hero);
        state.initializeEnemyPositions();
        int hx = state.getPosition().getX();
        int hy = state.getPosition().getY();
        assertNull(state.getEnemyAt(hx, hy));
    }

    @Test
    @DisplayName("initializeEnemyPositions: enemies spawn on inner cells only")
    void enemiesOnInnerCellsOnly() {
        GameState state = new GameState(hero);
        state.initializeEnemyPositions();
        int ms = state.getMapSize();
        for (Enemy e : state.getEnemies()) {
            int x = e.getPosition().getX();
            int y = e.getPosition().getY();
            assertTrue(x > 0 && x < ms - 1, "enemy x=" + x + " out of inner range");
            assertTrue(y > 0 && y < ms - 1, "enemy y=" + y + " out of inner range");
        }
    }

    @Test
    @DisplayName("initializeEnemyPositions: at least 5 enemies")
    void enemiesMinCount() {
        GameState state = new GameState(hero);
        state.initializeEnemyPositions();
        assertTrue(state.getEnemies().size() >= 5);
    }
}
