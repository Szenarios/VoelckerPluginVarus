package de.Jan.Varus.Commands;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;

public class spawnCommand implements CommandExecutor {
	private JavaPlugin plugin;
	public spawnCommand(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg2,
			String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender; 			
			if(plugin.getConfig().get("spawn") != null) {
				Location loc = plugin.getConfig().getLocation("spawn"); 
				player.teleport(loc); 
				player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 5, 5);
				player.sendMessage(Main.PREFIX + "Du wurdest Teleportiert!");
			} else if(player.isOp()) {
				Location loc = player.getLocation(); 
				plugin.getConfig().set("spawn", loc);
				plugin.saveConfig();
				player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 5, 5);
				player.sendMessage(Main.PREFIX + "Du hast den Spawn Punkt gesetzt!");
			}
		}
		return true;
	}
}
