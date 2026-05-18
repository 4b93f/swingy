package com.swingy.view.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.swingy.controller.GuiController;
import com.swingy.model.Hero;

public class SelectHeroPanel extends JPanel {

    private final JList<String> heroList;
    private final List<Hero>    heroes = new ArrayList<>();

    public SelectHeroPanel(List<Hero> heroes, GuiController controller) {
        this.heroes.addAll(heroes);
        setLayout(new BorderLayout());
        setBackground(GuiConstants.BG);

        add(GuiConstants.makeTitle("SELECT HERO"), BorderLayout.NORTH);

        String[] names = heroes.stream()
            .map(h -> h.getHeroName() + "  [" + h.getHeroClass() + "  Lv." + h.getLevel() + "]")
            .toArray(String[]::new);

        heroList = new JList<>(names);
        heroList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(heroList);
        scroll.setPreferredSize(new Dimension(GuiConstants.COL_W, 200));

        JButton select = GuiConstants.makeButton("Select Hero");
        JButton back   = GuiConstants.makeButton("Back");

        select.setEnabled(false);
        heroList.addListSelectionListener(e -> select.setEnabled(heroList.getSelectedIndex() >= 0));
        select.addActionListener(e -> controller.onHeroSelected(heroes.get(heroList.getSelectedIndex())));
        back.addActionListener(e   -> controller.onBack());

        JPanel column = GuiConstants.centeredColumn();
        addHeroListContent(column, scroll, select, back);

        add(column, BorderLayout.CENTER);
    }

    private void addHeroListContent(JPanel column, JScrollPane scroll, JButton select, JButton back) {
        column.add(Box.createVerticalGlue());
        column.add(scroll);
        column.add(Box.createVerticalStrut(15));
        column.add(select);
        column.add(Box.createVerticalStrut(10));
        column.add(back);
        column.add(Box.createVerticalGlue());
    }

    public void refresh(List<Hero> updatedHeroes) {
        heroes.clear();
        heroes.addAll(updatedHeroes);
        String[] names = updatedHeroes.stream()
            .map(h -> h.getHeroName() + "  [" + h.getHeroClass() + "  Lv." + h.getLevel() + "]")
            .toArray(String[]::new);
        heroList.setListData(names);
    }
}
