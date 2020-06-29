package de.Jan.Varus;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Commands.changeDorfPositionCommand;
import de.Jan.Varus.Commands.changeNameCommand;
import de.Jan.Varus.Commands.chatCommand;
import de.Jan.Varus.Commands.confirmedSetDorfPositionCommand;
import de.Jan.Varus.Commands.dorfCommand;
import de.Jan.Varus.Commands.findpetCommand;
import de.Jan.Varus.Commands.getCoinsCommand;
import de.Jan.Varus.Commands.getItemCommand;
import de.Jan.Varus.Commands.homeCommand;
import de.Jan.Varus.Commands.marryCommand;
import de.Jan.Varus.Commands.marryacceptCommand;
import de.Jan.Varus.Commands.marrydenyCommand;
import de.Jan.Varus.Commands.remitCoinsCommand;
import de.Jan.Varus.Commands.sethomeCommand;
import de.Jan.Varus.Commands.spawnCommand;
import de.Jan.Varus.Commands.titleCommand;
import de.Jan.Varus.Commands.volkCommand;
import de.Jan.Varus.Events.CreeperExplodeEvent;
import de.Jan.Varus.Events.EntityDieEvent;
import de.Jan.Varus.Events.ErfolgeEvent;
import de.Jan.Varus.Events.FeeEffektEvent;
import de.Jan.Varus.Events.JoinEvent;
import de.Jan.Varus.Events.KoboldEffektEvent;
import de.Jan.Varus.Events.KoboldUnsichtbarkeitEvent;
import de.Jan.Varus.Events.PlayerDieEvent;
import de.Jan.Varus.Events.ProtectManagerEvent;
import de.Jan.Varus.Events.SpawnBlockBreakEvent;
import de.Jan.Varus.Events.VampirEffektEvent;
import de.Jan.Varus.Events.WikingerEffektEvent;
import de.Jan.Varus.Events.WikingerStärkeEvent;
import de.Jan.Varus.Events.chatEvent;
import de.Jan.Varus.Events.checkVillagerCommand;
import de.Jan.Varus.Events.leaveEvent;
import de.Jan.Varus.Events.pingEvent;
import de.Jan.Varus.Events.Dorf.ClickOnDorfVillagerEvent;
import de.Jan.Varus.Events.Dorf.DorfBlockEvent;
import de.Jan.Varus.Events.InventoryClickEvents.AntiAbilityAbuseEvent;
import de.Jan.Varus.Events.InventoryClickEvents.AntiAnvilAbuseEvent;
import de.Jan.Varus.Events.InventoryClickEvents.DorfManagerClickEvent;
import de.Jan.Varus.Events.InventoryClickEvents.ErfolgeInventoryClickEvent;
import de.Jan.Varus.Events.InventoryClickEvents.NewDorfInvClickEvent;
import de.Jan.Varus.Events.InventoryClickEvents.VolkInventoryClickEvent;
import de.Jan.Varus.Events.InventoryClickEvents.WarsManagerInventoryClickEvent;
import de.Jan.Varus.Events.SpezialItem.HolzfällerAxtEvent;
import de.Jan.Varus.Events.SpezialItem.MegaPickeEvent;
import de.Jan.Varus.Events.SpezialItem.SchwerDesHänkers;
import de.Jan.Varus.Events.SpezialItem.axtDesDonnersEvent;
import de.Jan.Varus.Events.SpezialItem.stabDesWindesEvent;
import de.Jan.Varus.Events.Wachen.WacheTargetEntity;
import de.Jan.Varus.Events.pets.OwnerInventroyClick;
import de.Jan.Varus.Events.pets.TameInventroyClick;
import de.Jan.Varus.Events.pets.antiPetDamageEvent;
import de.Jan.Varus.Events.pets.buyEntityEvent;
import de.Jan.Varus.Events.pets.playerMoveEvent;
import de.Jan.Varus.Objects.Dorf;
import de.Jan.Varus.Objects.Völker;

public class Main extends JavaPlugin {
	public static String PREFIX = "§9VARUS §8| §7"; 
	public static String NOPERMISSIONS = PREFIX + "§cDu Hast keine Berechtigung diesen Befehl auszuführen!"; 
	private static File dorfFile = new File("plugins\\VarusVoelker\\dörfer.yml"); 
	private static File chestlogFile = new File("plugins\\VarusVoelker\\chestlog.yml");
	private static File petFile = new File("plugins\\VarusVoelker\\pet.yml"); 
	MobsScanner scanner; 
	
