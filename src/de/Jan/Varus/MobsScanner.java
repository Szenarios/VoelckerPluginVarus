package de.Jan.Varus;


import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Objects.Dorf;
import net.md_5.bungee.api.chat.ClickEvent;

public class MobsScanner {
	int math = 2; 
	public JavaPlugin plugin; 
	public MobsScanner(JavaPlugin plugin) {
		this.plugin = plugin; 
	}
	public static int shedular =0; 
	@SuppressWarnings("deprecation")
	public void go() {
		shedular = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
					for(String key : Main.dorf.getKeys(false)) {
						Dorf dorf = new Dorf(key); 
						System.out.println(key);
						int mobs = 0; 
						if(dorf.getOrt() != null) {
							for(Entity entity : dorf.getOrt().getWorld().getEntities()) {
								if(entity.getCustomName() != null) {
									if(entity.getCustomName().toLowerCase().contains(dorf.getName())) {
										if(mobs > 10) {
											entity.remove();
										} else {
											mobs++; 
											CheckPosition(dorf, (Creature) entity);
										}
									}
								}
							}
							if(mobs < (dorf.getLevel()*10)) {
								if(dorf.isFee() || dorf.isKobold() || dorf.isMenschen() | dorf.isKobold() || dorf.isVampir() || dorf.isZwerg()) {
									SpawnMob(dorf, (dorf.getLevel()*10)-mobs);
								}
							}
						}
					}
					
					callMessage();
			}
		}, 20*10, 20*60*5); 
		
	}
	public void CheckPosition(Dorf dorf, Creature creature) {
		Location loc = dorf.getOrt(); 
		int enfernung = 16; 
		if(loc.getX() - (dorf.getLevel()*enfernung) >= creature.getLocation().getX() || loc.getX() + (dorf.getLevel()*enfernung) <= creature.getLocation().getX()) {
			creature.teleport(loc); 
		}
		if(loc.getZ() - (dorf.getLevel()*enfernung) >= creature.getLocation().getZ() || loc.getZ() + (dorf.getLevel()*enfernung) <= creature.getLocation().getZ()) {
			creature.teleport(loc); 
		}
	}

	public void SpawnMob(Dorf dorf, int count) {
		System.out.println("spawning: " + count);
		for (int i = 0; i < count; i++) {
			Entity entity = null; 
			Color color = null; 
			switch (dorf.getVolk()) {
			case FEE:
				color = Color.PURPLE; 
				entity = Bukkit.getWorld("world").spawnEntity(dorf.getOrt(), EntityType.SKELETON); 
				break;
			case KOBOLD:
				color = Color.GREEN; 
				entity = Bukkit.getWorld("world").spawnEntity(dorf.getOrt(), EntityType.STRAY); 
				break; 
			case WIKINGER: 
				entity = Bukkit.getWorld("world").spawnEntity(dorf.getOrt(), EntityType.ZOMBIE); 
				color = Color.ORANGE; 
				break; 
			default:
				break;
			}
			
			Creature creature = (Creature) entity; 
			creature.setCustomName(dorf.getVolk().getPrefix() + dorf.getName() + " Wache");
			creature.setCustomNameVisible(true);
			creature.setCanPickupItems(false);
			creature.setTarget(null);
			creature.setHealth(20);
			creature.setRemoveWhenFarAway(false);
			
			
			ItemStack helmet = new ItemStack(Material.LEATHER_HELMET); 
			LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta(); 
			meta.setColor(color);
			helmet.setItemMeta(meta); 
			
			ItemStack boots = new ItemStack(Material.LEATHER_BOOTS); 
			LeatherArmorMeta sekMeta = (LeatherArmorMeta) boots.getItemMeta(); 
			sekMeta.setColor(color);
			boots.setItemMeta(sekMeta); 
			
			creature.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
			creature.getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD));
			creature.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			creature.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			creature.getEquipment().setBoots(boots);
			creature.getEquipment().setHelmet(helmet);
		}
	}
	public void callMessage() {
		if(math == 0) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				player.sendMessage(Leerzeichen(("§9Kennst du unseren Discord? §cNein? §7dann trete doch jetzt bei!".length() /2)-5) + "§7-==+==-");
				player.sendMessage("§9Kennst du unseren Discord? §cNein? §7dann trete doch jetzt bei!");
				net.md_5.bungee.api.chat.TextComponent component = new net.md_5.bungee.api.chat.TextComponent();
				component.setText(Leerzeichen(("§9Kennst du unseren Discord? §cNein? §7dann trete doch jetzt bei!".length() /2)-5) + "§8[§9Click here§8]");
				component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/HAs2sSP"));
				player.spigot().sendMessage(component);
				player.sendMessage(Leerzeichen(("§9Kennst du unseren Discord? §cNein? §7dann trete doch jetzt bei!".length() /2)-5) + "§7-==+==-");
			}
			math = 1; 
		} else 
		if(math == 1) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				net.md_5.bungee.api.chat.TextComponent component = new net.md_5.bungee.api.chat.TextComponent();
				component.setText(Leerzeichen((Main.PREFIX + "§eSchau dir doch die coolen dinge auf diesen Server an:").length() /2) + "§8[§9Click here§8]");
				component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/3ph92a4"));
				
				player.sendMessage(Main.PREFIX + "§eSchau dir doch die coolen dinge auf diesen Server an:");
				player.spigot().sendMessage(component);
			}
			math = 2; 
		} else 
		if(math == 2) {
			String mitte = "§dDas Neuste vom neusten! ";
			Bukkit.broadcastMessage(Leerzeichen(5) + "§7-== " + mitte + " §7==-");
			String update = "§a+ §7Neuer Item Händler (§eSpezial Items§7)\n§e= §7Title Befehl gefixt!\n§cAb jetzt sind alle §40 Tick §cfarmen verboten!"; 
			
			Bukkit.broadcastMessage(update);
			Bukkit.broadcastMessage(Leerzeichen(5) + "§7-== " + mitte + " §7==-");
			math = 0; 
		}
	}
	private static String Leerzeichen(int i) {
		String string = ""; 
		for (int j = 0; j < i; j++) {
			string = string + " "; 
		}
		return string; 
	}
}