package de.Jan.Varus.Events.essentials;

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
		int menschen = 0; 
		int vampir = 0; 
		int zwerg = 0; 
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
			case VAMPIR: 
				vampir++; 
				break; 
			case ZWERG: 
				zwerg++; 
				break; 
			default:
				break;
			}
		}
		boolean wartung = false; 
		if(plugin.getConfig().get("wartung") == null) {
			wartung = false; 
			plugin.getConfig().set("wartung", false);
			plugin.saveConfig();
		} else {
			wartung = plugin.getConfig().getBoolean("wartung"); 
		}
		event.setMotd("§7Auf §9Varus §7ist gerade§8: §e" + (isDay() ? "§6Tag": "§1Nacht") + "                §7[§4Wartung§7: " + (wartung ? "§aan"  :"§caus") + "§7]" +
					"\n"+ "§7--§8=== §d" + feen + " §2" + kobolde + " §f" + menschen + " §6" + wikinger + " §0" + vampir + " §8" + zwerg + " §8===§7--");
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
