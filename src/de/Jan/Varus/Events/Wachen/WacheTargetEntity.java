package de.Jan.Varus.Events.Wachen;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Objects.Dorf;
import de.Jan.Varus.Objects.User;


public class WacheTargetEntity implements Listener {
	JavaPlugin plugin; 
	public WacheTargetEntity(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void onEntityDie(EntityDeathEvent event) {
		if(event.getEntity().getCustomName() == null) {
			return; 
		}
		if(event.getEntity().getCustomName().toLowerCase().contains("wache")) {
			event.setDroppedExp(0);
			event.getDrops().clear();
		}
	}
	@EventHandler
	public void onTarget(EntityTargetLivingEntityEvent event) {
		if(event.getTarget() != null) {
			if(event.getTarget().getCustomName() != null) {
				if(event.getTarget().getCustomName().toLowerCase().contains("wache")) {
					event.setTarget(null);
					event.setCancelled(true);
				}
			}
		}
		if(event.getEntity().getCustomName() == null) {
			return; 
		}
		
		if(event.getEntity().getCustomName().toLowerCase().contains("wache")) {
			
			if(event.getTarget() instanceof Player) {
				Player player = (Player) event.getTarget(); 
				User targetUser = new User(player, plugin); 
				Dorf dorf = getDorf(event.getEntity()); 
				switch (targetUser.getVolk()) {
				case FEE:
					if(dorf.isFee()) {
						event.setTarget(player);
					} else {
						event.setTarget(null);
					}
					break;
				case KOBOLD: 
					if(dorf.isKobold()) {
						event.setTarget(player);
					} else {
						event.setTarget(null);
					}
					break; 
				case MENSCH: 
					if(dorf.isMenschen()) {
						event.setTarget(player);
					} else {
						event.setTarget(null);
					}
					break; 
				case WIKINGER:
					if(dorf.isWikinger()) {
						event.setTarget(player);
					} else {
						event.setTarget(null);
					}
					break; 
				default:
					break;
				}
				
			} else {
				event.setTarget(null);
				event.setCancelled(true);
			}
			
		}
	}
	public Dorf getDorf(Entity entity) {
		if(entity.getCustomName().startsWith("§d")) {
			return new Dorf("fee"); 
		} else 
		if(entity.getCustomName().startsWith("§2")) {
			return new Dorf("kobold"); 
		} else 
		if(entity.getCustomName().startsWith("§6")) {
			return new Dorf("wikinger"); 
		} else {
			return null; 
		}
	}
}