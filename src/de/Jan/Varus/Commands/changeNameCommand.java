package de.Jan.Varus.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.Jan.Varus.Main;
import de.Jan.Varus.Events.pets.OwnerInventroyClick;
import de.Jan.Varus.Objects.Pet;

public class changeNameCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] args) {
		Player player = (Player) sender; 
		if(OwnerInventroyClick.namechanges.containsKey(player)) {
			if(args.length == 1) {
				Entity entity = OwnerInventroyClick.namechanges.get(player);
				Pet pet = new Pet(entity);
				
				String name = args[0]; 
				name = name.replace("&", "§"); 
				pet.setName(name);
				entity.setCustomName(name);
				pet.save();
				OwnerInventroyClick.namechanges.remove(player); 
				
			} else {
				sender.sendMessage(Main.PREFIX + "§eBitte nutze den Command so: §c/changename <Name>");
			}
			
		} else {
			sender.sendMessage(Main.PREFIX + "§cDu musst vorher auf dem Amboss in deinen einstellungs Menu Clicken!");
		}
		return true;
	}

}
