package de.Jan.Varus.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.User;
import de.Jan.Varus.Objects.mobMoney;

public class EntityDieEvent implements Listener {
	JavaPlugin plugin; 
	public EntityDieEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void onPlayerDie(EntityDeathEvent event) {
		for (mobMoney mob : mobMoney.values()){
			if(event.getEntity().getType() == mob.getEntityType()) {
				if(event.getEntity().getKiller() instanceof Player) {
					User user = new User(event.getEntity().getKiller(), plugin); 
					user.setCoins(user.getCoins() + mob.getCoins());
					user.save();
					event.getEntity().getKiller().sendMessage("§e+§6" + mob.getCoins() + " Coins§8!");
				}
			}
		}	
	}
}
