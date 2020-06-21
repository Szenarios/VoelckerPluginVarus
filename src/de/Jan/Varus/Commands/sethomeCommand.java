package de.Jan.Varus.Commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.User;

public class sethomeCommand implements CommandExecutor {
	JavaPlugin plugin; 
	public sethomeCommand(JavaPlugin plugin) { 
		this.plugin = plugin; 
	}
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (sender instanceof Player) {
			Player player = (Player) sender; 
			User user = new User(player, plugin); 
			user.setHome(player.getLocation());
			user.save();
			
			if(user.getHome() == null) { 
				player.sendMessage(Main.PREFIX + "§aDeine Home wurde gespeichert! §7(Tab wird erst Aktualisiert beim Joinen!)");
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 5, 5);
			} else {
				player.sendMessage(Main.PREFIX + "§eDeine Home wurde Aktualisiert! §7(Tab wird erst Aktualisiert beim Joinen!)");
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 5, 5);
			}
		} else {
			sender.sendMessage(Main.PREFIX + "Du musst ein Spieler sein!");
		}
		return true;
	}
}
