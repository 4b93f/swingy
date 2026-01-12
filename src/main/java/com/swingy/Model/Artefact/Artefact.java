package com.swingy.Model.Artefact;

public abstract class Artefact {
	String artefactName;
	int bonusAttack;
	int bonusDefense;
	int bonusHitPoints;

	Artefact(String artefactName, int bonusAttack, int bonusDefense, int bonusHitPoints) {
		this.artefactName = artefactName;
		this.bonusAttack = bonusAttack;
		this.bonusDefense = bonusDefense;
		this.bonusHitPoints = bonusHitPoints;
	}

	public String getArtefactName() {
		return artefactName;
	}

	public void setArtefactName(String artefactName) {
		this.artefactName = artefactName;
	}

	public int getBonusAttack() {
		return bonusAttack;
	}

	public void setBonusAttack(int bonusAttack) {
		this.bonusAttack = bonusAttack;
	}

	public int getBonusDefense() {
		return bonusDefense;
	}

	public void setBonusDefense(int bonusDefense) {
		this.bonusDefense = bonusDefense;
	}

	public int getBonusHitPoints() {
		return bonusHitPoints;
	}

	public void setBonusHitPoints(int bonusHitPoints) {
		this.bonusHitPoints = bonusHitPoints;
	}

	public String toString() {
		return "Artefact [artefactName=" + artefactName + ", bonusAttack=" + bonusAttack + ", bonusDefense=" + bonusDefense + ", bonusHitPoints=" + bonusHitPoints + "]";
	}
}
