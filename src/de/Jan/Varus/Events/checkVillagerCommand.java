package de.Jan.Varus.Events;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.Jan.Varus.Main;

public class checkVillagerCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof PlayerDieEvent) {
			Player player = (Player) sender; 
			if(player.isOp()) {
				Main.CheckingVillager();
				sender.sendMessage(Main.PREFIX + "§aDone!");
			} else {
				sender.sendMessage(Main.PREFIX + "§cDu hast keine Rechnte!");
			}
		}
		return true;
	}	
}