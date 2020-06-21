package de.Jan.Varus.Commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.Dorf;
import de.Jan.Varus.Objects.User;
import de.Jan.Varus.Objects.Völker;

public class dorfCommand implements CommandExecutor {
	JavaPlugin plugin; 
	public dorfCommand(JavaPlugin plugin){
		this.plugin = plugin; 
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender; 
			User user = new User(player, plugin); 
			if(user.getVolk() != Völker.MENSCH) {
				Dorf dorf = user.getDorf(); 
				if(countVolk(dorf.getVolk()) >= 7) {
					player.openInventory(getinv(dorf)); 
				} else {
					sender.sendMessage(Main.PREFIX + "§cDein Volk muss Größer sein als 7 Spieler um zugriff auf Weitere Dörfer zu erhalten!");
				}
			} else {
				sender.sendMessage(Main.PREFIX + "§cDu musst leider in einem Anderen Volk dafür sein!");
			}
		}
		
		return true;
	}
	public Inventory getinv(Dorf dorf) {
		Inventory inv = Bukkit.createInventory(null, 27, "§eWillst du dein Dorf ändern?"); 
		int dörfer = 0; 
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
		default:
			break;
		}
		
		volkCommand.ChangeMeta(stack, "§6", "§o"); 
		for (int i = 0; i < 9; i++) {
			inv.setItem(i, stack);
		}
		inv.setItem(9, stack);
		inv.setItem(17, stack);
		for (int i = 18; i < 27; i++) {
			inv.setItem(i, stack);
		}
		
		for(String key : Main.dorf.getKeys(false)) {
			Dorf secDorf = new Dorf(key); 
			if(secDorf.getVolk() == dorf.getVolk()) {
				dörfer++; 
				ItemStack itemStack = new ItemStack(Material.WHITE_BANNER); 
				volkCommand.ChangeMeta(itemStack, secDorf.getVolk().getPrefix() + secDorf.getName(), "§7Level§8: §6" + secDorf.getLevel(), secDorf.getAnführer() != null ? "§7Anführer§8: §e"+Bukkit.getOfflinePlayer(secDorf.getAnführer()).getName() : "§7Anführer§8: §ekeiner", secDorf.getCoAnführer() != null ? "§7Coanführer§8: §e"+Bukkit.getOfflinePlayer(secDorf.getCoAnführer()).getName() : "§7Coanführer§8: §ekeiner", "§7Mitglieder§8: §e" + secDorf.getPlayer().size());
				inv.addItem(itemStack); 
			}
		}
		System.out.println(((int)countVolk(dorf.getVolk()) / 7));
		System.out.println((dörfer+1));
		System.out.println();
		if((countVolk(dorf.getVolk()) >= 7 && dörfer < 2)|| (countVolk(dorf.getVolk()) >= 13) && dörfer < 3)  {
			ItemStack newCity = new ItemStack(Material.BARRIER); 
			volkCommand.ChangeMeta(newCity, "§aErstelle ein neues Dorf", "§eErstelle ein weiteres Dorf und werde dessen Anführer!");
			inv.addItem(newCity);
		}
		
		return inv; 
	}
	public int countVolk(Völker volk) {
		int count = 0; 
		for(String name : plugin.getConfig().getKeys(false)) {
			if(!name.equalsIgnoreCase("spawn")) {
				User user = new User(name, plugin); 
				if(user.getVolk() == volk) {
					count++; 
				}
			}
		}
		return count; 
	}
}
