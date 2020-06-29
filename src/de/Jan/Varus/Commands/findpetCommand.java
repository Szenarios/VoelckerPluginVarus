
package de.Jan.Varus.Commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.booleans.BooleanSets.SynchronizedSet;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.Jan.Varus.Main;
import de.Jan.Varus.Events.pets.OwnerInventroyClick;
import de.Jan.Varus.Objects.Pet;
import de.Jan.Varus.Objects.Tamer;

public class findpetCommand implements CommandExecutor, Listener {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] args) {
		if(sender instanceof Player ) {
			Player player = (Player) sender; 
			Tamer tamer = new Tamer(player); 
			if(tamer.getIds().size() > 0) {
				Inventory inv = Bukkit.createInventory(null, 27, "§eDeine Haustiere"); 
				ItemStack glas = new ItemStack(Material.GREEN_STAINED_GLASS_PANE); 
				for (int i = 0; i < 9; i++) {
					inv.setItem(i, glas);
				}
				for(String id : tamer.getIds()) {
					if(Bukkit.getEntity(UUID.fromString(id)) != null){
						Entity entity = Bukkit.getEntity(UUID.fromString(id)); 
						Pet pet = new Pet(entity); 
						ItemStack stack = new ItemStack(Material.valueOf(pet.getType() + "_SPAWN_EGG")); 
						Main.ChangeMeta(stack, entity.getCustomName(), OwnerInventroyClick.getInformation(entity));
						inv.addItem(stack); 
					}
					
				}
				
				for (int i = 18; i < 27; i++) {
					inv.setItem(i, glas);
				}
				player.openInventory(inv); 
			} else {
				sender.sendMessage(Main.PREFIX + "§cDu musst ein Haustier haben um dieses zu finden!");
			}
		}
		return true;
	}
	@EventHandler
	public void invClick(InventoryClickEvent event) {
		if(event.getView().getTitle().equalsIgnoreCase("§eDeine Haustiere")) {
			event.setCancelled(true);
		}
		
	}
}
