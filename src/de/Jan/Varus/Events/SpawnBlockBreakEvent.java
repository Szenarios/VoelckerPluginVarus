package de.Jan.Varus.Events;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import de.Jan.Varus.Main;


public class SpawnBlockBreakEvent implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Chunk chunk = event.getBlock().getChunk(); 
		if(chunk.getX() <= 7 && chunk.getX() >= 4) {
			if(chunk.getZ() >= -7 && chunk.getZ() <= -1) {
				if(!event.getPlayer().hasPermission("varus.buildspawn")) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(Main.PREFIX + "§cDu darfst hier nicht bauen!");
				}
			}
		}
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Chunk chunk = event.getBlock().getChunk(); 
		if(chunk.getX() <= 7 && chunk.getX() >= 4) {
			if(chunk.getZ() >= -7 && chunk.getZ() <= -1) {
				if(!event.getPlayer().hasPermission("varus.buildspawn")) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(Main.PREFIX + "§cDu darfst hier nicht bauen!");
				}
			}
		}
	}	
}