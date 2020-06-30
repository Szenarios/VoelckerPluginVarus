package de.Jan.Varus.Commands;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Events.essentials.pingEvent;
import de.Jan.Varus.Objects.User;
import de.Jan.Varus.Objects.Völker;

public class volkCommand implements CommandExecutor {
	private JavaPlugin plugin; 
	public volkCommand(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@Override
	public boolean onCommand( CommandSender sender,  Command command,  String arg2, String[] args) {
		if(sender instanceof Player) { 
			Player player = (Player) sender; 
			User user = new User(player, plugin);
			if(user.getVolk() == Völker.MENSCH) {
				player.openInventory(getVolkAuswahl(player)); 
				player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
			} else {
				player.sendMessage(Main.PREFIX + "§cDu musst im Volk Mensch sein um ein anderes Volk wählen zu können!");
			}
		}  else {
			sender.sendMessage(Main.PREFIX + "Du musst ein Spieler sein um diesen Command ausführen zu dürfen!");
		}
		return true;
	}
	
	
	public boolean onNewCommand( CommandSender sender,  Command command,  String arg2, String[] args) {
		if(sender instanceof Player) { 
			Player player = (Player) sender; 
			User user = new User(player, plugin);
			if(user.getDorf().getAnführer().toString().equalsIgnoreCase(player.getUniqueId().toString()) && user.getDorf().getPlayer().size() > 1) {
				player.sendMessage(Main.PREFIX + "§cDu kannst dein Volk nicht ändern! Dein Dorf braucht dich!");
			} else {
				player.openInventory(getVolkAuswahl(player)); 
				player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
			}
		}  else {
			sender.sendMessage(Main.PREFIX + "Du musst ein Spieler sein um diesen Command ausführen zu dürfen!");
		}
		return true;
	}
	
	
	
	public static Inventory getVolkAuswahl(Player player) { 
		Inventory inv = Bukkit.createInventory(player, 9*4, "§eWähle ein Volk"); 
		ItemStack crossbow = new ItemStack(Material.CROSSBOW); 
		ChangeMeta(crossbow, "§6Wikinger", "§7Passiv§8: §eMehr Atem Unterwasser", "§9Aktiv§8: §67sek Stärke", "§cDebuff§8: §7Weniger Exp");
		
		ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING); 
		ChangeMeta(totem, "§2Kobold", "§7Passiv§8: §eMore Drops", "§9Aktiv§8: §67sek Unsichtbar", "§cDebuff§8: §7Weniger Haltbarkeit bei Werkzeugen");
		
		ItemStack blume = new ItemStack(Material.LILY_OF_THE_VALLEY); 
		ChangeMeta(blume, "§dFee", "§7Passiv§8: §eMore Exp", "§9Aktiv§8: §67sek Fly", "§cDebuff§8: §7More Damage");

		ItemStack picke = new ItemStack(Material.IRON_AXE); 
		ChangeMeta(picke, "§8Zwerg", "§7Passiv§8: §eMehr Haltbarkeit bei Werkzeugen", "§9Aktiv§8: §67???", "§cDebuff§8: §7Weniger Mob Drops");
		
		ItemStack schwarzeBlume = new ItemStack(Material.WITHER_ROSE);
		ChangeMeta(schwarzeBlume, "§0Vampir", "§7Passiv§8: §eMehr Schaden bei Nacht", "§9Aktiv§8: §67Sekunden Speedboost", "§cDebuff§8: §7Weniger Schaden bei Tag");
		
		inv.setItem(10, crossbow);
		inv.setItem(21, schwarzeBlume);
		inv.setItem(13, totem);
		inv.setItem(23, picke);
		inv.setItem(16, blume);
		return inv; 
	}
	public static void ChangeMeta(ItemStack item, String name, String...strings) { 
		ItemMeta meta = item.getItemMeta(); 
		meta.setDisplayName(name);
		ArrayList<String> list = new ArrayList<>(); 
		for (int i = 0; i < strings.length; i++) {
			list.add(strings[i]); 
		}
		meta.setLore(list);
		item.setItemMeta(meta); 
	}
}
