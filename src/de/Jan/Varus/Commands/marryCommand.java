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
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class marryCommand implements CommandExecutor {
	JavaPlugin plugin; 
	public marryCommand(JavaPlugin plugin) {
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
					if (!user.isMarryed()) {
						Player target = Bukkit.getPlayer(userName); 
						User targetuser = new User(target, plugin); 
						if(player.getName().equalsIgnoreCase(target.getName())) {
							sender.sendMessage(Main.PREFIX + "§cDu kannst dich leider nicht selbst heiraten!");
							return true; 
						}
						if(!targetuser.isMarryed()) {
							marryConfirm.put(player, target); 
							player.sendMessage(Main.PREFIX + "§aDu hast den Spieler " + target.getDisplayName() + " §agebeten dich zu heiraten!");
							target.sendMessage(Main.PREFIX + "§aDer Spieler " + player.getDisplayName()+ " §a möchte dich Heiraten!");
							TextComponent ja = new TextComponent(); 
							TextComponent nein = new TextComponent(); 
							ja.setText("§7[§aJa, ich will§7] ");
							ja.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/marryaccept " + player.getName()));
							ja.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aHeirate " + player.getDisplayName() + " §aund werdet Glücklich zusammen!").create()));
							
							nein.setText(" §7[§cNe danke§7]");
							nein.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/marrydeny " + player.getName()));
							nein.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cHalte weiter ausschau!").create()));
							target.spigot().sendMessage(ja, nein);
							
						} else {
							sender.sendMessage(Main.PREFIX + "§cDer Spieler ist schon verheiratet!");
						}
					} else {
						sender.sendMessage(Main.PREFIX + "§cDu bist schon verheiratet, du schlingel du!");
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