package de.Jan.Varus.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Events.Dorf.ClickOnDorfVillagerEvent;
import de.Jan.Varus.Objects.Erfolge;
import de.Jan.Varus.Objects.User;

public class titleCommand implements CommandExecutor {
	JavaPlugin plugin; 
	public titleCommand(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender; 
			User user = new User(player, plugin); 
			if(user.getErfolge().size() == 0) {
				sender.sendMessage(Main.PREFIX + "§cDu hast keine Erfolge!");
			} else {
				player.openInventory(getInventroy(player, user)); 
				player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 5, 5);
			}
		}
		return true;
	}
	public Inventory getInventroy(Player player, User user) {
		Inventory inv = Bukkit.createInventory(null, 54, "§aDeine Erfolge"); 

		switch (user.getVolk()) {
		case FEE:
			ItemStack blume = new ItemStack(Material.LILY_OF_THE_VALLEY); 
			ClickOnDorfVillagerEvent.changeMeta("§dFee", blume, "§7Sei ein Kobold");
			inv.addItem(blume);
			break;
		case WIKINGER: 
			ItemStack crossbow = new ItemStack(Material.CROSSBOW); 
			ClickOnDorfVillagerEvent.changeMeta( "§6Vikinger", crossbow, "§7Sei ein Wikinger");
			inv.addItem(crossbow);
			break; 
		case KOBOLD: 
			ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING); 
			ClickOnDorfVillagerEvent.changeMeta("§2Kobold", totem, "§7Sei eine Fee");
			inv.addItem(totem);
			break; 
		case MENSCH:
			ItemStack mensch = new ItemStack(Material.PAPER); 
			ClickOnDorfVillagerEvent.changeMeta("§fMensch", mensch, "§7Sei eine Mensch");
			inv.addItem(mensch); 
			break; 
			
		default:
			break;
		}
		for(String string : user.getErfolge()) {
			Erfolge erfolg = Erfolge.valueOf(string); 
			ItemStack stack = new ItemStack(erfolg.getMaterial() != null ? erfolg.getMaterial() : Material.PAPER); 
			ClickOnDorfVillagerEvent.changeMeta(erfolg.getName(), stack, erfolg.getMessage(), "§e+" + erfolg.getCoins() + " §6Coins"); 
			inv.addItem(stack);
		}
		return inv; 
	}
}
