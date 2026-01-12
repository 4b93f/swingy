package com.swingy;

import com.swingy.Controller.MapController;
import com.swingy.Model.GameState;
import com.swingy.Model.Hero;
import com.swingy.View.MapView;

public class MapDemo {
    public static void main(String[] args) {
        Hero hero = new Hero.HeroBuilder()
            .setHeroName("Warrior")
            .setHeroClass("Tank")
            .setLevel(7)
            .build();

        GameState gameState = new GameState(hero);

        MapView mapView = new MapView(
            gameState.getHero(), 
            gameState.getPosition(), 
            gameState.getMapSize()
        );

        MapController mapController = new MapController(gameState, mapView);
        
        System.out.println("Starting map exploration...");
        System.out.println("Use W/A/S/D to move, M to view full map, Q to quit");
        System.out.println();

        mapController.startExploration();
    }
}
