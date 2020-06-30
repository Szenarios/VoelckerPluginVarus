package de.Jan.Varus.Events.InventoryClickEvents;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.connorlinfoot.titleapi.TitleAPI;

import de.Jan.Varus.Events.Effekte.FeeEffektEvent;
import de.Jan.Varus.Events.Effekte.KoboldEffektEvent;
import de.Jan.Varus.Events.Effekte.WikingerEffektEvent;
import de.Jan.Varus.Events.essentials.JoinEvent;
import de.Jan.Varus.Objects.Dorf;
import de.Jan.Varus.Objects.User;
import de.Jan.Varus.Objects.V�lker;


public class VolkInventoryClickEvent implements  Listener {
	private JavaPlugin plugin; 
	public VolkInventoryClickEvent(JavaPlugin plugin) {
		this.plugin = plugin; 	
	}
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		if(event.getView().getTitle().equalsIgnoreCase("�eW�hle ein Volk")) {
			if(event.getView().getTopInventory() == event.getClickedInventory()) {
				event.setCancelled(true);
				if(event.getCurrentItem() != null) {
					if(event.getCurrentItem().getItemMeta().hasDisplayName()) {
						User user = new User((Player) event.getWhoClicked(), plugin); 
						Player player = (Player) event.getWhoClicked(); 
						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�6Wikinger")) {
							user.setVolk(V�lker.WIKINGER);
							player.getInventory().addItem(WikingerEffektEvent.getItem(player)); 
						} else 
						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�2Kobold")) {
							player.getInventory().addItem(KoboldEffektEvent.getItem(player)); 
							user.setVolk(V�lker.KOBOLD);
						} else
						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�dFee")) {
							user.setVolk(V�lker.FEE);
							player.getInventory().addItem(FeeEffektEvent.getItem(player)); 
						} else 
						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�0Vampir")) {
							user.setVolk(V�lker.VAMPIR);
							// TODO 
						} else 
						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�8Zwerg")) {
							user.setVolk(V�lker.ZWERG);
							// TODO 
						}
						if(user.getDorf() != null) {
							Dorf dorf = user.getDorf(); 
							dorf.getPlayer().remove(player.getUniqueId().toString()); 
							if(dorf.getCoAnf�hrer() != null) {
								if(dorf.getCoAnf�hrer().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
									for(String players : dorf.getPlayer()) {
										OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(players); 
										if(!dorf.getAnf�hrer().toString().equalsIgnoreCase(offlinePlayer.getUniqueId().toString())) {
											dorf.setCoAnf�hrer(offlinePlayer.getUniqueId());
										}
									}
								}
							}
							dorf.save();
						}
						Dorf newDorf = new Dorf(user.getVolk().name().toLowerCase()); 
						if(newDorf.getVolk() == null) {
							newDorf.setVolk(user.getVolk());
						}
						user.setDorf(newDorf);
						user.save();
						event.getWhoClicked().closeInventory();
						TitleAPI.sendFullTitle((Player) event.getWhoClicked(), 10, 20, 15, "�eDu bist nun ein" + (user.getVolk() == V�lker.FEE ? "e" :""), event.getCurrentItem().getItemMeta().getDisplayName());
						JoinEvent.setPrefix((Player) event.getWhoClicked(), plugin);
						ckeckVolk((Player) event.getWhoClicked(), user);
					}
				}
			}
		}
	}
	public static void ckeckVolk (Player player, User user) {
		Dorf dorf = user.getDorf(); 
		List<String> leute = dorf.getPlayer();
		if (leute == null) {
			leute = new ArrayList<String>();
		}
		leute.add(player.getUniqueId().toString());
		dorf.setPlayer(leute);
		
		if (dorf.getAnf�hrer() == null) {
			dorf.setAnf�hrer(player.getUniqueId());
		} else if (dorf.getCoAnf�hrer() == null) {
			dorf.setCoAnf�hrer(player.getUniqueId());
		}
		dorf.save();
	}
}