package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;
import com.swingy.controller.GuiController;

public class MenuPanel extends JPanel {

    public MenuPanel(GuiController controller) {
        setLayout(new BorderLayout());
        setBackground(GuiConstants.BG);

        add(GuiConstants.makeTitle("S W I N G Y"), BorderLayout.NORTH);
        add(makeButtons(controller), BorderLayout.CENTER);
    }

    private JPanel makeButtons(GuiController controller) {
        JPanel column = GuiConstants.centeredColumn();

        JButton newHero    = GuiConstants.makeButton("New Hero");
        JButton selectHero = GuiConstants.makeButton("Select Hero");
        JButton quit       = GuiConstants.makeButton("Quit");

        newHero.addActionListener(e    -> controller.onNewHero());
        selectHero.addActionListener(e -> controller.onSelectHero());
        quit.addActionListener(e       -> controller.onQuit());

        column.add(Box.createVerticalGlue());
        column.add(newHero);
        column.add(Box.createVerticalStrut(10));
        column.add(selectHero);
        column.add(Box.createVerticalStrut(10));
        column.add(quit);
        column.add(Box.createVerticalGlue());

        return column;
    }
}
