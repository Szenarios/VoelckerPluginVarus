package de.Jan.Varus.Events.Dorf;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.Dorf;
import de.Jan.Varus.Objects.User;
import io.netty.handler.codec.compression.ZlibWrapper;

public class ClickOnDorfVillagerEvent implements Listener {
	JavaPlugin plugin;
	public ClickOnDorfVillagerEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void onEntityClick(PlayerInteractEntityEvent event) {
		if(event.getRightClicked().getType() == EntityType.VILLAGER) {
			Villager villager = (Villager) event.getRightClicked(); 
			if(villager.getCustomName() != null) {
				if(villager.getCustomName().toLowerCase().contains("manager")) {
					String customName = villager.getCustomName(); 
					Player player = event.getPlayer(); 
					User user = new User(player, plugin); 
					Dorf dorf = new Dorf(!customName.startsWith("§") ? customName.toLowerCase().split(" ")[0]: customName.toLowerCase().substring(2).split(" ")[0]);
					if(user.getVolk() == dorf.getVolk()) {
						player.openInventory(returnInventory(dorf)); 
					} else {
						player.sendMessage(Main.PREFIX + "§cDas ist nicht dein Volk!");
					}
				}
			}
		}
	}
	public Inventory returnInventory(Dorf dorf) {
		Inventory inv = Bukkit.createInventory(null, 27, dorf.getVolk().getPrefix() + dorf.getVolk().toString().toLowerCase() + " manager");
		ItemStack stack = null; 
		switch (dorf.getVolk()) {
		case FEE:
			stack = new ItemStack(Material.PINK_STAINED_GLASS_PANE); 
			break;
		case KOBOLD: 
			stack = new ItemStack(Material.GREEN_STAINED_GLASS_PANE); 
			break; 
		case WIKINGER:
			stack = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE); 
			break; 
		case VAMPIR: 
			stack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE); 
			break; 
		case ZWERG:
			stack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
			break; 
		default:
			stack = new ItemStack(Material.GLASS_PANE); 
			break;
		}
		
		changeMeta("§6", stack, "§o"); 
		
		ItemStack farming = new ItemStack(Material.GOLDEN_PICKAXE); 
		changeMeta("§dAutofarming", farming, "§c§oComming Soon"); 
		
		ItemStack info = new ItemStack(Material.PAPER); 
		changeMeta("§bDorf Information", info, "§7Level§8: §6" + dorf.getLevel(), dorf.getAnführer() != null ? "§7Anführer§8: §e"+Bukkit.getOfflinePlayer(dorf.getAnführer()).getName() : "§7Anführer§8: §ekeiner", dorf.getCoAnführer() != null ? "§7Coanführer§8: §e"+Bukkit.getOfflinePlayer(dorf.getCoAnführer()).getName() : "§7Coanführer§8: §ekeiner", "§7Mitglieder§8: §e" + dorf.getPlayer().size()); 
		
		ItemStack update = new ItemStack(Material.BREWING_STAND); 
		if(dorf.getLevel() == 5) {
			changeMeta("§6Upgrade dein Dorf!", update, "§eDein Dorf ist auf dem Momentanen §cMaximum§8!");
		} else {
			changeMeta("§6Upgrade dein Dorf!", update, "§7Für §6" + (50000 + ((dorf.getLevel()-1)*70000)) + " Coins§7!");
		}
		
		ItemStack defend = new ItemStack(Material.SHIELD); 
		changeMeta("§cDefender", defend, "§7Stelle deine Wachen ein!");
		
		inv.setItem(4, info);
		inv.setItem(10, farming);
		inv.setItem(16, defend);
		inv.setItem(22, update);
		
		for (int i = 0; i < 27; i++) {
			if (inv.getItem(i) == null) {
				inv.setItem(i, stack);
			}
		}
		return inv; 
	}
	public static ItemMeta changeMeta(String title, ItemStack item, String... lore) {
		ItemMeta meta = item.getItemMeta(); 
		meta.setDisplayName(title);
		List<String> list = new ArrayList<>(); 
		for(String string : lore) {
			list.add(string);
		}
		meta.setLore(list);
		item.setItemMeta(meta); 
		return meta; 
	}
}
