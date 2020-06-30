package de.Jan.Varus.Events.InventoryClickEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Events.Effekte.FeeEffektEvent;

public class AntiAbilityAbuseEvent implements Listener  {
	JavaPlugin plugin; 
	public AntiAbilityAbuseEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void onInvClick(CraftItemEvent event) {
		Player player = (Player) event.getWhoClicked(); 
		if(hasUncrafeble(event.getView().getTopInventory())) {
			event.setCancelled(true);
			player.sendMessage(Main.PREFIX + "§cna na na! Mit diesen Item darfst du nicht craften!");
			player.closeInventory(); 
		}
	}
	public boolean hasUncrafeble(Inventory inv) {
		for (ItemStack stack : inv.getContents()){
			if(stack.hasItemMeta()) {
				if (stack.getItemMeta().hasDisplayName()) {
					if(stack.getItemMeta().getDisplayName().startsWith("§") &&  !stack.getItemMeta().getDisplayName().startsWith("§e")) {
						return true; 
					}
				}
			}
		}
		return false; 
	}
}
