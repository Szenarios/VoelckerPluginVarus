package de.Jan.Varus.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.User;

public class getCoinsCommand implements CommandExecutor {
	JavaPlugin plugin; 
	public getCoinsCommand(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			plugin.reloadConfig();
			Player player = (Player) sender; 
			User user = new User(player, plugin); 
			player.sendMessage(Main.PREFIX + "Deine Coins betragen§8:§6" + user.getCoins());
		} else {
			sender.sendMessage(Main.PREFIX + "bro ne");
		}
			
		return true;
	}
}