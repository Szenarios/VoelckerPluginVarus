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
	ArrayList<EntityType> z�hmbar = new ArrayList<>(); 
	public buyEntityEvent(JavaPlugin plugin) {
		fillList();
		this.plugin = plugin; 
	}
	@EventHandler
	public void onEntityClickEvent(PlayerInteractEntityEvent event) {
		if(z�hmbar.contains(event.getRightClicked().getType())) {
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
					event.getPlayer().sendMessage(Main.PREFIX + "�cDu hast bereits �e" + tamer.getTames()+ "�7/�e" + tamer.getMaxTames() + " �cHaustiere! " + (tamer.getMaxTames() != 7 ? "�eDu kannst noch weitere Tiere Z�men in dem du dieses Skillst! ":""));
				}
			}
			
		}
		
	}
	public void fillList() {
		z�hmbar.add(EntityType.HORSE); 
		z�hmbar.add(EntityType.FOX);
		z�hmbar.add(EntityType.ENDERMITE); 
		z�hmbar.add(EntityType.DONKEY); 
		z�hmbar.add(EntityType.MUSHROOM_COW); 
		z�hmbar.add(EntityType.COW); 
		z�hmbar.add(EntityType.BEE);
		z�hmbar.add(EntityType.BAT);
		z�hmbar.add(EntityType.CHICKEN);
		z�hmbar.add(EntityType.RABBIT); 
		z�hmbar.add(EntityType.POLAR_BEAR); 
		z�hmbar.add(EntityType.PARROT); 
		z�hmbar.add(EntityType.PANDA);
		z�hmbar.add(EntityType.OCELOT);
		z�hmbar.add(EntityType.MULE);
		z�hmbar.add(EntityType.LLAMA); 
		z�hmbar.add(EntityType.ZOMBIE_HORSE);
		z�hmbar.add(EntityType.WOLF);
		z�hmbar.add(EntityType.TRADER_LLAMA); 
		z�hmbar.add(EntityType.SKELETON_HORSE); 
		z�hmbar.add(EntityType.SILVERFISH); 
		z�hmbar.add(EntityType.SHEEP); 
	}
}
