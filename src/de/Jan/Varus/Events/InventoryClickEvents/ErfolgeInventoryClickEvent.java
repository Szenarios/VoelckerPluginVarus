package de.Jan.Varus.Events.InventoryClickEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Events.JoinEvent;
import de.Jan.Varus.Objects.User;

public class ErfolgeInventoryClickEvent implements Listener {
	JavaPlugin plugin; 
	public ErfolgeInventoryClickEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void onClickEvent(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked(); 
		User user = new User(player, plugin); 
		if(event.getView().getTitle().contains("Erfolge")) {
			if(event.getView().getTopInventory() == event.getClickedInventory()) {
				event.setCancelled(true);
				if(event.getCurrentItem() != null) {
					if(event.getCurrentItem().getItemMeta().hasDisplayName()) {
						String customName = event.getCurrentItem().getItemMeta().getDisplayName(); 
						user.setTitle(customName);
						user.save();
						player.closeInventory();
						player.sendMessage(Main.PREFIX + "Du hast jetzt den Title: §c" + customName);
						JoinEvent.setPrefix(player, plugin);
					}
				}
			}
		}
	}
}
