package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;

import com.swingy.controller.GuiController;
import com.swingy.model.Hero;

public class HeroStatsPanel extends JPanel {

    private final JLabel nameLabel  = GuiConstants.makeLabel("");
    private final JLabel classLabel = GuiConstants.makeLabel("");
    private final JLabel levelLabel = GuiConstants.makeLabel("");
    private final JLabel xpLabel    = GuiConstants.makeLabel("");
    private final JLabel hpLabel    = GuiConstants.makeLabel("");
    private final JLabel atkLabel   = GuiConstants.makeLabel("");
    private final JLabel defLabel   = GuiConstants.makeLabel("");

    public HeroStatsPanel(GuiController controller) {
        setLayout(new BorderLayout());
        setBackground(GuiConstants.BG);

        add(GuiConstants.makeTitle("HERO STATS"), BorderLayout.NORTH);

        JPanel column = GuiConstants.centeredColumn();

        JButton play = GuiConstants.makeButton("Play");
        JButton back = GuiConstants.makeButton("Back");

        play.addActionListener(e -> controller.onConfirmHero());
        back.addActionListener(e -> controller.onBack());

        column.add(Box.createVerticalGlue());
        column.add(nameLabel);
        column.add(Box.createVerticalStrut(6));
        column.add(classLabel);
        column.add(Box.createVerticalStrut(20));
        column.add(levelLabel);
        column.add(Box.createVerticalStrut(6));
        column.add(xpLabel);
        column.add(Box.createVerticalStrut(6));
        column.add(hpLabel);
        column.add(Box.createVerticalStrut(6));
        column.add(atkLabel);
        column.add(Box.createVerticalStrut(6));
        column.add(defLabel);
        column.add(Box.createVerticalStrut(24));
        column.add(play);
        column.add(Box.createVerticalStrut(10));
        column.add(back);
        column.add(Box.createVerticalGlue());

        add(column, BorderLayout.CENTER);
    }

    public void show(Hero hero) {
        nameLabel .setText("Name      : " + hero.getHeroName());
        classLabel.setText("Class     : " + hero.getHeroClass());
        levelLabel.setText("Level     : " + hero.getLevel());
        xpLabel   .setText("Experience: " + hero.getExperience());
        hpLabel   .setText("Hit Points: " + hero.getTotalHitPoints());
        atkLabel  .setText("Attack    : " + hero.getTotalAttack());
        defLabel  .setText("Defense   : " + hero.getTotalDefense());
    }
}
