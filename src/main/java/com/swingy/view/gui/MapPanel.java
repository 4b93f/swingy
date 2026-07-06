package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;
import com.swingy.model.GameState;
import com.swingy.model.Position;

public class MapPanel extends JPanel {
    private static final int VIEW = 19;
    private static final int HALF = VIEW / 2;

    private final GameState gameState;

    public MapPanel(GameState gameState) {
        this.gameState = gameState;
        setBackground(Color.BLACK);
    }

    // Drawing the map
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Position hero    = gameState.getPosition();
        int      mapSize = gameState.getMapSize();
        int      cell    = Math.min(getWidth(), getHeight()) / VIEW;

        for (int row = 0; row < VIEW; row++) {
            for (int col = 0; col < VIEW; col++) {
                int mx = hero.getX() - HALF + col;
                int my = hero.getY() - HALF + row;
                int px = col * cell;
                int py = row * cell;

                if (mx == hero.getX() && my == hero.getY()) {
                    g.setColor(new Color(0, 100, 220));
                    g.fillRect(px + 1, py + 1, cell - 2, cell - 2);
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Monospaced", Font.BOLD, cell / 2));
                    g.drawString("H", px + cell / 4, py + cell * 3 / 4);

                } else if (mx < 0 || mx >= mapSize || my < 0 || my >= mapSize) {
                    g.setColor(new Color(10, 10, 10));
                    g.fillRect(px, py, cell, cell);

                } else if (mx == 0 || mx == mapSize - 1 || my == 0 || my == mapSize - 1) {
                    g.setColor(new Color(160, 130, 0));
                    g.fillRect(px + 1, py + 1, cell - 2, cell - 2);

                } else if (gameState.getEnemyAt(mx, my) != null) {
                    g.setColor(new Color(180, 30, 30));
                    g.fillRect(px + 1, py + 1, cell - 2, cell - 2);
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Monospaced", Font.BOLD, cell / 2));
                    g.drawString("E", px + cell / 4, py + cell * 3 / 4);

                } else {
                    g.setColor(new Color(45, 45, 45));
                    g.fillRect(px, py, cell, cell);
                    g.setColor(new Color(65, 65, 65));
                    g.drawRect(px, py, cell - 1, cell - 1);
                }
            }
        }
    }
}
