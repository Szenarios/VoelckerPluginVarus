package de.Jan.Varus.Commands;

import java.lang.annotation.Target;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.User;

public class remitCoinsCommand implements CommandExecutor {
	JavaPlugin plugin; 
	public remitCoinsCommand(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender; 
			User user = new User(player, plugin); 
			plugin.reloadConfig();
			if(args.length == 2) {
				String playerName = args[0]; 
				int coins = Integer.parseInt(args[1]);
				if(user.getCoins() >= coins) {
					if(Bukkit.getPlayer(playerName) != null) {
						Player target = Bukkit.getPlayer(playerName); 
						user.setCoins(user.getCoins() - coins);
						user.save();
						
						User targetUser = new User(target, plugin); 
						targetUser.setCoins(targetUser.getCoins() + coins);
						targetUser.save();
						
						target.sendMessage(Main.PREFIX + "§aDir wurden §6" + coins + " Coins §avon dem Spieler §e" + player.getDisplayName() + " §aüberwiesen!");
						player.sendMessage(Main.PREFIX + "Du hast " + target.getDisplayName() + " §6" + coins + " Coins §7überwiesen!");
					} else {
						sender.sendMessage(Main.PREFIX + "§cDer Spieler muss online sein!");
					}
					
				} else {
					sender.sendMessage(Main.PREFIX + "§cDu hast nicht genügend coins!");
				}
				
			} else {
				sender.sendMessage(Main.PREFIX + "§e/remitcoins <player> <betrag>");
			}
			
		} else {
			sender.sendMessage(Main.PREFIX + "bro ne");
		}
			
		return true;
	}
}
