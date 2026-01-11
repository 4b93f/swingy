package src.main.java.com.swingy.Controller;
import src.main.java.com.swingy.Model.Hero;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class HeroController {
	private Hero hero;
	private static final Map<Integer, Integer> XP_THRESHOLD = Map.of(
		1, 1000,
		2, 2450,
		3, 4800,
		4, 8050, 
		5, 12000
	);

	public HeroController(Hero hero) {
		this.hero = hero;
	}

	public void levelUp() {
		int experience = hero.getExperience();
		int level = hero.getLevel();

		if (experience < XP_THRESHOLD.get(level)) {
			return;
		}

		experience -= XP_THRESHOLD.get(level);
		level++;
		
		hero.setLevel(level);
		hero.setExperience(experience);
	}

	public void run() {
		Random random = new Random();
		boolean isEscape = random.nextBoolean();
		if (isEscape) {
			System.out.println("Hero escaped!");
		} else {
			System.out.println("Hero was caught!");
		}
	}

	public void attack() {
		
	}
	
	public void won() {

	}
}