	public static FileConfiguration pets; 
	public static FileConfiguration dorf; 
	public static FileConfiguration chestlog; 
	@Override
	public void onEnable() {
		createCustomConfig();
		Bukkit.getPluginManager().registerEvents(new JoinEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new leaveEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new chatEvent(), this);
		Bukkit.getPluginManager().registerEvents(new pingEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new VolkInventoryClickEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new WacheTargetEntity(this), this);
		Bukkit.getPluginManager().registerEvents(new DorfBlockEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new FeeEffektEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new KoboldEffektEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new KoboldUnsichtbarkeitEvent(), this);
		Bukkit.getPluginManager().registerEvents(new WikingerEffektEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new WikingerStärkeEvent(), this);
		Bukkit.getPluginManager().registerEvents(new ClickOnDorfVillagerEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new SpawnBlockBreakEvent(), this);
		Bukkit.getPluginManager().registerEvents(new DorfManagerClickEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new WarsManagerInventoryClickEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new ProtectManagerEvent(), this);
		Bukkit.getPluginManager().registerEvents(new ErfolgeEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new ErfolgeInventoryClickEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new EntityDieEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDieEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new AntiAbilityAbuseEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new NewDorfInvClickEvent(this), this);
		
		Bukkit.getPluginManager().registerEvents(new stabDesWindesEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new axtDesDonnersEvent(), this);
		Bukkit.getPluginManager().registerEvents(new HolzfällerAxtEvent(), this);
		Bukkit.getPluginManager().registerEvents(new MegaPickeEvent(), this);
		Bukkit.getPluginManager().registerEvents(new SchwerDesHänkers(), this);
		Bukkit.getPluginManager().registerEvents(new AntiAnvilAbuseEvent(), this);
		
		Bukkit.getPluginManager().registerEvents(new CreeperExplodeEvent(), this);
		
		Bukkit.getPluginManager().registerEvents(new VampirEffektEvent(this), this);
		
		Bukkit.getPluginManager().registerEvents(new buyEntityEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new antiPetDamageEvent(), this);
		Bukkit.getPluginManager().registerEvents(new playerMoveEvent(), this);
		Bukkit.getPluginManager().registerEvents(new TameInventroyClick(this), this);
		Bukkit.getPluginManager().registerEvents(new OwnerInventroyClick(), this);

		
		Bukkit.getPluginCommand("spawn").setExecutor(new spawnCommand(this));
		Bukkit.getPluginCommand("home").setExecutor(new homeCommand(this));
		Bukkit.getPluginCommand("sethome").setExecutor(new sethomeCommand(this));
		Bukkit.getPluginCommand("volk").setExecutor(new volkCommand(this));
		Bukkit.getPluginCommand("changedorfposition").setExecutor(new changeDorfPositionCommand(this));
		Bukkit.getPluginCommand("confirmedsetdorfposition").setExecutor(new confirmedSetDorfPositionCommand(this));
		Bukkit.getPluginCommand("getcoins").setExecutor(new getCoinsCommand(this));
		Bukkit.getPluginCommand("remitcoins").setExecutor(new remitCoinsCommand(this));
		Bukkit.getPluginCommand("title").setExecutor(new titleCommand(this));
		Bukkit.getPluginCommand("chat").setExecutor(new chatCommand(this));
		Bukkit.getPluginCommand("getitem").setExecutor(new getItemCommand(this));
		
		Bukkit.getPluginCommand("marry").setExecutor(new marryCommand(this));
		Bukkit.getPluginCommand("marryaccept").setExecutor(new marryacceptCommand(this));
		Bukkit.getPluginCommand("marrydeny").setExecutor(new marrydenyCommand(this));
		
		Bukkit.getPluginCommand("dorf").setExecutor(new dorfCommand(this));
		Bukkit.getPluginCommand("checkvillager").setExecutor(new checkVillagerCommand());
		Bukkit.getPluginCommand("changename").setExecutor(new changeNameCommand());
		findpetCommand findpetCommand = new findpetCommand(); 
		Bukkit.getPluginCommand("findpet").setExecutor(findpetCommand);
		Bukkit.getPluginManager().registerEvents(findpetCommand, this);
		
		scanner = new MobsScanner(this); 
		scanner.go();
		
		CheckingVillager();
		
		Bukkit.getConsoleSender().sendMessage(PREFIX + "§aPlugin ist nun geladen!");  
	}
	@Override
	public void onDisable() {
		scanner.shedular = 0; 
		Bukkit.getConsoleSender().sendMessage(PREFIX + "§cPlugin wurde erfolgreich geschlossen!");  
	};
	private void createCustomConfig() {
		if(!dorfFile.exists()) {
			dorfFile.getParentFile().mkdirs(); 
			try {
				dorfFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			Bukkit.getConsoleSender().sendMessage(PREFIX + "§aDörfer YML wurde erfolgreich erstellt!"); 
		}
		if(!chestlogFile.exists()) {
			chestlogFile.getParentFile().mkdirs(); 
			try {
				chestlogFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			Bukkit.getConsoleSender().sendMessage(PREFIX + "§aChestlog YML wurde erfolgreich erstellt!"); 
		}
		if(!petFile.exists()) {
			petFile.getParentFile().mkdirs(); 
			try {
				petFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			Bukkit.getConsoleSender().sendMessage(PREFIX + "§aPets YML wurde erfolgreich erstellt!"); 
		}
		dorf = new YamlConfiguration(); 
		chestlog = new YamlConfiguration(); 
		pets = new YamlConfiguration(); 
		try {
			dorf.load(dorfFile);
			Bukkit.getConsoleSender().sendMessage(PREFIX + "§aDörfer YML wurde erfolgreich geladen!"); 
			chestlog.load(chestlogFile);
			Bukkit.getConsoleSender().sendMessage(PREFIX + "§aChestlog YML wurde erfolgreich erstellt!"); 
			pets.load(petFile);
			Bukkit.getConsoleSender().sendMessage(PREFIX + "§aPets YML wurde erfolgreich erstellt!"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void SaveDrof() {
		try {
			dorf.save(dorfFile);	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void SaveChestLog() { 
		try {
			chestlog.save(chestlogFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void SavePets() { 
		try {
			pets.save(petFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void CheckingVillager() { 
		for(Entity entity : Bukkit.getWorld("world").getEntities()) {
			if(entity.getType() == EntityType.VILLAGER) {
				if(entity.getCustomName() != null) {
					if(entity.getCustomName().toLowerCase().contains("manager")) {
						String customName = entity.getCustomName(); 
						Dorf dorf = new Dorf(!customName.startsWith("§") ? customName.toLowerCase().split(" ")[0]: customName.toLowerCase().substring(2).split(" ")[0]); 
						if((int)entity.getLocation().getX() != (int)dorf.getOrt().getX() && (int)entity.getLocation().getY() != (int)dorf.getOrt().getY() && (int)entity.getLocation().getZ() != (int)dorf.getOrt().getZ()) {
							entity.remove();
						} else 
						if(entity.getNearbyEntities(2, 2, 2).size() >= 2) {
							entity.remove();
						}
					}
				}
			}
		}
		
		for(String key : dorf.getKeys(false)) { 
			if(dorf.get(key + ".ort") != null) {
				Location ort = dorf.getLocation(key + ".ort"); 
				ort.setPitch(0);
				ort.setYaw(0);
				
				
				boolean isAliv = false; 
				Völker volk = Völker.valueOf(dorf.getString(key + ".volk").toUpperCase()); 
				for (Entity entity : ort.getWorld().getNearbyEntities(ort, 3, 3, 3)) {
					if (entity.getType() == EntityType.VILLAGER) {
						if (entity.getCustomName() != null) {
							if (entity.getCustomName().toLowerCase().contains(volk.getPrefix() + key.toUpperCase() + " Manager".toLowerCase())) {
								isAliv = true;
							}
						} 
					}
				}
				if(!isAliv) { 
					System.out.println("spawning");
					Entity entity = Bukkit.getWorld("world").spawnEntity(ort, EntityType.VILLAGER); 
					Villager villager = (Villager) entity; 
					
					villager.setCustomNameVisible(true);
					villager.setCustomName(volk.getPrefix() + key.toUpperCase() + " Manager");
					villager.setRemoveWhenFarAway(false);
					
					villager.setAI(false);
					villager.setCanPickupItems(false);
					villager.setInvulnerable(true);
					switch (volk) {
					case WIKINGER:
						villager.setVillagerType(org.bukkit.entity.Villager.Type.JUNGLE);
						break;
					case KOBOLD: 
						villager.setVillagerType(org.bukkit.entity.Villager.Type.SWAMP);
						break; 
					case FEE: 
						villager.setVillagerType(org.bukkit.entity.Villager.Type.SAVANNA);
						break; 
					case VAMPIR:
						villager.setVillagerType(org.bukkit.entity.Villager.Type.TAIGA);
						break; 
					case ZWERG: 
						villager.setVillagerType(org.bukkit.entity.Villager.Type.SNOW);
						break; 
					default:
						villager.setVillagerType(org.bukkit.entity.Villager.Type.PLAINS);
						break;
					}
				}
			}
		}
	}
	public static int returnRDM(int max, int min) {
		return (int) (Math.random() * (max - min + 1) + min);
	}
	public static void ChangeMeta(ItemStack item, String name, String...strings) { 
		ItemMeta meta = item.getItemMeta(); 
		meta.setDisplayName(name);
		ArrayList<String> list = new ArrayList<>(); 
		for (int i = 0; i < strings.length; i++) {
			list.add(strings[i]); 
		}
		meta.setLore(list);
		item.setItemMeta(meta); 
	}
}
