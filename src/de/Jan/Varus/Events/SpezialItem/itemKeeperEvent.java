package de.Jan.Varus.Events.SpezialItem;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import de.Jan.Varus.Main;
import de.Jan.Varus.Commands.volkCommand;

public class itemKeeperEvent implements Listener {
	@EventHandler
	public void onDieEvent(PlayerDeathEvent event) {
		Player player = event.getEntity(); 
		System.out.println(player.getInventory().contains(itemKeeperEvent.getKeeper()));
		if(player.getInventory().contains(itemKeeperEvent.getKeeper())) {
			event.setKeepInventory(true);
			player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 5, 5);
			player.sendMessage(Main.PREFIX + "§6Dein Item Keeper wurde genutzt!");
			System.out.println("used");
		}
		for(ItemStack stack : player.getInventory().getContents()) {
			if(stack != null) {
				if(stack.getType() == Material.PHANTOM_MEMBRANE) {
					if(stack.hasItemMeta()) {
						if(stack.getItemMeta().hasDisplayName()) {
							if(stack.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lItem Keeper")) {
								if(stack.getAmount() >= 1) {
									stack.setAmount(stack.getAmount()-1);
								} else {
									player.getInventory().remove(stack);
								}
								System.out.println("gelöscht");
							}
						}
					}
				}
			}
		}
	}
	public static ItemStack getKeeper() {
		ItemStack stack = new ItemStack(Material.PHANTOM_MEMBRANE); 
		volkCommand.ChangeMeta(stack, "§6§lItem Keeper", "§7Mit diesen Item wirst", "§7du kein Item beim Sterben verlieren!");
		return stack; 
	}
}
