package de.Jan.Varus.Events.SpezialItem;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import de.Jan.Varus.Main;
import de.Jan.Varus.Commands.volkCommand;

public class stabDesWindesEvent implements Listener {
	ArrayList<Player> used = new ArrayList<>(); 
	JavaPlugin plugin; 
	public stabDesWindesEvent(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer(); 
		if(event.getAction() == Action.RIGHT_CLICK_AIR) {
			if(player.getItemInHand() != null) {
				if(player.getItemInHand().getType() == getStab().getType()) {
					if(player.getItemInHand().hasItemMeta()) {
						if(player.getItemInHand().getItemMeta().hasLore()){
							if(player.getItemInHand().getItemMeta().getLore().get(0).equalsIgnoreCase(getStab().getItemMeta().getLore().get(0))){
								player.setVelocity(player.getLocation().getDirection().multiply(2).add(new Vector(0.5, 0.7, 0.5)));
								used.add(player); 
								if(player.getItemInHand().getAmount() >= 1) {
									player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
								} else {
									player.getInventory().remove(player.getItemInHand());
								}
							}
						}
					}
				}
			}
		} else
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(player.getItemInHand() != null) {
				if(player.getItemInHand().getType() == getStab().getType()) {
					if(player.getItemInHand().hasItemMeta()) {
						if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(getStab().getItemMeta().getDisplayName())){
							event.getPlayer().sendMessage(Main.PREFIX + "§cBitte nutze den Stab sinvoll!");
							event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_GHAST_WARN, 5, 5);
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void onFallDamange(EntityDamageEvent event) {
		if(event.getEntityType() == EntityType.PLAYER) {
			Player player = (Player) event.getEntity(); 
			if(event.getCause() == DamageCause.FALL) {
				if(used.contains(player)) {
					event.setCancelled(true);
					used.remove(player); 
				}
			}
		}
		
	}
	public static ItemStack getStab() {
		ItemStack stack = new ItemStack(Material.BLAZE_ROD); 
		volkCommand.ChangeMeta(stack, "Stab des Windes", "§7Der Stab schleudert dich in die Lüfte");
		return stack; 
	}
}
