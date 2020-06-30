package de.Jan.Varus.Events.essentials;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;

public class PlayerDieEvent implements Listener {
	JavaPlugin plugin; 
	public PlayerDieEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void onPlayerDie(PlayerDeathEvent event) {
		Player player = event.getEntity(); 
		if(event.getEntity().getKiller() instanceof Player) {
			event.setDeathMessage(Main.PREFIX + "Der Spieler " + player.getDisplayName() + " §7wurde von " + event.getEntity().getKiller().getDisplayName() + " §7getötet!");
		} else {
			event.setDeathMessage(Main.PREFIX + "Der Spieler " + player.getDisplayName() + " §7ist gestorben!");
		}
	}
}
