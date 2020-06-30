package de.Jan.Varus.Events.essentials;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Objects.User;

public class leaveEvent implements Listener{
	JavaPlugin plugin; 
	public leaveEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void leaveEvent(PlayerQuitEvent event) { 
		User user = new User(event.getPlayer(), plugin); 
		user.setLastPlayed(System.currentTimeMillis());
		user.save();
		event.setQuitMessage("§8[§c-§8] " + event.getPlayer().getDisplayName());
	}
}
