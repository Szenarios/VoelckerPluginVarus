package de.Jan.Varus.Events.pets;

import java.util.ArrayList;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.Jan.Varus.Main;
import de.Jan.Varus.Objects.Pet;
import de.Jan.Varus.Objects.Tamer;


public class buyEntityEvent implements Listener {
	JavaPlugin plugin; 
	ArrayList<EntityType> zähmbar = new ArrayList<>(); 
	public buyEntityEvent(JavaPlugin plugin) {
		fillList();
		this.plugin = plugin; 
	}
	@EventHandler
	public void onEntityClickEvent(PlayerInteractEntityEvent event) {
		if(zähmbar.contains(event.getRightClicked().getType())) {
			Tamer tamer = new Tamer(event.getPlayer()); 
			Pet pet = new Pet(event.getRightClicked()); 
			
			if(pet.getOwner() != null) {
				if (pet.getOwner().equalsIgnoreCase(event.getPlayer().getUniqueId().toString())) {
					event.getPlayer().openInventory(OwnerInventroyClick.getInventory(event.getRightClicked())); 
				} 
			} else {
				if(tamer.getTames() < tamer.getMaxTames()) {
					System.out.println(pet.getOwner());
					if(pet.getOwner() == null) {
						event.getPlayer().openInventory(TameInventroyClick.getInventory(event.getRightClicked())); 
					} 
				} else {
					event.getPlayer().sendMessage(Main.PREFIX + "§cDu hast bereits §e" + tamer.getTames()+ "§7/§e" + tamer.getMaxTames() + " §cHaustiere! " + (tamer.getMaxTames() != 7 ? "§eDu kannst noch weitere Tiere Zämen in dem du dieses Skillst! ":""));
				}
			}
			
		}
		
	}
	public void fillList() {
		zähmbar.add(EntityType.HORSE); 
		zähmbar.add(EntityType.FOX);
		zähmbar.add(EntityType.ENDERMITE); 
		zähmbar.add(EntityType.DONKEY); 
		zähmbar.add(EntityType.MUSHROOM_COW); 
		zähmbar.add(EntityType.COW); 
		zähmbar.add(EntityType.BEE);
		zähmbar.add(EntityType.BAT);
		zähmbar.add(EntityType.CHICKEN);
		zähmbar.add(EntityType.RABBIT); 
		zähmbar.add(EntityType.POLAR_BEAR); 
		zähmbar.add(EntityType.PARROT); 
		zähmbar.add(EntityType.PANDA);
		zähmbar.add(EntityType.OCELOT);
		zähmbar.add(EntityType.MULE);
		zähmbar.add(EntityType.LLAMA); 
		zähmbar.add(EntityType.ZOMBIE_HORSE);
		zähmbar.add(EntityType.WOLF);
		zähmbar.add(EntityType.TRADER_LLAMA); 
		zähmbar.add(EntityType.SKELETON_HORSE); 
		zähmbar.add(EntityType.SILVERFISH); 
		zähmbar.add(EntityType.SHEEP); 
	}
}
