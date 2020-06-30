package de.Jan.Varus.Events.essentials;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class CreeperExplodeEvent implements Listener {
	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		if(event.getEntity().getType() == EntityType.CREEPER) {
			event.setCancelled(true);
		}
	}
}
