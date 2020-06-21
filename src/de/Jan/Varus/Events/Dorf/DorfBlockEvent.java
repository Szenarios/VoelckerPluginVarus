package de.Jan.Varus.Events.Dorf;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.Dorf;
import de.Jan.Varus.Objects.User;

public class DorfBlockEvent implements Listener {
	JavaPlugin plugin; 
	public DorfBlockEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		if(event.getBlock().getType() == Material.TNT) {
			return; 
		}
		Player player = event.getPlayer(); 
		User user = new User(player, plugin); 
		for(String key : Main.dorf.getKeys(false)) {
			Dorf dorf = new Dorf(key); 
			Location loc = dorf.getOrt();
			if(loc != null) {
				int chunkX = loc.getChunk().getX(); 
				int chunkZ = loc.getChunk().getZ(); 
				
				for (int x = chunkX-dorf.getLevel(); x < chunkX+(dorf.getLevel() == 1 ? 2 : dorf.getLevel()*2); x++) {
					for (int z = chunkZ+dorf.getLevel(); z > chunkZ-(dorf.getLevel() == 1 ? 2 : dorf.getLevel()*2); z--) {
						if(event.getBlock().getLocation().getChunk().getX() == x) {
							if(event.getBlock().getLocation().getChunk().getZ() == z) {
								if(!dorf.getVolk().toString().contains(user.getVolk().toString()) && !player.hasPermission("varus.build")) {
									event.setCancelled(true);
									player.sendMessage(Main.PREFIX + "§cDu musst im Volk " +  dorf.getVolk().getPrefix() + dorf.getVolk().toString() + " §csein um hier abbauen zu können!");
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer(); 
		User user = new User(player, plugin); 
		if(event.getBlock().getType() == Material.TNT) {
			return; 
		}
		for(String key : Main.dorf.getKeys(false)) {
			Dorf dorf = new Dorf(key); 
			Location loc = dorf.getOrt();
			if(loc != null) {
				int chunkX = loc.getChunk().getX(); 
				int chunkZ = loc.getChunk().getZ(); 
				
				for (int x = chunkX-dorf.getLevel(); x < chunkX+(dorf.getLevel() == 1 ? 2 : dorf.getLevel()*2); x++) {
					for (int z = chunkZ+dorf.getLevel(); z > chunkZ-(dorf.getLevel() == 1 ? 2 : dorf.getLevel()*2); z--) {
						if(event.getBlock().getLocation().getChunk().getX() == x) {
							if(event.getBlock().getLocation().getChunk().getZ() == z) {
								if(!dorf.getVolk().toString().contains(user.getVolk().toString()) && !player.hasPermission("varus.build")) {
									event.setCancelled(true);
									player.sendMessage(Main.PREFIX + "§cDu musst im Volk " +  dorf.getVolk().getPrefix() + dorf.getVolk().toString() + " §csein um hier bauen zu können!");
								}
							}
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void onBukkit(PlayerInteractEvent event) {
		Player player = event.getPlayer(); 
		User user = new User(player, plugin); 
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getPlayer().getItemInHand().getType() == Material.LAVA_BUCKET || event.getPlayer().getItemInHand().getType() == Material.WATER_BUCKET) {
				for(String key : Main.dorf.getKeys(false)) {
					Dorf dorf = new Dorf(key); 
					Location loc = dorf.getOrt();
					if(loc != null) {
						int chunkX = loc.getChunk().getX(); 
						int chunkZ = loc.getChunk().getZ(); 
						
						for (int x = chunkX-dorf.getLevel(); x < chunkX+(dorf.getLevel() == 1 ? 2 : dorf.getLevel()*2); x++) {
							for (int z = chunkZ+dorf.getLevel(); z > chunkZ-(dorf.getLevel() == 1 ? 2 : dorf.getLevel()*2); z--) {
								if(player.getLocation().getChunk().getX() == x) {
									if(player.getLocation().getChunk().getZ() == z) {
										if(!dorf.getVolk().toString().contains(user.getVolk().toString()) && !player.hasPermission("varus.build")) {
											event.setCancelled(true);
											player.sendMessage(Main.PREFIX + "§cDu musst im Volk " +  dorf.getVolk().getPrefix() + dorf.getVolk().toString() + " §csein um hier bauen zu können!");
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
}
