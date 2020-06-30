package de.Jan.Varus.Events.Effekte;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class WikingerStärkeEvent implements Listener {
	public static List<Player> list = new ArrayList<>(); 
	
	@EventHandler
	public void onDmg(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) { 
			Player player = (Player) event.getDamager(); 
			if(list.contains(player)) {
				double dmg = event.getDamage(); 
				double half = (dmg / 100) * 30; 
				event.setDamage(dmg + half);
			}
		}
	}
}
