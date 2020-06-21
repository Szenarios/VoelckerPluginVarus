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
import de.Jan.Varus.Objects.V�lker;

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
			if(user.getVolk() != V�lker.MENSCH) {
				Dorf dorf = user.getDorf(); 
				if(countVolk(dorf.getVolk()) >= 7) {
					player.openInventory(getinv(dorf)); 
				} else {
					sender.sendMessage(Main.PREFIX + "�cDein Volk muss Gr��er sein als 7 Spieler um zugriff auf Weitere D�rfer zu erhalten!");
				}
			} else {
				sender.sendMessage(Main.PREFIX + "�cDu musst leider in einem Anderen Volk daf�r sein!");
			}
		}
		
		return true;
	}
	public Inventory getinv(Dorf dorf) {
		Inventory inv = Bukkit.createInventory(null, 27, "�eWillst du dein Dorf �ndern?"); 
		int d�rfer = 0; 
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
		
		volkCommand.ChangeMeta(stack, "�6", "�o"); 
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
				d�rfer++; 
				ItemStack itemStack = new ItemStack(Material.WHITE_BANNER); 
				volkCommand.ChangeMeta(itemStack, secDorf.getVolk().getPrefix() + secDorf.getName(), "�7Level�8: �6" + secDorf.getLevel(), secDorf.getAnf�hrer() != null ? "�7Anf�hrer�8: �e"+Bukkit.getOfflinePlayer(secDorf.getAnf�hrer()).getName() : "�7Anf�hrer�8: �ekeiner", secDorf.getCoAnf�hrer() != null ? "�7Coanf�hrer�8: �e"+Bukkit.getOfflinePlayer(secDorf.getCoAnf�hrer()).getName() : "�7Coanf�hrer�8: �ekeiner", "�7Mitglieder�8: �e" + secDorf.getPlayer().size());
				inv.addItem(itemStack); 
			}
		}
		System.out.println(((int)countVolk(dorf.getVolk()) / 7));
		System.out.println((d�rfer+1));
		System.out.println();
		if((countVolk(dorf.getVolk()) >= 7 && d�rfer < 2)|| (countVolk(dorf.getVolk()) >= 13) && d�rfer < 3)  {
			ItemStack newCity = new ItemStack(Material.BARRIER); 
			volkCommand.ChangeMeta(newCity, "�aErstelle ein neues Dorf", "�eErstelle ein weiteres Dorf und werde dessen Anf�hrer!");
			inv.addItem(newCity);
		}
		
		return inv; 
	}
	public int countVolk(V�lker volk) {
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
