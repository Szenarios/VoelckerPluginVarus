package de.Jan.Varus.Objects;

import java.sql.Date;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import de.Jan.Varus.Main;

public class Pet {
	private EntityType type; 
	private Date tameDate; 
	private String name; 
	private boolean follow; 
	private String owner; 
	private Entity entity; 
	public Pet(Entity entity) {
		ConfigurationSection pet = (ConfigurationSection) Main.pets.get(entity.getUniqueId().toString()); 
		if(pet == null) {
			this.tameDate = null; 
			this.name = entity.getName(); 
			this.follow = true; 
			this.owner = null; 
			this.type = entity.getType(); 
		} else {
			this.name = pet.getString("name"); 
			this.type = EntityType.valueOf(pet.getString("type")); 
			this.tameDate = new Date(pet.getLong("tamedate")); 
			this.follow = pet.getBoolean("follow"); 
			this.owner = pet.getString("owner"); 
		}
		this.entity = entity; 
	}
	public EntityType getType() {
		return type;
	}
	public void setType(EntityType type) {
		this.type = type;
	}
	public Date getTameDate() {
		return tameDate;
	}
	public void setTameDate(Date tameDate) {
		this.tameDate = tameDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isFollow() {
		return follow;
	}
	public void setFollow(boolean follow) {
		this.follow = follow;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public void save() { 
		Main.pets.set(entity.getUniqueId().toString() + ".type", this.getType().toString());
		Main.pets.set(entity.getUniqueId().toString() + ".tamedate", this.getTameDate());
		Main.pets.set(entity.getUniqueId().toString() + ".name", this.getName());
		Main.pets.set(entity.getUniqueId().toString() + ".follow", this.isFollow());
		Main.pets.set(entity.getUniqueId().toString() + ".owner", this.getOwner());
		Main.SavePets();
	}
}
