package de.Jan.Varus.Events.SpezialItem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.Jan.Varus.Commands.volkCommand;

public class axtDesDonnersEvent implements Listener {
	@EventHandler
	public void onItenInteractEvent(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player player = event.getPlayer(); 
			if(player.getItemInHand() != null) {
				ItemStack item = player.getItemInHand(); 
				if(item.hasItemMeta()) {
					if (item.getItemMeta().hasDisplayName()) {
						if(item.getItemMeta().getDisplayName().contains(getAxt().getItemMeta().getDisplayName())) {
							player.getWorld().strikeLightning(player.getTargetBlock(null, 12).getLocation());
							player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 1));
							if(player.getItemInHand().getDurability() >= 32) {
								player.getInventory().remove(player.getItemInHand());
							}
						}
					}
				}
			}
		}
	}
	
	public static ItemStack getAxt() {
		ItemStack stack = new ItemStack(Material.GOLDEN_AXE); 
		volkCommand.ChangeMeta(stack, "Axt des Donners", "§7Werfe einen §bblitz§7!");
		return stack;  
	}
}
