package com.swingy.controller;

import java.io.IOException;
import java.util.Scanner;

import com.swingy.model.Enemy;
import com.swingy.model.GamePhase;
import com.swingy.model.GameState;
import com.swingy.model.BattleResult;
import com.swingy.model.artefact.Artefact;
import com.swingy.model.artefact.ArtefactGenerator;
import com.swingy.view.MapView;

public class MapController {
    private final GameState gameState;
    private final MapView   mapView;
    private final Scanner   scanner;
    private int prevX, prevY;

    private boolean rawMode = false;

    public MapController(GameState gameState, MapView mapView, Scanner scanner) {
        this.gameState = gameState;
        this.mapView   = mapView;
        this.scanner   = scanner;
        try {
            Process p = new ProcessBuilder("sh", "-c", "stty -icanon -echo min 1 < /dev/tty")
                .start();
            rawMode = (p.waitFor() == 0);
        } catch (Exception e) {
            rawMode = false;
        }
    }

    private char readKey() {
        if (rawMode) {
            try {
                char c;
                do { c = Character.toLowerCase((char) System.in.read()); }
                while (c == '\r' || c == '\n');
                return c;
            } catch (IOException e) {}
        }
        String line = scanner.nextLine().trim().toLowerCase();
        return line.isEmpty() ? ' ' : line.charAt(0);
    }

    private void waitForKey() {
        System.out.print("\n  [any key] continue...");
        readKey();
    }

    private void restoreTerminal() {
        if (!rawMode) return;
        try {
            new ProcessBuilder("sh", "-c", "stty sane < /dev/tty").start().waitFor();
        } catch (Exception ignored) {}
        rawMode = false;
    }

    public void startExploration() {
        boolean running = true;
        try {
            while (running) {
                if (gameState.getCurrentPhase() == GamePhase.GAME_OVER) {
                    mapView.displayGameOver(gameState);
                    running = false;
                    continue;
                }

                if (gameState.getCurrentPhase() == GamePhase.VICTORY) {
                    mapView.displayVictory(gameState);
                    running = false;
                    continue;
                }

                mapView.displayMap(gameState);

                char key = readKey();
                switch (key) {
                    case 'w': moveHero(0, -1);  break;
                    case 's': moveHero(0, 1);   break;
                    case 'a': moveHero(-1, 0);  break;
                    case 'd': moveHero(1, 0);   break;
                    case 'm':
                        mapView.displayFullMap(gameState);
                        waitForKey();
                        break;
                    case 'q': running = false;  break;
                }

                if (gameState.isAtBorder())
                    gameState.setCurrentPhase(GamePhase.VICTORY);
            }
        } finally {
            restoreTerminal();
        }
    }

    private void moveHero(int dx, int dy) {
        if (!gameState.isValidMove(dx, dy)) {
            mapView.displayInvalidMove();
            return;
        }

        prevX = gameState.getPosition().getX();
        prevY = gameState.getPosition().getY();
        gameState.getPosition().move(dx, dy);

        Enemy enemy = gameState.getEnemyAt(
            gameState.getPosition().getX(),
            gameState.getPosition().getY()
        );

        if (enemy != null) {
            gameState.setCurrentPhase(GamePhase.BATTLE);
            handleBattle(enemy);
        }
    }

    private void handleBattle(Enemy enemy) {
        mapView.displayBattleStart(gameState.getHero(), enemy);

        char choice;
        do {
            mapView.displayBattleOptions();
            choice = readKey();
        } while (choice != 'f' && choice != 'r');

        if (choice == 'f') {
            executeBattle(enemy);
        } else if (choice == 'r') {
            if (Math.random() < 0.5) {
                gameState.getPosition().setX(prevX);
                gameState.getPosition().setY(prevY);
                mapView.displayRunSuccess();
                gameState.setCurrentPhase(GamePhase.EXPLORATION);
            } else {
                mapView.displayRunFailure();
                mapView.displayForcedToFight();
                executeBattle(enemy);
            }
        }
    }

    private void executeBattle(Enemy enemy) {
        BattleResult result = gameState.getHero().fightEnemyDetailed(enemy);

        for (BattleResult.BattleTurn turn : result.getTurns()) {
            mapView.displayBattleTurn(turn.getAttacker(), turn.getDefender(),
                turn.getDamage(), turn.isCritical(), turn.getDefenderHPRemaining());
        }

        if (result.isHeroWon()) {
            int xpGained = gameState.getHero().gainXpFromEnemy(enemy);
            boolean leveled = false;
            if (gameState.getHero().shouldLevelUp()) {
                gameState.getHero().levelUp();
                leveled = true;
            }
            mapView.displayBattleVictory(gameState.getHero(), enemy, xpGained, leveled);

            if (Math.random() < 0.3) {
                Artefact artifact = ArtefactGenerator.generate(enemy);
                if (artifact != null) {
                    mapView.displayArtifactFound(artifact);
                    char pick = readKey();
                    if (pick == 'k')
                        gameState.getHero().equipArtifact(artifact);
                }
            }

            gameState.removeEnemy(enemy);
            gameState.setCurrentPhase(GamePhase.EXPLORATION);
            waitForKey();
        } else {
            mapView.displayBattleDefeat();
            waitForKey();
            gameState.setCurrentPhase(GamePhase.GAME_OVER);
        }
    }

    public GameState getGameState() { return gameState; }
}
