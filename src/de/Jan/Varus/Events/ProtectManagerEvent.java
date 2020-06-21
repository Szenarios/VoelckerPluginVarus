package de.Jan.Varus.Events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ProtectManagerEvent implements Listener {
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if(event.getEntityType() == EntityType.VILLAGER) {
			if(event.getEntity().getCustomName() != null) {
				if(event.getEntity().getCustomName().toLowerCase().contains("manager")) {
					event.setCancelled(true);
				}
			}
		}
	}
}
