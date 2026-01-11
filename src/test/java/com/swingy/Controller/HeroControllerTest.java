package src.main.java.com.swingy.Controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import src.main.java.com.swingy.Model.Hero;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HeroController Tests")
public class HeroControllerTest {

	private Hero hero;
	private HeroController controller;

	@BeforeEach
	void setUp() {
		hero = new Hero.HeroBuilder()
				.setHeroName("TestHero")
				.setHeroClass("Warrior")
				.build();
		controller = new HeroController(hero);
	}

	@Test
	@DisplayName("Should not level up when experience is below threshold")
	void testLevelUpWithInsufficientExperience() {
		// Hero starts at level 1 with 0 experience
		// Threshold for level 1 is 1000 XP
		hero.setExperience(500);

		controller.levelUp();

		assertEquals(1, hero.getLevel());
		assertEquals(500, hero.getExperience());
	}

	@Test
	@DisplayName("Should level up when experience meets threshold")
	void testLevelUpWithExactThreshold() {
		// Set experience to exactly 1000 (threshold for level 1)
		hero.setExperience(1000);

		controller.levelUp();

		assertEquals(2, hero.getLevel());
		assertEquals(0, hero.getExperience()); // 1000 - 1000 = 0
	}

	@Test
	@DisplayName("Should level up and carry over excess experience")
	void testLevelUpWithExcessExperience() {
		// Set experience to 1500 (500 more than threshold)
		hero.setExperience(1500);

		controller.levelUp();

		assertEquals(2, hero.getLevel());
		assertEquals(500, hero.getExperience()); // 1500 - 1000 = 500
	}

	@Test
	@DisplayName("Should level up from level 2 to level 3")
	void testLevelUpFromLevel2() {
		// Set hero to level 2 with enough XP for level 3
		hero.setLevel(2);
		hero.setExperience(2450); // Threshold for level 2

		controller.levelUp();

		assertEquals(3, hero.getLevel());
		assertEquals(0, hero.getExperience());
	}

	@Test
	@DisplayName("Should level up from level 3 to level 4")
	void testLevelUpFromLevel3() {
		hero.setLevel(3);
		hero.setExperience(5000); // More than threshold of 4800

		controller.levelUp();

		assertEquals(4, hero.getLevel());
		assertEquals(200, hero.getExperience()); // 5000 - 4800 = 200
	}

	@Test
	@DisplayName("Should level up from level 4 to level 5")
	void testLevelUpFromLevel4() {
		hero.setLevel(4);
		hero.setExperience(8050); // Exact threshold

		controller.levelUp();

		assertEquals(5, hero.getLevel());
		assertEquals(0, hero.getExperience());
	}

	@Test
	@DisplayName("Should level up from level 5 to level 6")
	void testLevelUpFromLevel5() {
		hero.setLevel(5);
		hero.setExperience(15000); // More than threshold of 12000

		controller.levelUp();

		assertEquals(6, hero.getLevel());
		assertEquals(3000, hero.getExperience()); // 15000 - 12000 = 3000
	}

	@Test
	@DisplayName("Should not level up when experience is just below threshold")
	void testLevelUpJustBelowThreshold() {
		hero.setExperience(999); // Just 1 XP below threshold

		controller.levelUp();

		assertEquals(1, hero.getLevel());
		assertEquals(999, hero.getExperience());
	}

	@Test
	@DisplayName("Should handle zero experience")
	void testLevelUpWithZeroExperience() {
		hero.setExperience(0);

		controller.levelUp();

		assertEquals(1, hero.getLevel());
		assertEquals(0, hero.getExperience());
	}

	@Test
	@DisplayName("Should handle multiple level ups correctly")
	void testMultipleLevelUps() {
		// Level 1 -> 2
		hero.setExperience(1000);
		controller.levelUp();
		assertEquals(2, hero.getLevel());
		assertEquals(0, hero.getExperience());

		// Level 2 -> 3
		hero.setExperience(2450);
		controller.levelUp();
		assertEquals(3, hero.getLevel());
		assertEquals(0, hero.getExperience());

		// Level 3 -> 4
		hero.setExperience(4800);
		controller.levelUp();
		assertEquals(4, hero.getLevel());
		assertEquals(0, hero.getExperience());
	}

	@Test
	@DisplayName("Should maintain hero state when level up fails")
	void testHeroStateUnchangedWhenLevelUpFails() {
		hero.setLevel(2);
		hero.setExperience(1000);

		int originalLevel = hero.getLevel();
		int originalExperience = hero.getExperience();

		controller.levelUp();

		// Should not change since 1000 < 2450 (threshold for level 2)
		assertEquals(originalLevel, hero.getLevel());
		assertEquals(originalExperience, hero.getExperience());
	}

	@Test
	@DisplayName("Should work with different heroes")
	void testControllerWithDifferentHeroes() {
		Hero hero1 = new Hero.HeroBuilder()
				.setHeroName("Hero1")
				.setLevel(1)
				.setExperience(1000)
				.build();

		Hero hero2 = new Hero.HeroBuilder()
				.setHeroName("Hero2")
				.setLevel(3)
				.setExperience(5000)
				.build();

		HeroController controller1 = new HeroController(hero1);
		HeroController controller2 = new HeroController(hero2);

		controller1.levelUp();
		controller2.levelUp();

		assertEquals(2, hero1.getLevel());
		assertEquals(4, hero2.getLevel());
	}
}
