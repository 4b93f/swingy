package com.swingy.model;
import com.swingy.model.artefact.Artefact;

public class Equipment {
	private Artefact weapon;
	private Artefact armor;
	private Artefact helmet;

	public Artefact getWeapon() { return weapon; }

	public void setWeapon(Artefact weapon) { this.weapon = weapon; }

	public Artefact getArmor() { return armor; }

	public void setArmor(Artefact armor) { this.armor = armor; }

	public Artefact getHelmet() { return helmet; }

	public void setHelmet(Artefact helmet) { this.helmet = helmet; }

	public String toString() { return "Equipment [weapon=" + weapon + ", armor=" + armor + ", helmet=" + helmet + "]"; }
}
