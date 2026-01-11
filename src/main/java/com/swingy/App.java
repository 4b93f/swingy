package src.main.java.com.swingy;
import src.main.java.com.swingy.Controller.HeroController;
import src.main.java.com.swingy.Model.Hero;
import src.main.java.com.swingy.Model.Artefact.Armor;
import src.main.java.com.swingy.Model.Enemy;

public class App {
    public static void main(String[] args) {
        Hero hero = new Hero.HeroBuilder().setHeroName("PlaceholderName").setHeroClass("PlaceholderClass").build();
        System.out.println(hero);

        HeroController heroController = new HeroController(hero);
        heroController.run();

        Enemy enemy0 = new Enemy.EnemyBuilder().build();
        System.out.println(enemy0);

        Enemy enemy1 = new Enemy.EnemyBuilder().build();
        System.out.println(enemy1);

        Enemy enemy2 = new Enemy.EnemyBuilder().build();
        System.out.println(enemy2);

        Enemy enemy = new Enemy.EnemyBuilder().build();
        System.out.println(enemy);


    }
}
