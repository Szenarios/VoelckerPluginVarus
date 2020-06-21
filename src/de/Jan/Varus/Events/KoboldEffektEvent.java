package de.Jan.Varus.Events;

import java.text.SimpleDateFormat;
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
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Commands.volkCommand;
import de.Jan.Varus.Objects.User;
import de.Jan.Varus.Objects.Völker;


public class KoboldEffektEvent implements Listener {
	private JavaPlugin plugin; 
	public KoboldEffektEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void onDrop(EntityDeathEvent event) {
		Player player = event.getEntity().getKiller(); 
		if(event.getEntity() instanceof Player) {
			System.out.println("Player");
			return; 
		}
		if (player != null) {
			User user = new User(player, plugin);
			List<ItemStack> list = event.getDrops();
			if (user.getVolk() == Völker.KOBOLD) {
				if (list.size() != 0) {
					int rdm = Main.returnRDM(list.size() - 1, 0);
					ItemStack stack = list.get(rdm);
					stack.setAmount(stack.getAmount() + Main.returnRDM(3, 1));
					System.out.println("Test");
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onUseTools(PlayerItemDamageEvent event) {
		Player player = event.getPlayer(); 
		User user = new User(player, plugin); 
		if(user.getVolk() == Völker.KOBOLD) {
			int rdm = Main.returnRDM(3, 1);
			if(rdm == 1) {
				event.getItem().setDurability((short) (event.getItem().getDurability() +1));
			} 
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer(); 
		User user = new User(player, plugin);
		if(user.getVolk() == Völker.KOBOLD) {
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
					if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Trank der Totalen Unsichtbarkeit")) {
						if(event.getItem().getItemMeta().getLore().get(1).toLowerCase().contains(event.getPlayer().getName().toLowerCase())) {
							User user = new User(event.getPlayer(), plugin); 
							Player player = event.getPlayer(); 
							if(user.getVolk() == Völker.KOBOLD) {
								// COPY 
								Long now = System.currentTimeMillis(); 
								Long lastused = user.getLastUsed(); 
								if(lastused != null) {
									Date date = new Date(lastused); 
									date.setMinutes(date.getMinutes() +7);
									if(date.getTime() > now) {
										Date nowDate = new Date(now); 
										player.sendMessage(Main.PREFIX + "§cDu bist noch nicht bereit Unsichtbar zu werden! §eum " + new SimpleDateFormat("HH:mm:ss").format(date));
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
										players.playSound(players.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 5, 5);
									}
								}
								
								for(Player players : Bukkit.getOnlinePlayers()) {
									players.hidePlayer(player);
								}
								KoboldUnsichtbarkeitEvent.aktivPlayer.add(event.getPlayer());
								event.setCancelled(true);
			
								
								
								Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
									@Override
									public void run() {
										for(Player players : Bukkit.getOnlinePlayers()) {
											players.showPlayer(player);
										}
										KoboldUnsichtbarkeitEvent.aktivPlayer.remove(event.getPlayer());
										player.playSound(player.getLocation(), Sound.ENTITY_SPLASH_POTION_THROW, 5, 5);
										player.closeInventory();
										player.sendMessage(Main.PREFIX + "§cDu bist wieder sichtbar!");
									}
								}, 20*7);
							} else {
								event.getPlayer().getInventory().remove(event.getItem());
								event.getPlayer().sendMessage(Main.PREFIX + "§cDu bist kein §2Kobold §cmehr und kannst diese Fähigkeit nicht mehr nutzen! §7(Es wurde gelöscht)"); 	
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
		ItemStack drachenAtem = new ItemStack(Material.DRAGON_BREATH); 
		
		volkCommand.ChangeMeta(drachenAtem, "§2Trank der Totalen Unsichtbarkeit", "§7§oGibt dir §e7sek §7§oUnsichtbarkeit", "§7§o" + player.getName());
		return drachenAtem; 
	}
	
}