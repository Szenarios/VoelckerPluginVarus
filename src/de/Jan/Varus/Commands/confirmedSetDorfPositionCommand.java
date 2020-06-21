package de.Jan.Varus.Commands;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.Dorf;
import de.Jan.Varus.Objects.User;
import de.Jan.Varus.Objects.Völker;

public class confirmedSetDorfPositionCommand implements CommandExecutor {
	private JavaPlugin plugin; 
	public confirmedSetDorfPositionCommand(JavaPlugin plugin) { 
		this.plugin = plugin; 
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] args) {
		if(sender instanceof Player) { 
			Player player = (Player) sender;
			User user = new User(player, plugin);
			if(user.getVolk() != Völker.MENSCH) {
				Dorf dorf = user.getDorf(); 
				if(dorf.getAnführer().toString().equalsIgnoreCase(user.getUuid().toString())) {
					ChangePosition(player, user, dorf);
				} else
				if(dorf.getCoAnführer().toString().equalsIgnoreCase(user.getUuid().toString()) && changeDorfPositionCommand.checkDate(user)) {
					ChangePosition(player, user, dorf);
				} else {
					player.sendMessage(Main.PREFIX + "§cDu musst Anführer der Stadt sein um diesen Command ausführen zu können!");
				}
			} else {
				player.sendMessage(Main.PREFIX + "§cAls Mensch kannst du keine Position setzen§7, §edu wohnst überall§c!");
			}
		} else {
			sender.sendMessage(Main.PREFIX + "Du kannst diesen Command nicht ausführen du musst ein spieler sein!");
		}
		return true;
	}
	public void ChangePosition(Player player, User user, Dorf dorf) { 
		if (dorf.getOrt() == null) {
			setPosition(player, user, dorf);
		} else {
			if(user.getCoins() >= 5000) {
				Location loc = player.getLocation(); 
				dorf.setOrt(loc);
				dorf.save();
				user.setCoins(user.getCoins()-5000);
				user.save();
				
				player.playSound(loc, Sound.ITEM_ARMOR_EQUIP_GOLD, 5, 5);
				player.sendMessage(Main.PREFIX + "Du hast §65000 Coins §7bezahlt um die Position deines Dorfes umzusetzten!");
			} else {
				player.sendMessage(Main.PREFIX + "§cDu hast leider nicht genügend Coins!");
			}
		}
	}
	public void setPosition(Player player, User user, Dorf dorf) { 
			Location loc = player.getLocation(); 
			dorf.setOrt(loc);
			dorf.save();
			
			Main.CheckingVillager();
			player.playSound(loc, Sound.ITEM_ARMOR_EQUIP_GOLD, 5, 5);
			player.sendMessage(Main.PREFIX + "Die Position deines Dorfes ist nun gestzt!");
	}
}
