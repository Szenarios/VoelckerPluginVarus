package de.Jan.Varus.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Objects.User;

public class pingEvent implements Listener{
	JavaPlugin plugin; 
	public pingEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void pingEvent(ServerListPingEvent event) {
		event.setMaxPlayers(event.getNumPlayers() + 1);
		int kobolde = 0; 
		int wikinger = 0; 
		int feen = 0; 
		int menschen =0; 
		for (Player player : Bukkit.getOnlinePlayers()) {
			User user = new User(player, plugin); 
			switch (user.getVolk()) {
			case FEE:
				feen++; 
				break;
			case KOBOLD: 
				kobolde++; 
				break; 
			case MENSCH: 
				menschen++; 
				break; 
			case WIKINGER:
				wikinger++; 
				break; 
			default:
				break;
			}
		}
		// "§7--=== §9Varus Minecraft Server §7===--" + 
		event.setMotd("§7Auf §9Varus §7ist gerade§8: §e" + (isDay() ? "§6Tag": "§1Nacht") + "\n§7Online§8: §fMenschen§8:§7 " + menschen + " §2Kobolde§8:§7 " + kobolde + " §6Wikinger§8:§7 " + wikinger + " §dFeen§8:§7 " + feen);
	}
	public static boolean isDay() {
		long tick = Bukkit.getWorld("world").getTime(); 
		if(tick >= 0 && tick <= 12000) {
			return true; 
		} else {
			return false; 
		}
		
	}
}
