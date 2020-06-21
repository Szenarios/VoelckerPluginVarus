package de.Jan.Varus.Commands;

import javax.print.attribute.standard.Media;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.User;
import de.Jan.Varus.Objects.Völker;


public class chatCommand implements CommandExecutor {
	JavaPlugin plugin; 
	public chatCommand(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender; 
			User user = new User(player, plugin);
			if(user.getVolk() == Völker.MENSCH) {
				sender.sendMessage(Main.PREFIX + "§cDu musst in einem Volk sein!");
				return true; 
			}
			if(args.length >= 1) {
				String msg = ""; 
				for (String arg : args) {
					msg = msg + " "+ arg; 
				}
				boolean is = false;   
				for(Player players : Bukkit.getOnlinePlayers()) {
					User users = new User(players, plugin); 
					if(player != players) {
						if(users.getVolk() == user.getVolk()) {
							is = true; 
							players.sendMessage("§8[" + users.getVolk().getPrefix() + users.getVolk().name() + "§8] " + player.getDisplayName() + " §7| §f" +  msg);
						}
					}
				}
				if(is == false) {
					sender.sendMessage(Main.PREFIX + "§cKeiner aus deinem Volk ist online!");
					return true; 
				} else {
					player.sendMessage("§8[" + user.getVolk().getPrefix() + user.getVolk().name() + "§8] " + player.getDisplayName() + " §7| §f" +  msg);
				}
				
			} else {
				return true; 
			}
			
			
			
		}
		return true;
	}
}