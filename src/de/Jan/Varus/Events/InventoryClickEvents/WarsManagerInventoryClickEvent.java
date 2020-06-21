package de.Jan.Varus.Events.InventoryClickEvents;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.Dorf;
import de.Jan.Varus.Objects.User;

public class WarsManagerInventoryClickEvent implements Listener {
	private JavaPlugin plugin; 
	public WarsManagerInventoryClickEvent(JavaPlugin plugin) {
		this.plugin = plugin; 	
	}
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked(); 
		User user = new User(player, plugin); 
		if(event.getView().getTitle().contains("verteidigung") && event.getView().getTitle().contains(user.getVolk().name().toLowerCase())) {
			if(event.getView().getTopInventory() == event.getClickedInventory()) {
				event.setCancelled(true);
				if(event.getCurrentItem() != null) {
					if(event.getCurrentItem().getItemMeta().hasDisplayName()) {
						String customName = event.getCurrentItem().getItemMeta().getDisplayName(); 
						Dorf dorf = user.getDorf(); 
						System.out.println(customName);
						if(customName.equalsIgnoreCase("Menschen")) {
							dorf.setMenschen(!dorf.isMenschen());
							player.closeInventory(); 
							player.sendMessage(Main.PREFIX + (dorf.isMenschen() ? "§cNun greifen deine Wachen alle Menschen an!" : "§aNun greifen deine Wachen keine Menschen an!"));
							dorf.save();
						} else
						if(customName.equalsIgnoreCase("§6Wikinger")) {
							dorf.setWikinger(!dorf.isWikinger());
							dorf.save();
							player.closeInventory(); 
							player.sendMessage(Main.PREFIX + (dorf.isWikinger() ? "§cNun greifen deine Wachen alle Wikinger an!" : "§aNun greifen deine Wachen keine Wikinger an!"));
						} else 
						if(customName.equalsIgnoreCase("§dFee")) {
							dorf.setFee(!dorf.isFee());
							dorf.save();
							player.closeInventory(); 
							player.sendMessage(Main.PREFIX + (dorf.isFee() ? "§cNun greifen deine Wachen alle Feen an!" : "§aNun greifen deine Wachen keine Feen an!"));
						} else 
						if(customName.equalsIgnoreCase("§2Kobold")) {
							dorf.setKobold(!dorf.isKobold());
							dorf.save();
							player.closeInventory(); 
							player.sendMessage(Main.PREFIX + (dorf.isKobold() ? "§cNun greifen deine Wachen alle Kobold an!" : "§aNun greifen deine Wachen keine Kobold an!"));
						}
					}
				}
			}
		}
	}
}
