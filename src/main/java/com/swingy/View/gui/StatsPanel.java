package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;
import com.swingy.model.Hero;

public class StatsPanel extends JPanel {

    private final JLabel levelLabel;
    private final JLabel hpLabel;
    private final JLabel atkLabel;
    private final JLabel defLabel;
    private final JLabel xpLabel;

    public StatsPanel(Hero hero) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(28, 28, 42));
        setPreferredSize(new Dimension(180, 0));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        add(makeTitle(hero.getHeroName()));
        add(makeTitle(hero.getHeroClass()));
        add(Box.createVerticalStrut(20));

        levelLabel = GuiConstants.makeLabel("Level: " + hero.getLevel());
        hpLabel    = GuiConstants.makeLabel("HP:    " + hero.getTotalHitPoints());
        atkLabel   = GuiConstants.makeLabel("ATK:   " + hero.getTotalAttack());
        defLabel   = GuiConstants.makeLabel("DEF:   " + hero.getTotalDefense());
        xpLabel    = GuiConstants.makeLabel("XP:    " + hero.getExperience());

        add(levelLabel);
        add(Box.createVerticalStrut(8));
        add(hpLabel);
        add(Box.createVerticalStrut(8));
        add(atkLabel);
        add(Box.createVerticalStrut(8));
        add(defLabel);
        add(Box.createVerticalStrut(8));
        add(xpLabel);
    }

    public void refresh(Hero hero) {
        levelLabel.setText("Level: " + hero.getLevel());
        hpLabel.setText(   "HP:    " + hero.getTotalHitPoints());
        atkLabel.setText(  "ATK:   " + hero.getTotalAttack());
        defLabel.setText(  "DEF:   " + hero.getTotalDefense());
        xpLabel.setText(   "XP:    " + hero.getExperience());
        revalidate();
        repaint();
    }

    private JLabel makeTitle(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(new Color(100, 180, 255));
        l.setFont(new Font("Monospaced", Font.BOLD, 14));
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }


}
