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

public class marryacceptCommand implements CommandExecutor {
	JavaPlugin plugin; 
	public marryacceptCommand(JavaPlugin plugin) {
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
					User user = new User(player, plugin); 
					Player target = Bukkit.getPlayer(userName); 
					User targetuser = new User(target, plugin); 
					if(player.getName().equalsIgnoreCase(target.getName())) {
						sender.sendMessage(Main.PREFIX + "§cDu kannst dich leider nicht selbst heiraten!");
						return true; 
					}
					if(marryCommand.marryConfirm.containsKey(target)){
						marryCommand.marryConfirm.remove(target); 
						user.setMarry(target.getUniqueId().toString());
						user.setMarryed(true);
						targetuser.setMarry(user.getUuid().toString());
						targetuser.setMarryed(true);
						user.save();
						targetuser.save();
						Bukkit.broadcastMessage(Main.PREFIX + "§dHerzlichen Glückwunsch! §e" + player.getDisplayName() + " §eund " + target.getDisplayName() + " §esind nun verheiratet!");  
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
