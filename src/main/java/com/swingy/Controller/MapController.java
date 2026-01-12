package com.swingy.Controller;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import com.swingy.Model.GameState;
import com.swingy.View.MapView;

public class MapController {
    private final GameState gameState;
    private final MapView mapView;
    private Terminal terminal;

    public MapController(GameState gameState, MapView mapView) {
        this.gameState = gameState;
        this.mapView = mapView;
        try {
            this.terminal = TerminalBuilder.builder()
                .system(true)
                .build();
            terminal.enterRawMode();
        } catch (Exception e) {
            System.err.println("Failed to initialize terminal: " + e.getMessage());
        }
    }

    public void startExploration() {
        boolean running = true;
        
        while (running) {
            mapView.displayMap(gameState);
            
            try {
                int key = terminal.reader().read();
                char input = (char) key;
                
                switch (Character.toLowerCase(input)) {
                    case 'w':
                        moveHero(0, -1);
                        break;
                    case 's':
                        moveHero(0, 1);
                        break;
                    case 'a':
                        moveHero(-1, 0);
                        break;
                    case 'd':
                        moveHero(1, 0);
                        break;
                    case 'm':
                        showFullMap();
                        break;
                    case 'q':
                        running = false;
                        System.out.println("Thanks for playing!");
                        break;
                }
                
                if (gameState.isAtBorder()) {
                    mapView.displayVictory(gameState);
                    running = false;
                }
            } catch (Exception e) {
                System.err.println("Error reading input: " + e.getMessage());
                running = false;
            }
        }
        
        cleanup();
    }

    private void moveHero(int dx, int dy) {
        if (gameState.isValidMove(dx, dy)) {
            gameState.getPosition().move(dx, dy);
        } else {
            mapView.displayInvalidMove();
            waitForKey();
        }
    }

    private void showFullMap() {
        mapView.displayFullMap(gameState);
        try {
            terminal.reader().read();
        } catch (Exception e) {
        }
    }

    private void waitForKey() {
        System.out.print("  Press any key to continue...");
        try {
            terminal.reader().read();
        } catch (Exception e) {
        }
    }

    private void cleanup() {
        try {
            if (terminal != null) {
                terminal.close();
            }
        } catch (Exception e) {
        }
    }

    public GameState getGameState() { return gameState; }
}
