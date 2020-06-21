package de.Jan.Varus.Events.InventoryClickEvents;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Commands.volkCommand;
import de.Jan.Varus.Objects.Dorf;
import de.Jan.Varus.Objects.User;

public class NewDorfInvClickEvent implements Listener {
	private JavaPlugin plugin; 
	public NewDorfInvClickEvent(JavaPlugin plugin) {
		this.plugin = plugin; 	
	}
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		if(event.getView().getTitle().equalsIgnoreCase("§eWillst du dein Dorf ändern?")) {
			if(event.getView().getTopInventory() == event.getClickedInventory()) {
				event.setCancelled(true);
				if(event.getCurrentItem() != null) {
					if(event.getCurrentItem().getItemMeta().hasDisplayName()) {
						User user = new User((Player) event.getWhoClicked(), plugin); 
						Player player = (Player) event.getWhoClicked(); 
						String customname = event.getCurrentItem().getItemMeta().getDisplayName(); 
						if(customname.equalsIgnoreCase("§aErstelle ein neues Dorf")) {
							Dorf dorf = user.getDorf(); 
							if(dorf.getAnführer().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
								player.closeInventory();
								player.sendMessage(Main.PREFIX + "§cDu bist Anführer deines Dorfes also kannst du nicht Wechseln!");
							} else {
								int dörfer = 0; 
								for(String key : Main.dorf.getKeys(false)) {
									Dorf secDorf = new Dorf(key); 
									if(secDorf.getVolk() == dorf.getVolk()) {
										dörfer++; 
									}
								}
								dorf.getPlayer().remove(player.getUniqueId().toString()); 
								dorf.save();
								
								Dorf newdorf = new Dorf(dorf.getVolk().name().toLowerCase() + (dörfer+1)); 
								newdorf.setVolk(dorf.getVolk());
								user.setDorf(newdorf);
								user.save();
								VolkInventoryClickEvent.ckeckVolk(player, user); // DORF SAVED
								player.sendMessage(Main.PREFIX + "§aDu hast ein neues Dorf erstellt!");
							}
						} else 
						if(!customname.contentEquals("§6")) {
							Dorf dorf = user.getDorf(); 
							Dorf clickedDorf = new Dorf(customname.substring(2)); 
							if(dorf.getAnführer().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
								player.closeInventory();
								player.sendMessage(Main.PREFIX + "§cDu bist Anführer deines Dorfes also kannst du nicht Wechseln!");
							} else {
								if(dorf.getName().equalsIgnoreCase(clickedDorf.getName())) {
									player.closeInventory();
									player.sendMessage(Main.PREFIX + "§cDu bist bereits in diesem Dorf!");
								} else {
									user.setDorf(clickedDorf);
									dorf.getPlayer().remove(player.getUniqueId().toString()); 
									dorf.save();
									user.save();
									VolkInventoryClickEvent.ckeckVolk(player, user); // DORF SAVED
									player.sendMessage(Main.PREFIX + "§aDu hast dein Dorf geweselt!");
								}
							}
						}
					}
				}
			}
		}
	}
}
