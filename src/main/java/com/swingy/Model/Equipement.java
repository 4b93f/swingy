package com.swingy.Model;
import com.swingy.Model.Artefact.Artefact;

public class Equipement {
	private Artefact weapon;
	private Artefact armor;
	private Artefact helmet;

	public Artefact getWeapon() { return weapon; }

	public void setWeapon(Artefact weapon) { this.weapon = weapon; }

	public Artefact getArmor() { return armor; }

	public void setArmor(Artefact armor) { this.armor = armor; }

	public Artefact getHelmet() { return helmet; }

	public void setHelmet(Artefact helmet) { this.helmet = helmet; }

	public String toString() { return "Equipement [weapon=" + weapon + ", armor=" + armor + ", helmet=" + helmet + "]"; }
}