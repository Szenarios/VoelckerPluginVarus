package de.Jan.Varus.Events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.connorlinfoot.titleapi.TitleAPI;

import de.Jan.Varus.Objects.User;

public class JoinEvent implements Listener {
	public static Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();  
	private JavaPlugin plugin;  
	public JoinEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void onJoinEvent(PlayerJoinEvent event) {
		Player player = event.getPlayer(); 	
		if(!player.hasPlayedBefore()) { 
			TitleAPI.sendTitle(player, 10, 25, 8, "§eWillkommen auf einem §cVarus-Server!", "§aZuerst müssen wir dir einiges erklären!");
			player.teleport(new Location(Bukkit.getWorld("world"), 112, 75, -12));
			
		}
		if(Bukkit.getServer().hasWhitelist()) {
			if(!event.getPlayer().hasPermission("varus.teams")) {
				player.kickPlayer("§cBitte warte einen Moment! Momentan dürfen nur Berechtigte leute auf diesen Server!");
			}
		}
		User user = new User(player, plugin); 
		setPrefix(player, plugin);
		TitleAPI.sendTabTitle(event.getPlayer(), "§7--= §9Varus Präsentiert §7=-- \n§cVölker und Dörfer §d2!", 
				"§7Deine Coins§8: §6" + user.getCoins() + "\n" +
				"§7Dein Zuhause§8: §f" + (int)user.getHome().getX() + " §7|§f " + (int)user.getHome().getY() + " §7|§f " + (int)user.getHome().getZ() + "\n" + 
				(user.isMarryed() ? "§6Dein Ehepartner§8: §c" + Bukkit.getOfflinePlayer(UUID.fromString(user.getMarry())).getName()+ "\n": "") + 
		"\n§7--= §8Ränge §7=-- \n§9Administrator §1Senior Moderator §3Entwickler\n §bModerator §aBauarbeiter\n §eÄltester §dVIP");

		event.setJoinMessage("§8[§a+§8] " + event.getPlayer().getDisplayName()); 
	}
	public static void setPrefix(Player player, JavaPlugin plugin) {
		User user = new User(player, plugin); 
		Team team = null; 
		if (scoreboard.getTeam(player.getName()) != null) {
			team = scoreboard.getTeam(player.getName());
		} else {
			team = scoreboard.registerNewTeam(player.getName());
		}
		
		if (player.hasPermission("varus.admin")) {
			team.setColor(ChatColor.BLUE);
		} else 
		if(player.hasPermission("varus.srmod")) {
			team.setColor(ChatColor.DARK_BLUE);
		} else 			
		if (player.hasPermission("varus.dev")) {
			team.setColor(ChatColor.DARK_AQUA);
		} else 
		if (player.hasPermission("varus.mod")) {
			team.setColor(ChatColor.AQUA);
		} else 
		if(player.hasPermission("varus.ältester")) {
			team.setColor(ChatColor.YELLOW);
		} else 
		if(player.hasPermission("varus.vip")) {
			team.setColor(ChatColor.LIGHT_PURPLE);
		} else 
		if(player.hasPermission("varus.verifiziert")) {
			team.setColor(ChatColor.DARK_AQUA);
		}
		else {
			team.setColor(ChatColor.GRAY);
		}

		player.setDisplayName(user.getVolk().getPrefix() + (user.getTitle() != null ? user.getTitle() : user.getVolk().toString()) + " §7| " + returenRankPrefix(player) + player.getName() + (user.isMarryed() ? " §8[" + user.getVolk().getPrefix() + "M§8]" : ""));

		team.addPlayer(player);
		if(user.getTitle() == null) {
			team.setPrefix(user.getVolk().getPrefix() + user.getVolk().toString() + " §7| ");
		} else {
			team.setPrefix(user.getVolk().getPrefix() + user.getTitle() + " §7| ");
			team.setSuffix(" §7(" + user.getVolk().getPrefix() + user.getVolk().name() + "§7)");
		}
		player.setScoreboard(scoreboard);
		for(Player players : Bukkit.getOnlinePlayers()) {
			players.setScoreboard(scoreboard);
		}
	}
	
	private static String returenRankPrefix(Player player) {
		if (player.hasPermission("varus.admin")) {
			return "§9"; 
		} else 
		if(player.hasPermission("varus.srmod")) {
			return "§1"; 
		} else 			
		if (player.hasPermission("varus.dev")) {
			return "§3"; 
		} else 
		if (player.hasPermission("varus.mod")) {
			return "§b"; 
		} else 
		if(player.hasPermission("varus.ältester")) {
			return "§e"; 
		} else 
		if(player.hasPermission("varus.vip")) {
			return "§d"; 
		} else 
		if(player.hasPermission("varus.verifiziert")) {
			return "§5"; 
		}
		else {
			return "§7"; 
		}

	}
	
}
