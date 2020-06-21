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
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Commands.volkCommand;
import de.Jan.Varus.Objects.User;
import de.Jan.Varus.Objects.Völker;

public class WikingerEffektEvent implements Listener {
	JavaPlugin plugin;
	List<Player> denied = new ArrayList<>(); 
	public WikingerEffektEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onUseTools(PlayerItemDamageEvent event) {
		Player player = event.getPlayer(); 
		User user = new User(player, plugin); 
		if(user.getVolk() == Völker.WIKINGER) {
			int rdm = Main.returnRDM(3, 1);
			if(rdm == 1) {
				event.getItem().setDurability((short) (event.getItem().getDurability() -1));
			} 
		}
	}
//	@EventHandler
//	public void onWater(PlayerMoveEvent event) {
//		if(event.getPlayer().isSwimming()) {
//			Player player = event.getPlayer(); 
//			User user = new User(event.getPlayer(), plugin); 
//			if(user.getVolk() == Völker.WIKINGER) {
//				System.out.println(player.getRemainingAir());
//				player.setRemainingAir(player.getRemainingAir() +1);
//			}
//		}
//	}
	@EventHandler
	public void onExp (PlayerExpChangeEvent event) {
		int amount = event.getAmount(); 
		Player player = event.getPlayer(); 
		User user = new User(player, plugin);
		if(user.getVolk() == Völker.WIKINGER) {
			double half = ((double)amount / 100) * 30; 
			event.setAmount(amount - (int)half);
		}
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer(); 
		User user = new User(player, plugin);
		if(user.getVolk() == Völker.WIKINGER) {
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
					if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Starb des Starken")) {
						if(event.getItem().getItemMeta().getLore().get(1).toLowerCase().contains(event.getPlayer().getName().toLowerCase())) {
							User user = new User(event.getPlayer(), plugin); 
							Player player = event.getPlayer(); 
							if(user.getVolk() == Völker.WIKINGER) {
								// COPY 
								Long now = System.currentTimeMillis(); 
								Long lastused = user.getLastUsed(); 
								if(lastused != null) {
									Date date = new Date(lastused); 
									date.setMinutes(date.getMinutes() +7);
									if(date.getTime() > now) {
										Date nowDate = new Date(now); 
										player.sendMessage(Main.PREFIX + "§cDu bist noch nicht bereit Stark zu werden! §eum " + new SimpleDateFormat("HH:mm:ss").format(date));
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
										players.playSound(players.getLocation(), Sound.ENTITY_ENDER_DRAGON_AMBIENT, 5, 5);
									}
								}								
								event.setCancelled(true);
								WikingerStärkeEvent.list.add(player); 								
								
								Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
									@Override
									public void run() {
										WikingerStärkeEvent.list.remove(player); 
										player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_DEATH, 5, 5);
										player.sendMessage(Main.PREFIX + "Deine Stärke ist vergangen!");
									}
								}, 20*7);
							} else {
								event.getPlayer().getInventory().remove(event.getItem());
								event.getPlayer().sendMessage(Main.PREFIX + "§cDu bist kein &6Wikinger §cmehr und kannst diese Fähigkeit nicht mehr nutzen! §7(Es wurde gelöscht)"); 	
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
		ItemStack drachenAtem = new ItemStack(Material.BLAZE_ROD); 
		
		volkCommand.ChangeMeta(drachenAtem, "§6Starb des Starken", "§7§oGibt dir §e7sek §7§oStärke", "§7§o" + player.getName());
		return drachenAtem; 
	}
}
