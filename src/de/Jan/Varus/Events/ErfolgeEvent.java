package de.Jan.Varus.Events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Objects.Erfolge;
import de.Jan.Varus.Objects.ErfolgeType;
import de.Jan.Varus.Objects.User;

public class ErfolgeEvent implements Listener {
	JavaPlugin plugin; 
	public ErfolgeEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		for(Erfolge erfolge : Erfolge.values()) {
			if(erfolge.getType() == ErfolgeType.BLOCKBREAK) {
				if(event.getBlock().getType() == erfolge.getMaterial()) {
					User user = new User(event.getPlayer(), plugin); 
					if(!user.getErfolge().contains(erfolge.name())) {
						user.setCoins(user.getCoins() + erfolge.getCoins());
						user.save();
						sendMessage(erfolge, event.getPlayer());
						user.getErfolge().add(erfolge.name()); 
						user.save();
					}
				}
			}
		}
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		for(Erfolge erfolge : Erfolge.values()) {
			if(erfolge.getType() == ErfolgeType.BLOCKPLACE) {
				if(event.getBlock().getType() == erfolge.getMaterial()) {
					User user = new User(event.getPlayer(), plugin); 
					if(!user.getErfolge().contains(erfolge.name())) {
						user.setCoins(user.getCoins() + erfolge.getCoins());
						user.save();
						sendMessage(erfolge, event.getPlayer());
						user.getErfolge().add(erfolge.name()); 
						user.save();
					}
				}
			}
		}
	}
	@EventHandler
	public void onEntityKill(EntityDeathEvent event) {
		for(Erfolge erfolge : Erfolge.values()) {
			if(erfolge.getType() == ErfolgeType.ENTITYKILL) {
				if(event.getEntity().getType() == erfolge.getEntityType()) {
					if(event.getEntity().getKiller() instanceof Player) {
						User user = new User(event.getEntity().getKiller(), plugin); 
						if(!user.getErfolge().contains(erfolge.name())) {
							user.setCoins(user.getCoins() + erfolge.getCoins());
							user.save();
							sendMessage(erfolge, event.getEntity().getKiller());
							user.getErfolge().add(erfolge.name()); 
							user.save();
						}
					}
				}
			}
		}
	}
	public void sendMessage(Erfolge erfolge, Player player) {
		String mitte = "§7--=== §a" + erfolge.getName() + " §7===--"; 
		String side = Leerzeichen((erfolge.getMessage().length() - mitte.length()) / 2) ; 
		System.out.println((erfolge.getMessage().length() - mitte.length()) / 2);
		player.sendMessage(side + mitte + side);
		player.sendMessage(erfolge.getMessage());
		player.sendMessage("§e+§6" + erfolge.getCoins() + " §7Coins§8!");
		player.sendMessage(side + mitte + side);
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
		
	}
	private static String Leerzeichen(int i) {
		String string = ""; 
		for (int j = 0; j < i; j++) {
			string = string + " "; 
		}
		return string; 
	}
}
