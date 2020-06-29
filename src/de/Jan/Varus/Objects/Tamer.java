package de.Jan.Varus.Objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import de.Jan.Varus.Main;

public class Tamer {
	private int maxTames; 
	private int tames; 
	private List<String> ids; 
	private Player owner; 
	public Tamer(Player owner) {
		ConfigurationSection tamer = (ConfigurationSection) Main.pets.get(owner.getUniqueId().toString() ); 
		if(tamer == null) {
			this.maxTames = 3; 
			this.tames = 0; 
			ids = new ArrayList<String>(); 
		} else {
			this.maxTames = tamer.getInt("maxtames"); 
			this.tames = tamer.getInt("tame "); 
			this.ids = (List<String>) tamer.get("ids"); 
		}
		this.owner = owner; 
	}
	public int getMaxTames() {
		return maxTames;
	}
	public void setMaxTames(int maxTames) {
		this.maxTames = maxTames;
	}
	public int getTames() {
		return tames;
	}
	public void setTames(int tames) {
		this.tames = tames;
	}

	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public void save() { 
		Main.pets.set(owner.getUniqueId().toString() + ".tames", tames);
		Main.pets.set(owner.getUniqueId().toString() + ".ids", ids);
		Main.pets.set(owner.getUniqueId().toString() + ".maxtames", maxTames);
		Main.SavePets();
	}
}
