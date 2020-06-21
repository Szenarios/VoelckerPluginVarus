package de.Jan.Varus.Events.InventoryClickEvents;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.Jan.Varus.Main;
import de.Jan.Varus.Events.SpezialItem.HolzfällerAxtEvent;
import de.Jan.Varus.Events.SpezialItem.MegaPickeEvent;

public class AntiAnvilAbuseEvent implements Listener {
	@EventHandler
	public void onAnvilEvent(InventoryClickEvent event) {
		if (!event.isCancelled()) {
			HumanEntity ent = event.getWhoClicked();
			if (ent instanceof Player) {
				Player player = (Player) ent;
				Inventory inv = event.getInventory();
				if (inv instanceof AnvilInventory) {
					InventoryView view = event.getView();
					int rawSlot = event.getRawSlot();

					if (rawSlot == view.convertSlot(rawSlot)) {
						if (rawSlot == 2) {
							ItemStack item = event.getCurrentItem();

							if (item != null) {
								ItemMeta meta = item.getItemMeta();

								if (meta != null) {
									if (meta.hasDisplayName()) {
										String displayName = meta.getDisplayName();
										if(MegaPickeEvent.getPicke().getItemMeta().getDisplayName().contains(displayName) || HolzfällerAxtEvent.getAxt().getItemMeta().getDisplayName().contains(displayName)) {
											if(item.getEnchantments().containsKey(Enchantment.MENDING) || item.getEnchantments().containsKey(Enchantment.DURABILITY)) {
												event.setCancelled(true);
												player.closeInventory();
												player.sendMessage(Main.PREFIX + "§cDu darf dieses Item nicht mit §eMending §coder §eUnbreaking §ckombinieren!");
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
