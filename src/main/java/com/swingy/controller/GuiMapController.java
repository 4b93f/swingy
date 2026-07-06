package com.swingy.controller;

import com.swingy.model.*;
import com.swingy.model.artefact.Artefact;
import com.swingy.model.artefact.ArtefactGenerator;
import com.swingy.view.gui.GamePanel;

import java.util.Random;
import java.util.function.Consumer;

public class GuiMapController {

    private final GameState      gameState;
    private final GamePanel      gamePanel;
    private final Random         random      = new Random();
    private final Consumer<Hero> onVictory;
    private final Consumer<Hero> onGameOver;

    private Enemy pendingEnemy;
    private int   prevX, prevY;

    public GuiMapController(Hero hero, Consumer<Hero> onVictory, Consumer<Hero> onGameOver) {
        this.onVictory  = onVictory;
        this.onGameOver = onGameOver;
        gameState = new GameState(hero);
        gameState.initializeEnemyPositions();
        gamePanel = new GamePanel(this, gameState);
    }

    public GamePanel getPanel() { return gamePanel; }

    public void moveHero(int dx, int dy) {
        if (gameState.getCurrentPhase() != GamePhase.EXPLORATION) return;
        if (!gameState.isValidMove(dx, dy)) {
            gamePanel.setStatus("Can't move there.");
            return;
        }

        prevX = gameState.getPosition().getX();
        prevY = gameState.getPosition().getY();
        gameState.getPosition().move(dx, dy);

        if (gameState.isAtBorder()) {
            gameState.setCurrentPhase(GamePhase.VICTORY);
            gamePanel.refresh();
            onVictory.accept(gameState.getHero());
            return;
        }

        Enemy enemy = gameState.getEnemyAt(
            gameState.getPosition().getX(),
            gameState.getPosition().getY()
        );

        if (enemy != null) {
            pendingEnemy = enemy;
            gameState.setCurrentPhase(GamePhase.BATTLE);
            gamePanel.setStatus("Encountered " + enemy.getName() + " (Lv." + enemy.getLevel() + ") — Fight or Run?");
            gamePanel.showBattleButtons();
        }

        gamePanel.refresh();
    }

    public void onFight() {
        BattleResult result = gameState.getHero().fightEnemyDetailed(pendingEnemy);

        if (result.isHeroWon()) {
            int xp  = gameState.getHero().gainXpFromEnemy(pendingEnemy);
            String msg = "Victory! +" + xp + " XP";

            if (gameState.getHero().shouldLevelUp()) {
                gameState.getHero().levelUp();
                msg += "  |  LEVEL UP! Now Lv." + gameState.getHero().getLevel();
            }

            if (random.nextInt(100) < 30) {
                Artefact artifact = ArtefactGenerator.generate(pendingEnemy);
                if (artifact != null && gamePanel.confirmArtifact(artifact))
                    gameState.getHero().equipArtifact(artifact);
            }

            gameState.removeEnemy(pendingEnemy);
            endBattle(msg);
        } else {
            gameState.setCurrentPhase(GamePhase.GAME_OVER);
            gamePanel.refresh();
            onGameOver.accept(gameState.getHero());
        }

        gamePanel.refresh();
    }

    public void onRun() {
        if (random.nextDouble() < 0.5) {
            gameState.getPosition().setX(prevX);
            gameState.getPosition().setY(prevY);
            endBattle("Escaped successfully!");
            gamePanel.refresh();
        } else {
            gamePanel.showEscapeFailed();
            onFight();
        }
    }

    private void endBattle(String message) {
        pendingEnemy = null;
        gameState.setCurrentPhase(GamePhase.EXPLORATION);
        gamePanel.setStatus(message);
        gamePanel.showMoveButtons();
    }
}
