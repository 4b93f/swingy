package com.swingy;

import javax.swing.SwingUtilities;
import java.util.List;
import java.util.Scanner;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import com.swingy.controller.GuiController;
import com.swingy.controller.MapController;
import com.swingy.model.GamePhase;
import com.swingy.model.GameState;
import com.swingy.model.Hero;
import com.swingy.model.HeroPersistence;
import com.swingy.view.MapView;

public class App {
    private static final Validator VALIDATOR =
        Validation.buildDefaultValidatorFactory().getValidator();
    public static void main(String[] args) {
        if (args.length == 0 || (!args[0].equals("console") && !args[0].equals("gui"))) {
            System.err.println("Usage: java -jar swingy.jar [console|gui]");
            System.exit(1);
        }

        if (args[0].equals("gui")) {
            SwingUtilities.invokeLater(GuiController::new);
        } else {
            runConsole();
        }
    }

    private static void runConsole() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Hero hero = pickOrCreateHero(scanner);
            if (hero == null) break;

            printHeroStats(hero);
            System.out.print("Press Enter to start...");
            scanner.nextLine();

            boolean keepPlaying = true;
            while (keepPlaying) {
                HeroPersistence.save(hero);
                GameState gameState = new GameState(hero);
                gameState.initializeEnemyPositions();
                MapView mapView = new MapView(hero, gameState.getEnemies(), gameState.getPosition(), gameState.getMapSize());
                new MapController(gameState, mapView, scanner).startExploration();
                hero.restoreHp();
                HeroPersistence.save(hero);

                if (gameState.getCurrentPhase() == GamePhase.VICTORY) {
                    System.out.print("\nPlay again on a new map? (y/n): ");
                    keepPlaying = scanner.nextLine().trim().equalsIgnoreCase("y");
                } else {
                    keepPlaying = false;
                }
            }
            // fall through to menu
        }
        scanner.close();
    }

    private static Hero pickOrCreateHero(Scanner scanner) {
        List<Hero> saved = HeroPersistence.loadAll();

        while (true) {
            System.out.println("\n=== SWINGY ===");
            System.out.println("1. New hero");
            if (!saved.isEmpty()) System.out.println("2. Load hero");
            System.out.println("0. Quit");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();
            if (choice.equals("0")) return null;
            if (choice.equals("1")) return createHero(scanner);
            if (choice.equals("2") && !saved.isEmpty()) return selectHero(saved, scanner);
            System.out.println("Invalid choice.");
        }
    }

    private static Hero selectHero(List<Hero> saved, Scanner scanner) {
        while (true) {
            System.out.println("\nSaved heroes:");
            for (int i = 0; i < saved.size(); i++) {
                Hero h = saved.get(i);
                System.out.printf("  %d. %s [%s  Lv.%d]%n", i + 1, h.getHeroName(), h.getHeroClass(), h.getLevel());
            }
            System.out.print("Select (1-" + saved.size() + "): ");
            try {
                int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (idx >= 0 && idx < saved.size()) return saved.get(idx);
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid selection.");
        }
    }

    private static void printHeroStats(Hero hero) {
        System.out.println("\n══════════════════════════════");
        System.out.printf("  %s  [%s]%n", hero.getHeroName(), hero.getHeroClass());
        System.out.println("══════════════════════════════");
        System.out.printf("  Level      : %d%n", hero.getLevel());
        System.out.printf("  Experience : %d%n", hero.getExperience());
        System.out.printf("  Hit Points : %d%n", hero.getTotalHitPoints());
        System.out.printf("  Attack     : %d%n", hero.getTotalAttack());
        System.out.printf("  Defense    : %d%n", hero.getTotalDefense());
        System.out.println("══════════════════════════════\n");
    }

    private static Hero createHero(Scanner scanner) {
        while (true) {
            System.out.print("Hero name: ");
            String name = scanner.nextLine().trim();
            System.out.println("Classes: Warrior, Mage, Rogue, Tank");
            System.out.print("Class: ");
            String heroClass = scanner.nextLine().trim();

            Hero hero = new Hero.HeroBuilder()
                .setHeroName(name)
                .setHeroClass(heroClass)
                .build();

            var violations = VALIDATOR.validate(hero);
            if (violations.isEmpty()) return hero;

            System.out.println("Invalid input:");
            for (ConstraintViolation<Hero> v : violations)
                System.out.println("  - " + v.getPropertyPath() + ": " + v.getMessage());
        }
    }
}
