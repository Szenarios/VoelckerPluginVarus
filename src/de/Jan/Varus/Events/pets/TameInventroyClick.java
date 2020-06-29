package de.Jan.Varus.Events.pets;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.Pet;
import de.Jan.Varus.Objects.Tamer;
import de.Jan.Varus.Objects.User;

public class TameInventroyClick implements Listener {
	JavaPlugin plugin; 
	public TameInventroyClick(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	@EventHandler
	public void onÍnvClick(InventoryClickEvent event) {
		try {
			if(UUID.fromString(event.getView().getTitle()) != null) {
				Entity entity = Bukkit.getEntity(UUID.fromString(event.getView().getTitle())); 
				if (event.getCurrentItem() != null) {
					if (event.getCurrentItem().getType() == Material.LEAD) {
						event.setCancelled(true);
						Player player = (Player) event.getWhoClicked();
						User user = new User((Player) event.getWhoClicked(), plugin);
						Tamer tamer = new Tamer((Player) event.getWhoClicked());
						if (tamer.getMaxTames() > tamer.getTames()) {
							int neededCoins = 0;
							switch (entity.getType()) {
							case FOX:
								neededCoins = 1500;
								break;
							case PANDA:
								neededCoins = 5000;
								break;
							case ENDERMITE:
								neededCoins = 3000;
								break;
							case MUSHROOM_COW:
								neededCoins = 3000;
								break;
							case BEE:
								neededCoins = 2500;
								break;
							case BAT:
								neededCoins = 1000;
								break;
							case POLAR_BEAR:
								neededCoins = 5000;
								break;
							case SILVERFISH:
								neededCoins = 1500;
								break;
							default:
								neededCoins = 0;
								break;
							}
							if (neededCoins <= user.getCoins()) {
								user.setCoins(user.getCoins() - neededCoins);
								checkTame(entity, player);
								tamer.setTames(tamer.getTames() + 1);
								List<String> ids = tamer.getIds();
								ids.add(entity.getUniqueId().toString());
								tamer.setIds(ids);
								Pet pet = new Pet(entity);
								pet.setOwner(player.getUniqueId().toString());
								pet.setTameDate(new Date(System.currentTimeMillis()));
								pet.setFollow(true);
								pet.setName(player.getName() + "`s Haustier");
								
								entity.setCustomName(pet.getName());
								entity.setCustomNameVisible(true);
								entity.setInvulnerable(true);
								Creature creature = (Creature) entity; 
								creature.setRemoveWhenFarAway(false);
								
								user.save();
								pet.save();
								tamer.save();
								player.closeInventory();
								player.sendMessage(Main.PREFIX + "§eDu hast Erfolgreich dein Tier gezähmt!");
								
							} else {
								player.closeInventory();
								player.sendMessage(Main.PREFIX + "§eDu hast nicht genügend Coins um §c" + entity.getType()
								+ " §ezu Zähmen!");
							}
							
						} else {
							player.closeInventory();
							player.sendMessage(Main.PREFIX + "§cDu hast bereits zu viele Haustiere!");
						}
						
					}
				}
				
			}
		} catch (Exception e) {
			System.out.println("NotTargetInv");
		}
	}

	public static Inventory getInventory(Entity entity) {
		Inventory inv = Bukkit.createInventory(null, 27, entity.getUniqueId().toString());
		ItemStack stack = new ItemStack(Material.LEAD);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§eZähme §c" + entity.getType());
		List<String> list = new ArrayList<String>();
		switch (entity.getType()) {
		case FOX:
			list.add("§eZähme es für §c1.500");
			break;
		case PANDA:
			list.add("§eZähme es für §c5.000");
			break;
		case ENDERMITE:
			list.add("§eZähme es für §c3.000");
			break;
		case MUSHROOM_COW:
			list.add("§eZähme es für §c3.000");
			break;
		case BEE:
			list.add("§eZähme es für §c2.500");
			break;
		case BAT:
			list.add("§eZähme es für §c1.000");
			break;
		case POLAR_BEAR:
			list.add("§eZähme es für §c5.000");
			break;
		case SILVERFISH:
			list.add("§eZähme es für §c1.500");
			break;
		default:
			list.add("§eZähme es dir Kostenlos!");
			break;
		}
		meta.setLore(list);
		stack.setItemMeta(meta);
		inv.setItem(13, stack);
		return inv;
	}

	public static void checkTame(Entity entity, Player player) {
		if (entity.getType() == EntityType.OCELOT) {
			Wolf wolf = (Wolf) entity;
			wolf.setTamed(true);
			wolf.setOwner(player);
		} else if (entity.getType() == EntityType.WOLF) {
			Cat cat = (Cat) entity;
			cat.setTamed(true);
			cat.setOwner(player);
		} else if(entity.getType() == EntityType.BEE) {
			Bee bee = (Bee) entity; 
			bee.setAgeLock(true);
			bee.setCannotEnterHiveTicks(Integer.MAX_VALUE);
		}
	}
}
