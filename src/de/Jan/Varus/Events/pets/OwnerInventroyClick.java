package de.Jan.Varus.Events.pets;


import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.Pet;

public class OwnerInventroyClick implements Listener {
	
	public static HashMap<Player, Entity> namechanges = new HashMap<>(); 
	@EventHandler
	public void onInvClick(InventoryClickEvent event) {
		try {
			if(UUID.fromString(event.getView().getTitle()) != null) {
				Entity entity = Bukkit.getEntity(UUID.fromString(event.getView().getTitle())); 
				if(event.getCurrentItem() != null) {
					if(event.getCurrentItem().hasItemMeta()) {
						if (event.getCurrentItem().getItemMeta().hasDisplayName()) {
							event.setCancelled(true);
							String name = event.getCurrentItem().getItemMeta().getDisplayName(); 
							if(name.equalsIgnoreCase("§eÄnder den Namen")) {
								namechanges.put((Player) event.getWhoClicked(), entity); 
								event.getWhoClicked().closeInventory(); 
								event.getWhoClicked().sendMessage(Main.PREFIX + "Bitte nutze jz §e/changename <Name>");
							} else 
								if(name.equalsIgnoreCase("§aFolgt dir")) {
									Pet pet = new Pet(entity); 
									pet.setFollow(false);
									pet.save();
									event.getWhoClicked().closeInventory();
									event.getWhoClicked().sendMessage(Main.PREFIX + "§cDein Haustier folgt dir nun nicht mehr!");
								} else 
									if(name.equalsIgnoreCase("§cBleibt still stehen!")) {
										Pet pet = new Pet(entity); 
										pet.setFollow(true);
										pet.save();
										event.getWhoClicked().closeInventory(); 
										event.getWhoClicked().sendMessage(Main.PREFIX + "§aDein Haustier folgt dir nun!");
									}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("NotTargetInv");
		}
	}
	
	public static Inventory getInventory(Entity entity) {
		Inventory inv = Bukkit.createInventory(null, 27, entity.getUniqueId().toString()); 
		ItemStack anvil = new ItemStack(Material.ANVIL); 
		ItemStack paper = new ItemStack(Material.PAPER); 
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS); 
		Main.ChangeMeta(anvil, "§eÄnder den Namen", "§7Click um Namen deines", "§7haustieres zu ändern");
		Main.ChangeMeta(paper, "§aInformationen", getInformation(entity));

		LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta(); 
		Pet pet = new Pet(entity); 
		if(pet.isFollow()) {
			meta.setDisplayName("§aFolgt dir");
			meta.setColor(Color.GREEN);
		} else {
			meta.setDisplayName("§cBleibt still stehen!");
			meta.setColor(Color.RED);
		}
		boots.setItemMeta(meta);
		
		inv.setItem(11, anvil);
		inv.setItem(13, paper);
		inv.setItem(15, boots);
		
		return inv;
	}
	public static String[] getInformation(Entity entity) { 
		return new String[] {
				"§7Name§8: §r" + entity.getCustomName(),
				"§7UUID§8: §r" + entity.getUniqueId(),
				"§7Type§8: §r" + entity.getType().toString(),
				"§7Am Leven§8: §r" + generateTime(entity.getTicksLived()),
				"§7Location§8: §r" + (int) entity.getLocation().getX() + " §8| §r" + (int) entity.getLocation().getY() + " §8| §r" +  (int)entity.getLocation().getZ(),
				}; 
	}
	public static String generateTime(int ticks) {
		int secend = ticks / 20; 
		int minuten = 0; 
		int stunden = 0; 
		int tage = 0; 
		int wochen = 0; 
		for(int x=0; secend >= 60; secend = secend-60) {
			minuten++; 
			for(int m = minuten; minuten >= 60; minuten = minuten-60) {
				stunden++;
				for(int st = stunden; stunden >= 24; stunden = stunden-24) {
					tage++;
					for(int t = tage; tage >= 7; tage = tage-7) {
						wochen = wochen + 1; 
					}
				}
			}
		}
		return (wochen == 0? "": wochen +" Wochen ") + (tage == 0? "": tage +" Tage ") + (stunden == 0? "": stunden +" Stunden ") + (minuten == 0? "": minuten +" Minuten ") + (secend == 0? "": secend +" Sekunden");
		
	}
}
