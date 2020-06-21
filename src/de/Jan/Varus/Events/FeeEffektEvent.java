package de.Jan.Varus.Events;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.Jan.Varus.Main;
import de.Jan.Varus.Commands.volkCommand;
import de.Jan.Varus.Objects.User;
import de.Jan.Varus.Objects.Völker;


public class FeeEffektEvent implements Listener {
	JavaPlugin plugin; 
	public FeeEffektEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void onExp (PlayerExpChangeEvent event) {
		int amount = event.getAmount(); 
		Player player = event.getPlayer(); 
		User user = new User(player, plugin);
		if(user.getVolk() == Völker.FEE) {
			double half = ((double)amount / 100) * 70; 
			event.setAmount(amount + (int)half);
		}
	}
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity(); 
			User user = new User(player, plugin); 
			if(user.getVolk() == Völker.FEE) {
				double dmg = event.getDamage(); 
				double half = (dmg / 100) * 30;
				event.setDamage(dmg + half);
			}
		}
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer(); 
		User user = new User(player, plugin);
		if(user.getVolk() == Völker.FEE) {
			if(!player.hasPlayedBefore()) {
				player.getInventory().addItem(getItem(player)); 
			}
		}
	}
	@EventHandler 
	public void onRightClick(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getItem() != null) {
				if(event.getItem().getItemMeta().hasDisplayName()) {
					if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§dFlügel des Himmels")) {
						if(event.getItem().getItemMeta().getLore().get(1).toLowerCase().contains(event.getPlayer().getName().toLowerCase())) {
							User user = new User(event.getPlayer(), plugin); 
							
							Player player = event.getPlayer(); 
							if(user.getVolk() == Völker.FEE) {
								// COPY 
								Long now = System.currentTimeMillis(); 
								Long lastused = user.getLastUsed(); 
								if(lastused != null) {
									Date date = new Date(lastused); 
									date.setMinutes(date.getMinutes() +7);
									if(date.getTime() > now) {
										player.sendMessage(Main.PREFIX + "§cDu bist noch nicht bereit zum Fliegen! §eum " + new SimpleDateFormat("HH:mm:ss").format(date));
										player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 5, 5);
										return; 
									}
								}
								user.setLastUsed(now);
								user.save();
								// COOY
								for(Entity entity : player.getWorld().getNearbyEntities(player.getLocation(), 10, 10, 10)) {
									if(entity instanceof Player) {
										Player players = (Player) entity; 
										players.playSound(players.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 5, 5);
									}
								}
								event.getPlayer().setAllowFlight(true);
								event.getPlayer().setFlying(true);
								event.setCancelled(true);
			
								Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
									@Override
									public void run() {
										event.getPlayer().setFlying(false);
										event.getPlayer().setAllowFlight(false);
										int time = getBlock(player) / 4;
										System.out.println(time * 4);
										time = time * 20; 
										player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, time , 1));
									}
								}, 20*7);
							} else {
								event.getPlayer().getInventory().remove(event.getItem());
								event.getPlayer().sendMessage(Main.PREFIX + "§cDu bist keine §dFee §cmehr und kannst diese Fähigkeit nicht mehr nutzen! §7(Es wurde gelöscht)"); 	
							}
						} else {
							event.getPlayer().getInventory().remove(event.getItem());
							event.getPlayer().sendMessage(Main.PREFIX + "§cDu bist nicht der Besitzer dieses Items! §7(Es wurde gelöscht)"); 
						}
					}
				}
			}
		}
	}
	public static ItemStack getItem(Player player) {
		ItemStack elytra = new ItemStack(Material.NETHER_STAR); 
		
		volkCommand.ChangeMeta(elytra, "§dFlügel des Himmels", "§7§oGibt dir §e7sek §7§oFlymode", "§7§o" + player.getName());
		return elytra; 
	}
	public int getBlock(Player player) {
		int zahl = 0; 
		Location loc = player.getLocation(); 
		for (int i = (int) loc.getY(); i > 0; i--) {
			if(loc.getWorld().getBlockAt((int)loc.getX(), i, (int)loc.getZ()).getType() != Material.AIR) {
				return i; 
			}
		}
		return zahl; 
	}
}
