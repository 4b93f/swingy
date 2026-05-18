package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import com.swingy.controller.GuiMapController;
import com.swingy.model.GameState;
import com.swingy.model.artefact.Artefact;

public class GamePanel extends JPanel {

    private final MapPanel   mapPanel;
    private final StatsPanel statsPanel;
    private final JLabel     statusLabel;
    private final CardLayout southLayout = new CardLayout();
    private final JPanel     southPanel  = new JPanel(southLayout);
    private final GameState  gameState;

    public GamePanel(GuiMapController controller, GameState gameState) {
        this.gameState  = gameState;

        mapPanel    = new MapPanel(gameState);
        statsPanel  = new StatsPanel(gameState.getHero());
        statusLabel = makeStatusLabel();

        southPanel.setBackground(GuiConstants.BG);
        southPanel.add(buildMoveButtons(controller),   "move");
        southPanel.add(buildBattleButtons(controller), "battle");
        southLayout.show(southPanel, "move");

        setLayout(new BorderLayout());
        setBackground(GuiConstants.BG);
        add(statusLabel, BorderLayout.NORTH);
        add(mapPanel,    BorderLayout.CENTER);
        add(statsPanel,  BorderLayout.EAST);
        add(southPanel,  BorderLayout.SOUTH);

        setupKeyBindings(controller);
    }

    public void refresh() {
        mapPanel.repaint();
        statsPanel.refresh(gameState.getHero());
    }

    public void setStatus(String message) {
        statusLabel.setText(message);
    }

    public void showMoveButtons() {
        southLayout.show(southPanel, "move");
    }

    public void showBattleButtons() {
        southLayout.show(southPanel, "battle");
    }

    public void showEscapeFailed() {
        JOptionPane.showMessageDialog(this, "Escape failed! You must fight!", "No escape!", JOptionPane.WARNING_MESSAGE);
    }

    public boolean confirmArtifact(Artefact artifact) {
        String msg = "Found: " + artifact.getArtefactName()
            + "  ATK+" + artifact.getBonusAttack()
            + " DEF+" + artifact.getBonusDefense()
            + " HP+"  + artifact.getBonusHitPoints()
            + "\nEquip it?";
        return JOptionPane.showConfirmDialog(this, msg, "Artifact!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    // Button panels
    private JPanel buildMoveButtons(GuiMapController controller) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(GuiConstants.BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);

        JButton north = GuiConstants.makeButton("↑");
        JButton south = GuiConstants.makeButton("↓");
        JButton west  = GuiConstants.makeButton("←");
        JButton east  = GuiConstants.makeButton("→");

        north.addActionListener(e -> controller.moveHero(0, -1));
        south.addActionListener(e -> controller.moveHero(0,  1));
        west.addActionListener(e  -> controller.moveHero(-1, 0));
        east.addActionListener(e  -> controller.moveHero(1,  0));

        gbc.gridx = 1; gbc.gridy = 0; panel.add(north, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(west,  gbc);
        gbc.gridx = 2; gbc.gridy = 1; panel.add(east,  gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(south, gbc);

        return panel;
    }

    private JPanel buildBattleButtons(GuiMapController controller) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(GuiConstants.BG);

        JButton fight = GuiConstants.makeButton("⚔  Fight");
        JButton run   = GuiConstants.makeButton("🏃 Run");

        fight.addActionListener(e -> controller.onFight());
        run.addActionListener(e   -> controller.onRun());

        panel.add(fight);
        panel.add(run);
        return panel;
    }


    // Key bindings 
    private void setupKeyBindings(GuiMapController controller) {
        InputMap  im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke("W"),     "north");
        im.put(KeyStroke.getKeyStroke("UP"),    "north");
        im.put(KeyStroke.getKeyStroke("S"),     "south");
        im.put(KeyStroke.getKeyStroke("DOWN"),  "south");
        im.put(KeyStroke.getKeyStroke("A"),     "west");
        im.put(KeyStroke.getKeyStroke("LEFT"),  "west");
        im.put(KeyStroke.getKeyStroke("D"),     "east");
        im.put(KeyStroke.getKeyStroke("RIGHT"), "east");

        am.put("north", action(() -> controller.moveHero(0, -1)));
        am.put("south", action(() -> controller.moveHero(0,  1)));
        am.put("west",  action(() -> controller.moveHero(-1, 0)));
        am.put("east",  action(() -> controller.moveHero(1,  0)));
    }

    private AbstractAction action(Runnable r) {
        return new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { r.run(); }
        };
    }


    private JLabel makeStatusLabel() {
        JLabel l = new JLabel(" ", SwingConstants.CENTER);
        l.setForeground(new Color(220, 200, 100));
        l.setFont(new Font("Monospaced", Font.BOLD, 13));
        l.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        return l;
    }
}
