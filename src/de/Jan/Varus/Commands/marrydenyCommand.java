package de.Jan.Varus.Commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.User;

public class marrydenyCommand implements CommandExecutor {
	JavaPlugin plugin; 
	public marrydenyCommand(JavaPlugin plugin) {
		this.plugin = plugin; 
	} 
	public static HashMap<Player, Player> marryConfirm = new HashMap<>(); 
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender; 
			if(args.length == 1) {
				String userName = args[0]; 
				if(Bukkit.getPlayer(userName) != null) {
					Player target = Bukkit.getPlayer(userName); 
					if(marryCommand.marryConfirm.containsKey(target)){
						marryCommand.marryConfirm.remove(target); 
						target.sendMessage(Main.PREFIX + "§cDer Spieler hat deine Anfrage abgelehnt!");
						player.sendMessage(Main.PREFIX + "§cDu hast die Anfrage abgelehnt!");
					} else {
						sender.sendMessage(Main.PREFIX + "§eDer Spieler hat dir keine Anfrage gechickt!");
					}
				} else {
					sender.sendMessage(Main.PREFIX + "§cDer Spieler muss online sein!");
				}
			} else {
				sender.sendMessage(Main.PREFIX + "§e/marry <username>");
			}
			
		}
		return true;
	}
}
