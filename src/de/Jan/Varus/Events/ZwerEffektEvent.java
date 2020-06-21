package de.Jan.Varus.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.User;
import de.Jan.Varus.Objects.Völker;

public class ZwerEffektEvent implements Listener {
	JavaPlugin plugin;
	public ZwerEffektEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onUseTools(PlayerItemDamageEvent event) {
		Player player = event.getPlayer(); 
		User user = new User(player, plugin); 
		if(user.getVolk() == Völker.WIKINGER) {
			int rdm = Main.returnRDM(3, 1);
			if(rdm == 1) {
				event.getItem().setDurability((short) (event.getItem().getDurability() -1));
			} 
		}
	}
}
