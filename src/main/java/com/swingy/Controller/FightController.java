package com.swingy.Controller;

import java.util.Random;

import com.swingy.Model.Artefact.Artefact;
import com.swingy.Model.Artefact.ArtefactGenerator;
import com.swingy.Model.Enemy;
import com.swingy.Model.Hero;
import com.swingy.View.FightView;

public class FightController {

	private final Hero hero;
	private final Enemy enemy;
	private final FightView fightView;

	public FightController(Hero hero, Enemy enemy, FightView fightView) {
		this.hero = hero;
		this.enemy = enemy;
		this.fightView = fightView;
	}

	private boolean simulateFight() { return hero.fightEnemy(enemy); }

	public void startFight() {
		boolean heroWon = simulateFight();
		if (heroWon) {
			winFight();
		} else {
			loseFight();
		}
	}

	private void winFight() {
		gainXp(enemy);
		gainArtifact(enemy);
	}

	private void loseFight() {}

	public void run() {
		Random random = new Random();
		boolean isEscape = random.nextBoolean();

		if (isEscape) {
			fightView.displayHeroEscaped();
		} else {
			fightView.displayHeroCaught();
		}
	}

	private void gainXp(Enemy enemy) {
		int xpGained = hero.gainXpFromEnemy(enemy);
		fightView.displayHeroGainedXp(xpGained);
		
		if (hero.shouldLevelUp()) {
			hero.levelUp();
			fightView.displayHeroLevelUp(hero.getLevel());
		}
	}

	private void gainArtifact(Enemy enemy) {
		Random random = new Random();
		int artifactChance = random.nextInt(100);

		if (artifactChance < 30) {
			Artefact artifact = ArtefactGenerator.generate(enemy);
			hero.equipArtifact(artifact);
			fightView.displayHeroFoundArtifact(artifact);
		} else {
			fightView.displayNoArtifactFound();
		}
	}
}
