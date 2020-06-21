package de.Jan.Varus.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class User {
	private UUID uuid; 
	private Völker volk;
	private Dorf dorf; 
	private boolean marryed; 
	private String marry; 
	private int coins; 
	private Location home; 
	private Long lastPlayed; 
	private Long lastUsed; 
	private String title; 
	private List<String> erfolge; 
	private JavaPlugin plugin; 
	public User(Player player, JavaPlugin plugin) {
		this.plugin = plugin; 
		plugin.reloadConfig();
		ConfigurationSection user = (ConfigurationSection) plugin.getConfig().get(player.getUniqueId().toString()); 
		this.uuid = player.getUniqueId(); 
		
		if(user != null) {
			this.volk = Völker.valueOf(user.getString("volk").toUpperCase()); 
			if(user.getString("dorf") == null) {
				this.dorf = new Dorf(volk.name().toLowerCase()); 
			} else {
				this.dorf = new Dorf(user.getString("dorf").toLowerCase()); 
			}
			this.marryed = user.getBoolean("marryed"); 
			this.marry = user.getString("marry"); 
			this.coins = user.getInt("coins"); 
			this.home = user.getLocation("home"); 
			this.lastPlayed = user.getLong("lastplayed"); 
			this.lastUsed = user.getLong("lastused"); 
			this.title = user.getString("title");
			this.erfolge = (List<String>) user.get("erfolge"); 
		} else {
			this.coins = 0; 
			this.home = Bukkit.getWorld("world").getSpawnLocation(); 
			this.marryed = false; 
			this.volk = Völker.MENSCH; 
			this.dorf = null; 
			this.lastPlayed = null; 
			this.title = null; 
			this.erfolge = new ArrayList<String>(); 
		}

	}
	public User(String uuid, JavaPlugin plugin) {
		this.plugin = plugin; 
		plugin.reloadConfig();
		this.uuid = UUID.fromString(uuid); 
		ConfigurationSection user = (ConfigurationSection) plugin.getConfig().get(uuid.toString()); 
		
		if(user != null) {
			this.volk = Völker.valueOf(user.getString("volk").toUpperCase()); 
			if(user.getString("dorf") == null) {
				this.dorf = new Dorf(volk.name().toLowerCase()); 
			} else {
				this.dorf = new Dorf(user.getString("dorf").toLowerCase()); 
			}
			this.marryed = user.getBoolean("marryed"); 
			this.marry = user.getString("marry"); 
			this.coins = user.getInt("coins"); 
			this.home = user.getLocation("home"); 
			this.lastPlayed = user.getLong("lastplayed"); 
			this.lastUsed = user.getLong("lastused"); 
			this.title = user.getString("title");
			this.erfolge = (List<String>) user.get("erfolge"); 
		} else {
			this.coins = 0; 
			this.home = Bukkit.getWorld("world").getSpawnLocation(); 
			this.marryed = false; 
			this.volk = Völker.MENSCH; 
			this.dorf = null; 
			this.lastPlayed = null; 
			this.title = null; 
			this.erfolge = new ArrayList<String>(); 
		}

	}
	public void save() {
		String uuid = this.uuid.toString(); 
		plugin.reloadConfig();
		plugin.getConfig().set(uuid  + ".volk", this.volk.toString().toLowerCase());
		if(dorf != null) {
			plugin.getConfig().set(uuid + ".dorf", this.dorf.getName());
		}
		plugin.getConfig().set(uuid  + ".marryed", this.marryed);
		plugin.getConfig().set(uuid  + ".marry", this.marry);
		plugin.getConfig().set(uuid  + ".coins", this.coins);
		plugin.getConfig().set(uuid  + ".home", this.home);
		plugin.getConfig().set(uuid  + ".lastplayed", this.lastPlayed);
		plugin.getConfig().set(uuid + ".lastused", this.lastUsed);
		plugin.getConfig().set(uuid + ".title", this.title);
		plugin.getConfig().set(uuid + ".erfolge", this.erfolge);
		plugin.saveConfig();
	}
	
	public Long getLastUsed() {
		return lastUsed;
	}
	public void setLastUsed(Long lastUsed) {
		this.lastUsed = lastUsed;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public Völker getVolk() {
		return volk;
	}
	public void setVolk(Völker volk) {
		this.volk = volk;
	}
	public boolean isMarryed() {
		return marryed;
	}
	public void setMarryed(boolean marryed) {
		this.marryed = marryed;
	}
	public String getMarry() {
		return marry;
	}
	public void setMarry(String marry) {
		this.marry = marry;
	}
	public Long getLastPlayed() {
		return lastPlayed;
	}
	public void setLastPlayed(Long lastPlayed) {
		this.lastPlayed = lastPlayed;
	}
	public int getCoins() {
		return coins;
	}
	public void setCoins(int coins) {
		this.coins = coins;
	}
	public Location getHome() {
		return home;
	}
	public void setHome(Location home) {
		this.home = home;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getErfolge() {
		return erfolge;
	}
	public void setErfolge(List<String> erfolge) {
		this.erfolge = erfolge;
	}
	public Dorf getDorf() {
		return dorf;
	}
	public void setDorf(Dorf dorf) {
		this.dorf = dorf;
	}
	
}