package de.Jan.Varus.Events.Effekte;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class KoboldUnsichtbarkeitEvent implements Listener {
	public static List<Player> aktivPlayer = new ArrayList<>(); 
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(aktivPlayer.contains(event.getPlayer())) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if(aktivPlayer.contains(event.getPlayer())) {
			event.setCancelled(true);
		}
	}
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			if(aktivPlayer.contains(event.getEntity())) {
				event.setCancelled(true);
			}
		}  
		if(event.getDamager() instanceof Player) {
			if(aktivPlayer.contains(event.getDamager())) {
				event.setCancelled(true);
			}
		}
	}	
}
