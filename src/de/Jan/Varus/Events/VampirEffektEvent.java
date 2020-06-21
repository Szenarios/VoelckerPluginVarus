package de.Jan.Varus.Events;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import de.Jan.Varus.Main;
import de.Jan.Varus.Commands.volkCommand;
import de.Jan.Varus.Objects.User;
import de.Jan.Varus.Objects.Völker;

public class VampirEffektEvent implements Listener {
	JavaPlugin plugin; 
	public static List<Player> akitv = new ArrayList<>(); 
	public VampirEffektEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void onDamgeEvent(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity(); 
			User user = new User(player, plugin); 
			if(user.getVolk() == Völker.VAMPIR) {
				if(pingEvent.isDay()) {
					double dmg = event.getDamage(); 
					double half = (dmg / 100) * 30;
					event.setDamage(dmg + half);
					System.out.println("damaged");
				}
			}
		}
	}
	@EventHandler
	public void onAttakOther(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager(); 
			User user = new User(player, plugin); 
			if(user.getVolk() == Völker.VAMPIR) {
				if(!pingEvent.isDay()) {
					double dmg = event.getDamage(); 
					double half = (dmg / 100) * 70;
					event.setDamage(dmg + half);
					System.out.println("Attack");
				}
			}
		}
	}
	@EventHandler 
	public void onRightClick(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getItem() != null) {
				if(event.getItem().getItemMeta().hasDisplayName()) {
					if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§0Knochen der Schnelligkeit")) {
						if(event.getItem().getItemMeta().getLore().get(1).toLowerCase().contains(event.getPlayer().getName().toLowerCase())) {
							User user = new User(event.getPlayer(), plugin); 
							
							Player player = event.getPlayer(); 
							if(user.getVolk() == Völker.VAMPIR) {
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
								this.akitv.add(player); 
								player.setWalkSpeed(0.5F);
								Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
									@Override
									public void run() {
										player.setWalkSpeed(0.2F);
										akitv.remove(player);
									}
								}, 20*7);
							} else {
								event.getPlayer().getInventory().remove(event.getItem());
								event.getPlayer().sendMessage(Main.PREFIX + "§cDu bist kein §0Vampir §cmehr und kannst diese Fähigkeit nicht mehr nutzen! §7(Es wurde gelöscht)"); 	
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
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if(VampirEffektEvent.akitv.contains(event.getPlayer())) {
			Player player = event.getPlayer(); 
			for(Player target : Bukkit.getOnlinePlayers()) {
				if(player.getLocation().distance(target.getLocation()) <= 2) {
					if(player != target) {
						kickPlayer(target, player);
					}
				}
			}
			
		}
	}
	public void kickPlayer(Player target, Player player) {
		double x = player.getLocation().getX() - target.getLocation().getX(); 
		double y = player.getLocation().getY() - target.getLocation().getY(); 
		double z = player.getLocation().getZ() - target.getLocation().getZ(); 
		
		Vector vector = new Vector(x, y, z).normalize().multiply(2D).setY(0.3D); 
		if(target != null) {
			target.setVelocity(vector);
		}
	}
	public static ItemStack getItem(Player player) {
		ItemStack elytra = new ItemStack(Material.BONE); 
		
		volkCommand.ChangeMeta(elytra, "§0Knochen der Schnelligkeit", "§7§oGibt dir §e7sek §7§oeinen Speedboost", "§7§o" + player.getName());
		return elytra; 
	}
	
}
