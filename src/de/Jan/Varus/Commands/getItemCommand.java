package de.Jan.Varus.Commands;

import org.apache.commons.lang.Validate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Events.Effekte.FeeEffektEvent;
import de.Jan.Varus.Events.Effekte.KoboldEffektEvent;
import de.Jan.Varus.Events.Effekte.VampirEffektEvent;
import de.Jan.Varus.Events.Effekte.WikingerEffektEvent;
import de.Jan.Varus.Events.SpezialItem.HolzfällerAxtEvent;
import de.Jan.Varus.Events.SpezialItem.MegaPickeEvent;
import de.Jan.Varus.Events.SpezialItem.SchwerDesHänkers;
import de.Jan.Varus.Events.SpezialItem.axtDesDonnersEvent;
import de.Jan.Varus.Events.SpezialItem.itemKeeperEvent;
import de.Jan.Varus.Events.SpezialItem.stabDesWindesEvent;
import de.Jan.Varus.Objects.User;

public class getItemCommand implements CommandExecutor {
	private static JavaPlugin plugin; 
	public getItemCommand(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender; 
			
			User user = new User(player, plugin); 
			switch (user.getVolk()) {
			case FEE:
				player.getInventory().addItem(FeeEffektEvent.getItem(player)); 				
				break;
			case KOBOLD: 
				player.getInventory().addItem(KoboldEffektEvent.getItem(player)); 	
				break; 
			case WIKINGER:
				player.getInventory().addItem(WikingerEffektEvent.getItem(player)); 	
				break;
			case VAMPIR:
				player.getInventory().addItem(VampirEffektEvent.getItem(player)); 	
				break; 
			default:
				break;
			}
			player.sendMessage(Main.PREFIX + "Bitteschön!");
		}
		return true;
	}
}
