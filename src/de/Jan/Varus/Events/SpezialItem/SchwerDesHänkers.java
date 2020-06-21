package de.Jan.Varus.Events.SpezialItem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.Jan.Varus.Commands.volkCommand;

public class SchwerDesHänkers implements Listener {
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(event.getEntity().getKiller() instanceof Player) {
			Player player = event.getEntity().getKiller(); 
			if(player.getItemInHand() != null) {
				if(player.getItemInHand().hasItemMeta()) {
					if(player.getItemInHand().getItemMeta().hasDisplayName()) {
						if(player.getItemInHand().getItemMeta().getDisplayName().contains(getSword().getItemMeta().getDisplayName())) {
							Player target = event.getEntity(); 
							ItemStack skull = new ItemStack(Material.PLAYER_HEAD); 
							SkullMeta skullMeta = (SkullMeta) skull.getItemMeta(); 
							skullMeta.setOwningPlayer(target); 
							skull.setItemMeta(skullMeta); 
							
							event.getEntity().getLocation().getWorld().dropItem(event.getEntity().getLocation(), skull); 
							player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 30));
							if(player.getItemInHand().getDurability() >= 250) {
								player.getInventory().remove(player.getItemInHand());
							}
						}
					}
				}
			}
		}
	}
	public static ItemStack getSword() {
		ItemStack stack = new ItemStack(Material.IRON_SWORD); 
		volkCommand.ChangeMeta(stack, "Schwert des Hänkers", "Tote einen Spieler und bekomme seinen Kopf");
		return stack; 
	}
}
