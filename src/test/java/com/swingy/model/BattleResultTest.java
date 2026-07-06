package com.swingy.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BattleResultTest {

    private BattleResult result;

    @BeforeEach
    void setUp() { result = new BattleResult(); }

    @Test
    @DisplayName("New result has no turns")
    void startsEmpty() {
        assertTrue(result.getTurns().isEmpty());
    }

    @Test
    @DisplayName("heroWon defaults to false")
    void defaultHeroWonFalse() {
        assertFalse(result.isHeroWon());
    }

    @Test
    @DisplayName("setHeroWon true is reflected by isHeroWon")
    void setHeroWonTrue() {
        result.setHeroWon(true);
        assertTrue(result.isHeroWon());
    }

    @Test
    @DisplayName("addTurn adds one turn")
    void addTurnIncrementsCount() {
        result.addTurn("Hero", "Enemy", 15, false, 100, 85);
        assertEquals(1, result.getTurns().size());
    }

    @Test
    @DisplayName("addTurn data is accessible via BattleTurn getters")
    void turnDataAccessible() {
        result.addTurn("Arthur", "Goblin", 20, true, 90, 60);
        BattleResult.BattleTurn turn = result.getTurns().get(0);

        assertEquals("Arthur",  turn.getAttacker());
        assertEquals("Goblin",  turn.getDefender());
        assertEquals(20,        turn.getDamage());
        assertTrue(turn.isCritical());
        assertEquals(90,        turn.getAttackerHPRemaining());
        assertEquals(60,        turn.getDefenderHPRemaining());
    }

    @Test
    @DisplayName("Multiple turns recorded in order")
    void multipleTurnsOrdered() {
        result.addTurn("Hero",  "Enemy", 10, false, 100, 90);
        result.addTurn("Enemy", "Hero",   5, false,  90, 95);
        result.addTurn("Hero",  "Enemy",  8, true,   95, 82);

        assertEquals(3, result.getTurns().size());
        assertEquals("Hero",  result.getTurns().get(0).getAttacker());
        assertEquals("Enemy", result.getTurns().get(1).getAttacker());
        assertEquals("Hero",  result.getTurns().get(2).getAttacker());
    }

    @Test
    @DisplayName("Non-critical turn has isCritical false")
    void nonCriticalTurn() {
        result.addTurn("Hero", "Enemy", 5, false, 100, 95);
        assertFalse(result.getTurns().get(0).isCritical());
    }
}
