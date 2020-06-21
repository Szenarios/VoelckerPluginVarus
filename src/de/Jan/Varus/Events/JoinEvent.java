package de.Jan.Varus.Events;

import java.sql.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.connorlinfoot.titleapi.TitleAPI;

import de.Jan.Varus.Commands.getItemCommand;
import de.Jan.Varus.Objects.User;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerInfo.PlayerInfoData;

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
		event.getPlayer().setWalkSpeed(0.2F);
		TitleAPI.sendTabTitle(event.getPlayer(), "§7--= §9Varus Präsentiert §7=-- \n§cVölker und Dörfer!", 
				"§7Dein Volk§8: " + user.getVolk().getPrefix() + user.getVolk().toString().toLowerCase() + "\n"+
				"§7Deine Coins§8: §6" + user.getCoins() + "\n" +
				"§7Dein Zuhause§8: §f" + (int)user.getHome().getX() + " §7|§f " + (int)user.getHome().getY() + " §7|§f " + (int)user.getHome().getZ() + "\n" + 
				(user.isMarryed() ? "§6Dein Ehepartner§8: §c" + Bukkit.getOfflinePlayer(UUID.fromString(user.getMarry())).getName()+ "\n": "")); 

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
			team.setSuffix(" §7(§cAdmin§7)");
		} else 
		if (player.hasPermission("varus.dev")) {
			team.setSuffix(" §7(§bDev§7)");
		} else 
		if (player.hasPermission("varus.mod")) {
			team.setSuffix(" §7(§eMod§7)");
		}
		team.addPlayer(player);
		if(user.getTitle() == null) {
			team.setPrefix(user.getVolk().getPrefix() + user.getVolk().toString() + " §7| ");
			player.setDisplayName(user.getVolk().getPrefix() + user.getVolk().toString() + " §7| " + user.getVolk().getPrefix() + player.getName() + (user.isMarryed() ? " §8["+user.getVolk().getPrefix()+"M§8]" : ""));
		} else {
			team.setPrefix(user.getVolk().getPrefix() + user.getTitle() + " §7| ");
			player.setDisplayName(user.getVolk().getPrefix() + user.getTitle() + " §7| " + user.getVolk().getPrefix() + player.getName() + (user.isMarryed() ? " §8[" + user.getVolk().getPrefix() + "M§8]" : ""));
		}
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			players.setScoreboard(scoreboard);
		}
	}
}
