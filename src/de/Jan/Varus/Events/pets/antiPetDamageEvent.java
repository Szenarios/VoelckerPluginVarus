package de.Jan.Varus.Events.pets;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import de.Jan.Varus.Objects.Pet;

public class antiPetDamageEvent implements Listener {
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		Pet pet = new Pet(event.getEntity()); 
		if(pet.getOwner() != null) {
			event.setCancelled(true);
		}
	}
}
