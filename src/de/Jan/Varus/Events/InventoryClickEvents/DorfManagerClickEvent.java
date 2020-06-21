package de.Jan.Varus.Events.InventoryClickEvents;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Events.Dorf.ClickOnDorfVillagerEvent;
import de.Jan.Varus.Objects.Dorf;
import de.Jan.Varus.Objects.User;

public class DorfManagerClickEvent implements Listener {
	private JavaPlugin plugin; 
	public DorfManagerClickEvent(JavaPlugin plugin) {
		this.plugin = plugin; 	
	}
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked(); 
		User user = new User(player, plugin); 
		if(event.getView().getTitle().contains("manager") && event.getView().getTitle().contains(user.getVolk().name().toLowerCase())) {
			if(event.getView().getTopInventory() == event.getClickedInventory()) {
				event.setCancelled(true);
				if(event.getCurrentItem() != null) {
					if(event.getCurrentItem().getItemMeta().hasDisplayName()) {
						String customName = event.getCurrentItem().getItemMeta().getDisplayName(); 
						Dorf dorf = user.getDorf(); 
						
						if(customName.equalsIgnoreCase("§dAutofarming")) {
							player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 5, 5);
						} else 
						if(customName.equalsIgnoreCase("§6Upgrade dein Dorf!")) {
							if(dorf.getLevel() == 5) {
								player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 5, 5);
								player.sendMessage(Main.PREFIX + "§cDein Dorf ist auf dem maximalem Level!");
								player.closeInventory(); 
							} else {
								if(player.getUniqueId().toString().equalsIgnoreCase(dorf.getAnführer().toString()) || player.getUniqueId().toString().equalsIgnoreCase(dorf.getCoAnführer().toString()) ) {
									if(user.getCoins() >= (50000 + ((dorf.getLevel()-1)*70000))) {
										user.setCoins(user.getCoins()-(50000 + ((dorf.getLevel()-1)*70000)));
										dorf.setLevel(dorf.getLevel() + 1);
										dorf.save();
										user.save();
										
										player.sendMessage(Main.PREFIX + "§aDein Dorf ist nun Level §e" + dorf.getLevel() + "§a!");
										player.closeInventory();
										player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
										
									} else {
										player.sendMessage(Main.PREFIX + "§cDu hast nicht genügend Coins!");
										player.closeInventory();
										player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BLOCK, 5, 5);
									}
								} else {
									player.sendMessage(Main.PREFIX + "§cDu musst der Anführer sein um diese Summe befahlen zu können!");
									player.closeInventory();
									player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BLOCK, 5, 5);
								}
							}
							
						} else 
						if(customName.equalsIgnoreCase("§cDefender")) {
							player.openInventory(getWars(dorf)); 
						}
					}
				}
			}
		}
	}
	public Inventory getWars(Dorf dorf) {
		Inventory inv = Bukkit.createInventory(null, 27, dorf.getVolk().getPrefix() + dorf.getVolk().name().toLowerCase() + " verteidigung"); 
		ItemStack stack = null; 
		switch (dorf.getVolk()) {
		case FEE:
			stack = new ItemStack(Material.PINK_STAINED_GLASS_PANE); 
			break;
		case KOBOLD: 
			stack = new ItemStack(Material.GREEN_STAINED_GLASS_PANE); 
			break; 
		case WIKINGER:
			stack = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE); 
			break; 
		case VAMPIR: 
			stack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE); 
			break; 
		case ZWERG:
			stack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
			break; 
		default:
			stack = new ItemStack(Material.GLASS_PANE); 
			break;
		}
		ClickOnDorfVillagerEvent.changeMeta("§6", stack, "§o");
		for (int i = 0; i < 27; i++) {
			if (inv.getItem(i) == null) {
				if(i != 10 && i != 13 && i !=  15 && i != 23 && i != 21) {
					inv.setItem(i, stack);
				}
			}
		}
		ItemStack mensch = new ItemStack(dorf.isMenschen() ? Material.RED_WOOL : Material.LIME_WOOL); 
		ClickOnDorfVillagerEvent.changeMeta("§fMenschen", mensch, dorf.isMenschen() ? "§cDie Wachen greifen Menschen an!" : "§aDie Wachen greifen Menschen nicht an!");
		
		ItemStack wikinger = new ItemStack(dorf.isWikinger() ? Material.RED_WOOL : Material.LIME_WOOL); 
		ClickOnDorfVillagerEvent.changeMeta("§6Wikinger", wikinger, dorf.isWikinger() ? "§cDie Wachen greifen Wikinger an!" : "§aDie Wachen greifen Wikinger nicht an!");
		
		ItemStack kobold = new ItemStack(dorf.isKobold() ? Material.RED_WOOL : Material.LIME_WOOL); 
		ClickOnDorfVillagerEvent.changeMeta("§2Kobold", kobold, dorf.isKobold() ? "§cDie Wachen greifen Kobold an!" : "§aDie Wachen greifen Kobold nicht an!");
		
		ItemStack fee = new ItemStack(dorf.isFee() ? Material.RED_WOOL : Material.LIME_WOOL); 
		ClickOnDorfVillagerEvent.changeMeta("§dFee", fee, dorf.isFee() ? "§cDie Wachen greifen Feen an!" : "§aDie Wachen greifen Feen nicht an!");

		ItemStack zwerge = new ItemStack(dorf.isZwerg() ? Material.RED_WOOL : Material.LIME_WOOL); 
		ClickOnDorfVillagerEvent.changeMeta("§8Zwerg", zwerge, dorf.isZwerg() ? "§cDie Wachen greifen Zwerge an!" : "§aDie Wachen greifen Zwerge nicht an!");
		
		ItemStack vampir = new ItemStack(dorf.isVampir() ? Material.RED_WOOL : Material.LIME_WOOL); 
		ClickOnDorfVillagerEvent.changeMeta("§0Vampir", vampir, dorf.isVampir() ? "§cDie Wachen greifen Vampire an!" : "§aDie Wachen greifen Vampire nicht an!");
		
		switch (dorf.getVolk()) {
		case FEE:
			inv.addItem(mensch, wikinger, kobold, vampir, zwerge); 
			break;
		case KOBOLD: 
			inv.addItem(mensch, wikinger, fee, vampir, zwerge); 
			break; 
		case WIKINGER: 
			inv.addItem(mensch, kobold, fee, vampir, zwerge); 
			break; 
		case ZWERG:
			inv.addItem(mensch, wikinger, kobold, fee, vampir); 
			break;
		case VAMPIR: 
			inv.addItem(mensch, wikinger, kobold, fee, zwerge); 
			break; 
		default:
			throw new NullPointerException("Kein Volk gefunden!"); 
		}
		return inv; 
	}
}
