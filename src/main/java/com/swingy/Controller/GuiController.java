package com.swingy.controller;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import com.swingy.model.Hero;
import com.swingy.model.HeroPersistence;
import com.swingy.view.gui.CreateHeroPanel;
import com.swingy.view.gui.GuiView;
import com.swingy.view.gui.HeroStatsPanel;
import com.swingy.view.gui.MenuPanel;
import com.swingy.view.gui.SelectHeroPanel;

public class GuiController {

    private static final Validator VALIDATOR =
        Validation.buildDefaultValidatorFactory().getValidator();

    private final GuiView          view;
    private final CreateHeroPanel  createHPanel;
    private final MenuPanel        menuPanel;
    private final SelectHeroPanel  selectPanel;
    private final HeroStatsPanel   statsPanel;
    private final List<Hero>       savedHeroes;
    private Hero                   pendingHero;

    public GuiController() {
        savedHeroes  = HeroPersistence.loadAll();
        view         = new GuiView();
        createHPanel = new CreateHeroPanel(this);
        menuPanel    = new MenuPanel(this);
        selectPanel  = new SelectHeroPanel(savedHeroes, this);
        statsPanel   = new HeroStatsPanel(this);

        view.addCard(menuPanel,    "menu");
        view.addCard(createHPanel, "create");
        view.addCard(selectPanel,  "select");
        view.addCard(statsPanel,   "stats");

        view.showCard("menu");
        view.setVisible(true);
    }

    private void showStats(Hero hero) {
        pendingHero = hero;
        statsPanel.show(hero);
        view.showCard("stats");
    }

    private void startGame(Hero hero) {
        GuiMapController mapController = new GuiMapController(hero, this::onVictory, this::onGameOver);
        view.addCard(mapController.getPanel(), "game");
        view.showCard("game");
    }

    public void onConfirmHero() {
        startGame(pendingHero);
    }

    private void onVictory(Hero hero) {
        hero.restoreHp();
        HeroPersistence.save(hero);
        refreshSavedHeroes();

        String msg = "You reached the border — VICTORY!\n"
            + hero.getHeroName() + " saved at Lv." + hero.getLevel() + ".\n\n"
            + "Play again on a new map?";
        int choice = JOptionPane.showConfirmDialog(null, msg, "Victory!", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION)
            showStats(hero);
        else
            view.showCard("menu");
    }

    private void onGameOver(Hero hero) {
        hero.restoreHp();
        HeroPersistence.save(hero);
        refreshSavedHeroes();

        JOptionPane.showMessageDialog(null,
            hero.getHeroName() + " was defeated...\nProgress saved at Lv." + hero.getLevel() + ".",
            "Game Over", JOptionPane.ERROR_MESSAGE);
        view.showCard("menu");
    }

    private void refreshSavedHeroes() {
        savedHeroes.clear();
        savedHeroes.addAll(HeroPersistence.loadAll());
        selectPanel.refresh(savedHeroes);
    }

    public void onNewHero() {
        view.showCard("create");
    }

    public void onSelectHero() {
        refreshSavedHeroes();
        view.showCard("select");
    }

    public void onCreateHero(String name, String heroClass) {
        Hero hero = new Hero.HeroBuilder()
            .setHeroName(name)
            .setHeroClass(heroClass)
            .build();

        var violations = VALIDATOR.validate(hero);
        if (!violations.isEmpty()) {
            String msg = violations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("\n"));
            createHPanel.showError(msg);
            return;
        }

        createHPanel.clearError();
        HeroPersistence.save(hero);
        refreshSavedHeroes();
        showStats(hero);
    }

    public void onHeroSelected(Hero hero) {
        showStats(hero);
    }

    public void onBack() {
        view.showCard("menu");
    }

    public void onQuit() {
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuiController::new);
    }
}
