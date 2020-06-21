package de.Jan.Varus.Commands;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.Dorf;
import de.Jan.Varus.Objects.User;
import de.Jan.Varus.Objects.Völker;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class changeDorfPositionCommand implements CommandExecutor {
	private JavaPlugin plugin; 
	public changeDorfPositionCommand(JavaPlugin plugin) { 
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
					if(dorf.getOrt() == null) {
						setPosition(player);
					} else {
						changePosition(player);
					}
				} else
				if(dorf.getCoAnführer().toString().equalsIgnoreCase(user.getUuid().toString()) && checkDate(user)) {
					if(dorf.getOrt() == null) {
						setPosition(player);
					} else {
						changePosition(player);
					}
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
	public static boolean checkDate(User user) {
		Date date = new Date(user.getLastPlayed()); 
		SimpleDateFormat format = new SimpleDateFormat("DDD"); 
		int lastPlayed = Integer.parseInt(format.format(date));
		int today = Integer.parseInt(format.format(new Date(System.currentTimeMillis()))); 
		if((lastPlayed - today) >= 3) {
			return true; 
		}
		return false; 
	}
	public void changePosition(Player player) {
		TextComponent tc = new TextComponent(); 
		tc.setText("§8[§eJa, bezahlen§8]");
		tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/confirmedsetdorfposition"));
		tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Du musst §65000 Coins §7dafür bezahlen!").create()));
		
		player.sendMessage(Main.PREFIX + "Bist du dir sicher das du die Position deines Dorfes ändern möchtest? §7(Auf deine Akuelle Position)");
		player.spigot().sendMessage(tc);
		
	}
	public void setPosition(Player player) {
		TextComponent tc = new TextComponent(); 
		tc.setText("§8[§eJa§8]");
		tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/confirmedsetdorfposition"));
		tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Setze ihn Kostenlos!").create()));
		
		player.sendMessage(Main.PREFIX + "Bist du dir sicher das du die Position deines Dorfes ändern möchtest? §7(Auf deine Akuelle Position)");
		player.spigot().sendMessage(tc);
		
	}
}
