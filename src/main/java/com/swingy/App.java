package com.swingy;

import com.swingy.Controller.FightController;
import com.swingy.Controller.HeroController;
import com.swingy.Model.Enemy;
import com.swingy.Model.Hero;
import com.swingy.View.FightView;
import com.swingy.View.HeroView;


public class App {
    public static void main(String[] args) {
        HeroView heroView = new HeroView();
        HeroController heroController = new HeroController(null, heroView);
        
        Hero hero = heroController.createNewHero("Aragorn", "Warrior");
        
        heroController = new HeroController(hero, heroView);
        
        heroController.displayHeroStats();

        Enemy enemy = new Enemy.EnemyBuilder().build();
        System.out.println("\n" + enemy);

        FightView fightView = new FightView();
        FightController fightController = new FightController(hero, enemy, fightView);

        fightController.startFight();
        
        heroController.displayHeroStats();

    }
}
