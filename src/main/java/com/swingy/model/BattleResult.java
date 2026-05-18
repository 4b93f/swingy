package com.swingy.model;

import java.util.ArrayList;
import java.util.List;

public class BattleResult {
    private boolean heroWon;
    private List<BattleTurn> turns;
    
    public static class BattleTurn {
        private String attacker;
        private String defender;
        private int damage;
        private boolean isCritical;
        private int attackerHPRemaining;
        private int defenderHPRemaining;
        
        public BattleTurn(String attacker, String defender, int damage, boolean isCritical, 
                         int attackerHPRemaining, int defenderHPRemaining) {
            this.attacker = attacker;
            this.defender = defender;
            this.damage = damage;
            this.isCritical = isCritical;
            this.attackerHPRemaining = attackerHPRemaining;
            this.defenderHPRemaining = defenderHPRemaining;
        }
        
        public String getAttacker() { return attacker; }
        public String getDefender() { return defender; }
        public int getDamage() { return damage; }
        public boolean isCritical() { return isCritical; }
        public int getAttackerHPRemaining() { return attackerHPRemaining; }
        public int getDefenderHPRemaining() { return defenderHPRemaining; }
    }
    
    public BattleResult() {
        this.turns = new ArrayList<>();
    }
    
    public void addTurn(String attacker, String defender, int damage, boolean isCritical, 
                       int attackerHP, int defenderHP) {
        turns.add(new BattleTurn(attacker, defender, damage, isCritical, attackerHP, defenderHP));
    }
    
    public void setHeroWon(boolean heroWon) {
        this.heroWon = heroWon;
    }
    
    public boolean isHeroWon() { return heroWon; }
    public List<BattleTurn> getTurns() { return turns; }
}
