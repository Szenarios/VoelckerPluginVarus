package de.Jan.Varus.Commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.User;

public class homeCommand implements CommandExecutor {
	JavaPlugin plugin; 
	public homeCommand(JavaPlugin plugin) { 
		this.plugin = plugin; 
	}
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (sender instanceof Player) {
			Player player = (Player) sender; 
			User user =new User(player, plugin); 
			if(user.getHome() != null) { 
				player.teleport(user.getHome()); 
				player.sendMessage(Main.PREFIX + "§aDu wurdest zu deinem Home Teleportiert!");
				player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 5, 5);
			} else {
				player.sendMessage(Main.PREFIX + "Du hast leider keinen homepunkt gesetzt! "); 
			}
		} else {
			sender.sendMessage(Main.PREFIX + "Du musst ein Spieler sein!");
		}
		return true;
	}
}